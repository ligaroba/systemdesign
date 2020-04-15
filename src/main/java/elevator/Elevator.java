package elevator;

import java.util.Queue;

public class Elevator {
    private Boolean putAsAvailable;
    private Queue<Integer> isAstop;
    private int maxLiftCapacity;
    private String elevID;
    private int currentFloor;
    private int goToFloor;
    private Boolean goingUp;
    private int floors;






    public Elevator(String elevID, int maxLiftCapacity, Boolean putAsAvailable, Queue<Integer> isAstop, int currentFloor,  int floors) {
      this.elevID=elevID;
      this.maxLiftCapacity = maxLiftCapacity;
      this.isAstop=isAstop; // flag for all the floors elevator is supposed to stop
      this.putAsAvailable=putAsAvailable; // Boolean to show if elevator is available
      this.currentFloor =  currentFloor;
      this.floors=floors;

    }

    public Elevator(String elevID, int maxLiftCapacity, Boolean putAsAvailable, Queue<Integer> isAstop, int currentFloor, int goToFloor, int floors ) {
        this.elevID=elevID;
        this.maxLiftCapacity = maxLiftCapacity;
        this.isAstop=isAstop; // flag for all the floors elevator is supposed to stop
        this.putAsAvailable=putAsAvailable; // Boolean to show if elevator is available
        this.currentFloor =  currentFloor;
        this.goToFloor=goToFloor;
        this.floors=floors;

    }

    public int getMaxLiftCapacity() {
        return maxLiftCapacity;
    }

    public Boolean getPutAsAvailable() {
        return putAsAvailable;
    }

    public Queue<Integer> getIsAstop() {
        return isAstop;
    }

    public String getElevID() {
        return elevID;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getGoToFloor() {
        return goToFloor;
    }

    public int getFloors() {
        return floors;
    }

    private void openDoors( int state){
        if(state>=0){
            try {
                System.out.println("   Reached floor "+ state + " ... Openning lift doors ... ");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void start(){
        /*
        * isAstop : stores all the floors our lift/car should stop at
        * motionDirection : hold the flag on the direction of the car either upward or downwards
        * description
        *
        * */

      //if(getGoToFloor()!=getCurrentFloor() && getGoToFloor()>0)
        //        isAstop.add(getGoToFloor());
       int i = getCurrentFloor();
       if(!isAstop.isEmpty()) {
           if(i==isAstop.peek())
               openDoors(isAstop.poll());
            while (!isAstop.isEmpty()) {
                if (isAstop.peek() == i) {
                    openDoors(isAstop.poll());
                } else if (isAstop.peek() > i){
                    i++;
                    goingUp=true;
                }else if (isAstop.peek()<i) {
                    i--;
                    goingUp=false;
                }
                 System.out.print(" Current floor :  " + i + " ");
                 System.out.println(" Lift  Going up : " + goingUp + " " +
                         " Next Floor(s) : " + isAstop.toString());
             }

            putAsAvailable=true;
            System.out.println(" Lift Available : " + putAsAvailable);

       }else{
           //To do
           // Nothing in the queue

           putAsAvailable = true;
       }


     }

    class LiftButton {
        private int buttonID;
        private int floorNumber;

        LiftButton(int buttonID,int floorNumber){
            this.buttonID=buttonID;
            this.floorNumber=floorNumber;
        }

    }
}
