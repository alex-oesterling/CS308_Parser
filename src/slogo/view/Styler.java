package slogo.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class Styler {

    public Label createLabel(String string){
        return new Label(string);
    }

    public Button createButton(String string, EventHandler e){
        Button button = new Button(string);
        button.setOnAction(e);
        return button;
    }

    /**
     * Handles the creation of MenuItem objects, applies a label to them as specified in the createLabel method and
     * gives them an action on click.
     * @param property - the label to be applied to the object specified in the .properties file
     * @param handler - the action to occur when the menu item is clicked
     * @return a MenuItem to be placed in a Menu object with a label and action on click
     */
    public MenuItem makeMenuItem(String property, EventHandler<ActionEvent> handler) {
        MenuItem result = new MenuItem();
        createLabel(property, result);
        result.setOnAction(handler);
        return result;
    }

    /**
     * Handles the creation of Menu objects, applies a label to them as specified in the createLabel method
     * @param property - the label to be applied to the menu, from the .properties file
     * @return a Menu to be rendered on stage with a label
     */
    public Menu makeMenu(String property) {
        Menu result = new Menu();
        createLabel(property, result);
        return result;
    }

    /**
     * Handles the creation of Menu and MenuItem labels
     * @param property - the label to be applied to the items, from the .properties file
     * @param result - the menu item to which the label is applied
     */
    private void createLabel(String property, MenuItem result) {
        String label = myResources.getString(property);
        result.setText(label);
    }
}
