package ac.liv.csc.comp201;

import java.util.Arrays;

import ac.liv.csc.comp201.control.CoinControl;
import ac.liv.csc.comp201.control.DrinkControl;
import ac.liv.csc.comp201.control.DrinkMaking;
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

		CoinControl handleCoin = new CoinControl(machine);
		HotWaterControl handleWater = new HotWaterControl(machine);
		DrinkMaking drinkMaking = new DrinkMaking(machine);
		DrinkControl drinkControl = new DrinkControl(machine);
		
		Cup cup = machine.getCup();
		
		if(temperatureControl==true) {
			handleWater.controlTemperature();// 控制温度
			handleWater.cannotControlTemperature();
		}

		int keyCode = machine.getKeyPad().getNextKeyCode();
		if (lockKeypad == false) {

			// System.out.println("Key is "+keyCode);

			if (keyCode != -1) {
				if (keyCode == RETURN_CHANGE_BUTTON) {
					handleCoin.returnChange();
//					machine.getCoinHandler().getCoinTray();
//					machine.getCoinHandler().clearCoinTry();
				} else {
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
					if (keyCodeCount >= codeLen) { // we have got a key code of target length
						for (int idx = 0; idx < codeLen; idx++) {
							System.out.println("Code is " + orderCode[idx]);
							inputBuffer.append(orderCode[idx]);
						}
						keypadInput = inputBuffer.toString();
						System.out.println(keypadInput);

						// TO do check code is valid 101 102 etc, check balance
						// check ingredient level if all ok then make a drink
						
						// valid input
						boolean validInput = drinkControl.validInput(orderCode,keypadInput);
						//valid balance
						boolean validBalance = drinkControl.validBalance(orderCode, keypadInput);
						//valid ingredients
						//{"Coffee","Milk (powder)","Sugar","Chocolate","Temperature","Cup litres"};
						boolean validIngredients = drinkControl.validIngredients(orderCode, keypadInput);
						
						if(validInput) {
							System.out.println("validInput");
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

						inputBuffer.delete(0, inputBuffer.length());
						keypadInput = "";
						keyCodeCount = 0; // used up this code
					}
				}
			}
		}
		
//		code to make the drink
		if (cup != null) {
			
			System.out.println("cup!"+cup.getCoffeeGrams()+" "+cup.getMilkGrams()+" "+cup.getSugarGrams()+" "+cup.getSugarGrams()+" "+cup.getChocolateGrams()+" "+cup.getWaterLevelLitres());
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
			
			if(cup.getWaterLevelLitres()==0) {
				
				machine.getCoinHandler().lockCoinHandler();	
				
				if(machine.getWaterHeater().getTemperatureDegreesC()<=ingredientsTemperature[4]) {
					temperatureControl = false;
					machine.getWaterHeater().setHeaterOn();
				}else {
					machine.getWaterHeater().setHotWaterTap(true);
				}
			}
//			if(cup.getWaterLevelLitres()<ingredientsTemperature[5]) 
//				if(cup.getTemperatureInC()>84) {
//					machine.getWaterHeater().setColdWaterTap(true);
//				}
//			}
			if(cup.getWaterLevelLitres()>0&&cup.getWaterLevelLitres()<ingredientsTemperature[5]*0.2) {
				machine.getWaterHeater().setHotWaterTap(true);
				if(machine.getWaterHeater().getTemperatureDegreesC()>=ingredientsTemperature[4]) {
					machine.getWaterHeater().setHotWaterTap(true);
				}else {
					machine.getWaterHeater().setHotWaterTap(false);
				}
			}
			if(cup.getWaterLevelLitres()>ingredientsTemperature[5]*0.2&&cup.getWaterLevelLitres()<ingredientsTemperature[5]) {
				if(cup.getTemperatureInC()>=80) {
					//machine.getWaterHeater().setHeaterOff();
					machine.getWaterHeater().setColdWaterTap(true);
				}else {
					machine.getWaterHeater().setHeaterOn();
					machine.getWaterHeater().setColdWaterTap(false);
					machine.getWaterHeater().setHotWaterTap(true);
				}
			}

			if(cup.getWaterLevelLitres()>=ingredientsTemperature[5]) {
				machine.getWaterHeater().setHotWaterTap(false);
				machine.getWaterHeater().setColdWaterTap(false);
				machine.getCoinHandler().unlockCoinHandler();
				lockKeypad = false;
				temperatureControl = true;
			}
		}
		
		String coinCode = machine.getCoinHandler().getCoinKeyCode();
		if (coinCode != null) {
			System.out.println("Got coin code .." + coinCode);
			machine.getDisplay().setTextString("Got coin code .." + coinCode);

			currentCredit = handleCoin.insertedCoin(coinCode, currentCredit);
			machine.getDisplay().setTextString("Now coin" + (currentCredit / 100));
			// handleCoin.CoinAmount(coinCode);
			handleCoin.printCoinLevel();
			System.out.println(machine.getBalance());
		}
	}
	

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
