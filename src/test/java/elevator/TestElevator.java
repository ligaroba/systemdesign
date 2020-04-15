package elevator;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;


public class TestElevator {

    Elevator lift;
    public static final int MAX_FLOOR_NUMBER=5;
    private int currentFloor = 2;
    private PriorityQueue<Integer> isAstop;
    private int [] testIsAstop;
    private Boolean motionDirection;
    static Random random;
    private HashSet<Integer> floorPressed= new HashSet<>();

    @BeforeAll
    static void SetupAll(){
        /*
         * Setting up global parameters*/
        random=new Random();

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
        isAstop = new PriorityQueue<>(floorPressed);
        lift = new Elevator("ele001",10,true,isAstop,currentFloor,MAX_FLOOR_NUMBER);
    }

    @Test
    @DisplayName("testFloorsHaveBeenVisitedGoingUp : \nThe list should contain same elements")
    public void testFloorsHaveBeenVisitedGoingUp(){
        System.out.println( "Current floor "+currentFloor+"  initial Queue isAStop " + isAstop.toString());
        lift.start();
        assertThat(isAstop.isEmpty(),Matchers.equalTo(lift.getIsAstop().isEmpty()));
    }

    public void testFloorsHaveBeenVisitedGoingDown(){
          System.out.println( "Current floor "+currentFloor+"  initial Queue isAStop " + isAstop.toString());
            lift.start();
            assertThat(isAstop.isEmpty(),Matchers.equalTo(lift.getIsAstop().isEmpty()));

        }




}
