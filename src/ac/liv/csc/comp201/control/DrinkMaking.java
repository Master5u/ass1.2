package ac.liv.csc.comp201.control;

import ac.liv.csc.comp201.model.IMachine;
import ac.liv.csc.comp201.simulate.Cup;
import ac.liv.csc.comp201.simulate.Hoppers;

public class DrinkMaking {

	private IMachine machine;
	
	public DrinkMaking (IMachine machine) {
		this.machine = machine;
	}
	
	public void makeDrink (String keypadInput) {
		HotWaterControl handleWater = new HotWaterControl(machine);
		handleWater.controlTemperature();
		handleWater.cannotControlTemperature();
		
		
		switch (keypadInput) {
		case "101" :
			System.out.println("This is samll black coffee");
			machine.vendCup(Cup.MEDIUM_CUP);
			Cup cup=machine.getCup();
//			if (cup!=null) {
			double coffeeGrams = machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE);
			machine.getHoppers().setHopperOn(Hoppers.COFFEE);
			while(machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE)>coffeeGrams-2) {
				machine.getHoppers().setHopperOn(Hoppers.COFFEE);
			}
			machine.getHoppers().setHopperOff(Hoppers.COFFEE);
			while(machine.getWaterHeater().getTemperatureDegreesC()<=90) {
				machine.getWaterHeater().setHeaterOn();
			}
			while(machine.getCup().getWaterLevelLitres()<0.45) {
				machine.getWaterHeater().setHotWaterTap(true);
			}
			machine.getWaterHeater().setHotWaterTap(false);
			while(machine.getCup().getWaterLevelLitres()<0.45) {
				machine.getWaterHeater().setColdWaterTap(true);
			}
			machine.getWaterHeater().setColdWaterTap(false);
			machine.getDisplay().setTextString("Finish!");
//			}
			break;
		case "5101" :
			System.out.println("This is medium black coffee");
			break;
		case "6101" :
			System.out.println("This is large black coffee");
			break;
		case "102" :
			System.out.println("This is samll black coffee with sugar");
			break;
		case "5102" :
			System.out.println("This is medium black coffee with sugar");
			break;
		case "6102" :
			System.out.println("This is large black coffee with sugar");
			break;
		case "201" :
			System.out.println("This is samll white coffee");
			break;
		case "5201" :
			System.out.println("This is medium white coffee");
			break;
		case "6201" :
			System.out.println("This is large white coffee");
			break;
		case "202" :
			System.out.println("This is samll white coffee with sugar");
			break;
		case "5202" :
			System.out.println("This is medium white coffee with sugar");
			break;
		case "6202" :
			System.out.println("This is large white coffee with sugar");
			break;
		case "300" :
			System.out.println("This is samll hot chocolate");
			break;
		case "5300" :
			System.out.println("This is medium hot chocolate");
			break;
		case "6300" :
			System.out.println("This is large hot chocolate");
			break;
		default:machine.getDisplay().setTextString("Please input right code");
		}
	}
	
//	public void blackCoffee () {
//		Cup cup=machine.getCup();
//		machine.getHoppers().setHopperOn(Hoppers.COFFEE);
//		if(cup.getCoffeeGrams()>=2.0) {
//			machine.getHoppers().setHopperOff(Hoppers.COFFEE);
//		}
//	}
//	
//	public void blackCoffeeWithSugar () {
//		Cup cup=machine.getCup();
//		machine.getHoppers().setHopperOn(Hoppers.COFFEE);
//		if(cup.getCoffeeGrams()>=2.0) {
//			machine.getHoppers().setHopperOff(Hoppers.COFFEE);
//		}
//		
//		machine.getHoppers().setHopperOn(Hoppers.SUGAR);
//		if(cup.getSugarGrams()>=5.0) {
//			machine.getHoppers().setHopperOff(Hoppers.SUGAR);
//		}
//	}
//	
//	public void whiteCoffee () {
//		Cup cup=machine.getCup();
//		machine.getHoppers().setHopperOn(Hoppers.COFFEE);
//		if(cup.getCoffeeGrams()>=2.0) {
//			machine.getHoppers().setHopperOff(Hoppers.COFFEE);
//		}
//		
//		machine.getHoppers().setHopperOn(Hoppers.MILK);
//		if(cup.getSugarGrams()>=3.0) {
//			machine.getHoppers().setHopperOff(Hoppers.MILK);
//		}
//	}
//	
//	public void whiteCoffeeWithSugar () {
//		Cup cup=machine.getCup();
//		machine.getHoppers().setHopperOn(Hoppers.COFFEE);
//		if(cup.getCoffeeGrams()>=2.0) {
//			machine.getHoppers().setHopperOff(Hoppers.COFFEE);
//		}
//		
//		machine.getHoppers().setHopperOn(Hoppers.MILK);
//		if(cup.getSugarGrams()>=3.0) {
//			machine.getHoppers().setHopperOff(Hoppers.MILK);
//		}
//		
//		machine.getHoppers().setHopperOn(Hoppers.SUGAR);
//		if(cup.getSugarGrams()>=5.0) {
//			machine.getHoppers().setHopperOff(Hoppers.SUGAR);
//		}
//	}
//	
//	public void hotChocolate () {
//		Cup cup=machine.getCup();
//		machine.getHoppers().setHopperOn(Hoppers.CHOCOLATE);
//		if(cup.getCoffeeGrams()>=28.0) {
//			machine.getHoppers().setHopperOff(Hoppers.CHOCOLATE);
//		}
//	}
	
}
