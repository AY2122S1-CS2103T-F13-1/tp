package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.Model;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;

public class GroupEditNameCommand extends Command {
    public static final String COMMAND_WORD = "groupeditname";
    public static final String MESSAGE_SUCCESS = "Group name changed to %1$s";
    public static final String MESSAGE_ERROR = "Group name not changed.";
    public static final String MESSAGE_USAGE = "groupeditname gn/[OLDGROUPNAME] gn/[NEWGROUPNAME]";
    public static final String MESSAGE_NONEXISTENT_GROUP = "Group %1$s does not exist.";

    private final GroupName oldGroupName;
    private final GroupName newGroupName;
    private final boolean isValidCommand;

    /**
     * Creates a GroupEditNameCommand to edit the specified {@code GroupName}
     */
    public GroupEditNameCommand(GroupName oldGroupName, GroupName newGroupName, boolean isValidCommand) {
        requireAllNonNull(newGroupName, isValidCommand);
        this.oldGroupName = oldGroupName;
        this.newGroupName = newGroupName;
        this.isValidCommand = isValidCommand;
    }

    public GroupName getOldGroupName() {
        return oldGroupName;
    }

    public GroupName getNewGroupName() {
        return newGroupName;
    }

    public boolean getValidCommand() {
        return isValidCommand;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!isValidCommand) {
            throw new CommandException(MESSAGE_ERROR + "\n" + MESSAGE_USAGE);
        }

        Group oldGroup = model.getGroupByName(oldGroupName);
        if (Objects.isNull(oldGroup)) {
            throw new CommandException(String.format(MESSAGE_NONEXISTENT_GROUP, oldGroupName));
        }
        Group newGroup = new Group(newGroupName, oldGroup.getMembers(), oldGroup.getTags());
        model.deleteGroup(oldGroup);
        model.addGroup(newGroup);
        return new CommandResult(String.format(MESSAGE_SUCCESS, newGroupName));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GroupEditNameCommand)) {
            return false;
        }
        GroupEditNameCommand otherCommand = (GroupEditNameCommand) other;
        if (this.isValidCommand == otherCommand.getValidCommand()
                && this.oldGroupName.equals(otherCommand.getOldGroupName())
                && this.newGroupName.equals(otherCommand.getNewGroupName())) {
            return true;
        }
        return false;
    }
}
