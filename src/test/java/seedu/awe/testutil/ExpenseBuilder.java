package seedu.awe.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.awe.model.expense.Cost;
import seedu.awe.model.expense.Description;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.person.Address;
import seedu.awe.model.person.Email;
import seedu.awe.model.person.Name;
import seedu.awe.model.person.Person;
import seedu.awe.model.person.Phone;
import seedu.awe.model.tag.Tag;
import seedu.awe.model.util.SampleDataUtil;

public class ExpenseBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    public static final String DEFAULT_COST = "50";
    public static final String DEFAULT_DESCRIPTION = "holiday";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private final ArrayList<Person> selfPayees = new ArrayList<>();
    private Cost cost;
    private Description description;
    private final ArrayList<Person> included = new ArrayList<>();

    /**
     * Creates a {@code ExpenseBuilder} with the default details.
     */
    public ExpenseBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        cost = new Cost(DEFAULT_COST);
        description = new Description(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the GroupBuilder with the data of {@code groupToCopy}.
     */
    public ExpenseBuilder(Expense expenseToCopy) {
        name = expenseToCopy.getPayer().getName();
        phone = expenseToCopy.getPayer().getPhone();
        email = expenseToCopy.getPayer().getEmail();
        address = expenseToCopy.getPayer().getAddress();
        tags = expenseToCopy.getPayer().getTags();
        cost = expenseToCopy.getCost();
        description = expenseToCopy.getDescription();
    }

    /**
     * Sets the {@code Description} of the {@code Expense} that we are building.
     *
     * @param description The description of the expense.
     * @return An expensebuilder.
     */
    public ExpenseBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ExpenseBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public ExpenseBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public ExpenseBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public ExpenseBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public ExpenseBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Cost} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withCost(String cost) {
        this.cost = new Cost(cost);
        return this;
    }

    /**
     * Sets the {@code List of Person} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withSelfPayees(Person... persons) {
        for (Person person : persons) {
            selfPayees.add(new PersonBuilder(person).build());
        }
        return this;
    }

    /**
     * Sets the {@code List of Person} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withIncluded(Person... persons) {
        for (Person person : persons) {
            this.included.add(new PersonBuilder(person).build());
        }
        return this;
    }

    /**
     * Builds an {@code Expense} class with the relevant information.
     *
     * @return The built Expense class.
     */
    public Expense build() {
        Person person = new Person(name, phone, email, address, tags);
        for (Person p : selfPayees) {

        }
        return new Expense(person, cost, description, included);
    }
}
