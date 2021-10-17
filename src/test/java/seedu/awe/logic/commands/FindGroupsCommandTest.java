package seedu.awe.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.awe.commons.core.Messages.MESSAGE_GROUPS_LISTED_OVERVIEW;
import static seedu.awe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.awe.testutil.TypicalGroups.BALI;
import static seedu.awe.testutil.TypicalGroups.CHINA;
import static seedu.awe.testutil.TypicalGroups.INDIA;
import static seedu.awe.testutil.TypicalGroups.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.awe.model.Model;
import seedu.awe.model.ModelManager;
import seedu.awe.model.UserPrefs;
import seedu.awe.model.group.GroupContainsKeywordsPredicate;


/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindGroupsCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        GroupContainsKeywordsPredicate firstPredicate =
                new GroupContainsKeywordsPredicate(Collections.singletonList("first"));
        GroupContainsKeywordsPredicate secondPredicate =
                new GroupContainsKeywordsPredicate(Collections.singletonList("second"));

        FindGroupsCommand findFirstCommand = new FindGroupsCommand(firstPredicate);
        FindGroupsCommand findSecondCommand = new FindGroupsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindGroupsCommand findFirstCommandCopy = new FindGroupsCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_GROUPS_LISTED_OVERVIEW, 0);
        GroupContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindGroupsCommand command = new FindGroupsCommand(predicate);
        expectedModel.updateFilteredGroupList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, true, false, false);
        assertEquals(Collections.emptyList(), model.getFilteredGroupList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_GROUPS_LISTED_OVERVIEW, 3);
        GroupContainsKeywordsPredicate predicate = preparePredicate("Bali India China");
        FindGroupsCommand command = new FindGroupsCommand(predicate);
        expectedModel.updateFilteredGroupList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, true, false, false);
        assertEquals(Arrays.asList(BALI, INDIA, CHINA), model.getFilteredGroupList());
    }

    /**
     * Parses {@code userInput} into a {@code GroupContainsKeywordsPredicate}.
     */
    private GroupContainsKeywordsPredicate preparePredicate(String userInput) {
        return new GroupContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
