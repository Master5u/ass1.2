package ac.liv.csc.comp201;

import ac.liv.csc.comp201.control.CoinControl;
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
	
	public void startController(IMachine machine) {
		this.machine=machine;				// Machine that is being controlled
		machine.getKeyPad().setCaption(0,"Cup");
		machine.getKeyPad().setCaption(1,"Water heater on");
		machine.getKeyPad().setCaption(2,"Water heater off");		
		machine.getKeyPad().setCaption(3,"Hot Water On");
		machine.getKeyPad().setCaption(4,"Hot Water Off");		
		machine.getKeyPad().setCaption(5,"Dispense coffee");
		machine.getKeyPad().setCaption(6,"Dispense milk");
		machine.getKeyPad().setCaption(7,"Cold water on");
		machine.getKeyPad().setCaption(8,"Cold water off");
		
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
		
		handleWater.controlTemperature();
		handleWater.cannotControlTemperature();
		
		Cup cup=machine.getCup();
		if (cup!=null) {
			System.out.println("Water level is "+cup.getWaterLevelLitres()+" coffee is "+cup.getCoffeeGrams()+" grams");
			if (cup.getCoffeeGrams()>=5) {
				machine.getHoppers().setHopperOff(Hoppers.COFFEE);
			}
		}
		int keyCode=machine.getKeyPad().getNextKeyCode();
	//	System.out.println("Key is "+keyCode);
		String coinCode=machine.getCoinHandler().getCoinKeyCode();
		if (coinCode!=null) {
			System.out.println("Got coin code .."+coinCode);
			machine.getDisplay().setTextString("Got coin code .."+coinCode);
			currentCredit=handleCoin.insertedCoin(coinCode,currentCredit);
			machine.getDisplay().setTextString("Now coin"+currentCredit);
			//handleCoin.CoinAmount(coinCode);
			handleCoin.printCoinLevel();
			
		}
		switch (keyCode) {
			case 0 :
				System.out.println("Vending cup");
				machine.vendCup(Cup.SMALL_CUP);break;
			case 1 :
				machine.getWaterHeater().setHeaterOn();								
				break;
			case 2 :
				machine.getWaterHeater().setHeaterOff();break;
			case 3 :
				machine.getWaterHeater().setHotWaterTap(true);
				break;
			case 4 :
				machine.getWaterHeater().setHotWaterTap(false);break;
			case 5 :
				machine.getHoppers().setHopperOn(Hoppers.COFFEE);break;
			case 6 :
				machine.getHoppers().setHopperOn(Hoppers.MILK);break;			
			case 7 :
				machine.getWaterHeater().setColdWaterTap(true);
				break;
			case 8 :
				machine.getWaterHeater().setColdWaterTap(false);
				break;
				
		}
		
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
