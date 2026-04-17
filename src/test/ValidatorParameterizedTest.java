package src.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import sudoku.Board;
import sudoku.ValidationResult;
import sudoku.Validator;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ValidatorParameterizedTest {

    private final String testName;
    private final Board board;
    private final boolean expectedValid;
    private final String expectedMessage;

    // Constructor
    public ValidatorParameterizedTest(String testName,
                                      Board board,
                                      boolean expectedValid,
                                      String expectedMessage) {
        this.testName = testName;
        this.board = board;
        this.expectedValid = expectedValid;
        this.expectedMessage = expectedMessage;
    }

    // Test data
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {

        // --- Valid Board ---
        Board validBoard = new Board();
        validBoard.initialize();

        // --- Row Violation ---
        Board rowViolation = new Board();
        rowViolation.initialize();
        rowViolation.setValue(0, 2, 3);

        // --- Column Violation ---
        Board colViolation = new Board();
        colViolation.initialize();
        colViolation.setValue(2, 0, 5);

        // --- Subgrid Violation ---
        Board subgridViolation = new Board();
        subgridViolation.initialize();
        subgridViolation.setValue(1, 1, 5); // safe choice for subgrid

        return Arrays.asList(new Object[][] {
                {
                        "Valid Board",
                        validBoard,
                        true,
                        "No rule violations detected."
                },
                {
                        "Row Violation",
                        rowViolation,
                        false,
                        "Row A"
                },
                {
                        "Column Violation",
                        colViolation,
                        false,
                        "Column 1"
                },
                {
                        "Subgrid Violation",
                        subgridViolation,
                        false,
                        "3×3 subgrid"
                }
        });
    }

    @Test
    public void testValidation() {
        ValidationResult result = Validator.validate(board);

        // ✅ Check validity
        assertEquals("Failed validity check for: " + testName,
                expectedValid, result.isValid());

        // ✅ Robust message check (works for multiple errors)
        assertTrue("Expected message not found for: " + testName +
                        "\nActual message:\n" + result.getMessage(),
                result.getMessage().contains(expectedMessage));
    }
}
