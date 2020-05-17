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


public class TestElevator {

    Elevator lift;
    public static final int MAX_FLOOR_NUMBER=5;
    private int currentFloor = 2;
    private PriorityQueue<Integer> floorToStopAt;
    private int [] testIsAstop;
    private Boolean motionDirection;
    static Random random;
    private HashSet<Integer> floorPressed= new HashSet<>();
    static Map<String, Elevator.ElevatorStateObj> elevatorStateStore;
    static Elevator.ElevatorStateObj elevatorStateObj;
    static LogHandler logger;


    @BeforeAll
    static void SetupAll(){
        /*
         * Setting up global parameters*/
        random=new Random();
        elevatorStateStore=new HashMap<>();
        elevatorStateObj=new Elevator.ElevatorStateObj();
        logger =new LogHandler();


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
    public void testCapacityMaxCheck(){
        String elevID="ele001";
        int maxLiftCapacity=10;
        elevatorStateObj.setIsCarAvailable(true);
        elevatorStateObj.setCurrCapacity(15);
        elevatorStateObj.setCurrFloorNumber(0);
        elevatorStateStore.put(elevID,elevatorStateObj);
        lift = new Elevator(elevID,maxLiftCapacity,logger,floorToStopAt,elevatorStateStore);
        lift.start();

    }

    @Test
    @DisplayName("testFloorsHaveBeenVisitedGoingUp : \nThe list should contain same elements")
    public void testFloorsHaveBeenVisitedGoingUp(){
        System.out.println( "Current floor "+currentFloor+"  initial Queue isAStop " + floorToStopAt.toString());
        lift.start();
        assertThat(floorToStopAt.isEmpty(),Matchers.equalTo(lift.getFloorsToStopAt().isEmpty()));
    }

    public void testFloorsHaveBeenVisitedGoingDown(){
          System.out.println( "Current floor "+currentFloor+"  initial Queue isAStop " + floorToStopAt.toString());
            lift.start();
            assertThat(floorToStopAt.isEmpty(),Matchers.equalTo(lift.getFloorsToStopAt().isEmpty()));

        }




}
