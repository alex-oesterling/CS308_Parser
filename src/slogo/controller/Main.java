package slogo.controller;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main {
    /**
     * Start of the program.
     */
    public static final String TITLE = "Parser";
    public static final int SIZE_WIDTH = 1000;
    public static final int SIZE_HEIGHT = 800;
    public static final Paint BACKGROUND = Color.AZURE;

    private Group root;
    private Stage stage;

    public void start (Stage stage) {
        Scene scene = setupScene();
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.show();
    }

    private Scene setupScene(){
        root = new Group();
        return new Scene(root, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
    }

    public static void main (String[] args) {
        launch(args);
    }
}
