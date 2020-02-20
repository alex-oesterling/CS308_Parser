package slogo;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import slogo.controller.Controller;
import slogo.model.Parser;

public class SlogoApp {
  /**
   * Start of the program.
   */
  public static final String TITLE = "Parser";
  public static final int SIZE_WIDTH = 1000;
  public static final int SIZE_HEIGHT = 800;
  public static final Paint BACKGROUND = Color.AZURE;

  private Group root;
  private Stage stage;

  public SlogoApp(Stage stage){
    Parser myParser = new Parser();
    Controller myController = new Controller(myParser);
    
    Scene scene = setupScene();
    this.stage = stage;
    stage.setScene(scene);
    stage.setTitle(TITLE);
    stage.show();
  }

  /*
  FIXME Alex Oesterling: I was thinking Visualizer creates a node with all the stuff and passes it back here to be placed in the stage?
  Visualizer could also have the setupScene() method and pass it back up to the App through the controller
   */

  private Scene setupScene(){
    root = new Group();
    return new Scene(root, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
  }
}
