package ac.liv.csc.comp201.control;

import ac.liv.csc.comp201.model.IMachine;
import ac.liv.csc.comp201.simulate.Cup;
import ac.liv.csc.comp201.simulate.Hoppers;

public class DrinkMaking {

	private IMachine machine;
	
	public DrinkMaking (IMachine machine) {
		this.machine = machine;
	}
	
	public void blackCoffee () {
		Cup cup=machine.getCup();
		machine.getHoppers().setHopperOn(Hoppers.COFFEE);
		if(cup.getCoffeeGrams()>=2.0) {
			machine.getHoppers().setHopperOff(Hoppers.COFFEE);
		}
	}
	
	public void blackCoffeeWithSugar () {
		Cup cup=machine.getCup();
		machine.getHoppers().setHopperOn(Hoppers.COFFEE);
		if(cup.getCoffeeGrams()>=2.0) {
			machine.getHoppers().setHopperOff(Hoppers.COFFEE);
		}
		
		machine.getHoppers().setHopperOn(Hoppers.SUGAR);
		if(cup.getSugarGrams()>=5.0) {
			machine.getHoppers().setHopperOff(Hoppers.SUGAR);
		}
	}
	
	public void whiteCoffee () {
		Cup cup=machine.getCup();
		machine.getHoppers().setHopperOn(Hoppers.COFFEE);
		if(cup.getCoffeeGrams()>=2.0) {
			machine.getHoppers().setHopperOff(Hoppers.COFFEE);
		}
		
		machine.getHoppers().setHopperOn(Hoppers.MILK);
		if(cup.getSugarGrams()>=3.0) {
			machine.getHoppers().setHopperOff(Hoppers.MILK);
		}
	}
	
	public void whiteCoffeeWithSugar () {
		Cup cup=machine.getCup();
		machine.getHoppers().setHopperOn(Hoppers.COFFEE);
		if(cup.getCoffeeGrams()>=2.0) {
			machine.getHoppers().setHopperOff(Hoppers.COFFEE);
		}
		
		machine.getHoppers().setHopperOn(Hoppers.MILK);
		if(cup.getSugarGrams()>=3.0) {
			machine.getHoppers().setHopperOff(Hoppers.MILK);
		}
		
		machine.getHoppers().setHopperOn(Hoppers.SUGAR);
		if(cup.getSugarGrams()>=5.0) {
			machine.getHoppers().setHopperOff(Hoppers.SUGAR);
		}
	}
	
	public void hotChocolate () {
		Cup cup=machine.getCup();
		machine.getHoppers().setHopperOn(Hoppers.CHOCOLATE);
		if(cup.getCoffeeGrams()>=28.0) {
			machine.getHoppers().setHopperOff(Hoppers.CHOCOLATE);
		}
	}
	
}
