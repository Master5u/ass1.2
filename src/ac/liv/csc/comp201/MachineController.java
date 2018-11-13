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
	
	private boolean validInput = false;

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

		// System.out.println("Running state machine");

		// TO DO
		// Add your code to run the drinks machine
		// in here
		// There is some basic code to show
		// you the working of the machine

		CoinControl handleCoin = new CoinControl(machine);
		HotWaterControl handleWater = new HotWaterControl(machine);
		DrinkMaking drinkMaking = new DrinkMaking(machine);
		DrinkControl drinkControl = new DrinkControl(machine);
		
		Cup cup = machine.getCup();

		if (lockKeypad == false) {

			handleWater.controlTemperature();// 控制温度
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
						
						// valid input
						boolean validInput = drinkControl.validInput(orderCode,keypadInput);
						//valid balance
						boolean validBalance = drinkControl.validBalance(orderCode, keypadInput);
						
						

//					inputBuffer.delete(0, inputBuffer.length());
//					keypadInput = "";
						keyCodeCount = 0; // used up this code
					}
				}
			}
		}

//		if(orderCode.length==MIN_CODE_LEN) {
//			if(orderCode[0]==1|orderCode[0]==2)
//		}

		// 检查原料
		if (orderCode[0] == 1) {
			if (keypadInput == "101") {

			}
		}

//		machine.vendCup(Cup.SMALL_CUP);

//		if (keypadInput.length() != 0) {
//			lockKeypad = true;
//			// code to make the drink
//			if (cup != null) {
//
//			}
//		}

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
