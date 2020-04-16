package elevator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;

public class ElevatorSystem {

    private HashMap<String,ElevatorState> liftState = new HashMap<>();
    private Elevator lift;
    public static final int MAX_FLOOR_NUMBER=5;
    private int currentFloor = 5;
    private PriorityQueue<Integer> isAstop;
    static Random random;
    private HashSet<Integer> floorPressed= new HashSet<>();


    public ElevatorSystem(){
        random=new Random();

   }

    public void assignElevator(){
        int visit=0;
        for(int i=0;i*random.nextInt(MAX_FLOOR_NUMBER)<MAX_FLOOR_NUMBER;i++) {
            visit = random.nextInt(MAX_FLOOR_NUMBER);
            floorPressed.add(visit);
        }
        isAstop = new PriorityQueue<>(floorPressed);
        lift = new Elevator("ele001",10,true,isAstop,currentFloor,MAX_FLOOR_NUMBER);
    }


    public String getElevatorID() {
        return lift.getElevID();
    }
    public boolean getPutAvailable(){
        return lift.getPutAsAvailable();
    }
    public int getCurrentFloor(){
        return lift.getCurrentFloor();
    }

    public void ElevatorRunning(){
          /*
          * Create Multiple Threads to start multiple lifts
          * */

    }

    public void saveState(String elevid,boolean putAvailable,int currentFloor) {
         if(!liftState.containsKey(elevid))
            liftState.put(elevid,new ElevatorState(putAvailable,currentFloor));
    }

    public ElevatorState getState(String elevid) {
         if(liftState.containsKey(elevid))
             return liftState.get(elevid);
         return null;
    }


    class ElevatorState{
        private int elevatorCurrentFloor;
        private boolean elevatorPutAvailable;

        ElevatorState(boolean putAvailable,int currentFloor){
            this.elevatorPutAvailable=putAvailable;
            this.elevatorCurrentFloor=currentFloor;
        }

        public int getElevatorCurrentFloor() {
            return elevatorCurrentFloor;
        }

        public boolean getElevatorPutAvailable() {
            return elevatorPutAvailable;
        }
    }
}
