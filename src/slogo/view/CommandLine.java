package slogo.view;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import slogo.controller.Controller;


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
    VBox commandLine = new VBox();
    ScrollPane terminal = new ScrollPane();
    terminal.setContent(historyBox);
    terminal.setPrefSize(TEXTBOX_WIDTH,TURTLE_SCREEN_HEIGHT-TEXTBOX_HEIGHT);
//    terminal.setMaxWidth(TEXTBOX_WIDTH*2);
    historyBox.heightProperty().addListener((obs, old, newValue) -> terminal.setVvalue((Double)newValue));
    HBox userControls = new HBox();

    textBox = new TextArea();
    textBox.setEditable(true);
    textBox.wrapTextProperty();
<<<<<<< Updated upstream
    textBox.setMaxWidth(TEXTBOX_WIDTH);
    textBox.setMaxHeight(TEXTBOX_HEIGHT);
    textBox.setPromptText(myResources.getString("TextBoxFiller"));
    textBox.setPromptText("Enter command:");
    commandLine.getChildren().add(textBox);
=======
    textBox.setPrefWidth(TEXTBOX_WIDTH-BUTTON_WIDTH);
    textBox.setMaxHeight(TEXTBOX_HEIGHT);
    textBox.setPromptText(myResources.getString("TextBoxFiller"));
    userControls.setHgrow(textBox, Priority.ALWAYS);
    userControls.getChildren().add(textBox);
>>>>>>> Stashed changes

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

<<<<<<< Updated upstream
=======
    userControls.getChildren().add(buttonBox);
    commandLine.setVgrow(terminal, Priority.ALWAYS);
    commandLine.getChildren().add(terminal);
    commandLine.getChildren().add(userControls);
//    commandLine.setLayoutX(2* XPOS_OFFSET+TURTLE_SCREEN_HEIGHT);
//    commandLine.setLayoutY(YPOS_OFFSET);
>>>>>>> Stashed changes
    return commandLine;
  }

  private void submitCommand() {
    if((textBox.getText() != null) && !textBox.getText().isEmpty()){
      myController.runCommand(textBox.getText());
      textBox.clear();
    }
  }
}
