package elevator;

import interfaces.ElevatorControls;
import utils.LogHandler;

import java.util.*;

public class ElevatorSystem implements ElevatorControls {

    private Queue<Integer> downQueue = new LinkedList<>();
    private Queue<Integer> upQueue= new LinkedList<>();
    private Map<String,Elevator.ElevatorStateObj> listOfWaitingElevators;
    private Map<String,Elevator.ElevatorStateObj> stateOfActiveElevators;
    private List<Integer> listOfFloorPressesed ;
    private LogHandler logger;
    private List<Elevator> listOfElevators;
    private List<Integer> listOfPassengers;


    public ElevatorSystem(Map<String,Elevator.ElevatorStateObj> listOfWaitingElevators
            ,Map<String,Elevator.ElevatorStateObj> stateOfActiveElevators
            ,LogHandler logger
            ,List<Integer> listOfPassengers
            ,List<Integer> listOfFloorPressed
    ,List<Elevator> listOfElevators){

        this.listOfWaitingElevators = listOfWaitingElevators;
        this.stateOfActiveElevators=stateOfActiveElevators;
        this.logger=logger;
        this.listOfPassengers=listOfPassengers;
        this.listOfFloorPressesed=listOfFloorPressed;
        this.listOfElevators=listOfElevators;

    }

    private int getClosestElevator(Queue<Integer> floorPressedQueue) {
        int j = listOfWaitingElevators.size()*100;
        int currMinDistance=Integer.MAX_VALUE;

        if(!floorPressedQueue.isEmpty()) {
            for (int i = 0; i < listOfWaitingElevators.size(); i++) {
                int carCurrFloorState = listOfWaitingElevators.get(i).getCurrFloorNumber();
                int diff = Math.abs(carCurrFloorState - floorPressedQueue.peek());
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
        // TODO : All active elevators that have failed to stop
        for (Elevator elevator : listOfElevators){
            if(stateOfActiveElevators.containsKey(elevator.getElevID())){
                elevator.stopElevator();
            }
        }
    }

    @Override
    public void openDoorsControl() {
        //TODO : traverse all the elevators with closed doors due to error when trying to close
        Map<String, Elevator.ElevatorStateObj> elevstate;
        for (Elevator elevator : listOfElevators){
            elevstate=elevator.getStateOfActiveElevators();
            if(elevstate.get(elevator.getElevID()).getElevatorDoorClosed() &&
                    listOfWaitingElevators.containsKey(elevator.getElevID())){
                elevator.openElevatorDoors();
            }
        }
    }

    @Override
    public void closeDoorsControl() {
        //TODO : traverse all the elevators with open doors due to error when trying to open
        Map<String,Elevator.ElevatorStateObj> elevstate;
        for(Elevator elevator : listOfElevators){
            elevstate=elevator.getStateOfActiveElevators();
            if(elevstate.get(elevator.getElevID()).getElevatorDoorOpened()){
                elevator.closeElevatorDoors();
            }
        }

    }



}
