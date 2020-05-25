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
    private Map<String, ElevatorStateObj> stateOfActiveElevators;
    private Map<String, ElevatorStateObj> listOfWaitingElevators;
    private long threadSleepTimeInMillis=10;

    private ElevatorStateObj currState;



  public Elevator(String elevID,int maxLiftCapacity, LogHandler logging, Queue<Integer> floorsToStopAt
                  ,Map<String, ElevatorStateObj> listOfActiveElevators
                  ,Map<String, ElevatorStateObj> listOfWaitingElevators){
      this.elevID=elevID;
      this.maxLiftCapacity=maxLiftCapacity;
      this.logging=logging;
      this.stateOfActiveElevators = listOfActiveElevators;
      this.listOfWaitingElevators = listOfWaitingElevators;
      this.floorsToStopAt = floorsToStopAt; // flag for all the floors elevator is supposed to stop
      this.currState= this.stateOfActiveElevators.get(elevID);
  }
    public int getMaxLiftCapacity() {
        return maxLiftCapacity;
    }

    public Map<String, ElevatorStateObj> getStateOfActiveElevators() {
        return stateOfActiveElevators;
    }

    public Map<String, ElevatorStateObj> getListOfWaitingElevators() {
        return listOfWaitingElevators;
    }

    public Queue<Integer> getFloorsToStopAt() {
        return floorsToStopAt;
    }

    public String getElevID() {
        return elevID;
    }



    private void openDoors(){
        currState=stateOfActiveElevators.get(getElevID());
        String msg ;
        if(currState.getCurrFloorNumber()>=0){
            try {
                 msg = "Reached floor "+ currState.getCurrFloorNumber() + " ... Opening lift doors ...  ";
                logging.writeInfo(msg);
                Thread.sleep(threadSleepTimeInMillis);
                currState.setElevatorDoorOpened(true);
                currState.setElevatorDoorClosed(false);
            } catch (InterruptedException exception) {
                msg ="Something went wrong while trying to open the Car doors. Kindly keep calm you'll be evacuated";
                currState.setElevatorDoorOpened(false);
                currState.setElevatorDoorClosed(true);
                logging.writeError(msg,exception);
            }
            stateOfActiveElevators.put(elevID, currState);
        }
    }

    /*
  Close the doors:
  Why am not checking if currCapacity is zero to change the availability status of the car
  is because am using queue floorsToStopAt to determine if the car has finished its trip or not then it stops
 */
    private void closeDoors() {
        currState=stateOfActiveElevators.get(getElevID());
        String msg;
        if(currState.getCurrCapacity() <= maxLiftCapacity){
            try {
                msg = "Current Floor : " + currState.getCurrFloorNumber() + " ... closing lift doors ...  ";
                logging.writeInfo(msg);
                Thread.sleep(threadSleepTimeInMillis);
                currState.setElevatorDoorOpened(false);
                currState.setElevatorDoorClosed(true);
            } catch (InterruptedException exception) {
                msg = "Something went wrong while trying to close the Car doors. Kindly Step out.";
                currState.setElevatorDoorOpened(true);
                currState.setElevatorDoorClosed(false);
                logging.writeError(msg, exception);
            }
            stateOfActiveElevators.put(elevID, currState);
        }else{
            msg = " Car capacity exceed maximum allowed, " +
                    (currState.getCurrCapacity() - maxLiftCapacity) + " need to exit the car!!.";
            logging.writeWarn(msg);

            int passExcessNum=1;
            int currCapacity =currState.getCurrCapacity();
            while (currCapacity-- > maxLiftCapacity) {
                currState.setCurrCapacity(currCapacity);
                if(passExcessNum>0) {
                    msg = "Passanger(s) exiting : " + (passExcessNum++) ;
                    logging.writeInfo(msg);
                }

            }
            currState.setElevatorDoorOpened(false);
            currState.setElevatorDoorClosed(true);
            stateOfActiveElevators.put(elevID, currState);
            msg = "Ready to go : closing doors..." ;
            logging.writeInfo(msg);
        }

    }

    private void stop(Boolean stillHaveFloorsInQueue) {
        String msg ;
      currState=stateOfActiveElevators.get(getElevID());
      if(stillHaveFloorsInQueue){
          try {
              msg = "Decelerating the Car";
              logging.writeInfo(msg);
              Thread.sleep(threadSleepTimeInMillis);
              msg = "Car Stopped";
              logging.writeInfo(msg);
              openDoors();
          }catch (InterruptedException exception){
              msg ="Something went wrong while trying to stop the Car. Kindly keep calm you'll be evacuated.";
              logging.writeError(msg,exception);
          }
      } else{
           currState.setIsCarAvailable(true);
           changeState(currState);
           msg="Trip finished : Car is available, adding it to the waiting queue.";
           logging.writeInfo(msg);
           // Add the elevator to Elevator available List
          listOfWaitingElevators.put(getElevID(),currState);
          stateOfActiveElevators.remove(getElevID());
      }


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
                openDoors();
            while (!floorsToStopAt.isEmpty()) {
                logging.writeInfo(movingMsg);
                if (floorsToStopAt.peek() == i) {
                    currState.setCurrFloorNumber(floorsToStopAt.poll());
                    changeState(currState);
                    stop(true);
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

            stop(false);
        }else{
            //To do
            // Nothing in the queue
            stop(false);
        }

    }
    private void changeState(ElevatorStateObj currState) {
        stateOfActiveElevators.put(elevID,currState);
    }

    @Override
    public void stopElevator() {
        stop(false);
    }

    @Override
    public void openElevatorDoors() {
          openDoors();
    }

    @Override
    public void closeElevatorDoors() {
         closeDoors();
    }

    @Override
    public void startElevator() {
        closeDoors();
        movingCar();
    }



    static class ElevatorStateObj {
      private int currCapacity=0;
      private Boolean isCarAvailable =false;
      private int currFloorNumber=0;
      private Boolean goingUp=false;
      private Boolean isElevatorDoorClosed=false;
      private Boolean isElevatorDoorOpened=false;

        public void setCarAvailable(Boolean carAvailable) {
            isCarAvailable = carAvailable;
        }

        public Boolean getElevatorDoorClosed() {
            return isElevatorDoorClosed;
        }

        public void setElevatorDoorClosed(Boolean elevatorDoorClosed) {
            isElevatorDoorClosed = elevatorDoorClosed;
        }

        public Boolean getElevatorDoorOpened() {
            return isElevatorDoorOpened;
        }

        public void setElevatorDoorOpened(Boolean elevatorDoorOpened) {
            isElevatorDoorOpened = elevatorDoorOpened;
        }

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
