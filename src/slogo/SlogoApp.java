package slogo;

import javafx.stage.Stage;
import slogo.view.Visualizer;

public class SlogoApp {
  /**
   * Start of the program.
   */
  public static final String TITLE = "i like turtles <3";

  public SlogoApp(Stage stage){
    Visualizer myVisualizer = new Visualizer();
    stage.setScene(myVisualizer.setupScene());
    stage.setTitle(TITLE);
    stage.show();
  }
}
