package seedu.awe.testutil;

import static seedu.awe.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.awe.logic.commands.CreateGroupCommand;
import seedu.awe.model.group.Group;

/**
 * A utility class for Group.
 */
public class GroupUtil {

    /**
     * Returns an add command string for adding the {@code group}.
     */
    public static String getAddCommand(Group group) {
        return CreateGroupCommand.COMMAND_WORD + " " + getGroupDetails(group);
    }

    /**
     * Returns the part of command string for the given {@code group}'s details.
     */
    public static String getGroupDetails(Group group) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_GROUP_NAME + group.getGroupName().getName() + " ");

        group.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );

        return sb.toString();
    }

    /*
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.

    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }*/
}
