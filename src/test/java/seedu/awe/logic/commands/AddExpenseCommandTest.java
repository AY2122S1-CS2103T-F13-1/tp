package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.awe.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.awe.commons.core.GuiSettings;
import seedu.awe.model.AddressBook;
import seedu.awe.model.Model;
import seedu.awe.model.ReadOnlyAddressBook;
import seedu.awe.model.ReadOnlyUserPrefs;
import seedu.awe.model.expense.Cost;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.group.exceptions.DuplicateGroupException;
import seedu.awe.model.group.exceptions.GroupNotFoundException;
import seedu.awe.model.person.Person;
import seedu.awe.testutil.ExpenseBuilder;
import seedu.awe.testutil.GroupBuilder;
import seedu.awe.testutil.PersonBuilder;

public class AddExpenseCommandTest {

    @Test
    public void constructor_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddExpenseCommand(null,
                null, null, null, null));
    }

    @Test
    public void execute_expenseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();
        Person validPerson = new PersonBuilder().build();
        Group validGroup = new GroupBuilder().build();
        Expense validExpense = new ExpenseBuilder().build();
        validGroup.addMember(validPerson);
        modelStub.addGroup(validGroup);
        GroupName groupName = validGroup.getGroupName();

        CommandResult commandResult = new AddExpenseCommand(validExpense, groupName,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()).execute(modelStub);

        assertEquals(String.format(AddExpenseCommand.MESSAGE_SUCCESS, validPerson),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_payerNotInGroup_throwsCommandException() throws Exception {
        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();
        Person validPerson = new PersonBuilder().build();
        Group validGroup = new GroupBuilder().build();
        Expense validExpense = new ExpenseBuilder().build();
        modelStub.addGroup(validGroup);
        GroupName groupName = validGroup.getGroupName();

        CommandResult commandResult = new AddExpenseCommand(validExpense, groupName,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()).execute(modelStub);

        assertEquals(String.format(AddExpenseCommand.MESSAGE_NOT_PART_OF_GROUP, validPerson),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_allGroupMembersExcluded_throwsCommandException() throws Exception {
        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();
        Person validPerson = new PersonBuilder().build();
        Group validGroup = new GroupBuilder().build();
        Expense validExpense = new ExpenseBuilder().build();
        validGroup.addMember(validPerson);
        modelStub.addGroup(validGroup);
        GroupName groupName = validGroup.getGroupName();

        CommandResult commandResult = new AddExpenseCommand(validExpense, groupName,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Arrays.asList(validPerson))).execute(modelStub);

        assertEquals(String.format(AddExpenseCommand.MESSAGE_ALL_MEMBERS_EXCLUDED, validPerson),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_includedPersonNotPartOfGroup_throwsCommandException() throws Exception {
        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();

        Person validPerson = new PersonBuilder().build();
        Person validPersonNotInGroup = new PersonBuilder().withName("nic").build();

        Group validGroup = new GroupBuilder().build();
        Expense validExpense = new ExpenseBuilder().build();

        validGroup.addMember(validPerson);
        modelStub.addGroup(validGroup);
        GroupName groupName = validGroup.getGroupName();

        CommandResult commandResult = new AddExpenseCommand(validExpense, groupName,
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(Arrays.asList(validPersonNotInGroup))).execute(modelStub);

        assertEquals(String.format(AddExpenseCommand.MESSAGE_NOT_PART_OF_GROUP, validPerson),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidExpenseCost_throwsCommandException() throws Exception {
        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();
        Person validPerson = new PersonBuilder().build();
        Group validGroup = new GroupBuilder().build();
        Expense validExpense = new ExpenseBuilder().withCost("0").build();
        validGroup.addMember(validPerson);
        modelStub.addGroup(validGroup);
        GroupName groupName = validGroup.getGroupName();

        CommandResult commandResult = new AddExpenseCommand(validExpense, groupName,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()).execute(modelStub);

        assertEquals(String.format(AddExpenseCommand.MESSAGE_COST_ZERO_OR_LESS, validPerson),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_withSelfPayees_addSuccessfulWithReducedCost() throws Exception {
        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();
        Person validPerson = new PersonBuilder().build();
        Person validSelfPayee = new PersonBuilder().withName("nic").build();
        Group validGroup = new GroupBuilder().build();
        Expense validExpense = new ExpenseBuilder().build();
        validGroup.addMember(validPerson);
        validGroup.addMember(validSelfPayee);
        modelStub.addGroup(validGroup);
        GroupName groupName = validGroup.getGroupName();
        Cost costOfSelfPayment = new Cost("30");

        AddExpenseCommand addExpenseCommand = new AddExpenseCommand(validExpense, groupName,
                Arrays.asList(validSelfPayee), Arrays.asList(costOfSelfPayment), new ArrayList<>());

        CommandResult commandResult = addExpenseCommand.execute(modelStub);

        Cost actualFinalCost = addExpenseCommand.getExpense().getCost();
        Cost expectedFinalCost = new Cost("20");

        assertEquals(String.format(actualFinalCost.toString()),
                expectedFinalCost.toString());
    }

    @Test
    public void execute_withSelfPayeesNotInGroup_throwsCommandException() throws Exception {
        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();
        Person validPerson = new PersonBuilder().build();
        Person validSelfPayee = new PersonBuilder().withName("nic").build();
        Group validGroup = new GroupBuilder().build();
        Expense validExpense = new ExpenseBuilder().build();
        validGroup.addMember(validPerson);
        modelStub.addGroup(validGroup);
        GroupName groupName = validGroup.getGroupName();
        Cost costOfSelfPayment = new Cost("30");

        CommandResult commandResult = new AddExpenseCommand(validExpense, groupName,
                Arrays.asList(validSelfPayee), Arrays.asList(costOfSelfPayment), new ArrayList<>()).execute(modelStub);

        assertEquals(String.format(seedu.awe.logic.commands.AddExpenseCommand.MESSAGE_NOT_PART_OF_GROUP, validPerson),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Expense alicePayer = new ExpenseBuilder().withName("Alice").build();
        Expense bobPayer = new ExpenseBuilder().withName("Bob").build();
        GroupName groupName = new GroupName("arcade");
        AddExpenseCommand addAlicePayerCommand = new AddExpenseCommand(alicePayer,
                groupName, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        AddExpenseCommand addBobPayerCommand = new AddExpenseCommand(bobPayer,
                groupName, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        // same object -> returns true
        assertTrue(addAlicePayerCommand.equals(addAlicePayerCommand));

        // same values -> returns true
        AddExpenseCommand addAlicePayerCommandCopy = new AddExpenseCommand(alicePayer,
                groupName, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        assertTrue(addAlicePayerCommandCopy.equals(addAlicePayerCommandCopy));

        // different types -> returns false
        assertFalse(addAlicePayerCommand.equals(1));

        // null -> returns false
        assertFalse(addAlicePayerCommand.equals(null));

        // different person -> returns false
        assertFalse(addAlicePayerCommand.equals(addBobPayerCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns an unmodifiable view of the list of {@code Group} backed by the internal list of
         * {@code versionedAddressBook}
         */
        @Override
        public ObservableList<Group> getFilteredGroupList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Group getGroupByName(GroupName groupName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroup(Group group, Group newGroup) throws DuplicateGroupException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredExpenseList(Predicate<Expense> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExpense(Expense expense, Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteExpense(Expense expense, Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Expense> getExpenses() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExpenses(Group group) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that always accept the expense being added.
     */
    private class ModelStubAcceptingExpenseAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        final ArrayList<Group> groups = new ArrayList<>();
        final ArrayList<Expense> expenses = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public void addExpense(Expense expense, Group group) {
            requireNonNull(expense);
            requireNonNull(group);
            expenses.add(expense);
        }

        @Override
        public void addGroup(Group group) {
            requireNonNull(group);
            groups.add(group);
        }

        @Override
        public void setGroup(Group group, Group newGroup) {
            int index = groups.indexOf(group);
            groups.set(index, newGroup);
        }

        @Override
        public Group getGroupByName(GroupName groupName) {
            requireNonNull(groupName);
            for (Group group : groups) {
                if (group.getGroupName().equals(groupName)) {
                    return group;
                }
            }
            throw new GroupNotFoundException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
