package slogo.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import slogo.controller.Controller;
import java.util.ResourceBundle;

public class MoveTurtle {

    public static final String TITLE = "Move Turtle";
    public static final Paint BACKGROUND = Color.web("#808080");
    private static final String RESOURCES = "resources";
    public static final String FORMAT_PACKAGE = RESOURCES + ".formats.";
    public static final String DEFAULT_RESOURCE_FOLDER = RESOURCES + "/formats/";
    private static final String STYLESHEET = "styling.css";
    public static final int SIZE_WIDTH = 210;
    public static final int SIZE_HEIGHT = 105;

    private Controller myController;
    private Styler styler;

    public MoveTurtle(Controller controller){
        this.myController = controller;
        styler = new Styler();
        Stage stage = new Stage();
        stage.setScene(setScene());
        stage.setTitle(TITLE);
        stage.show();
    }

    private Scene setScene(){
        ResourceBundle myResources = ResourceBundle.getBundle(FORMAT_PACKAGE + "English");
        Group root = new Group();
        Scene myScene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
        myScene.getStylesheets()
                .add(getClass().getClassLoader().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
                        .toExternalForm());
        HBox hbox1 = new HBox(styler.createButton(myResources.getString("ForwardCommand"), e-> myController.sendCommands("fd 1")),
                styler.createButton(myResources.getString("BackwardCommand"), e-> myController.sendCommands("bk 1")));
        HBox hbox2 = new HBox(styler.createButton(myResources.getString("RRotateCommand"), e-> myController.sendCommands("rt 1")),
                styler.createButton(myResources.getString("LRotateCommand"), e-> myController.sendCommands("lt 1")));
        VBox vbox = new VBox(hbox1, hbox2);
        root.getChildren().add(vbox);
        return myScene;
    }
}
