package sudoku;

import java.util.Scanner;

public class SudokuGame {

    private final Board board = new Board();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Welcome to Sudoku!\n");

        board.initialize();
        board.print();

        while (true) {
            System.out.print("\nEnter command (e.g., A3 4, C5 clear, hint, check, quit): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                System.out.println("Game exited.");
                return;
            }

            process(input);

            if (board.isComplete() &&
                    Validator.validate(board).isValid()) {

                board.print();
                System.out.println("\nYou have successfully completed the Sudoku puzzle!");
                return;
            }
        }
    }

    private void process(String input) {
        CommandParser.Command command = CommandParser.parse(input);

        // Handle syntax errors FIRST
        if (command.getType() == CommandParser.Type.INVALID) {
            System.out.println("Syntax Error: " + command.getErrorMessage());
            return;
        }

        switch (command.getType()) {

            case MOVE:
                MoveResult result = board.place(
                        command.getRow(),
                        command.getCol(),
                        command.getValue()
                );

                if (!result.isSuccess()) {
                    System.out.println("Rule Violation: " + result.getMessage());
                } else {
                    System.out.println(result.getMessage());
                }

                board.print();
                break;

            case CLEAR:
                result = board.clear(command.getRow(), command.getCol());
                System.out.println(result.getMessage());
                board.print();
                break;

            case HINT:
                HintProvider.provideHint(board);
                board.print();
                break;

            case CHECK:
                ValidationResult check = Validator.validate(board);
                System.out.println(check.getMessage());
                break;
        }
    }

}
