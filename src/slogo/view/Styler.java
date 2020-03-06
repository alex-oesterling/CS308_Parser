package slogo.view;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * In an effort to reduce duplicate code, this class is called to create labels and buttons.
 */
public class Styler {

    /**
     * Given a string, creates a label.
     * @param string
     * @return
     */
    public Label createLabel(String string){
        return new Label(string);
    }

    /**
     * Given a string and an event, creates a button.
     * @param string - the label on the button
     * @param e - Eventhandler that is executed when a button is clicked
     * @return a Button
     */
    public Button createButton(String string, EventHandler e){
        Button button = new Button(string);
        button.setOnAction(e);
        return button;
    }
}
