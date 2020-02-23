package slogo.view;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import slogo.controller.Controller;
import slogo.model.Parser;

public class CommandLine {
  public static final int TEXTBOX_WIDTH = 500;
  public static final int TEXTBOX_HEIGHT = 100;

  public static final String RESOURCE = "resources.languages";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCE + ".";

  private Controller myController;
  private ResourceBundle myResources;
  private TextArea textBox;

  public CommandLine(Controller controller){
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Buttons");
    myController = controller;
  }

  public Node setupCommandLine(){
    HBox commandLine = new HBox();

    textBox = new TextArea();
    textBox.setEditable(true);
    textBox.wrapTextProperty();
    textBox.setMaxWidth(TEXTBOX_WIDTH);
    textBox.setMaxHeight(TEXTBOX_HEIGHT);
    textBox.setPromptText(myResources.getString("TextBoxFiller"));
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

//    commandLine.setLayoutX(XPOS_OFFSET);
//    commandLine.setLayoutY(2 * YPOS_OFFSET + TURTLE_SCREEN_HEIGHT);

    return commandLine;
  }

  private void submitCommand() {
    if((textBox.getText() != null) && !textBox.getText().isEmpty()){
      myController.runCommand(textBox.getText());
      textBox.clear();
    }
  }
}
