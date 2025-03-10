package seedu.awe.logic.parser;

import static seedu.awe.commons.core.Messages.MESSAGE_GROUPADDTAGCOMMAND_USAGE;
import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.awe.commons.core.Messages.MESSAGE_NONEXISTENT_GROUP;
import static seedu.awe.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.awe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.awe.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.awe.logic.commands.GroupAddTagCommand;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.tag.Tag;
import seedu.awe.testutil.ModelBuilder;

public class GroupAddTagCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_GROUPADDTAGCOMMAND_USAGE);
    private static final String MESSAGE_GROUP_NAME_INVALID = GroupName.MESSAGE_CONSTRAINTS;
    private static final Tag[] tags = {new Tag("Friends"), new Tag("family")};
    private static final Tag[] tagsInGroup = {new Tag("friends"), new Tag("3days2night")};
    private static final Set<Tag> TAGS_NOT_IN_GROUP = new HashSet<>(Arrays.asList(tags));
    private static final Set<Tag> TAGS_IN_GROUP = new HashSet<>(Arrays.asList(tagsInGroup));
    private GroupAddTagCommandParser parser = new GroupAddTagCommandParser(new ModelBuilder().build());


    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // empty input
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);

        // only 1 group name specified
        assertParseFailure(parser, " gn/London", MESSAGE_INVALID_FORMAT);

        // only 1 member name specified
        assertParseFailure(parser, " t/friends", MESSAGE_INVALID_FORMAT);

        // empty gn tag
        assertParseFailure(parser, " gn/ t/friends", MESSAGE_GROUP_NAME_INVALID);

        // preambleEmpty
        assertParseFailure(parser, PREAMBLE_NON_EMPTY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // 1 gn/ tag, 1 t/ tag but no group name
        assertParseFailure(parser, " gn/ t/friends", MESSAGE_GROUP_NAME_INVALID);

        // 1 gn/ tag, 1 t/ tag but no tag name
        assertParseFailure(parser, " gn/London t/", Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidArguments_failure() {
        // invalid group name
        assertParseFailure(parser, " gn/* t/friends", MESSAGE_GROUP_NAME_INVALID);

        // invalid member name
        assertParseFailure(parser, " gn/Bali t/%s", Tag.MESSAGE_CONSTRAINTS);

        //non existent group
        assertParseFailure(parser, " gn/nonexistentGroup t/friends",
                String.format(MESSAGE_NONEXISTENT_GROUP, "nonexistentGroup"));
    }

    @Test
    public void parse_validGroupName_success() {
        //Adding duplicate tags into group
        GroupAddTagCommand expectedCommandDuplicateTags = new GroupAddTagCommand(new GroupName("Bali"), TAGS_IN_GROUP);
        assertParseSuccess(parser, " gn/Bali t/friends t/3days2night", expectedCommandDuplicateTags);

        //reset parser
        parser = new GroupAddTagCommandParser(new ModelBuilder().build());

        //Adding tags into group
        GroupAddTagCommand expectedCommand = new GroupAddTagCommand(new GroupName("Bali"),
                TAGS_NOT_IN_GROUP);
        assertParseSuccess(parser, " gn/Bali t/Friends t/family", expectedCommand);

        //reset parser
        parser = new GroupAddTagCommandParser(new ModelBuilder().build());

        // more than 1 group name
        GroupAddTagCommand expectedCommandDuplicateGroup = new GroupAddTagCommand(new GroupName("Bali"),
                TAGS_NOT_IN_GROUP);
        assertParseSuccess(parser, " gn/Japan gn/Bali t/Friends t/family", expectedCommandDuplicateGroup);
    }
}
