package seedu.awe.model.group;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.awe.model.person.Person;
import seedu.awe.model.tag.Tag;

public class Group {
    public static final String MESSAGE_CONSTRAINTS = "MESSAGE_CONSTRAINT TO BE COMPLETED";
    //TODO: WRITE MESSAGE CONSTRAINTS MESSAGE
    private GroupName groupName;
    private final ArrayList<Person> members = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Creates new Group object.
     *
     * @param groupName String object representing name of the group.
     * @param members ArrayList of Person objects representing list of members.
     */
    public Group(GroupName groupName, ArrayList<Person> members) {
        this.groupName = groupName;
        for (Person member : members) {
            this.addMember(member);
        }
    }

    /**
     * Creates new Group object with tags.
     *
     * @param groupName String object representing name of the group.
     * @param members ArrayList of Person objects representing list of members.
     * @param tags Set of Tag objects to describe group.
     */
    public Group(GroupName groupName, ArrayList<Person> members, Set<Tag> tags) {
        this.groupName = groupName;
        for (Person member : members) {
            this.addMember(member);
        }
        this.tags.addAll(tags);
    }

    /**
     * Adds member to Group.
     *
     * @param member Person object representing member to be added to group.
     */
    public void addMember(Person member) {
        this.members.add(member);
    }

    /**
     * Removes member from Group.
     *
     * @param member Person object representing member to be removed from group.
     */
    public void removeMember(Person member) {
        this.members.remove(member);
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public ArrayList<Person> getMembers() {
        return members;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public boolean isSameGroup(Group group) {
        return this.groupName.equals(group.getGroupName());
    }

    @Override
    public String toString() {
        //TODO: TO BE IMPROVED TO POSSIBLY LIST ALL MEMBERS NAMES
        return String.format("Group %s with %d members", this.groupName, this.members.size());
    }
}
