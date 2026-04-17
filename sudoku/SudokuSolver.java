package sudoku;

public class SudokuSolver {

    public static int[][] solve(int[][] board) {
        int[][] copy = new int[9][9]; // declare the size of the 2 dimensional array
        for (int i = 0; i < 9; i++)
            System.arraycopy(board[i], 0, copy[i], 0, 9); // make a copy of the 2 dimensional array

        solveHelper(copy); // call the solveHelper method and pass the copy of the 2 dimensional array
        return copy;
    }

    private static boolean solveHelper(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if (board[i][j] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isSafe(board, i, j, num)) {
                            board[i][j] = num;

                            if (solveHelper(board)) return true;

                            board[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isSafe(int[][] b, int r, int c, int num) {
        for (int i = 0; i < 9; i++) {
            if (b[r][i] == num || b[i][c] == num) return false;
        }

        int boxRow = r - r % 3;
        int boxCol = c - c % 3;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (b[boxRow+i][boxCol+j] == num)
                    return false;

        return true;
    }
}

