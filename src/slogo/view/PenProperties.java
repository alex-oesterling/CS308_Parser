package slogo.view;

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

public class PenProperties {

    public static final String TITLE = "Pen Properties";
    public static final Paint BACKGROUND = Color.web("#808080");
    public static final String RESOURCES = "resources";
    public static final String FORMAT_PACKAGE = RESOURCES + ".formats.";
    public static final String DEFAULT_RESOURCE_FOLDER = RESOURCES + "/formats/";
    public static final String STYLESHEET = "styling.css";
    public static final int SIZE_WIDTH = 300;
    public static final int SIZE_HEIGHT = 170;
    public static final int COLORPICKER_HEIGHT = 30;
    public static final int HBOX_SPACING = 10;
    public static final int VBOX_SPACING = 15;

    private Styler styler;
    private ResourceBundle myResources;
    private ColorPicker colorPicker;
    private Visualizer myVisualzer;
    private Stage stage;

    public PenProperties(Visualizer visualizer){
        styler = new Styler();
        myVisualzer = visualizer;
        stage = new Stage();
        stage.setScene(setScene());
        stage.setTitle(TITLE);
    }

    public void showProperties(){
        stage.show();
    }

    private Scene setScene(){
        myResources = ResourceBundle.getBundle(FORMAT_PACKAGE + "English");
        Scene myScene = new Scene(createGrid(),SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
        myScene.getStylesheets()
                .add(getClass().getClassLoader().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
                        .toExternalForm());
        return myScene;
    }

    private VBox createGrid(){
        TextField textField = new TextField();
        textField.setPromptText(myResources.getString("TextfieldText"));
        HBox hbox1 = new HBox(styler.createLabel(myResources.getString("PenColor")), penColor());
        hbox1.setSpacing(HBOX_SPACING);
        HBox hbox2 = new HBox(textField, styler.createButton(myResources.getString("ChangePenWidthCommand"), e->myVisualzer.getCurrentTurtle().changePenWidth(Double.parseDouble(textField.getText()))));
        hbox2.setSpacing(HBOX_SPACING);
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(myResources.getString("PenUp"), myResources.getString("PenDown")));
        comboBox.setOnAction(e->myVisualzer.getCurrentTurtle().changePenStatus(comboBox.getValue().equals("Put pen down")));
        HBox hbox3 = new HBox(styler.createLabel(myResources.getString("ChangePenCommand")), comboBox);
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

    public ColorPicker getColorPicker(){return colorPicker;}

}
