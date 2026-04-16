package src.test;

import org.junit.Test;
import src.main.java.GameEngine;
import src.main.java.Validator;

import static org.junit.Assert.*;

public class GameEngineE2ETest {

    @Test
    public void testFullGameSimulation() {

        GameEngine engine = new GameEngine();

        // Simulate a sequence of user moves
        String[] moves = {
                "A3 4",
                "A4 6",
                "A6 8",
                "A7 9",
                "A8 1",
                "A9 2"
        };

        for (String move : moves) {
            String response = engine.execute(move);
            assertTrue("Move failed: " + move,
                    response.contains("accepted") || response.contains("Move"));
        }

        // Validate board state
        String validation = engine.execute("check");

        assertNotNull(validation);
    }

    @Test
    public void testInvalidInputsAndViolations() {

        GameEngine engine = new GameEngine();

        // Syntax error
        String response1 = engine.execute("A3 X");
        assertTrue(response1.contains("Syntax Error"));

        // Out of range
        String response2 = engine.execute("A3 10");
        assertTrue(response2.contains("between 1 and 9"));

        // Pre-filled cell
        String response3 = engine.execute("A1 5");
        assertTrue(response3.contains("pre-filled"));

        // Rule violation
        engine.execute("A3 4");
        String response4 = engine.execute("A4 4");

        assertTrue("Expected rule violation but got: " + response4,
                response4.toLowerCase().contains("invalid"));
    }

    @Test
    public void testCompleteGameWin() {

        GameEngine engine = new GameEngine();

        String[] fullSolution = {
                "A3 4","A4 6","A6 8","A7 9","A8 1","A9 2",
                "B2 7","B3 2","B7 3","B8 4","B9 8",
                "C1 1","C4 3","C5 4","C6 2","C7 5","C9 7",
                "D2 5","D3 9","D4 7","D6 1","D7 4","D8 2",
                "E2 2","E3 6","E5 5","E7 7","E8 9",
                "F2 1","F3 3","F4 9","F6 4","F7 8","F8 5",
                "G1 9","G3 1","G4 5","G5 3","G6 7","G9 4",
                "H1 2","H2 8","H3 7","H7 6","H8 3",
                "I1 3","I2 4","I3 5","I4 2","I6 6","I7 1"
        };

        for (String move : fullSolution) {
            engine.execute(move);
        }

        assertTrue(engine.getBoard().isComplete());
        assertTrue(Validator.validate(engine.getBoard()).isValid());
    }
}
