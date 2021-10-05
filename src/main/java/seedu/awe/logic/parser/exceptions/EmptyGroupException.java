package seedu.awe.logic.parser.exceptions;

public class EmptyGroupException extends ParseException {

    public EmptyGroupException(String message) {
        super(message);
    }

    public EmptyGroupException(String message, Throwable cause) {
        super(message, cause);
    }
}
