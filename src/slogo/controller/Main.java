package slogo.controller;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import slogo.view.Visualizer;


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    /**
     * Start of the program.
     */
    public static final String TITLE = "Parser";
    public static final int SIZE_WIDTH = 1000;
    public static final int SIZE_HEIGHT = 800;
    public static final Paint BACKGROUND = Color.AZURE;

    private Stage stage;
    private Visualizer visualizer;

    @Override
    public void start (Stage stage) {
        visualizer = new Visualizer();
        Scene scene = visualizer.setupScene(SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.show();
    }


    public static void main (String[] args) {
        launch(args);
    }
}
