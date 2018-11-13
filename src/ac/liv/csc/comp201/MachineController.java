package ac.liv.csc.comp201;

import java.util.Arrays;

import ac.liv.csc.comp201.control.CoinControl;
import ac.liv.csc.comp201.control.DrinkMaking;
import ac.liv.csc.comp201.control.HotWaterControl;
import ac.liv.csc.comp201.model.IMachine;
import ac.liv.csc.comp201.model.IMachineController;
import ac.liv.csc.comp201.simulate.Cup;
import ac.liv.csc.comp201.simulate.Hoppers;

public class MachineController  extends Thread implements IMachineController {
	
	private boolean running=true;
	
	private IMachine machine;
	
	private static final String version="1.22";
	
	private double currentCredit=0;
	
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
	
	private boolean gotCup=false;
	
	public void startController(IMachine machine) {
		this.machine=machine;				// Machine that is being controlled
//		machine.getKeyPad().setCaption(0,"Cup");
//		machine.getKeyPad().setCaption(1,"Water heater on");
//		machine.getKeyPad().setCaption(2,"Water heater off");		
//		machine.getKeyPad().setCaption(3,"Hot Water On");
//		machine.getKeyPad().setCaption(4,"Hot Water Off");		
//		machine.getKeyPad().setCaption(5,"Dispense coffee");
//		machine.getKeyPad().setCaption(6,"Dispense milk");
//		machine.getKeyPad().setCaption(7,"Cold water on");
//		machine.getKeyPad().setCaption(8,"Cold water off");
//		machine.getKeyPad().setCaption(9, "test");//test button
//		
		
		machine.getKeyPad().setCaption(0,"0");
		machine.getKeyPad().setCaption(1,"1");
		machine.getKeyPad().setCaption(2,"2");		
		machine.getKeyPad().setCaption(3,"4");
		machine.getKeyPad().setCaption(4,"5");		
		machine.getKeyPad().setCaption(5,"6");
		machine.getKeyPad().setCaption(6,"7");
		machine.getKeyPad().setCaption(7,"8");
		machine.getKeyPad().setCaption(8,"9");
		machine.getKeyPad().setCaption(9, "return changes");//test button
		super.start();
	}
	
	
	public MachineController() {
					
	}
	
	
	private synchronized void runStateMachine() {
		
		//System.out.println("Running state machine");
		
		// TO DO 
		// Add your code to run the drinks machine
		// in here
		// There is some basic code to show
		// you the working of the machine
		
		
		
		CoinControl handleCoin = new CoinControl(machine);
		HotWaterControl handleWater = new HotWaterControl(machine);
		DrinkMaking drinkMaking = new DrinkMaking(machine);
		
		
		Cup cup=machine.getCup();
		
		if (cup!=null) {
			if (machine.getCup().getWaterLevelLitres()==0) {
			              gotCup=true;
			}
			}

		if (lockKeypad != true) {

			handleWater.controlTemperature();
			handleWater.cannotControlTemperature();
			int keyCode = machine.getKeyPad().getNextKeyCode();
			// System.out.println("Key is "+keyCode);

			if (keyCode != -1) {
				if (keyCode == RETURN_CHANGE_BUTTON) {
					handleCoin.returnChange();
					machine.getCoinHandler().getCoinTray();
					machine.getCoinHandler().clearCoinTry();
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
						// drinkMaking.makeDrink(keypadInput);

//					inputBuffer.delete(0, inputBuffer.length());
//					keypadInput = "";
//					keyCodeCount = 0; // used up this code
					}
				}

			}

		}
		if (keypadInput.length() != 0) {
			switch (keypadInput) {
			case "101":
				if(gotCup==false) {
				System.out.println(gotCup);
				machine.vendCup(Cup.SMALL_CUP);
				System.out.println("!!!");
				gotCup = true;
				}
				break;
			}}
		
		if (keypadInput.length() != 0) {
			switch (keypadInput) {
			case "101":
				lockKeypad = true;
				System.out.println("This is samll black coffee");
				
				    // code to make the drink
				if (cup != null) {
					if (machine.getCup().getCoffeeGrams() < 2) {
						System.out.println(cup);
						machine.getHoppers().setHopperOn(Hoppers.COFFEE);
						System.out.println(machine.getCup().getCoffeeGrams());
					}else {
						machine.getHoppers().setHopperOff(Hoppers.COFFEE);
					}

					if (machine.getWaterHeater().getTemperatureDegreesC() <= 95.9) {
						machine.getWaterHeater().setHeaterOn();
					}

					if (machine.getCup().getWaterLevelLitres() < 0.31) {
						machine.getWaterHeater().setHotWaterTap(true);
					}
					machine.getWaterHeater().setHotWaterTap(false);

					if (machine.getCup().getWaterLevelLitres() < 0.34) {
						machine.getWaterHeater().setColdWaterTap(true);
					}
					machine.getWaterHeater().setColdWaterTap(false);

					if (machine.getCup().getWaterLevelLitres() > 0.34) {
						lockKeypad = false;
						inputBuffer.delete(0, inputBuffer.length());
						keypadInput = "";
						keyCodeCount = 0; // used up this code
						machine.getDisplay().setTextString("Finish!");
					}
				}
				break;
			case "5101":
				System.out.println("This is medium black coffee");
				break;
			case "6101":
				System.out.println("This is large black coffee");
				break;
			case "102":
				System.out.println("This is samll black coffee with sugar");
				break;
			case "5102":
				System.out.println("This is medium black coffee with sugar");
				break;
			case "6102":
				System.out.println("This is large black coffee with sugar");
				break;
			case "201":
				System.out.println("This is samll white coffee");
				break;
			case "5201":
				System.out.println("This is medium white coffee");
				break;
			case "6201":
				System.out.println("This is large white coffee");
				break;
			case "202":
				System.out.println("This is samll white coffee with sugar");
				break;
			case "5202":
				System.out.println("This is medium white coffee with sugar");
				break;
			case "6202":
				System.out.println("This is large white coffee with sugar");
				break;
			case "300":
				System.out.println("This is samll hot chocolate");
				break;
			case "5300":
				System.out.println("This is medium hot chocolate");
				break;
			case "6300":
				System.out.println("This is large hot chocolate");
				break;
			default:
				machine.getDisplay().setTextString("invaild code");
				inputBuffer.delete(0, inputBuffer.length());
				keypadInput = "";
				keyCodeCount = 0; // used up this code
			}
		}
		

		String coinCode=machine.getCoinHandler().getCoinKeyCode();
		if (coinCode!=null) {
			System.out.println("Got coin code .."+coinCode);
			machine.getDisplay().setTextString("Got coin code .."+coinCode);
			
			
			currentCredit=handleCoin.insertedCoin(coinCode,currentCredit);
			machine.getDisplay().setTextString("Now coin"+(currentCredit/100));
			//handleCoin.CoinAmount(coinCode);
			handleCoin.printCoinLevel();
			System.out.println(machine.getBalance());
			
		}
//		switch (keyCode) {
//			case 0 :
//				System.out.println("Vending cup");
//				machine.vendCup(Cup.SMALL_CUP);break;
//			case 1 :
//				machine.getWaterHeater().setHeaterOn();								
//				break;
//			case 2 :
//				machine.getWaterHeater().setHeaterOff();break;
//			case 3 :
//				machine.getWaterHeater().setHotWaterTap(true);
//				break;
//			case 4 :
//				machine.getWaterHeater().setHotWaterTap(false);break;
//			case 5 :
//				machine.getHoppers().setHopperOn(Hoppers.COFFEE);break;
//			case 6 :
//				machine.getHoppers().setHopperOn(Hoppers.MILK);break;			
//			case 7 :
//				machine.getWaterHeater().setColdWaterTap(true);
//				break;
//			case 8 :
//				machine.getWaterHeater().setColdWaterTap(false);
//				break;
//			case 9:
//				machine.getHoppers().setHopperOff(Hoppers.COFFEE);
//				break;
//		}
//		int inputFinish = keypadInput.length();
//		if(inputFinish>=4) {
//		}
		
	}
	
	
	
	public void run() {
		// Controlling thread for coffee machine
		int counter=1;
		while (running) {
			//machine.getDisplay().setTextString("Running drink machine controller "+counter);
			counter++;
			try {
				Thread.sleep(10);		// Set this delay time to lower rate if you want to increase the rate
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			runStateMachine();
		}		
	}
	
	public void updateController() {
		//runStateMachine();
	}
	
	public void stopController() {
		running=false;
	}


	

}
