package seedu.awe.model.group.exceptions;

import seedu.awe.logic.parser.exceptions.ParseException;

public class DuplicateGroupException extends ParseException {
    public DuplicateGroupException() {
        super("Operation would result in duplicate groups");
    }
}
