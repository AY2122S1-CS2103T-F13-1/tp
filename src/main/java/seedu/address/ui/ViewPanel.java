package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;
import seedu.address.model.group.Group;

/**
 * The View Window. Handles the displaying of individual viewPanel.
 */
public class ViewPanel extends UiPart<Region> {

    private static final String FXML = "ViewPanel.fxml";

    private Logic logic;

    // Panels for toggling
    private PersonListPanel personListPanel;
    private GroupListPanel groupListPanel;

    @FXML
    private StackPane viewListPlaceholder;

    /**
     * Constructor for ViewPanel
     *
     * @param logic To get the respective list for ListPanel to render.
     */
    public ViewPanel(Logic logic) {
        super(FXML);
        this.logic = logic;

        fillInnerParts();
    }

    private void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        groupListPanel = new GroupListPanel(new FilteredList<Group>(FXCollections.observableArrayList()));
        // Need update with logic and get groups

        viewListPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    /**
     * Switches different different view for AddressBook and GroupsPage.
     *
     * @param uiView Page to be changed
     */
    public void toggleView(UiView uiView) {
        viewListPlaceholder.getChildren().clear();

        if (uiView == UiView.ADDRESS_BOOK) {
            viewListPlaceholder.getChildren().add(personListPanel.getRoot());
        } else if (uiView == UiView.GROUP_PAGE) {
            viewListPlaceholder.getChildren().add(groupListPanel.getRoot());
        } else {
            throw new AssertionError("Toggle tab not found");
        }
    }
}
