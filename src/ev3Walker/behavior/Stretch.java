package ev3Walker.behavior;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;

public class Stretch implements Behavior {

	private boolean _suppressed = false;

	public boolean takeControl() {
		if (Button.readButtons() != 0) {
			_suppressed = true;

			Button.LEDPattern(6);
			Button.discardEvents();

			if ((Button.waitForAnyPress() & Button.ID_ESCAPE) != 0) {
				Button.LEDPattern(0);
				System.exit(1);
			}

			Button.waitForAnyEvent();

			return false;
		} else {
			if (ev3Walker.Model.isInitialized()) {
				// this behavior always wants control but only after initialization
				return true;
			} else {
				return false;
			}
		}
	}

	public void suppress() {
		_suppressed = true;// standard practice for suppress methods
	}

	public void action() {
		_suppressed = false;
		while (!_suppressed) {

			LCD.drawString("Stretch: run lK      ", 0, 1);
//			ev3Walker.Model.leftKneeMotor.moveTo(50, false);
			LCD.drawString("Stretch: run rK      ", 0, 1);
//			ev3Walker.Model.rightKneeMotor.moveTo(50, false);

		}
	}
}