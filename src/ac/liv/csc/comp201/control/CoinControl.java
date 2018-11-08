package ac.liv.csc.comp201.control;

import ac.liv.csc.comp201.model.IMachine;

public class CoinControl {
	
	public static final String coinCodes[]={"ab","ac","ba","bc","bd","ef","zz" };
	public static final String coinNames[]={"1p","5p","10p","20p","50p","100p" };
	
	private IMachine machine;

	public CoinControl (IMachine machine) {
		this.machine = machine;
	}
	
	public void CoinAmount (String coinCode) {// repeat function as assCoin;should delete!!
		if(machine.getCoinHandler().coinAvailable(coinCode)) {
			//machine.getCoinHandler().setHopperLevel(coinCode, machine.getCoinHandler().getCoinHopperLevel(coinCode)+1);
		}
	}
	
	public void printCoinLevel() { //print coin in console
		for(int index=0; index<coinCodes.length;index++) {
			if(index<coinNames.length) {
				System.out.print(coinNames[index]+": "+machine.getCoinHandler().getCoinHopperLevel(coinCodes[index])+" ");
			}
		}
		System.out.println(" ");
	}
	
	public double insertedCoin (String coinCode, double currentCredit) {
		//need change line; should be in Machinecontroller
		switch (coinCode) {
		case "ab" : currentCredit+=0.010;break;
		case "ac" : currentCredit+=0.050;break;
		case "ba" : currentCredit+=0.100;break;
		case "bc" : currentCredit+=0.200;break;
		case "bd" : currentCredit+=0.500;break;
		case "ef" : currentCredit+=1.000;break;
		}
		return currentCredit;
	}
	
	
}
