package seedu.address.ui.listener;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import seedu.address.ui.UiView;
import seedu.address.ui.ViewPanel;

public class PersonButtonListener implements EventHandler<ActionEvent> {

    private ViewPanel viewPanel;

    public PersonButtonListener(ViewPanel viewPanel) {
        this.viewPanel = viewPanel;
    }

    /**
     * Invoked when a specific event of the type for which this handler is
     * registered happens.
     *
     * @param event the event which occurred
     */
    @Override
    public void handle(ActionEvent event) {
        viewPanel.toggleView(UiView.ADDRESS_BOOK);
    }
}
