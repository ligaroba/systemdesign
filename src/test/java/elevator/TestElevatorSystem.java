package elevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.UtilsFunction;


import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestElevatorSystem {
    static ElevatorSystem elevetorSystem;
    static Map<String,Elevator.ElevatorStateObj> listOfAvailableElevators;
    static Map<String,Elevator.ElevatorStateObj> stateOfActiveElevators;
    static List<Integer> listOfPassengers;
    static UtilsFunction utils;
    static Random random;

    public static final int MAX_FLOOR_NUMBER=5;
    private int currentFloor = 2;
    private Queue<Integer> goingnUP;
    private Queue<Integer> goingDown;

    private HashSet<Integer> floorPressed= new HashSet<>();


    @BeforeAll
    static void  SetupAll(){
      /*
      * Setting up global parameters*/
        listOfAvailableElevators = new HashMap<>();
        stateOfActiveElevators =new HashMap<>();
        listOfPassengers =new ArrayList<>();
        utils= new UtilsFunction();
        random=new Random();



    }

    @BeforeEach
    void SetUpTests(){
        /*
        * Setting up test parameters*/



        int visit=0;
        for(int i=0;i*random.nextInt(MAX_FLOOR_NUMBER)<MAX_FLOOR_NUMBER;i++) {
            visit = random.nextInt(MAX_FLOOR_NUMBER);
            floorPressed.add(visit);
        }

      }

    @Test
    @DisplayName("Test Saving Elevator state")
    void testSaveStateOfElevator(){


    }

    @Test
    @DisplayName("Test Assigning Queue to closet elevator from state")
    void testAssignQueueToLiftOptimally(){


    }
    @Test
    @DisplayName("Test updating waiting list after successful trip")
    void testUpdateAvailableList(){

    }

    @Test
    @DisplayName("Test boarding more passengers")
    void testAssignCarMorePassengers(){

    }

    @Test
    @DisplayName("Test forced closing of Car doors")
    void testForcedClosingOfElevatorDoors(){

    }
    @Test
    @DisplayName("Test opening of Car doors")
    void testForcedOpeningOfElevatorDoors(){

    }
}
