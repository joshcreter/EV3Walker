import lejos.hardware.Button;
import lejos.hardware.device.LnrActrFirgelliNXT;
import lejos.hardware.device.NXTMMX;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.EncoderMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class EV3Walker {
	/*
	 * static RegulatedMotor leftHipMotor = Motor.D; static RegulatedMotor
	 * rightHipMotor = Motor.C;
	 * 
	 * static NXTMMX nxtmmx1 = new NXTMMX(SensorPort.S1); static NXTMMX nxtmmx2
	 * = new NXTMMX(SensorPort.S4);
	 * 
	 * static LnrActrFirgelliNXT leftWaistMotor = new LnrActrFirgelliNXT(
	 * nxtmmx2.getRegulatedMotor(NXTMMX.MOTOR_M1)); static LnrActrFirgelliNXT
	 * rightWaistMotor = new LnrActrFirgelliNXT(
	 * nxtmmx2.getRegulatedMotor(NXTMMX.MOTOR_M2));
	 * 
	 * static LnrActrFirgelliNXT leftKneeMotor = new LnrActrFirgelliNXT(
	 * MotorPort.A); static LnrActrFirgelliNXT rightKneeMotor = new
	 * LnrActrFirgelliNXT( MotorPort.B);
	 * 
	 * static LnrActrFirgelliNXT leftAnkleMotor = new LnrActrFirgelliNXT(
	 * nxtmmx1.getRegulatedMotor(NXTMMX.MOTOR_M2)); static LnrActrFirgelliNXT
	 * rightAnkleMotor;
	 */
	private boolean initialzed = false;
	
	public void setInitialzed(boolean initialzed) {
		this.initialzed = initialzed;
	}

	public static boolean isInitialized() {
		return this.initialzed;
	}
	
	public static LnrActrFirgelliNXT leftKneeMotor = new LnrActrFirgelliNXT(
			new NXTMotor(MotorPort.A));
	public static LnrActrFirgelliNXT rightKneeMotor = new LnrActrFirgelliNXT(
			new NXTMotor(MotorPort.B));

	
	public static void main(String[] args) {
		LCD.drawString("Main", 0, 1);

		Behavior b1 = new FindMotorLimits();
		Behavior b2 = new Crouch();

		// Behavior b1 = new Stretch();
		// Behavior b2 = new DetectWall();
		Behavior[] behaviorList = { b1 };
		Arbitrator arbitrator = new Arbitrator(behaviorList);
		LCD.drawString("Main: Arb setup", 0, 1);

		arbitrator.start();
	}

	private static void resetMotorTachoCounts() {
		// leftHipMotor.resetTachoCount();
		// rightHipMotor.resetTachoCount();
		//
		// leftWaistMotor.resetTachoCount();
		// rightWaistMotor.resetTachoCount();
		//
		// leftKneeMotor.resetTachoCount();
		// rightKneeMotor.resetTachoCount();
		//
		// leftAnkleMotor.resetTachoCount();
		// rightAnkleMotor.resetTachoCount();
	}
}

class Stretch implements Behavior {

	private boolean _suppressed = false;

	public boolean takeControl() {
		if (Button.readButtons() != 0) {
			_suppressed = true;

			// EV3Walker.leftWaistMotor.stop();
			// EV3Walker.rightWaistMotor.stop();
			//
			// EV3Walker.leftKneeMotor.stop();
			// EV3Walker.rightKneeMotor.stop();
			//
			// EV3Walker.leftAnkleMotor.stop();
			// EV3Walker.rightAnkleMotor.stop();

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
		while (!_suppressed) {
			//
			// EV3Walker.leftKneeMotor.moveTo(100, true);
			// EV3Walker.rightKneeMotor.moveTo(100, true);
		}
		// Thread.yield(); // don't exit till suppressed
	}

}

class FindMotorLimits implements Behavior {
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
			if (EV3Walker.isInitialized()) {
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
			EV3Walker.leftKneeMotor.move(-100, false);
			EV3Walker.leftKneeMotor.resetTachoCount();

			LCD.drawString("FML: rK      ", 0, 1);
			EV3Walker.rightKneeMotor.move(-100, false);
			EV3Walker.rightKneeMotor.resetTachoCount();

			this.initialized = true;

			// this.suppress();
		}
	}
}

class Crouch implements Behavior {

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

		// RegulatedMotor leftHipMotor = Motor.D;
		// RegulatedMotor rightHipMotor = Motor.C;
		//
		// NXTMMX nxtmmx1 = new NXTMMX(SensorPort.S1);
		// NXTMMX nxtmmx2 = new NXTMMX(SensorPort.S4);
		//
		// LnrActrFirgelliNXT leftWaistMotor = new LnrActrFirgelliNXT(
		// nxtmmx2.getRegulatedMotor(NXTMMX.MOTOR_M1));
		// LnrActrFirgelliNXT rightWaistMotor = new LnrActrFirgelliNXT(
		// nxtmmx2.getRegulatedMotor(NXTMMX.MOTOR_M2));

		// LnrActrFirgelliNXT leftKneeMotor = new LnrActrFirgelliNXT(new
		// NXTMotor(MotorPort.A));
		// LnrActrFirgelliNXT rightKneeMotor = new LnrActrFirgelliNXT(new
		// NXTMotor(MotorPort.B));

		// LnrActrFirgelliNXT leftAnkleMotor = new LnrActrFirgelliNXT(
		// nxtmmx1.getRegulatedMotor(NXTMMX.MOTOR_M2));
		// LnrActrFirgelliNXT rightAnkleMotor = new LnrActrFirgelliNXT(
		// nxtmmx1.getRegulatedMotor(NXTMMX.MOTOR_M1));

		while (!_suppressed) {

			LCD.drawString("Crouch: run         ", 0, 1);

			// EV3Walker.leftHipMotor.rotate(-10);
			// EV3Walker.rightHipMotor.rotate(-10);

			// leftWaistMotor.move(-100, true);
			// rightWaistMotor.move(-100, true);

			LCD.drawString("Crouch: run lK      ", 0, 1);
			EV3Walker.leftKneeMotor.moveTo(0, false);
			LCD.drawString("Crouch: run rK      ", 0, 1);
			EV3Walker.rightKneeMotor.moveTo(0, false);

			// leftAnkleMotor.move(-100, true);
			// rightAnkleMotor.move(-100, true);
		}
		// Thread.yield(); // don't exit till suppressed
	}
}
