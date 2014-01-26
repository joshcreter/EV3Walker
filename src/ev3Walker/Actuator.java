//package ev3Walker;
//
//
///**This thread does the actuator control
// */
//public class Actuator implements Runnable{
//    private static final int STALL_COUNT = 3; 
//    
//    public void run() {
//        while(true) {
//            // wait until triggered to do an actuation
//            synchronized (LinearActuatorNXT.this.actuator) {
//                while (true){
//                    try {
//                        LinearActuatorNXT.this.actuator.wait();
//                        if (LinearActuatorNXT.this.isMoveCommand) break;
//                    } catch (InterruptedException e) {
//                        ; // do nothing and continue
//                    }
//                }
//            }
//            
//            // this blocks. When finished, toExtent() will reset this.isMoveCommand, etc. w/ call to stop()
//            toExtent(); 
//            
//            // wake up any wait in doAction()
//            synchronized(LinearActuatorNXT.this.synchObj1){
//                LinearActuatorNXT.this.synchObj1.notify();
//            }
//        }
//    }
//    
//    // starts the motor and waits until move is completed or interrupted with the this.killCurrentAction
//    // flag which in effect, causes the thread wait/block until next command is issued (this.isMoveCommand set to true)
//    private void toExtent() {
//        int power = LinearActuatorNXT.this.motorPower;
//        int tacho=0;
//        int temptacho;
//        
//        LinearActuatorNXT.this.encoderMotor.resetTachoCount();
//        
//        // initiate the actuator action
//        if (LinearActuatorNXT.this.dirExtend) {
//            LinearActuatorNXT.this.encoderMotor.forward(); 
//        } else {
//            LinearActuatorNXT.this.encoderMotor.backward(); 
//        }
//        
//        // wait until the actuator shaft starts moving (with a time limit)
//        int begTacho=LinearActuatorNXT.this.encoderMotor.getTachoCount();
//        long begTime = System.currentTimeMillis();
//        while (!LinearActuatorNXT.this.killCurrentAction&&(begTacho==LinearActuatorNXT.this.encoderMotor.getTachoCount())) {
//            doWait(LinearActuatorNXT.this.tick_wait/2);
//            // kill the move and exit if it takes too long to start moving  
//            if ((System.currentTimeMillis()-begTime)>LinearActuatorNXT.this.tick_wait*6) {
//                LinearActuatorNXT.this.isStalled=true;
//                LinearActuatorNXT.this.killCurrentAction=true; // will cause loop below to immediately finish
//                break;
//            }
//        }
//        
//        // monitor the move and stop when stalled or action completes
//        begTime = System.currentTimeMillis();
//        temptacho=LinearActuatorNXT.this.tachoCount;
//        while (!LinearActuatorNXT.this.killCurrentAction) {
//            // Stall check. if no tacho change...
//            if (begTacho==LinearActuatorNXT.this.encoderMotor.getTachoCount()) {
//                // ...and we exceed STALL_COUNT wait periods and have been commanded to move, it probably means we have stalled
//                if (System.currentTimeMillis()- begTime>LinearActuatorNXT.this.tick_wait*STALL_COUNT) {
//                    LinearActuatorNXT.this.isStalled=true;
//                    break;
//                }
//            } else {
//                // The tacho is moving, get the current point and time for next comparision
//                begTacho=LinearActuatorNXT.this.encoderMotor.getTachoCount();
//                begTime = System.currentTimeMillis();
//            }
//            // calc abs tacho
//            tacho = LinearActuatorNXT.this.encoderMotor.getTachoCount();
//            LinearActuatorNXT.this.tachoCount = temptacho - tacho;
//            tacho=Math.abs(tacho);
//            
//             // reduce speed when near destination when at higher speeds
//            if (LinearActuatorNXT.this.distanceTicks-tacho<=4&&power>80) LinearActuatorNXT.this.encoderMotor.setPower(70);
//            // exit loop if destination is reached
//            if (tacho>=LinearActuatorNXT.this.distanceTicks) break;
//            // if power changed during this run.... (only when immediateReturn=true)
//            if (power!=LinearActuatorNXT.this.motorPower) {
//                power = LinearActuatorNXT.this.motorPower;
//                LinearActuatorNXT.this.encoderMotor.setPower(power);
//            }
//            
//            if (LinearActuatorNXT.this.killCurrentAction) break;
//            doWait(LinearActuatorNXT.this.tick_wait/2);
//        }
//        
//        // stop the motor
//        LinearActuatorNXT.this.encoderMotor.stop(); 
//        stop(); // potentially redundant state-setting when user calls stop()
//        LinearActuatorNXT.this.tachoCount=temptacho-LinearActuatorNXT.this.encoderMotor.getTachoCount();
//        
//        // set the power back (if changed)
//        if (LinearActuatorNXT.this.distanceTicks-tacho<=4&&power>80) 
//            LinearActuatorNXT.this.encoderMotor.setPower(LinearActuatorNXT.this.motorPower);
//    }
//}