package elevator;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.LogHandler;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestElevator {

    Elevator lift;
    public static final int MAX_FLOOR_NUMBER=10;
    private int currentFloor = 2;
    private PriorityQueue<Integer> floorToStopAt;
    private int [] testIsAstop;
    private Boolean motionDirection;
    static Random random;
    private HashSet<Integer> floorPressed= new HashSet<>();
    static Map<String, Elevator.ElevatorStateObj> elevatorStateStore;
    static Elevator.ElevatorStateObj elevatorStateObj;
    static LogHandler logger;
    static String elevID="";
    static int maxLiftCapacity;


    @BeforeAll
    static void SetupAll(){
        /*
         * Setting up global parameters*/
        random=new Random();
        elevatorStateStore=new HashMap<>();
        elevatorStateObj=new Elevator.ElevatorStateObj();
        logger =new LogHandler();
        elevID="elev01";
        maxLiftCapacity=10;


    }

    @BeforeEach
    void SetUpTests(){
        /*
         * Setting up test parameters
         * */
        int visit=0;
        for(int i=0;i*random.nextInt(MAX_FLOOR_NUMBER)<MAX_FLOOR_NUMBER;i++) {
            visit = random.nextInt(MAX_FLOOR_NUMBER);
            floorPressed.add(visit);
        }
        floorToStopAt = new PriorityQueue<>(floorPressed);
     }

    @Test
    @DisplayName("Testing passengers exceeds max capacity ")
    public void testCapacityMaxCheck(){
        elevatorStateObj.setIsCarAvailable(true);
        elevatorStateObj.setCurrCapacity(15);
        elevatorStateObj.setCurrFloorNumber(0);
        elevatorStateStore.put(elevID,elevatorStateObj);
        lift = new Elevator(elevID,maxLiftCapacity,logger,floorToStopAt,elevatorStateStore);
        lift.start();
    }

    @Test
    @DisplayName("Start from random Floor")
    public void testStartingCarFromRandomFloor(){
        elevatorStateObj.setIsCarAvailable(true);
        elevatorStateObj.setCurrCapacity(random.nextInt(maxLiftCapacity));
        elevatorStateObj.setCurrFloorNumber(random.nextInt(MAX_FLOOR_NUMBER));
        elevatorStateStore.put(elevID,elevatorStateObj);
        lift = new Elevator(elevID,maxLiftCapacity,logger,floorToStopAt,elevatorStateStore);
        lift.start();
        // assertThat(floorToStopAt.isEmpty(),Matchers.equalTo(lift.getFloorsToStopAt().isEmpty()));
        assertTrue(floorToStopAt.isEmpty());
    }

}
