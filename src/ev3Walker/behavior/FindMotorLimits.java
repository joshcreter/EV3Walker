package ev3Walker.behavior;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class FindMotorLimits implements Behavior {
	private boolean _suppressed = false;
	private boolean _initialized = false;

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
				return false;
			} else {
				return true;
			}
		}
	}

	public void suppress() {
		_suppressed = true;// standard practice for suppress methods
	}

	public void action() {
		_suppressed = false;

		LCD.drawString("FML: action", 0, 1);
		while (!_suppressed) {
			LCD.drawString("FML: lK      ", 0, 1);

//			if(ev3Walker.Model.leftKneeMotor.isStalled()){
//				LCD.drawString("FML: Stalled      ", 0, 1);
////				ev3Walker.Model.setInitialzed(true);
//			}
			
			ev3Walker.Model.leftKneeMotor.move(100, false);

			Delay.msDelay(100);
			
			ev3Walker.Model.leftKneeMotor.resetTachoCount();

//			LCD.drawString("FML: rK      ", 0, 1);
//			ev3Walker.Model.rightKneeMotor.move(-100, false);
//			ev3Walker.Model.rightKneeMotor.resetTachoCount();

			ev3Walker.Model.setInitialzed(true);

			// this.suppress();
		}
	}
}

