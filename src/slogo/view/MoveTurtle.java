package slogo.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import slogo.controller.Controller;

import java.util.ResourceBundle;

public class MoveTurtle {

    public final String TITLE = "Move Turtle";
    public static final Paint BACKGROUND = Color.AZURE;
    private static final String RESOURCES = "resources";
    public static final String FORMAT_PACKAGE = RESOURCES + ".formats.";
    public static final int SIZE_WIDTH = 85;
    public static final int SIZE_HEIGHT = 110;

    private Controller myController;
    private Styler styler;
    private ResourceBundle myResources;
    private Group root;
    private Scene myScene;

    public MoveTurtle(Controller controller){
        this.myController = controller;
        styler = new Styler();
        Stage stage = new Stage();
        stage.setScene(setScene());
        stage.setTitle(TITLE);
        stage.show();
    }

    private Scene setScene(){
        myResources = ResourceBundle.getBundle(FORMAT_PACKAGE + "English");
        root = new Group();
        VBox vbox = new VBox(styler.createButton(myResources.getString("ForwardCommand"), e-> myController.sendCommands("fd 1")),
                styler.createButton(myResources.getString("BackwardCommand"), e-> myController.sendCommands("bk 1")),
                styler.createButton(myResources.getString("RRotateCommand"), e-> myController.sendCommands("rt 1")),
                styler.createButton(myResources.getString("LRotateCommand"), e-> myController.sendCommands("lt 1")));
        root.getChildren().add(vbox);
        return myScene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
    }
}
