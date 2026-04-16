package src.main.java;

public class HintProvider {

    public static void provideHint(Board board) {
        int[][] solution = board.getSolution();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getValue(i, j) == 0) {
                    int correct = solution[i][j];
                    board.setValue(i, j, correct);
                    System.out.println("Hint: Cell " + (char)('A'+i) + (j+1) + " = " + correct);
                    return;
                }
            }
        }

        System.out.println("No hints available.");
    }
}

