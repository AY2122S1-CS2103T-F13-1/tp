package seedu.awe.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.awe.testutil.GroupBuilder;

public class GroupsContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        GroupContainsKeywordsPredicate firstPredicate = new GroupContainsKeywordsPredicate(firstPredicateKeywordList);
        GroupContainsKeywordsPredicate secondPredicate = new GroupContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GroupContainsKeywordsPredicate firstPredicateCopy =
                new GroupContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        GroupContainsKeywordsPredicate predicate =
                new GroupContainsKeywordsPredicate(Collections.singletonList("London"));
        assertTrue(predicate.test(new GroupBuilder().withGroupName("London Singapore").build()));

        // Multiple keywords
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("London", "Singapore"));
        assertTrue(predicate.test(new GroupBuilder().withGroupName("London Singapore").build()));

        // Only one matching keyword
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("Singapore", "China"));
        assertTrue(predicate.test(new GroupBuilder().withGroupName("London China").build()));

        // Mixed-case keywords
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("lOnDon", "JAPAn"));
        assertTrue(predicate.test(new GroupBuilder().withGroupName("London Japan").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GroupContainsKeywordsPredicate predicate = new GroupContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GroupBuilder().withGroupName("Japan").build()));

        // Non-matching keyword
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("London"));
        assertFalse(predicate.test(new GroupBuilder().withGroupName("Japan Korea").build()));

        // Keywords match phone, email and awe, but does not match name
        predicate = new GroupContainsKeywordsPredicate(Arrays.asList("Korea"));
        assertFalse(predicate.test(new GroupBuilder().withGroupName("Grad Trip").withTags("Korea").build()));
    }
}
