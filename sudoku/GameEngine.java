package sudoku;

public class GameEngine {

    private final Board board;

    public GameEngine() {
        this.board = new Board();
        board.initialize();
    }

    public String execute(String input) {

        CommandParser.Command command = CommandParser.parse(input);

        if (command.getType() == CommandParser.Type.INVALID) {
            return "Syntax Error: " + command.getErrorMessage();
        }

        switch (command.getType()) {

            case MOVE:
                MoveResult result = board.place(
                        command.getRow(),
                        command.getCol(),
                        command.getValue()
                );
                return result.getMessage();

            case CLEAR:
                result = board.clear(command.getRow(), command.getCol());
                return result.getMessage();

            case CHECK:
                return Validator.validate(board).getMessage();

            case HINT:
                HintProvider.provideHint(board);
                return "Hint applied.";

            default:
                return "Invalid command.";
        }
    }

    public Board getBoard() {
        return board;
    }
}
