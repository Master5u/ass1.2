package ac.liv.csc.comp201;
import ac.liv.csc.comp201.control.CoinControl;
import ac.liv.csc.comp201.control.DrinkControl;
import ac.liv.csc.comp201.control.HotWaterControl;
import ac.liv.csc.comp201.model.IMachine;
import ac.liv.csc.comp201.model.IMachineController;
import ac.liv.csc.comp201.simulate.Cup;
import ac.liv.csc.comp201.simulate.Hoppers;

public class MachineController extends Thread implements IMachineController {

	private boolean running = true;

	private IMachine machine;

	private static final String version = "1.22";

	private double currentCredit = 0;

	private StringBuffer inputBuffer = new StringBuffer();

	private String keypadInput = "";

	private static final int MAX_CODE_LEN = 4;

	private static final int MIN_CODE_LEN = 3;

	private int orderCode[] = new int[MAX_CODE_LEN];

	private int keyCodeCount = 0;

	private int MEDIUM_CUP_PREFIX = 5;

	private int LARGE_CUP_PREFIX = 6;

	private int RETURN_CHANGE_BUTTON = 9;

	private boolean lockKeypad = false;
	
	private boolean temperatureControl = true;
	
	private double [] ingredientsTemperature = {0,0,0,0,0,0}; 

	public void startController(IMachine machine) {
		this.machine = machine; // Machine that is being controlled

		machine.getKeyPad().setCaption(0, "0");
		machine.getKeyPad().setCaption(1, "1");
		machine.getKeyPad().setCaption(2, "2");
		machine.getKeyPad().setCaption(3, "3");
		machine.getKeyPad().setCaption(4, "4");
		machine.getKeyPad().setCaption(5, "5");
		machine.getKeyPad().setCaption(6, "6");
		machine.getKeyPad().setCaption(7, "7");
		machine.getKeyPad().setCaption(8, "8");
		machine.getKeyPad().setCaption(9, "return changes");// test button
		super.start();
	}

	public MachineController() {

	}

	private synchronized void runStateMachine() {
		
		//initialize three classes which will be used in behind
		CoinControl handleCoin = new CoinControl(machine);
		HotWaterControl handleWater = new HotWaterControl(machine);
		DrinkControl drinkControl = new DrinkControl(machine);

//..........................................................................................
/*This block checks cups and keeps temperature*/
		
		//check and get cups
		Cup cup = machine.getCup();
		
		//control temperature not too high or too low
		if(temperatureControl==true) {
			handleWater.controlTemperature();
			handleWater.cannotControlTemperature();
		}
//..........................................................................................
/*In this block, first, it controls the keypad. Then, it checks the keypad input, ingredients and balance.
 If all conditions are satisfied, it will put cups. */
		
		//get keypad input
		int keyCode = machine.getKeyPad().getNextKeyCode();
		if (lockKeypad == false) {
			if (keyCode != -1) {
				if (keyCode == RETURN_CHANGE_BUTTON) {
					//get changes and display
					handleCoin.returnChange();
					double displayChange = machine.getBalance();
					double displayC = displayChange/100;
					machine.getDisplay().setTextString("your balance are "+displayC+" Pound");
				} else {
					//record input number
					if (keyCodeCount < MAX_CODE_LEN) { // only store up maximum code length
						orderCode[keyCodeCount++] = keyCode;
					}
					int codeLen = MIN_CODE_LEN;
					if (orderCode[0] == LARGE_CUP_PREFIX) { // codes with prefix are a little longer
						codeLen = MAX_CODE_LEN;
					}
					if (orderCode[0] == MEDIUM_CUP_PREFIX) { // codes with prefix are a little longer
						codeLen = MAX_CODE_LEN;
					}
					
					//display inputed number on screen
					String displayCoin = "";
					for(int i=0; i<keyCodeCount; i++) {
						String nowCoin = String.valueOf(orderCode[i]);
						displayCoin = displayCoin+nowCoin;
					}
					machine.getDisplay().setTextString("Your input: "+displayCoin);
					
					//save the inputed numbers
					if (keyCodeCount >= codeLen) { // we have got a key code of target length
						for (int idx = 0; idx < codeLen; idx++) {
							System.out.println("Code is " + orderCode[idx]);
							inputBuffer.append(orderCode[idx]);
						}
						keypadInput = inputBuffer.toString();
						System.out.println(keypadInput);
						
						// valid input
						boolean validInput = drinkControl.validInput(orderCode,keypadInput);
						//valid balance
						boolean validBalance = drinkControl.validBalance(orderCode, keypadInput);
						//valid ingredients
						//{"Coffee","Milk (powder)","Sugar","Chocolate","Temperature","Cup litres"};
						boolean validIngredients = drinkControl.validIngredients(orderCode, keypadInput);
						//check valid input
						if(validInput) {
							System.out.println("validInput");
							// check balance
							if(validBalance) {
								System.out.println("balance enough");
								//deduct balance
								drinkControl.deductBalance(orderCode, keypadInput);
								//get ingredients amount and initial temperature
								ingredientsTemperature = drinkControl.getIngredientsTemperature(orderCode, keypadInput);
								//put cup
								if(validIngredients) {
									System.out.println("ingredients enough");
									if(orderCode[0] == 1|orderCode[0] == 2|orderCode[0] == 3) {
										machine.vendCup(Cup.SMALL_CUP);
									}
									if(orderCode[0] == MEDIUM_CUP_PREFIX) {
										machine.vendCup(Cup.MEDIUM_CUP);
									}
									if(orderCode[0] == LARGE_CUP_PREFIX) {
										machine.vendCup(Cup.LARGE_CUP);
									}
									 lockKeypad = true;
									 System.out.println(lockKeypad);
									 machine.getDisplay().setTextString("start to make drink");
								}else {
									machine.getDisplay().setTextString("Ingredients not enough");
								}
							}else {
								machine.getDisplay().setTextString("Balance not enough");
							}
						}else {
							machine.getDisplay().setTextString("invalid input");
						}
						// clear buffer
						inputBuffer.delete(0, inputBuffer.length());
						keypadInput = "";
						keyCodeCount = 0;
					}
				}
			}
		}
//..........................................................................................
/*This block is about making drinks. It will put ingredients at first. 
  1,When cup's litres is 0, lock keypad and coin control, open water heater 
  until the temperature up to drink initial temperature.
  2,When cup's litres >0 and <20%, put hot water in initial temperature.
  3,When cup's litres >20% and <100%, put water and keep drink's temperature between 76 and 84 degree.
  4,When cup's litres >=100%, turn off water tap, unlock keypad and coin handler.
  */
		if (cup != null) {
			System.out.println("Cup "+"Coffee:"+cup.getCoffeeGrams()+" Milk:"+cup.getMilkGrams()+" Sugar:"+cup.getSugarGrams()+" Chocolate:"+cup.getChocolateGrams()+" Water:"+cup.getWaterLevelLitres());
			//put ingredients
			if(cup.getCoffeeGrams()<ingredientsTemperature[0]) {
				machine.getHoppers().setHopperOn(Hoppers.COFFEE);
			}else {
				machine.getHoppers().setHopperOff(Hoppers.COFFEE);
			}
			if(cup.getMilkGrams()<ingredientsTemperature[1]) {
				machine.getHoppers().setHopperOn(Hoppers.MILK);
			}else {
				machine.getHoppers().setHopperOff(Hoppers.MILK);
			}
			if(cup.getSugarGrams()<ingredientsTemperature[2]) {
				machine.getHoppers().setHopperOn(Hoppers.SUGAR);
			}else {
				machine.getHoppers().setHopperOff(Hoppers.SUGAR);
			}
			if(cup.getChocolateGrams()<ingredientsTemperature[3]) {
				machine.getHoppers().setHopperOn(Hoppers.CHOCOLATE);
			}else {
				machine.getHoppers().setHopperOff(Hoppers.CHOCOLATE);
			}
			
			//When cup's litres is 0, lock keypad and coin control, open water heater until the temperature up to drink initial temperature.
			if(cup.getWaterLevelLitres()==0) {
				machine.getCoinHandler().lockCoinHandler();	
				if(machine.getWaterHeater().getTemperatureDegreesC()<=ingredientsTemperature[4]) {
					temperatureControl = false;
					machine.getWaterHeater().setHeaterOn();
				}else {
					machine.getWaterHeater().setHotWaterTap(true);
				}
			}

			//When cup's litres >0 and <20%, put hot water in initial temperature.
			if(cup.getWaterLevelLitres()>0&&cup.getWaterLevelLitres()<ingredientsTemperature[5]*0.2) {
				machine.getWaterHeater().setHotWaterTap(true);
				if(machine.getWaterHeater().getTemperatureDegreesC()>=ingredientsTemperature[4]) {
					machine.getWaterHeater().setHotWaterTap(true);
				}else {
					machine.getWaterHeater().setHotWaterTap(false);
				}
			}
			
			//When cup's litres >20% and <100%, put water and keep drink's temperature between 76 and 84 degree.
			if(cup.getWaterLevelLitres()>ingredientsTemperature[5]*0.2&&cup.getWaterLevelLitres()<ingredientsTemperature[5]) {
				if(cup.getTemperatureInC()>=80) {
					machine.getWaterHeater().setColdWaterTap(true);
				}else {
					machine.getWaterHeater().setHeaterOn();
					machine.getWaterHeater().setColdWaterTap(false);
					machine.getWaterHeater().setHotWaterTap(true);
				}
			}
			
			//When cup's litres >=100%, turn off water tap, unlock keypad and coin handler.
			if(cup.getWaterLevelLitres()>=ingredientsTemperature[5]) {
				machine.getWaterHeater().setHotWaterTap(false);
				machine.getWaterHeater().setColdWaterTap(false);
				machine.getCoinHandler().unlockCoinHandler();
				lockKeypad = false;
				temperatureControl = true;
			}
		}
//..........................................................................................
/*This block shows the coin handle */
		String coinCode = machine.getCoinHandler().getCoinKeyCode();
		if (coinCode != null) {
			System.out.println("Got coin code .." + coinCode);
			machine.getDisplay().setTextString("Got coin code .." + coinCode);
			currentCredit = handleCoin.insertedCoin(coinCode, currentCredit);
			machine.getDisplay().setTextString("Now coin" + (currentCredit / 100)+" Pound");
			handleCoin.printCoinLevel();
			System.out.println(machine.getBalance());
		}
	}
//..........................................................................................

	public void run() {
		// Controlling thread for coffee machine
		int counter = 1;
		while (running) {
			// machine.getDisplay().setTextString("Running drink machine controller
			// "+counter);
			counter++;
			try {
				Thread.sleep(10); // Set this delay time to lower rate if you want to increase the rate
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			runStateMachine();
		}
	}

	public void updateController() {
		// runStateMachine();
	}

	public void stopController() {
		running = false;
	}

}
