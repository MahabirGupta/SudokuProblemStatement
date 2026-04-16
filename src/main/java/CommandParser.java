package src.main.java;

public class CommandParser {

    public enum Type { MOVE, CLEAR, HINT, CHECK, INVALID }

    public static class Command {
        private Type type;
        private int row, col, value;

        private ErrorType errorType;
        private String errorMessage;

        public ErrorType getErrorType() { return errorType; }
        public String getErrorMessage() { return errorMessage; }

        public Command(Type t) {
            this.type = t;
            this.errorType = ErrorType.NONE;
        }

        public Command(ErrorType errorType, String message) {
            this.type = Type.INVALID;
            this.errorType = errorType;
            this.errorMessage = message;
        }


        public Command(Type t, int r, int c, int v) {
            this.type = t;
            this.row = r;
            this.col = c;
            this.value = v;
            this.errorType = ErrorType.NONE;  // important to prevent null point exception
        }


        public Type getType() { return type; }
        public int getRow() { return row; }
        public int getCol() { return col; }
        public int getValue() { return value; }
    }

    public static Command parse(String input) {

        if (input == null || input.trim().isEmpty()) {
            return new Command(ErrorType.SYNTAX_ERROR, "Empty command.");
        }

        if (input.equalsIgnoreCase("hint")) return new Command(Type.HINT);
        if (input.equalsIgnoreCase("check")) return new Command(Type.CHECK);

        String[] parts = input.trim().split("\\s+");

        try {
            if (parts[0].length() < 2) {
                return new Command(ErrorType.SYNTAX_ERROR, "Invalid cell format.");
            }

            char rowChar = Character.toUpperCase(parts[0].charAt(0));
            int row = rowChar - 'A';

            int col = Integer.parseInt(parts[0].substring(1)) - 1;

            if (row < 0 || row >= 9 || col < 0 || col >= 9) {
                return new Command(ErrorType.SYNTAX_ERROR, "Invalid cell reference.");
            }

            if (parts.length == 2 && parts[1].equalsIgnoreCase("clear")) {
                return new Command(Type.CLEAR, row, col, 0);
            }

            if (parts.length == 2) {
                int val;

                try {
                    val = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    return new Command(ErrorType.SYNTAX_ERROR, "Value must be a number.");
                }

                return new Command(Type.MOVE, row, col, val);
            }

        } catch (Exception e) {
            return new Command(ErrorType.SYNTAX_ERROR, "Invalid command format.");
        }

        return new Command(ErrorType.SYNTAX_ERROR, "Invalid command.");
    }


}

