package slogo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import slogo.controller.Controller;
import javafx.scene.paint.Color;
import slogo.exceptions.InvalidCommandException;


public class CommandLine {
  public static final int TEXTBOX_WIDTH = 200;
  public static final int TEXTBOX_HEIGHT = 100;
  public static final int BUTTON_WIDTH = 50;
  public static final int TURTLE_SCREEN_HEIGHT = 500;
  public static final String RESOURCES = "resources.";
  public static final String FORMAT_PACKAGE = RESOURCES + ".formats.";

  private Controller myController;
  private ResourceBundle myResources;
  private TextArea textBox;
  private List<Label> history;
  private int historyIndex;
  private VBox historyBox;

  public CommandLine(Controller controller){
    myResources = ResourceBundle.getBundle(FORMAT_PACKAGE + "Buttons");
    myController = controller;
    history = new ArrayList<>();
    historyBox = new VBox();
    historyIndex = -1;
  }

  public Node setupCommandLine(){
    VBox commandLine = new VBox();
    ScrollPane terminal = new ScrollPane();
    terminal.setContent(historyBox);
    terminal.setPrefSize(TEXTBOX_WIDTH,TURTLE_SCREEN_HEIGHT-TEXTBOX_HEIGHT);

    historyBox.heightProperty().addListener((obs, old, newValue) -> terminal.setVvalue((Double)newValue));
    HBox userControls = new HBox();

    textBox = new javafx.scene.control.TextArea();
    textBox.setEditable(true);
    textBox.wrapTextProperty();

    textBox.setPrefWidth(TEXTBOX_WIDTH-BUTTON_WIDTH);
    textBox.setMaxHeight(TEXTBOX_HEIGHT);
    textBox.setPromptText(myResources.getString("TextBoxFiller"));
    userControls.setHgrow(textBox, Priority.ALWAYS);
    userControls.getChildren().add(textBox);

    VBox buttonBox = new VBox();
    Button run = new Button(myResources.getString("RunCommand"));
    run.setMinWidth(BUTTON_WIDTH);
    run.setOnAction(e->submitCommand());
    buttonBox.getChildren().add(run);

    Button clear = new Button(myResources.getString("ClearCommand"));
    clear.setOnAction(e->{
      textBox.clear();
      historyIndex = -1;
    });
    clear.setMinWidth(BUTTON_WIDTH);
    buttonBox.getChildren().add(clear);

    userControls.getChildren().add(buttonBox);
    commandLine.setVgrow(terminal, Priority.ALWAYS);
    commandLine.getChildren().add(terminal);
    commandLine.getChildren().add(userControls);

    return commandLine;
  }

  private void submitCommand() {
    if((textBox.getText() != null) && !textBox.getText().isEmpty()){
      try {
        myController.sendCommands(textBox.getText());
      } catch (InvalidCommandException e){
        Label recentCommand = new Label("Invalid " + e.getType() + ": " + e.getSyntax() + "\n" + textBox.getText());
        recentCommand.setTextFill(Color.RED);
        historyBox.getChildren().add(recentCommand);
        history.add(new Label(textBox.getText()));
        textBox.clear();
        return;
      }
      Label recentCommand = new Label(textBox.getText());
      history.add(recentCommand);
      historyBox.getChildren().add(recentCommand);
      textBox.clear();
      historyIndex = -1;
    }
  }

  public void scrollHistory(KeyCode input){
    if (input == KeyCode.UP && historyIndex < history.size()-1) {
      historyIndex++;
      textBox.setText(history.get(history.size()-historyIndex-1).getText());

    } else if (input == KeyCode.DOWN && historyIndex > 0){
      historyIndex--;
      textBox.setText(history.get(history.size()-historyIndex-1).getText());
    }
  }
}
