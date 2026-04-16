package src.main.java;

public class Board {

    private final Cell[][] grid = new Cell[9][9];
    private int[][] solution;

    public void initialize() {
        int[][] puzzle = {
                {5,3,0,0,7,0,0,0,0},
                {6,0,0,1,9,5,0,0,0},
                {0,9,8,0,0,0,0,6,0},
                {8,0,0,0,6,0,0,0,3},
                {4,0,0,8,0,3,0,0,1},
                {7,0,0,0,2,0,0,0,6},
                {0,6,0,0,0,0,2,8,0},
                {0,0,0,4,1,9,0,0,5},
                {0,0,0,0,8,0,0,7,9}
        };

        solution = SudokuSolver.solve(puzzle);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new Cell(puzzle[i][j], puzzle[i][j] != 0);
            }
        }
    }

    public MoveResult place(int row, int col, int value) {

        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            return MoveResult.failure("Invalid move.");
        }

        if (grid[row][col].isFixed()) {
            return MoveResult.failure("Invalid move. " + toCellLabel(row, col) + " is pre-filled.");
        }

        if (value < 1 || value > 9) {
            return MoveResult.failure("Invalid move. Value must be between 1 and 9.");
        }

        // TEMP set value
        int old = grid[row][col].getValue();
        grid[row][col].setValue(value);

        // Validate board
        if (!Validator.validate(this).isValid()) {
            grid[row][col].setValue(old); // rollback
            return MoveResult.failure("Invalid move. Rule violation.");
        }

        return MoveResult.success("Move accepted.");
    }
    public MoveResult clear(int row, int col) {
        if (!isInBounds(row, col)) {
            return MoveResult.failure("Invalid cell.");
        }

        if (grid[row][col].isFixed()) {
            return MoveResult.failure("Cannot clear pre-filled cell.");
        }

        grid[row][col].setValue(0);
        return MoveResult.success("Cell cleared.");
    }

    private boolean isInBounds(int r, int c) {
        return r >= 0 && r < 9 && c >= 0 && c < 9;
    }

    private String toCellLabel(int row, int col) {
        return "" + (char) ('A' + row) + (col + 1);
    }

    public int getValue(int r, int c) { return grid[r][c].getValue(); }

    public void setValue(int r, int c, int val) { grid[r][c].setValue(val); }

    public boolean isComplete() {
        for (Cell[] row : grid)
            for (Cell c : row)
                if (c.getValue() == 0) return false;
        return true;
    }

    public int[][] getSolution() { return solution; }

    public void print() {
        System.out.println("\n    1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < 9; i++) {
            char rowChar = (char) ('A' + i);
            System.out.print("  " + rowChar + " ");
            for (int j = 0; j < 9; j++) {
                int val = grid[i][j].getValue();
                System.out.print((val == 0 ? "_" : val) + " ");
            }
            System.out.println();
        }
    }
}
