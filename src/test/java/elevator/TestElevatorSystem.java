package elevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.UtilsFunction;


import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestElevatorSystem {
    ElevatorSystem elevtorControls;
    UtilsFunction utils;
    public static final int MAX_FLOOR_NUMBER=5;
    private int currentFloor = 2;
    private Queue<Integer> goingnUP;
    private Queue<Integer> goingDown;
    static Random random;
    private HashSet<Integer> floorPressed= new HashSet<>();


    @BeforeAll
    static void  SetupAll(){
      /*
      * Setting up global parameters*/

    }

    @BeforeEach
    void SetUpTests(){
        /*
        * Setting up test parameters*/
        elevtorControls =new ElevatorSystem();
        utils= new UtilsFunction();
        random=new Random();

        int visit=0;
        for(int i=0;i*random.nextInt(MAX_FLOOR_NUMBER)<MAX_FLOOR_NUMBER;i++) {
            visit = random.nextInt(MAX_FLOOR_NUMBER);
            floorPressed.add(visit);
        }

      }

    @Test
    void testSaveStateOfElevator(){
        elevtorControls.assignElevator();
        String elevid= elevtorControls.getElevatorID();
        boolean putAvailable= elevtorControls.getPutAvailable();
        int currentFloor=elevtorControls.getCurrentFloor();
        elevtorControls.saveState(elevid,putAvailable,currentFloor);

        System.out.println(" State of elevator : " + elevid);
        ElevatorSystem.ElevatorState state = elevtorControls.getState(elevid);

        System.out.println(" CurrentFloor :  " +  state.getElevatorCurrentFloor() +
        " Elevator Avalilable :  " + state.getElevatorPutAvailable());
        assertNotNull(elevtorControls.getState(elevid)," Elevator " + elevid  +
                " does not exits" );

    }

    @Test
    void testAssignQueueToLift(){
        int [] floors;
        System.out.println("floorPressed " + floorPressed.toString());
        floors=floorPressed.stream()
                .mapToInt(Integer::intValue)
                .toArray();

        utils.buildHeap(floors,floors.length,"min");
        System.out.println("floors : " + Arrays.toString(floors));

       /* goingDown.addAll(Arrays.asList(utils.heapSort(floors,floors.length,"max")));

        System.out.println("goingnUP " + goingnUP.toString());
        System.out.println("goingDown " + goingnUP.toString());

        System.out.println(goingnUP.peek());
        System.out.println(goingDown.peek());*/



    }
}
