package slogo.view.graphics;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import slogo.controller.Controller;
import java.util.ResourceBundle;
import slogo.view.Styler;
import slogo.view.Visualizer;

/**
 * This class creates a new window with a list of buttons in which can move the turtle forward, backward, rotate left,
 * and rotate right. By clicking each button, the turtle moves by 1, in this way if you keep clicking the button, the turtle
 * will continue to move 1.
 */
public class MoveTurtle {
    private static final String TITLE = "Move Turtle";
    private static final Paint BACKGROUND = Color.web("#808080");
    private static final String RESOURCES = "resources";
    private static final String DEFAULT_RESOURCE_FOLDER = RESOURCES + "/formats/";
    private static final String STYLESHEET = "styling.css";
    private static final int SIZE_WIDTH = 210;
    private static final int SIZE_HEIGHT = 105;

    private Visualizer myVisualizer;
    private Styler styler;

    /**
     * This constructor takes in the controller as a means of updating the turtle's position every time a button is clicked.
     * @param visualizer
     */
    public MoveTurtle(Visualizer visualizer, ResourceBundle resources){
        myVisualizer = visualizer;
        styler = new Styler(resources);
        Stage stage = new Stage();
        stage.setScene(setScene());
        stage.setTitle(TITLE);
        stage.show();
    }

    private Scene setScene(){
        Group root = new Group();
        Scene myScene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
        myScene.getStylesheets()
                .add(getClass().getClassLoader().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
                        .toExternalForm());
        HBox hbox1 = new HBox(styler.createButton("ForwardCommand", e-> myVisualizer.sendCommands("fd 1")),
                styler.createButton("BackwardCommand", e-> myVisualizer.sendCommands("bk 1")));
        HBox hbox2 = new HBox(styler.createButton(("RRotateCommand"), e-> myVisualizer.sendCommands("rt 1")),
                styler.createButton("LRotateCommand", e-> myVisualizer.sendCommands("lt 1")));
        VBox vbox = new VBox(hbox1, hbox2);
        root.getChildren().add(vbox);
        return myScene;
    }
}
