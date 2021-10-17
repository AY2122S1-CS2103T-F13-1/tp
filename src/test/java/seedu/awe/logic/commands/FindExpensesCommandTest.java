package seedu.awe.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.awe.commons.core.Messages.MESSAGE_EXPENSES_LISTED_OVERVIEW;
import static seedu.awe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.awe.testutil.TypicalExpenses.getTypicalAddressBook;
import static seedu.awe.testutil.TypicalGroups.BALI;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.awe.model.Model;
import seedu.awe.model.ModelManager;
import seedu.awe.model.UserPrefs;
import seedu.awe.model.expense.DescriptionContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindExpensesCommand}.
 */
public class FindExpensesCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        DescriptionContainsKeywordsPredicate firstPredicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("first"));
        DescriptionContainsKeywordsPredicate secondPredicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("second"));

        FindExpensesCommand findFirstCommand = new FindExpensesCommand(BALI.getGroupName(), firstPredicate);
        FindExpensesCommand findSecondCommand = new FindExpensesCommand(BALI.getGroupName(), secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindExpensesCommand findFirstCommandCopy = new FindExpensesCommand(BALI.getGroupName(), firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different expense -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noExpenseFound() {
        String expectedMessage = String.format(MESSAGE_EXPENSES_LISTED_OVERVIEW, 0);
        DescriptionContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindExpensesCommand command = new FindExpensesCommand(BALI.getGroupName(), predicate);
        expectedModel.updateFilteredExpenseList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, false, false, true);
        assertEquals(Collections.emptyList(), model.getExpenses());
    }

    /*
    @Test
    public void execute_multipleKeywords_multipleExpensesFound() {
        String expectedMessage = String.format(MESSAGE_EXPENSES_LISTED_OVERVIEW, 0);
        DescriptionContainsKeywordsPredicate predicate = preparePredicate("Souvenirs Buffet");
        FindExpensesCommand command = new FindExpensesCommand(new GroupName("Bali"), predicate);
        expectedModel.setExpenses(expectedModel.getGroupByName(new GroupName("Bali")));
        expectedModel.updateFilteredExpenseList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel, false, false, true);
        assertEquals(Arrays.asList(BUFFET, SOUVENIRS), model.getExpenses());
    }
     */

    /**
     * Parses {@code userInput} into a {@code DescriptionContainsKeywordsPredicate}.
     */
    private DescriptionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DescriptionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
