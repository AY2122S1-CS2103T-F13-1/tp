package seedu.awe.testutil;

import static seedu.awe.testutil.TypicalPersons.ALICE;
import static seedu.awe.testutil.TypicalPersons.AMY;
import static seedu.awe.testutil.TypicalPersons.BENSON;
import static seedu.awe.testutil.TypicalPersons.BOB;
import static seedu.awe.testutil.TypicalPersons.CARL;
import static seedu.awe.testutil.TypicalPersons.DANIEL;
import static seedu.awe.testutil.TypicalPersons.ELLE;
import static seedu.awe.testutil.TypicalPersons.FIONA;
import static seedu.awe.testutil.TypicalPersons.GEORGE;
import static seedu.awe.testutil.TypicalPersons.HOON;
import static seedu.awe.testutil.TypicalPersons.IDA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.awe.model.AddressBook;
import seedu.awe.model.group.Group;
import seedu.awe.model.person.Person;

/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroups {

    public static final Group BALI = new GroupBuilder().withGroupName("Bali")
            .withMembers(ALICE, BOB, AMY)
            .build();
    public static final Group OSLO = new GroupBuilder().withGroupName("Oslo")
            .withMembers(CARL, ELLE, DANIEL)
            .withTags("friends").build();
    public static final Group MALIBU = new GroupBuilder().withGroupName("Malibu")
            .withMembers(ALICE, ELLE, HOON)
            .withTags("friends").build();
    public static final Group INDIA = new GroupBuilder().withGroupName("India")
            .withMembers(AMY, BOB, BENSON)
            .withTags("friends").build();
    public static final Group CHINA = new GroupBuilder().withGroupName("China")
            .withMembers(IDA, ELLE, AMY)
            .withTags("friends").build();
    public static final Group DUBAI = new GroupBuilder().withGroupName("Dubai")
            .withMembers(FIONA, IDA, GEORGE)
            .withTags("friends").build();
    public static final Group DOHA = new GroupBuilder().withGroupName("Doha")
            .withMembers(FIONA, BENSON, ALICE)
            .withTags("friends").build();

    // Manually added
    public static final Group LONDON = new GroupBuilder().withGroupName("London")
            .withMembers(AMY, BENSON, GEORGE)
            .withTags("friends").build();
    public static final Group HELSINKI = new GroupBuilder().withGroupName("Helsinki")
            .withMembers(GEORGE, ELLE, DANIEL)
            .withTags("friends").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Group PARIS = new GroupBuilder().withGroupName("Paris")
            .withMembers(HOON, CARL, FIONA)
            .withTags("friends").build();
    public static final Group AMSTERDAM = new GroupBuilder().withGroupName("Amsterdam")
            .withMembers(HOON, IDA, ELLE)
            .withTags("friends").build();

    public static final Group VIENNA_NOT_IN_GROUPS = new GroupBuilder().withGroupName("Vienna")
            .withMembers(BENSON, IDA, FIONA)
            .withTags("friends").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalGroups() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons and groups.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Group group : getTypicalGroups()) {
            ab.addGroup(group);
        }
        return ab;
    }

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(BALI, LONDON, OSLO, HELSINKI, AMSTERDAM, INDIA,
                DUBAI, PARIS, DOHA, CHINA));
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
