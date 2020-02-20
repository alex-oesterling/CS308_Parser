package slogo.view;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import slogo.controller.Controller;
import slogo.model.Parser;

public class Visualizer implements ViewExternalAPI{
  public static final int SIZE_WIDTH = 1000;
  public static final int SIZE_HEIGHT = 800;
  public static final Paint BACKGROUND = Color.AZURE;

  Scene myScene;
  Parser myParser;
  Controller myController;
  Group root;
  TextField textBox;

  public Visualizer (Parser parser){
    myParser = parser;
    myController = new Controller(parser, this);
  }


  public Scene setupScene(){
    root = new Group();
    root.getChildren().add(setupCommandLine());
    myScene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
    return myScene;
  }

  private Node setupCommandLine(){
    HBox commandLine = new HBox();

    textBox = new TextField();
    textBox.setPromptText("Enter command:");
    commandLine.getChildren().add(textBox);

    //FIXME language labels/properties
    Button run = new Button("Run");
    run.setOnAction(e->submitCommand());
    commandLine.getChildren().add(run);

    return commandLine;
  }

  private void submitCommand() {
    if((textBox.getText() != null) && !textBox.getText().isEmpty()){
      myController.setCommand(textBox.getText());
    }
  }

  @Override
  public void updateXPos() {

  }

  @Override
  public void updateYPos() {

  }

  @Override
  public void updateOrientation() {

  }

  @Override
  public void updatePenColor() {

  }

  @Override
  public void updateSceneColor() {

  }
}
