package seedu.awe.testutil;

import static seedu.awe.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_COST_BUFFET;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_COST_SOUVENIRS;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BUFFET;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_DESCRIPTION_SOUVENIRS;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.awe.testutil.TypicalGroups.BALI;
import static seedu.awe.testutil.TypicalGroups.HELSINKI;
import static seedu.awe.testutil.TypicalGroups.LONDON;
import static seedu.awe.testutil.TypicalGroups.OSLO;
import static seedu.awe.testutil.TypicalPersons.ALICE;
import static seedu.awe.testutil.TypicalPersons.BENSON;
import static seedu.awe.testutil.TypicalPersons.CARL;
import static seedu.awe.testutil.TypicalPersons.DANIEL;
import static seedu.awe.testutil.TypicalPersons.ELLE;
import static seedu.awe.testutil.TypicalPersons.FIONA;
import static seedu.awe.testutil.TypicalPersons.GEORGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.awe.model.AddressBook;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.group.Group;
import seedu.awe.model.person.Person;


public class TypicalExpenses {

    public static final Expense HOLIDAY = new ExpenseBuilder().withDescription("Holiday")
            .withCost("1000").withName("alice").withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com").withPhone("94351253")
            .withTags("friends").build();
    public static final Expense PIZZA = new ExpenseBuilder().withDescription("Pizza")
            .withCost("100").withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Expense PARTY = new ExpenseBuilder().withDescription("Party")
            .withCost("500").withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Expense GIFTS = new ExpenseBuilder().withDescription("gifts")
            .withCost("60").withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Expense DINNER = new ExpenseBuilder().withDescription("dinner")
            .withCost("500").withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Expense TRANSPORTATION = new ExpenseBuilder().withDescription("Transportation")
            .withCost("30").withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Expense TICKETS = new ExpenseBuilder().withDescription("Tickets")
            .withCost("70").withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();
    public static final Expense NONEXISTENTEXPENSE = new ExpenseBuilder().withDescription("Nothing")
            .withCost("0").withName("Janelle Chua").withPhone("9543442")
            .withEmail("janelle@example.com").withAddress("20th street").build();

    // Manually added
    public static final Expense SNACKS = new ExpenseBuilder().withDescription("Snacks")
            .withCost("20").withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Expense LUNCH = new ExpenseBuilder().withDescription("Lunch")
            .withCost("300").withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Expense's details found in {@code CommandTestUtil}
    public static final Expense BUFFET = new ExpenseBuilder().withDescription(VALID_DESCRIPTION_BUFFET)
            .withCost(VALID_COST_BUFFET).withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Expense SOUVENIRS = new ExpenseBuilder().withDescription(VALID_DESCRIPTION_SOUVENIRS)
            .withCost(VALID_COST_SOUVENIRS).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_BUFFET = "Buffet"; // A keyword that matches BUFFET

    private TypicalExpenses() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical expenses.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();

        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }

        for (Group group : getTypicalGroups()) {
            for (Expense expense : getTypicalExpenses(group)) {
                group.addExpense(expense);
            }
            ab.addGroup(group);
        }
        return ab;
    }


    public static List<Expense> getTypicalExpenses(Group group) {
        String groupName = group.getGroupName().toString();

        switch (groupName) {

        case "Bali":
            return new ArrayList<>(Arrays.asList(BUFFET, SOUVENIRS, HOLIDAY));

        case "London":
            return new ArrayList<>(Arrays.asList(BUFFET, PIZZA, TICKETS));

        case "Oslo":
            return new ArrayList<>(Arrays.asList(PARTY, DINNER, GIFTS));

        case "Helsinki":
            return new ArrayList<>(Arrays.asList(TICKETS, DINNER, GIFTS));

        default:
            return new ArrayList<>();
        }
    }


    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(BALI, LONDON, OSLO, HELSINKI));
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}



