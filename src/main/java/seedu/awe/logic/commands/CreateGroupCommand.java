package seedu.awe.logic.commands;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.Model;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.group.exceptions.DuplicateGroupException;
import seedu.awe.model.person.Person;

public class CreateGroupCommand extends Command {
    public static final String COMMAND_WORD = "creategroup";
    public static final String MESSAGE_SUCCESS = "New group created";
    public static final String MESSAGE_ERROR = "Group not created. Be sure to use the exact names of group members";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists";
    public static final String MESSAGE_USAGE = "creategroup gn/[GROUPNAME] n/[NAME1] n/[OPTIONAL NAME2]...";
    public static final String MESSAGE_EMPTY_GROUP = "Group requires at least 1 member. \n%1$s";

    private final ArrayList<Person> members;
    private GroupName groupName;
    private final boolean validCommand;

    /**
     * Creates a CreateGroupCommand to create the specified {@code Group}
     */
    public CreateGroupCommand(GroupName groupName, ArrayList<Person> members, boolean validCommand) {
        this.groupName = groupName;
        this.members = members;
        this.validCommand = validCommand;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, DuplicateGroupException {
        requireNonNull(model);
        if (!validCommand) {
            return new CommandResult(MESSAGE_ERROR);
        }
        Group group = new Group(groupName, members);
        model.addGroup(group);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
