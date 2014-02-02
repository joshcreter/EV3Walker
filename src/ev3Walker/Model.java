package ev3Walker;

import lejos.hardware.Button;
import ev3Walker.LinearActuatorNXT;
//import lejos.hardware.device.LnrActrFirgelliNXT;
import lejos.hardware.device.NXTMMX;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTMotor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.EncoderMotor;
import lejos.robotics.LinearActuator;
import lejos.robotics.RegulatedMotor;

public class Model {

	private static boolean initialzed = false;

	public static void setInitialzed(boolean initialzed) {
		Model.initialzed = initialzed;
	}

	public static boolean isInitialized() {
		return initialzed;
	}
	
	/*
	 * 
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

	public static LinearActuator leftKneeMotor = new LinearActuatorNXT(
			new NXTMotor(MotorPort.A));
	public static LinearActuator rightKneeMotor = new LinearActuatorNXT(
			new NXTMotor(MotorPort.B));

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