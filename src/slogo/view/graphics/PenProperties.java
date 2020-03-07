package slogo.view.graphics;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import slogo.view.Styler;
import slogo.view.Visualizer;

/**
 * This class creates a new window in which holds a list of ways to customize the turtle's pen. By opening this window, the
 * user is able to change the pen color, the pen width, and decide if the pen should be up or down.
 */
public class PenProperties {
    private static final String TITLE = "Pen Properties";
    private static final Paint BACKGROUND = Color.web("#808080");
    private static final String RESOURCES = "resources";
    private static final String FORMAT_PACKAGE = RESOURCES + ".formats.";
    private static final String DEFAULT_RESOURCE_FOLDER = RESOURCES + "/formats/";
    private static final String STYLESHEET = "styling.css";
    private static final int SIZE_WIDTH = 300;
    private static final int SIZE_HEIGHT = 170;
    private static final int COLORPICKER_HEIGHT = 30;
    private static final int HBOX_SPACING = 10;
    private static final int VBOX_SPACING = 15;

    private Styler styler;
    private ColorPicker colorPicker;
    private Visualizer myVisualzer;
    private Stage stage;

    /**
     * This constructor sets a new stage and takes in the Visualizer class. It does this as a means of being able to call
     * the turtleView and update its properties.
     * @param visualizer
     */
    public PenProperties(Visualizer visualizer, ResourceBundle resources){
        styler = new Styler(resources);
        myVisualzer = visualizer;
        stage = new Stage();
        stage.setScene(setScene());
        stage.setTitle(TITLE);
    }

    /**
     * Shows the properties window when the button is clicked.
     */
    public void showProperties(){
        stage.show();
    }

    private Scene setScene(){
        Scene myScene = new Scene(createGrid(),SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
        myScene.getStylesheets()
                .add(getClass().getClassLoader().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
                        .toExternalForm());
        return myScene;
    }

    private VBox createGrid(){
        TextField textField = new TextField();
        textField.setPromptText(styler.getResourceText("TextfieldText"));
        HBox hbox1 = new HBox(styler.createLabel("PenColor"), penColor());
        hbox1.setSpacing(HBOX_SPACING);
        HBox hbox2 = new HBox(textField, styler.createButton("ChangePenWidthCommand", e->myVisualzer.getCurrentTurtle().changePenWidth(Double.parseDouble(textField.getText()))));
        hbox2.setSpacing(HBOX_SPACING);
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(styler.getResourceText("PenUp"), styler.getResourceText("PenDown")));
        comboBox.setOnAction(e->myVisualzer.getCurrentTurtle().changePenStatus(comboBox.getValue().equals("Put pen down")));
        HBox hbox3 = new HBox(styler.createLabel("ChangePenCommand"), comboBox);
        hbox3.setSpacing(HBOX_SPACING);
        VBox vbox = new VBox(hbox1, hbox2, hbox3);
        vbox.setSpacing(VBOX_SPACING);
        return vbox;
    }

    private ColorPicker penColor(){
        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        colorPicker.setMaxHeight(COLORPICKER_HEIGHT);
        colorPicker.setOnAction(e -> myVisualzer.getCurrentTurtle().updatePen(colorPicker.getValue()));
        return colorPicker;
    }

    /**
     * In an effort to bind the colorpicker and the displayed color with each turtle when there is more than one turtle,
     * the color picker is returned and used in the visualizer class.
     * @return the colorpicker
     */
    public ColorPicker getColorPicker(){return colorPicker;}

}
