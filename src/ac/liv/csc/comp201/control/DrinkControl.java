package ac.liv.csc.comp201.control;

import ac.liv.csc.comp201.model.IMachine;

public class DrinkControl {

private IMachine machine;

	
	public DrinkControl (IMachine machine) {
		this.machine = machine;
	}
	
	public boolean validInput(int orderCode[], String keypadInput) {
		if (keypadInput.equals("101") | keypadInput.equals("102") | keypadInput.equals("201")
				| keypadInput.equals("202") | keypadInput.equals("300") | keypadInput.equals("5101")
				| keypadInput.equals("5102") | keypadInput.equals("5201") | keypadInput.equals("5202")
				| keypadInput.equals("5300") | keypadInput.equals("6101") | keypadInput.equals("6102")
				| keypadInput.equals("6201") | keypadInput.equals("6202") | keypadInput.equals("6300")) {
			System.out.println("drink valid");
			return true;
		} else {
			machine.getDisplay().setTextString("invalid input");
			return false;

		}
	}
		
	public boolean validBalance (int orderCode[], String keypadInput) {
			//check balance
			if(keypadInput.equals("101") |keypadInput.equals("201")) {
				if(machine.getBalance()>120) {
					return true;
			}}
			if(keypadInput.equals("102") |keypadInput.equals("202")|keypadInput.equals("5300")) {
				if(machine.getBalance()>130) {
					return true;
			}}
			if(keypadInput.equals("300")) {
				if(machine.getBalance()>110) {
					return true;
			}}
			if(keypadInput.equals("5101") |keypadInput.equals("5201")) {
				if(machine.getBalance()>140) {
					return true;
			}}
			if(keypadInput.equals("5102") |keypadInput.equals("5202")) {
				if(machine.getBalance()>150) {
					return true;
			}}
			if(keypadInput.equals("6101") |keypadInput.equals("6201")) {
				if(machine.getBalance()>145) {
					return true;
			}}
			if(keypadInput.equals("6102") |keypadInput.equals("6202")) {
				if(machine.getBalance()>155) {
					return true;
			}}
			if(keypadInput.equals("6300")) {
				if(machine.getBalance()>1350) {
					return true;
			}}
			return false;
		}
		
	}
			
