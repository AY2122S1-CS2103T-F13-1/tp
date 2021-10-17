package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_EXCLUDE;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.Model;
import seedu.awe.model.expense.Cost;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.group.exceptions.DuplicateGroupException;
import seedu.awe.model.person.Person;

/**
 * Adds an expense to a group.
 */
public class AddExpenseCommand extends Command {

    public static final String COMMAND_WORD = "addexpense";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense to a group. "
            + "Parameters: "
            + PREFIX_NAME + "PAYER NAME "
            + PREFIX_GROUP_NAME + "GROUP NAME "
            + PREFIX_COST + "COST "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_NAME + "PAYEE NAME" + "] "
            + "[" + PREFIX_COST + "PAYEE EXPENSE" + "]"
            + "[" + PREFIX_EXCLUDE + "EXCLUDED PERSON" + "]";

    public static final String MESSAGE_SUCCESS = "Expense added!";
    public static final String MESSAGE_NOT_PART_OF_GROUP = "The person isn't part of the specified group!";
    public static final String MESSAGE_ALL_MEMBERS_EXCLUDED = "You can't exclude every member of the group!";
    public static final String MESSAGE_COST_ZERO_OR_LESS = "The cost of this expense is zero or less!";
    public static final String MESSAGE_CANNOT_ADD_EXCLUDED_MEMBER = "You tried to add an expense"
            + "for an excluded member!";

    private Expense expense;
    private final GroupName groupName;
    private final List<Person> selfPayees;
    private final List<Cost> selfCosts;
    private final List<Person> excluded;

    /**
     * Creates a AddExpenseCommand from the specified Person {@code Index} for the specified {@code Group}
     *
     * @param expense Expense to be added.
     * @param groupName Name of group to add expense to.
     * @param selfPayees List of persons to exclude from the expense.
     * @param selfCosts List of costs to exclude from the expense.
     */
    public AddExpenseCommand(Expense expense, GroupName groupName,
                             List<Person> selfPayees, List<Cost> selfCosts, List<Person> excluded) {
        requireNonNull(expense);
        requireNonNull(groupName);
        requireAllNonNull(selfPayees);
        requireAllNonNull(selfCosts);

        this.expense = expense;
        this.groupName = groupName;
        this.selfPayees = selfPayees;
        this.selfCosts = selfCosts;
        this.excluded = excluded;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, DuplicateGroupException {
        requireNonNull(model);

        Group group = model.getGroupByName(groupName);
        Cost finalCost = expense.getCost();

        if (!group.isPartOfGroup(expense.getPayer())) {
            return new CommandResult(MESSAGE_NOT_PART_OF_GROUP);
        }

        for (Person exclude : excluded) {
            if (!group.isPartOfGroup(exclude)) {
                return new CommandResult(MESSAGE_NOT_PART_OF_GROUP);
            }
        }

        if (group.getMembers().size() == excluded.size()) {
            return new CommandResult(MESSAGE_ALL_MEMBERS_EXCLUDED);
        }

        HashMap<Person, Cost> paidByPayees = new HashMap<>();

        for (int i = 0; i < selfPayees.size(); i++) {
            Person currentPayer = selfPayees.get(i);
            Cost currentCost = selfCosts.get(i);
            if (excluded.contains(currentPayer)) {
                return new CommandResult(MESSAGE_CANNOT_ADD_EXCLUDED_MEMBER);
            }
            if (currentPayer == null || !group.isPartOfGroup(currentPayer)) {
                return new CommandResult(MESSAGE_NOT_PART_OF_GROUP);
            }
            finalCost = finalCost.subtract(currentCost);
            if (!paidByPayees.containsKey(currentPayer)) {
                paidByPayees.put(currentPayer, currentCost);
            } else {
                paidByPayees.computeIfPresent(currentPayer, (key, val) -> val.add(currentCost));
            }
        }

        if (finalCost.cost <= 0) {
            return new CommandResult(MESSAGE_COST_ZERO_OR_LESS);
        }

        ArrayList<Person> groupMembers = new ArrayList<>(group.getMembers());
        for (Person toExclude : excluded) {
            groupMembers.remove(toExclude);
        }

        expense = expense.setCost(finalCost);
        expense = expense.setIncluded(groupMembers);

        Group newGroup = group.addExpenseWithIndivPayments(expense, paidByPayees);
        model.setGroup(group, newGroup);
        model.addExpense(expense, newGroup);
        return new CommandResult(String.format(MESSAGE_SUCCESS, expense));
    }

    public Expense getExpense() {
        return expense;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof AddExpenseCommand)) {
            return false;
        } else {
            AddExpenseCommand otherCommand = (AddExpenseCommand) other;
            return expense.equals(otherCommand.expense)
                    && groupName.equals(otherCommand.groupName);
        }
    }
}
