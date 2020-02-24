package slogo.view;


import static slogo.view.Visualizer.TURTLE_SCREEN_HEIGHT;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import slogo.controller.Controller;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import slogo.controller.Controller;
import slogo.exceptions.InvalidCommandException;


public class CommandLine {
  public static final int TEXTBOX_WIDTH = 200;
  public static final int TEXTBOX_HEIGHT = 100;
  public static final int BUTTON_WIDTH = 50;
  public static final int XPOS_OFFSET = 10;
  public static final int YPOS_OFFSET = 10;
  public static final int TURTLE_SCREEN_HEIGHT = 500;
  public static final String INVALID_COMMAND = "Invalid command: ";



  public static final String RESOURCE = "resources.languages";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCE + ".";

  private Controller myController;
  private ResourceBundle myResources;
  private TextArea textBox;
  private List<Label> history;
  private VBox historyBox;

  public CommandLine(Controller controller){
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Buttons");
    myController = controller;
    history = new ArrayList<>();
    historyBox = new VBox();
  }

  public Node setupCommandLine(){
    VBox commandLine = new VBox();
    ScrollPane terminal = new ScrollPane();
    VBox historyBox = new VBox();
    terminal.setContent(historyBox);
    terminal.setPrefSize(TEXTBOX_WIDTH,TURTLE_SCREEN_HEIGHT-TEXTBOX_HEIGHT);
//    terminal.setMaxWidth(TEXTBOX_WIDTH*2);

    historyBox.heightProperty().addListener((obs, old, newValue) -> terminal.setVvalue((Double)newValue));
    HBox userControls = new HBox();

    textBox = new javafx.scene.control.TextArea();
    textBox.setEditable(true);
    textBox.wrapTextProperty();

    textBox.setMaxWidth(TEXTBOX_WIDTH-BUTTON_WIDTH);
    textBox.setMaxHeight(TEXTBOX_HEIGHT);
    textBox.setPromptText(myResources.getString("TextBoxFiller"));
    userControls.getChildren().add(textBox);

    VBox buttonBox = new VBox();
    Button run = new Button(myResources.getString("RunCommand"));
    run.setMinWidth(BUTTON_WIDTH);
    run.setOnAction(e->submitCommand());
    buttonBox.getChildren().add(run);

    Button clear = new Button(myResources.getString("ClearCommand"));
    clear.setOnAction(e->{
      textBox.clear();
    });
    buttonBox.getChildren().add(clear);

//    commandLine.setLayoutX(XPOS_OFFSET);
//    commandLine.setLayoutY(2 * YPOS_OFFSET + TURTLE_SCREEN_HEIGHT);

    userControls.getChildren().add(buttonBox);
    commandLine.setVgrow(terminal, Priority.ALWAYS);
    commandLine.getChildren().add(terminal);
    commandLine.getChildren().add(userControls);
//    commandLine.setLayoutX(2* XPOS_OFFSET+TURTLE_SCREEN_HEIGHT);
//    commandLine.setLayoutY(YPOS_OFFSET);

    return commandLine;
  }

  private void submitCommand() {
    if((textBox.getText() != null) && !textBox.getText().isEmpty()){
      try {
        myController.runCommand(textBox.getText());
      } catch (InvalidCommandException e){
        Label recentCommand = new Label(INVALID_COMMAND + textBox.getText());
        recentCommand.setTextFill(Color.RED);
        history.add(recentCommand);
        historyBox.getChildren().add(recentCommand);
        return;
      }
      Label recentCommand = new Label(textBox.getText());
      history.add(recentCommand);
      historyBox.getChildren().add(recentCommand);
      textBox.clear();
    }
  }
}
