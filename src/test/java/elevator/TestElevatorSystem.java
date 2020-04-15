package elevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestElevatorSystem {
    ElevatorSystem elevtorControls;

    @BeforeAll
    void SetupAll(){
      /*
      * Setting up global parameters*/
        elevtorControls =new ElevatorSystem();
    }

    @BeforeEach
    void SetUpTests(){
        /*
        * Setting up test parameters*/

    }
    @Test
    void testReceivesCallsFromPasangerGoingUp(){



    }
}
