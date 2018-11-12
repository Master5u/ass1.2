package ac.liv.csc.comp201.control;

import ac.liv.csc.comp201.model.IMachine;

public class DrinkControl {

private IMachine machine;
	
	public DrinkControl (IMachine machine) {
		this.machine = machine;
	}
	
	public String input (String button, String keypadInput) {
		keypadInput = keypadInput + button;
		if(keypadInput.length()==4) {
			confirmInput(keypadInput);
		}
		return keypadInput;
	}
	
	public void confirmInput (String keypadInput) {
		
	}
	
}
