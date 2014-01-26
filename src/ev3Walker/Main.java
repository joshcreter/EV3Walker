package ev3Walker;

import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Main {

	public static void main(String[] args) {
		LCD.drawString("Main", 0, 1);

		Behavior b1 = new ev3Walker.behavior.FindMotorLimits();
		//Behavior b2 = new ev3Walker.behavior.Crouch();
		 Behavior b2 = new ev3Walker.behavior.Stretch();

		Behavior[] behaviorList = { b1, b2 };
		Arbitrator arbitrator = new Arbitrator(behaviorList);
		LCD.drawString("Main: Arb setup", 0, 1);

		arbitrator.start();
	}
}
