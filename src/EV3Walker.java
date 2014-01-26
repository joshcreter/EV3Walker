import lejos.hardware.Button;
import lejos.hardware.device.LnrActrFirgelliNXT;
import lejos.hardware.device.NXTMMX;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.I2CPort;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.EncoderMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class EV3Walker {

	static RegulatedMotor leftHipMotor = Motor.C;
	static RegulatedMotor rightHipMotor = Motor.D;

	static NXTMMX nxtmmx1; 
	static NXTMMX nxtmmx2; 
	
	static LnrActrFirgelliNXT leftWaistMotor;
	static LnrActrFirgelliNXT rightWaistMotor;
	
	static LnrActrFirgelliNXT leftKneeMotor; // = new LnrActrFirgelliNXT(MotorPort.A);
	static LnrActrFirgelliNXT rightKneeMotor; // = new LnrActrFirgelliNXT(MotorPort.B);
	
	static LnrActrFirgelliNXT leftAnkleMotor;
	static LnrActrFirgelliNXT rightAnkleMotor;	
	
	public static void main(String[] args) {
		LCD.drawString("Walker", 0, 1);

//		init();
		
		Behavior b1 = new Crouch();
//		Behavior b1 = new Stretch();
		// Behavior b2 = new DetectWall();
		Behavior[] behaviorList = { b1 };
		Arbitrator arbitrator = new Arbitrator(behaviorList);
		arbitrator.start();
	}
	
	private static void init(){
		nxtmmx1 = new NXTMMX(SensorPort.S1); 
		nxtmmx2 = new NXTMMX(SensorPort.S2); 

		resetMotorTachoCounts();
	}
	
	private static void resetMotorTachoCounts(){ 
		leftHipMotor.resetTachoCount();
		rightHipMotor.resetTachoCount();
		
		leftWaistMotor.resetTachoCount();
		rightWaistMotor.resetTachoCount();
		
		leftKneeMotor.resetTachoCount();
		rightKneeMotor.resetTachoCount();
		
		leftAnkleMotor.resetTachoCount();
		rightAnkleMotor.resetTachoCount();
	}
}



class Stretch implements Behavior {

	private boolean _suppressed = false;

	public boolean takeControl() {
		if (Button.readButtons() != 0) {
			_suppressed = true;

			EV3Walker.leftWaistMotor.stop();
			EV3Walker.rightWaistMotor.stop();
			
			EV3Walker.leftKneeMotor.stop();
			EV3Walker.rightKneeMotor.stop();
			
			EV3Walker.leftAnkleMotor.stop();
			EV3Walker.rightAnkleMotor.stop();
			
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

			EV3Walker.leftKneeMotor.moveTo(100, true);
			EV3Walker.rightKneeMotor.moveTo(100, true);
		}
//		Thread.yield(); // don't exit till suppressed
	}
	
}


class Crouch implements Behavior {

	private boolean _suppressed = false;

	public boolean takeControl() {
		if (Button.readButtons() != 0) {
			_suppressed = true;

			EV3Walker.leftWaistMotor.stop();
			EV3Walker.rightWaistMotor.stop();
			
			EV3Walker.leftKneeMotor.stop();
			EV3Walker.rightKneeMotor.stop();
			
			EV3Walker.leftAnkleMotor.stop();
			EV3Walker.rightAnkleMotor.stop();
			
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
			EV3Walker.leftWaistMotor.move(-100, true);
			EV3Walker.rightWaistMotor.move(-100, true);

			EV3Walker.leftKneeMotor.move(-100, true);
			EV3Walker.rightKneeMotor.move(-100, true);
			
			EV3Walker.leftAnkleMotor.move(-100, true);
			EV3Walker.rightAnkleMotor.move(-100, true);
		}
//		Thread.yield(); // don't exit till suppressed
	}
}
