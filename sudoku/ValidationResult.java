package sudoku;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationResult {

    private final boolean valid;
    private final List<String> messages;

    private ValidationResult(boolean valid, List<String> messages) {
        this.valid = valid;
        this.messages = messages;
    }

    public static ValidationResult success() {
        return new ValidationResult(true,
                List.of("No rule violations detected."));
    }

    public static ValidationResult failure(List<String> messages) {
        return new ValidationResult(false, messages);
    }

    public boolean isValid() {
        return valid;
    }

    public List<String> getMessages() {
        return messages;
    }

    // For compatibility with UI/tests
    public String getMessage() {
        return messages.stream().collect(Collectors.joining("\n"));
    }
}
