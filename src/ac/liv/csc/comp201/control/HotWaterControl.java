package ac.liv.csc.comp201.control;

import ac.liv.csc.comp201.model.IMachine;

public class HotWaterControl {

	private IMachine machine;

	public HotWaterControl(IMachine machine) {
		// TODO Auto-generated constructor stub
		this.machine = machine;
	}
	
	public void cannotControlTemperature() {
		if(machine.getWaterHeater().getHeaterOnStatus()==false) {
			if(machine.getWaterHeater().getHeaterOnStatus()==true) {
				machine.shutMachineDown();
				machine.getDisplay().setTextString("There are some errors in water heater");
			}
		}
	}
	
	public void controlTemperature () {
		float temperature = machine.getWaterHeater().getTemperatureDegreesC();
		if(temperature>=75) {
			machine.getWaterHeater().setHeaterOff();
		}
		if(temperature<75) {
			machine.getWaterHeater().setHeaterOn();
		}
	}

}
