package sudoku;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    public static boolean isValid(Board board) {
        return validate(board).isValid();
    }

    public static ValidationResult validate(Board board) {

        List<String> errors = new ArrayList<>();

        // --- Row & Column checks ---
        for (int i = 0; i < 9; i++) {
            boolean[] row = new boolean[10];
            boolean[] col = new boolean[10];

            for (int j = 0; j < 9; j++) {

                int r = board.getValue(i, j);
                int c = board.getValue(j, i);

                // Row check
                if (r != 0) {
                    if (row[r]) {
                        errors.add("Number " + r +
                                " already exists in Row " + (char) ('A' + i) + ".");
                    }
                    row[r] = true;
                }

                // Column check
                if (c != 0) {
                    if (col[c]) {
                        errors.add("Number " + c +
                                " already exists in Column " + (i + 1) + ".");
                    }
                    col[c] = true;
                }
            }
        }

        // --- Subgrid check ---
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {

                boolean[] seen = new boolean[10];

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {

                        int val = board.getValue(boxRow * 3 + i, boxCol * 3 + j);

                        if (val != 0) {
                            if (seen[val]) {
                                errors.add("Number " + val +
                                        " already exists in 3×3 subgrid (" +
                                        (boxRow + 1) + "," + (boxCol + 1) + ").");
                            }
                            seen[val] = true;
                        }
                    }
                }
            }
        }

        if (errors.isEmpty()) {
            return ValidationResult.success();
        }

        return ValidationResult.failure(errors);
    }
}
