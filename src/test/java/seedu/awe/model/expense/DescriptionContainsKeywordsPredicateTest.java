package seedu.awe.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.awe.testutil.ExpenseBuilder;

public class DescriptionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("lunch");
        List<String> secondPredicateKeywordList = Arrays.asList("lunch", "dinner");

        DescriptionContainsKeywordsPredicate firstPredicate = new DescriptionContainsKeywordsPredicate(
                firstPredicateKeywordList);
        DescriptionContainsKeywordsPredicate secondPredicate = new DescriptionContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DescriptionContainsKeywordsPredicate firstPredicateCopy = new DescriptionContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different expense -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        DescriptionContainsKeywordsPredicate predicate = new DescriptionContainsKeywordsPredicate(
                Collections.singletonList("Buffet"));
        assertTrue(predicate.test(new ExpenseBuilder().withDescription("Buffet").build()));

        // Multiple keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Buffet", "Dinner"));
        assertTrue(predicate.test(new ExpenseBuilder().withDescription("Buffet Dinner").build()));

        // Only one matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Dinner", "Holiday"));
        assertTrue(predicate.test(new ExpenseBuilder().withDescription("Dinner Buffet").build()));

        // Mixed-case keywords
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("diNNer", "holidaY"));
        assertTrue(predicate.test(new ExpenseBuilder().withName("Dinner Holiday").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DescriptionContainsKeywordsPredicate predicate = new DescriptionContainsKeywordsPredicate(
                Collections.emptyList());
        assertFalse(predicate.test(new ExpenseBuilder().withDescription("Buffet").build()));

        // Non-matching keyword
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("Lunch"));
        assertFalse(predicate.test(new ExpenseBuilder().withName("Buffet Dinner").build()));

        // Keywords match cost and payer but does not match description
        predicate = new DescriptionContainsKeywordsPredicate(Arrays.asList("100", "Alice", "lunch"));
        assertFalse(predicate.test(new ExpenseBuilder().withDescription("Buffet").withCost("100")
                .withName("Alice").build()));
    }
}

