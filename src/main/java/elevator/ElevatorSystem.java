package elevator;

import interfaces.ElevatorControls;
import utils.LogHandler;

import java.util.*;

public class ElevatorSystem implements ElevatorControls {

    private Queue<Integer> downQueue = new LinkedList<>();
    private Queue<Integer> upQueue= new LinkedList<>();
    private Map<String,Elevator.ElevatorStateObj> listOfAvailableElevators;
    private Map<String,Elevator.ElevatorStateObj> stateOfActiveElevators;
    private List<Integer> listOfFloorPressesed = new ArrayList<>();
    private LogHandler logger;
    private List<Elevator> lisOfElevators;
    private List<Integer> listOfPassengers;


    public ElevatorSystem(Map<String,Elevator.ElevatorStateObj> listOfAvailableElevators
            ,Map<String,Elevator.ElevatorStateObj> stateOfActiveElevators
            ,LogHandler logger
            ,List<Integer> listOfPassengers){

        this.listOfAvailableElevators=listOfAvailableElevators;
        this.stateOfActiveElevators=stateOfActiveElevators;
        this.logger=logger;
        this.listOfPassengers=listOfPassengers;

    }

    private int getClosestElevator(Queue<Integer> floorQueue) {
        int j = listOfAvailableElevators.size()*100;
        int currMinDistance=Integer.MAX_VALUE;

        if(!floorQueue.isEmpty()) {
            for (int i = 0; i < listOfAvailableElevators.size(); i++) {
                int carCurrFloorState = listOfAvailableElevators.get(i).getCurrFloorNumber();
                int diff = Math.abs(carCurrFloorState - floorQueue.peek());
                if (currMinDistance > diff) {
                    currMinDistance = diff;
                    j = i;
                }

            }
        }
        return j;
    }

    private int goingUpControl() {
        return getClosestElevator(upQueue);

    }
    private int goingDownControl() {
        return  getClosestElevator(downQueue);
    }


    @Override
    public void startElevators() {
        // TODO : Create Threads to call  goingUpControl and goingDownControl

    }

    @Override
    public void stopElevators() {
        // TODO : All active elevators

    }

    @Override
    public void openDoorsControl() {

    }

    @Override
    public void closeDoorsControl() {

    }



}
