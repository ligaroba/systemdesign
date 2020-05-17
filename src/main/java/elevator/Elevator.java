package elevator;

import interfaces.ElevatorMotion;
import utils.LogHandler;

import java.util.Map;
import java.util.Queue;

public class Elevator implements ElevatorMotion {
    private Queue<Integer> floorsToStopAt;
    private int maxLiftCapacity;
    private String elevID;
    private int currentFloor;
     private LogHandler logging;
    private Boolean isAvailable;
    private Map<String, ElevatorStateObj> elevatorStateStore;
    private ElevatorStateObj currState;



  public Elevator(String elevID,int maxLiftCapacity, LogHandler logging, Queue<Integer> floorsToStopAt, Map<String, ElevatorStateObj> elevatorStateStore){
      this.elevID=elevID;
      this.maxLiftCapacity=maxLiftCapacity;
      this.logging=logging;
      this.elevatorStateStore = elevatorStateStore;
      this.floorsToStopAt = floorsToStopAt; // flag for all the floors elevator is supposed to stop
      this.currState= this.elevatorStateStore.get(getElevID());
  }


    public int getMaxLiftCapacity() {
        return maxLiftCapacity;
    }

    public Map<String, ElevatorStateObj> getElevatorStateStore() {
        return elevatorStateStore;
    }

    public Queue<Integer> getFloorsToStopAt() {
        return floorsToStopAt;
    }

    public String getElevID() {
        return elevID;
    }

    public void openDoors( int currentFloor){
        String msg ;
        if(currentFloor>=0){
            try {
                 msg = "Reached floor "+ currentFloor + " ... Opening lift doors ...  ";
                logging.writeInfo(msg);
                Thread.sleep(3000);
            } catch (InterruptedException exception) {
                msg ="Something went wrong while trying to open the Car doors. Kindly keep calm you'll be evacuated";
                logging.writeError(msg,exception);
            }
        }
    }

    private void stop(ElevatorStateObj currState, Boolean stillHaveFloorsInQueue) {
        String msg ;
      if(stillHaveFloorsInQueue){
          try {
              msg = "Decelerating the Car";
              logging.writeInfo(msg);
              Thread.sleep(100);
              msg = "Car Stopped";
              logging.writeInfo(msg);
              openDoors(currState.getCurrFloorNumber());
          }catch (InterruptedException exception){
              msg ="Something went wrong while trying to stop the Car. Kindly keep calm you'll be evacuated.";
              logging.writeError(msg,exception);
          }
      } else{
           currState.setIsCarAvailable(true);
           changeState(currState);
           msg="Trip finished : Car is available, adding it to the waiting queue.";
           logging.writeInfo(msg);
           // Add the elevator to Elevator available Queue
      }


    }

    /*
      Close the doors:
      Why am not checking if currCapacity is zero to change the availability status of the car
      is because am using queue floorsToStopAt to determine if the car has finished its trip or not then it stops
     */
    private void closeDoors(int currCapacity,int currFloor) {

        String msg;
        if(currCapacity <= maxLiftCapacity){
            try {
                currState.setCurrFloorNumber(currFloor);
                currState.setCurrCapacity(currCapacity);
                elevatorStateStore.put(elevID,currState);

                msg = "Current Floor : " + currFloor + " ... closing lift doors ...  ";
                logging.writeInfo(msg);
                Thread.sleep(100);
            } catch (InterruptedException exception) {
                msg = "Something went wrong while trying to close the Car doors. Kindly Step out.";
                logging.writeError(msg, exception);
            }
        }else{
            msg = " Car capacity exceed maximum allowed " +
                    (currCapacity - maxLiftCapacity) + " need to exit the car!!.";
            logging.writeWarn(msg);
            currState.setCurrFloorNumber(currFloor);
            int passExcessNum=1;
            while (currCapacity-- > maxLiftCapacity) {
                  currState.setCurrCapacity(currCapacity);
                  if(passExcessNum>0) {
                      msg = " Passanger(s) exiting : " + (passExcessNum++) ;
                      logging.writeInfo(msg);
                  }

              }
            elevatorStateStore.put(elevID, currState);
            msg = " Ready to go : closing doors..." ;
            logging.writeInfo(msg);
        }

    }

    public void changeState(ElevatorStateObj currState) {
        elevatorStateStore.put(elevID,currState);
    }

    private void  movingCar(){
        /*
         * floorsToStopAt : stores all the floors our lift/car should stop at
         * motionDirection : hold the flag on the direction of the car either upward or downwards
         * description
         *
         * */

        int i = currState.getCurrFloorNumber();

        StringBuilder msgBuilder = new StringBuilder();
        String movingMsg ="Car moving ...";

        if(!floorsToStopAt.isEmpty()) {
            if(i== floorsToStopAt.peek())
                openDoors(floorsToStopAt.poll());
            while (!floorsToStopAt.isEmpty()) {
                logging.writeInfo(movingMsg);
                if (floorsToStopAt.peek() == i) {
                    currState.setCurrFloorNumber(floorsToStopAt.poll());
                    stop(currState,true);
                } else if (floorsToStopAt.peek() > i){
                    i++;
                    currState.setGoingUp(true);
                    currState.setCurrFloorNumber(i);
                    changeState(currState);

                }else if (floorsToStopAt.peek()<i) {
                    i--;
                    currState.setGoingUp(false);
                    currState.setCurrFloorNumber(i);
                    changeState(currState);

                }

                msgBuilder.append("Lift going ");
                if(currState.getGoingUp()){
                    msgBuilder.append("up");
                }else{
                    msgBuilder.append("down");
                }

                msgBuilder.append(" ")
                        .append(" current floor : ")
                        .append(currState.getCurrFloorNumber())
                        .append(" " + floorsToStopAt.toString() + "\n");
                logging.writeInfo(msgBuilder.toString());
                msgBuilder = new StringBuilder();
            }

            stop(currState,false);
        }else{
            //To do
            // Nothing in the queue
            stop(currState,false);
        }

    }

    @Override
    public void start() {
        closeDoors(currState.getCurrCapacity(),currState.getCurrFloorNumber());
        movingCar();
    }



    static class ElevatorStateObj {
      private int currCapacity=0;
      private Boolean isCarAvailable =false;
      private int currFloorNumber=0;
      private Boolean goingUp=false;

        public Boolean getGoingUp() {
            return goingUp;
        }

        public void setGoingUp(Boolean goingUp) {
            this.goingUp = goingUp;
        }

        public void setIsCarAvailable(Boolean isCarAvailable) {
            isCarAvailable = isCarAvailable;
        }

        public void setCurrCapacity(int currCapacity) {
            this.currCapacity = currCapacity;
        }

        public void setCurrFloorNumber(int currFloorNumber) {
            this.currFloorNumber = currFloorNumber;
        }

        public Boolean getCarAvailable() {
            return isCarAvailable;
        }

        public int getCurrCapacity() {
            return currCapacity;
        }

        public int getCurrFloorNumber() {
            return currFloorNumber;
        }
    }
}
