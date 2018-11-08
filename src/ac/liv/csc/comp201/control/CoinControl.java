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
		int coin = machine.getBalance();
		switch (coinCode) {
		case "ab" : coin+=1;break;
		case "ac" : coin+=5;break;
		case "ba" : coin+=10;break;
		case "bc" : coin+=20;break;
		case "bd" : coin+=50;break;
		case "ef" : coin+=100;break;
		}
		machine.setBalance(coin);
		currentCredit = coin;
		return currentCredit;
	}
	
	
}
