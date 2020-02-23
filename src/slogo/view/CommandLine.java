package slogo.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import slogo.controller.Controller;
import slogo.model.Parser;

public class CommandLine {
  private Controller myController;
  TextArea textBox;

  public CommandLine(Controller controller){
    myController = controller;
  }

  public Node setupCommandLine(){
    HBox commandLine = new HBox();

    textBox = new TextArea();
    textBox.setPromptText("Enter command:");
    commandLine.getChildren().add(textBox);

    //FIXME language labels/properties
    Button run = new Button("Run");
    run.setOnAction(e->submitCommand());
    commandLine.getChildren().add(run);

    Button clear = new Button("Clear");
    clear.setOnAction(e->{
      textBox.clear();
    });
    commandLine.getChildren().add(clear);

    return commandLine;
  }

  private void submitCommand() {
    if((textBox.getText() != null) && !textBox.getText().isEmpty()){
      myController.runCommand(textBox.getText());
      textBox.clear();
    }
  }
}
