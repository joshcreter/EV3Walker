package ev3Walker.behavior;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;

public class Crouch implements Behavior {

	private boolean _suppressed = false;

	public boolean takeControl() {
		if (Button.readButtons() != 0) {
			_suppressed = true;

			Button.LEDPattern(6);
			Button.discardEvents();
			System.out.println("Button pressed");
			if ((Button.waitForAnyPress() & Button.ID_ESCAPE) != 0) {
				Button.LEDPattern(0);
				System.exit(1);
			}
			System.out.println("Button pressed 2");
			Button.waitForAnyEvent();
			System.out.println("Button released");
		}
		return true; // this behavior always wants control.
	}

	public void suppress() {
		_suppressed = true;// standard practice for suppress methods
	}

	public void action() {
		_suppressed = false;

		LCD.drawString("Crouch: action     ", 0, 1);

		while (!_suppressed) {

			LCD.drawString("Crouch: run         ", 0, 1);

			// EV3Walker.leftHipMotor.rotate(-10);
			// EV3Walker.rightHipMotor.rotate(-10);

			// leftWaistMotor.move(-100, true);
			// rightWaistMotor.move(-100, true);

			LCD.drawString("Crouch: run lK      ", 0, 1);
			ev3Walker.Model.leftKneeMotor.moveTo(0, false);
			LCD.drawString("Crouch: run rK      ", 0, 1);
			ev3Walker.Model.rightKneeMotor.moveTo(0, false);

			// leftAnkleMotor.move(-100, true);
			// rightAnkleMotor.move(-100, true);
		}
		// Thread.yield(); // don't exit till suppressed
	}
}