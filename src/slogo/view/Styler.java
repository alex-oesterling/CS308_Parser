package slogo.view;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;


public class Styler {

    public static final int COLORPICKER_HEIGHT = 30;

    public Label createLabel(String string){
        Label label = new Label(string);
        return label;
    }

    public Button createButton(String string, EventHandler e){
        Button button = new Button(string);
        button.setOnAction(e);
        return button;
    }

}
