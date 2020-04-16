package elevator;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestElevatorSystem {
    ElevatorSystem elevtorControls;


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

    }
}
