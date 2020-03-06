package slogo.view.graphics;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import slogo.exceptions.*;
import slogo.view.Styler;
import slogo.view.Visualizer;

/**
 * This class creates a commandline for the user to type in commands along with the corresponding buttons. Additionally,
 * it creates a command window where past commands appear as the user has the option to reuse those commands and remember
 * what they typed.
 */
public class CommandLine {
  public static final int TEXTBOX_WIDTH = 200;
  public static final int TEXTBOX_HEIGHT = 100;
  public static final int BUTTON_WIDTH = 50;
  public static final int TURTLE_SCREEN_HEIGHT = 500;

  private Visualizer myVisualizer;
  private TextArea textBox;
  private List<Label> history;
  private int historyIndex;
  private VBox historyBox;
  private Styler myStyler;

  /**
   * Initializes all important instances of other classes as well as variables which are used throughout the class.
   * @param visualizer - takes in the visualizer instance
   * @param newResources - takes in the resources bundle in order to create labels.
   */
  public CommandLine(Visualizer visualizer, ResourceBundle newResources){
    myVisualizer = visualizer;
    history = new ArrayList<>();
    historyBox = new VBox();
    historyIndex = -1;
    myStyler = new Styler(newResources);
  }

  /**
   * When the view is created, along with the userinterface and the userdefined class, this command line is added to the view.
   * There are many components to this node - the text area, the buttons, and the command display.
   * @return node with command info
   */
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
    textBox.setPromptText(myStyler.getResourceText("TextBoxFiller"));
    userControls.setHgrow(textBox, Priority.ALWAYS);
    userControls.getChildren().add(textBox);

    VBox buttonBox = new VBox();
    Button run = myStyler.createButton("RunCommand", e->submitCommand());
    run.setMinWidth(BUTTON_WIDTH);
    run.setMaxHeight(TEXTBOX_HEIGHT/3);

    Button clear = myStyler.createButton("ClearCommand", e->{
      textBox.clear();
      historyIndex=-1;
      history.clear();
      historyBox.getChildren().clear();
    });
    clear.setMinWidth(BUTTON_WIDTH);
    clear.setMaxHeight(TEXTBOX_HEIGHT/3);


    Button undo = myStyler.createButton("Undo", e-> myVisualizer.getCurrentTurtle().undo());
    undo.setMaxHeight(TEXTBOX_HEIGHT/3);


    buttonBox.getChildren().addAll(run,clear,undo);
    userControls.getChildren().add(buttonBox);
    commandLine.setVgrow(terminal, Priority.ALWAYS);
    commandLine.getChildren().add(terminal);
    commandLine.getChildren().add(userControls);

    return commandLine;
  }

  /**
   * Once the button to run the command is clicked, this method calls on the controller which then sends info to the model
   * in order for the command to be executed.
   */
  private void submitCommand() {
    if((textBox.getText() != null) && !textBox.getText().isEmpty()){
      try {
        myVisualizer.sendCommands(textBox.getText());
      } catch (InvalidCommandException e){
        Label recentCommand = new Label("Invalid " + e.getType() + ": " + e.getSyntax() + "\n" + textBox.getText());
        finishSubmitCommand(recentCommand);
        return;
      } catch (InvalidConstantException | InvalidVariableException | InvalidPropertyException | IllegalException | InstantException |
              InvocationException | NoClassException | NoMethodException e){
        Label recentCommand = new Label(e.getMessage());
        finishSubmitCommand(recentCommand);
        return;
      }
      addHistory(textBox.getText());
    }
  }

  public void finishSubmitCommand(Label recentCommand){
    recentCommand.setTextFill(Color.RED); //FIXME sometimes the label just isnt appearing red
    System.out.println(recentCommand.getTextFill());
    recentCommand.setOnMouseClicked(setOnClick(textBox.getText()));
    historyBox.getChildren().add(recentCommand);
    history.add(new Label(textBox.getText()));
    textBox.clear();
  }

  /**
   * This method enables the user to click a past command in the command history and place it in the command text area in order
   * to be executed again.
   * @param fill - the command that has been clicked
   * @return executes that command
   */
  public EventHandler setOnClick(String fill){
    return e->textBox.setText(fill);
  }

  /**
   * Enables the user to scroll through the history if the list of commands has become long enough.
   * @param input
   */
  public void scrollHistory(KeyCode input){
    if (input == KeyCode.UP && historyIndex < history.size()-1) {
      historyIndex++;
      textBox.setText(history.get(history.size()-historyIndex-1).getText());

    } else if (input == KeyCode.DOWN && historyIndex > 0){
      historyIndex--;
      textBox.setText(history.get(history.size()-historyIndex-1).getText());
    }
  }

  /**
   * Adds each command to the history box once it is executed.
   * @param syntax
   */
  public void addHistory(String syntax){
    Label recentCommand = new Label(syntax);
    history.add(recentCommand);
    recentCommand.setOnMouseClicked(setOnClick(syntax));
    historyBox.getChildren().add(recentCommand);
    textBox.clear();
    historyIndex = -1;
  }

  /**
   * Loads code and command from an XML file.
   * @param file - XML file
   * @throws IOException
   */
  public void loadCodeFromFile(File file) throws IOException {
    textBox.setText(Files.readString(file.toPath()));
  }

  /**
   * Returns all the commands currently in the command history to then be used in XML file.
   * @return
   */
  public List<String> getHistory(){
    List<String> cmdHistory = new ArrayList<>();
    for(Label l : history){
      cmdHistory.add(l.getText());
    }
    return cmdHistory;
  }
}
