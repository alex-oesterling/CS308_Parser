package slogo;

import javafx.scene.Group;
import javafx.stage.Stage;
import slogo.model.Parser;
import slogo.view.Visualizer;

public class SlogoApp {
  /**
   * Start of the program.
   */
  public static final String TITLE = "Parser";

  private Stage stage;

  public SlogoApp(Stage stage){
    Parser myParser = new Parser();
    Visualizer myVisualizer = new Visualizer(myParser);

    stage = stage;
    stage.setScene(myVisualizer.setupScene());
    stage.setTitle(TITLE);
    stage.show();
  }
}