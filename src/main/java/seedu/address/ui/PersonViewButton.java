package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import seedu.address.ui.listener.PersonButtonListener;

/**
 * A button to toggle the view to PersonPanel.
 */
public class PersonViewButton extends UiPart<Region> {

    private static final String FXML = "PersonViewButton.fxml";

    @FXML
    private Button personViewButton;

    /**
     * Constructor for PersonViewButton.
     *
     * @param viewPanel viewPanel to set onclick action
     */
    public PersonViewButton(ViewPanel viewPanel) {
        super(FXML);
        personViewButton.setOnAction(new PersonButtonListener(viewPanel));
    }


}
