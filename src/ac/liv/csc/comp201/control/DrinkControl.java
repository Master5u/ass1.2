package ac.liv.csc.comp201.control;

import ac.liv.csc.comp201.model.IMachine;
import ac.liv.csc.comp201.simulate.Hoppers;

public class DrinkControl {

	private IMachine machine;

	public DrinkControl(IMachine machine) {
		this.machine = machine;
	}

	public boolean validInput(int orderCode[], String keypadInput) {
		if (keypadInput.equals("101") | keypadInput.equals("102") | keypadInput.equals("201")
				| keypadInput.equals("202") | keypadInput.equals("300") | keypadInput.equals("5101")
				| keypadInput.equals("5102") | keypadInput.equals("5201") | keypadInput.equals("5202")
				| keypadInput.equals("5300") | keypadInput.equals("6101") | keypadInput.equals("6102")
				| keypadInput.equals("6201") | keypadInput.equals("6202") | keypadInput.equals("6300")) {
			return true;
		} else {
			return false;

		}
	}

	public boolean validBalance(int orderCode[], String keypadInput) {
		// check balance
		if (keypadInput.equals("101") | keypadInput.equals("201")) {
			if (machine.getBalance() > 120) {
				return true;
			}
		}
		if (keypadInput.equals("102") | keypadInput.equals("202") | keypadInput.equals("5300")) {
			if (machine.getBalance() > 130) {
				return true;
			}
		}
		if (keypadInput.equals("300")) {
			if (machine.getBalance() > 110) {
				return true;
			}
		}
		if (keypadInput.equals("5101") | keypadInput.equals("5201")) {
			if (machine.getBalance() > 140) {
				return true;
			}
		}
		if (keypadInput.equals("5102") | keypadInput.equals("5202")) {
			if (machine.getBalance() > 150) {
				return true;
			}
		}
		if (keypadInput.equals("6101") | keypadInput.equals("6201")) {
			if (machine.getBalance() > 145) {
				return true;
			}
		}
		if (keypadInput.equals("6102") | keypadInput.equals("6202")) {
			if (machine.getBalance() > 155) {
				return true;
			}
		}
		if (keypadInput.equals("6300")) {
			if (machine.getBalance() > 1350) {
				return true;
			}
		}
		return false;
	}
	
	public void deductBalance(int orderCode[], String keypadInput) {
		// deduct balance
		if (keypadInput.equals("101") | keypadInput.equals("201")) {
			machine.setBalance(machine.getBalance()-120);
		}
		if (keypadInput.equals("102") | keypadInput.equals("202") | keypadInput.equals("5300")) {
			machine.setBalance(machine.getBalance()-130);
		}
		if (keypadInput.equals("300")) {
			machine.setBalance(machine.getBalance()-110);
		}
		if (keypadInput.equals("5101") | keypadInput.equals("5201")) {
			machine.setBalance(machine.getBalance()-140);
		}
		if (keypadInput.equals("5102") | keypadInput.equals("5202")) {
			machine.setBalance(machine.getBalance()-150);
		}
		if (keypadInput.equals("6101") | keypadInput.equals("6201")) {
			machine.setBalance(machine.getBalance()-145);
		}
		if (keypadInput.equals("6102") | keypadInput.equals("6202")) {
			machine.setBalance(machine.getBalance()-155);
		}
		if (keypadInput.equals("6300")) {
			machine.setBalance(machine.getBalance()-135);
		}
		String balacne = String.valueOf(machine.getBalance());
		machine.getDisplay().setTextString(balacne);
	}

	public boolean validIngredients(int orderCode[], String keypadInput) {
		if (keypadInput.equals("101")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE)>2) {
				return true;
			}
		}
		if (keypadInput.equals("5101")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE)>2.64) {
				return true;
			}
		}
		if (keypadInput.equals("6101")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE)>3.29) {
				return true;
			}
		}
		if (keypadInput.equals("102")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE)>2&&machine.getHoppers().getHopperLevelsGrams(Hoppers.SUGAR)>5) {
				return true;
			}
		}
		if (keypadInput.equals("5102")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE)>2.64&&machine.getHoppers().getHopperLevelsGrams(Hoppers.SUGAR)>6.61) {
				return true;
			}
		}
		if (keypadInput.equals("6102")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE)>3.29&&machine.getHoppers().getHopperLevelsGrams(Hoppers.SUGAR)>8.23) {
				return true;
			}
		}
		if (keypadInput.equals("201")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE)>2&&machine.getHoppers().getHopperLevelsGrams(Hoppers.MILK)>3) {
				return true;
			}
		}
		if (keypadInput.equals("5201")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE)>2.64&&machine.getHoppers().getHopperLevelsGrams(Hoppers.MILK)>3.97) {
				return true;
			}
		}
		if (keypadInput.equals("6201")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE)>3.29&&machine.getHoppers().getHopperLevelsGrams(Hoppers.MILK)>4.94) {
				return true;
			}
		}
		if (keypadInput.equals("202")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE)>2&&machine.getHoppers().getHopperLevelsGrams(Hoppers.MILK)>3&&machine.getHoppers().getHopperLevelsGrams(Hoppers.SUGAR)>5) {
				return true;
			}
		}
		if (keypadInput.equals("5202")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE)>2.64&&machine.getHoppers().getHopperLevelsGrams(Hoppers.MILK)>3.97&&machine.getHoppers().getHopperLevelsGrams(Hoppers.SUGAR)>6.61) {
				return true;
			}
		}
		if (keypadInput.equals("6202")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.COFFEE)>3.29&&machine.getHoppers().getHopperLevelsGrams(Hoppers.MILK)>4.94&&machine.getHoppers().getHopperLevelsGrams(Hoppers.SUGAR)>8.23) {
				return true;
			}
		}
		if (keypadInput.equals("300")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.CHOCOLATE)>28) {
				return true;
			}
		}
		if (keypadInput.equals("5300")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.CHOCOLATE)>37.05) {
				return true;
			}
		}
		if (keypadInput.equals("6300")) {
			if (machine.getHoppers().getHopperLevelsGrams(Hoppers.CHOCOLATE)>46.11) {
				return true;
			}
		}
		return false;
	}
	
	//	{"Coffee","Milk (powder)","Sugar","Chocolate","Temperature","Cup litres"};
	public double[] getIngredientsTemperature(int orderCode[], String keypadInput) {
		double[] initial = {0,0,0,0,0,0};
		if (keypadInput.equals("101")) {
			double[] ingredientsTemperature = {2, 0, 0, 0, 95.9, 0.34, 0.31};
			return ingredientsTemperature;
		}
		if (keypadInput.equals("5101")) {
			double[] ingredientsTemperature = {2.64, 0, 0, 0, 95.9, 0.45};
			return ingredientsTemperature;
		}
		if (keypadInput.equals("6101")) {
			double[] ingredientsTemperature = {3.29, 0, 0, 0, 95.9, 0.56};
			return ingredientsTemperature;
		}
		if (keypadInput.equals("102")) {
			double[] ingredientsTemperature = {2, 0, 5, 0, 95.9, 0.34};
			return ingredientsTemperature;
		}
		if (keypadInput.equals("5102")) {
			double[] ingredientsTemperature = {2.64, 0, 6.61, 0, 95.9, 0.45};
			return ingredientsTemperature;
		}
		if (keypadInput.equals("6102")) {
			double[] ingredientsTemperature = {3.29, 0, 8.23, 0, 95.9, 0.56};
			return ingredientsTemperature;
		}
		if (keypadInput.equals("201")) {
			double[] ingredientsTemperature = {2, 3, 0, 0, 95.9, 0.34};
			return ingredientsTemperature;
		}
		if (keypadInput.equals("5201")) {
			double[] ingredientsTemperature = {2.64, 3.97, 0, 0, 95.9, 0.45};
			return ingredientsTemperature;
		}
		if (keypadInput.equals("6201")) {
			double[] ingredientsTemperature = {3.29, 4.94, 0, 0, 95.9, 0.56};
			return ingredientsTemperature;
		}
		if (keypadInput.equals("202")) {
			double[] ingredientsTemperature = {2, 3, 5, 0, 95.9, 0.34};
			return ingredientsTemperature;
		}
		if (keypadInput.equals("5202")) {
			double[] ingredientsTemperature = {2.64, 3.97, 6.61, 0, 95.9, 0.45};
			return ingredientsTemperature;
		}
		if (keypadInput.equals("6202")) {
			double[] ingredientsTemperature = {3.29, 4.94, 8.23, 0, 95.9, 0.56};
			return ingredientsTemperature;
		}
		if (keypadInput.equals("300")) {
			double[] ingredientsTemperature = {0, 0, 0, 28, 90, 0.34};
			return ingredientsTemperature;
		}
		if (keypadInput.equals("5300")) {
			double[] ingredientsTemperature = {0, 0, 0, 37.05, 90, 0.45};
			return ingredientsTemperature;
		}
		if (keypadInput.equals("6300")) {
			double[] ingredientsTemperature = {0, 0, 0, 46.11, 90, 0.56};
			return ingredientsTemperature;
		}
		return initial;
	}

}
