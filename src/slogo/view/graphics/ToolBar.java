package slogo.view.graphics;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import slogo.SlogoApp;
import slogo.config.XMLReader;
import slogo.config.XMLWriter;
import slogo.view.Styler;
import slogo.view.Visualizer;

/**
 * Creates a toolbar with several options at the top of the visualizer.
 */
public class ToolBar {
  private static final String XML_FILEPATH = "user.dir";

  private Stage myStage;
  private CommandLine myTerminal;
  private Visualizer myVisualizer;
  private Styler myStyler;

  /**
   * Initializes all the other instances of classes needed.
   * @param stage - the stage of the visualizer
   * @param visualizer - the visualizer instance itself
   * @param newResources - a resource bundle in order to label everything
   */
  public ToolBar(Stage stage, Visualizer visualizer, ResourceBundle newResources){
    myStage = stage;
    myVisualizer = visualizer;
    myTerminal = visualizer.getTerminal();
    myStyler = new Styler(newResources);
  }

  /**
   * Creats a toolbar node that has several capabilities: a menu with a new window option, an exit option, a restart option,
   * a load workspace option, a load code option, and a save workspace option.
   * @return the menu
   */
  public Node setupToolBar(){
    HBox tools = new HBox();
    MenuBar menuBar = new MenuBar();
    Menu menu = myStyler.makeMenu("Menu");
    MenuItem newWindow = myStyler.makeMenuItem("New", e-> makeNewWindow());
    MenuItem exit = myStyler.makeMenuItem("Exit", e-> closeWindow());
    MenuItem restart = myStyler.makeMenuItem("Restart", e->{
      closeWindow();
      makeNewWindow();
    });
    MenuItem loadWorkspace = myStyler.makeMenuItem("LoadWorkspace", e-> new XMLReader(chooseXMLFile(), myStage));
    MenuItem loadCode = myStyler.makeMenuItem("LoadCode", e-> tryLoadCodeFromFile());
    MenuItem saveWorkspace = myStyler.makeMenuItem("SaveWorkspace", e-> pickAndSaveFile());
    menuBar.getMenus().add(menu);
    menu.getItems().addAll(newWindow, saveWorkspace, loadWorkspace, loadCode, restart, exit);
    tools.getChildren().addAll(menuBar, animationControls());
    return tools;
  }

  private Node createSlider(){
    Slider slider = new Slider();
    slider.setMin(0);
    slider.setMax(2000);
    slider.setValue(500);
    slider.valueProperty().addListener((o, old, neww) -> myVisualizer.getCurrentTurtle().setSpeed(neww.intValue()));
    return slider;
  }

  private void pickAndSaveFile() {
    XMLWriter writer = new XMLWriter(myVisualizer);
    String filepath = saveFile();
    if(filepath != null){
      writer.saveXML(filepath);
    }
  }

  private void tryLoadCodeFromFile() {
    try {
      File textfile = chooseTXTFile();
      if(textfile == null){
        return;
      }
      myTerminal.loadCodeFromFile(textfile);
    } catch (IOException ex) {
      retryLoadFile("Invalid text command history file");
    }
  }

  private void closeWindow() {
    myStage.close();
  }

  private void makeNewWindow() {
    SlogoApp newApp = new SlogoApp(new Stage());
  }

  /**
   * Opens a file navigator dialogue and allows the user to select an .xml file for importing into
   * the simulation
   *
   * @return the File object representing the .xml file to be used by the simulation
   */
  private File chooseXMLFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose Simulation File");
    fileChooser.setInitialDirectory(new File(System.getProperty(XML_FILEPATH)));
    fileChooser.getExtensionFilters().add(new ExtensionFilter("XML Files", "*.xml"));
    File file = fileChooser.showOpenDialog(myStage);
    if (file != null) {
      return file;
    } else {
      return null;
    }
  }

  private File chooseTXTFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose Code File");
    fileChooser.setInitialDirectory(new File(System.getProperty(XML_FILEPATH)));
    fileChooser.getExtensionFilters().add(new ExtensionFilter("txt files", "*.txt"));
    File file = fileChooser.showOpenDialog(myStage);
    if (file != null) {
      return file;
    } else {
      return null;
    }
  }

  /**
   * Creates filechooser dialog where the user can select the filepath for the current configuration of the simulation
   * to be saved to.
   * @return the filepath where the current simulation will be saved to.
   */
  private String saveFile() {
    FileChooser fileSaver = new FileChooser();
    fileSaver.setTitle("Save Simulation Configuration");
    fileSaver.setInitialDirectory(new File(System.getProperty(XML_FILEPATH)));
    fileSaver.getExtensionFilters().add(new ExtensionFilter("XML Files", "*.xml"));
    File file = fileSaver.showSaveDialog(myStage);
    if (file != null) {
      return file.getPath();
    } else {
      return null;
    }
  }

  /**
   * Helps with error handling by creating a loop which will pop up an error message until the user has
   * selected a valid XML file
   * @param message - The string message to be displayed by the error popup
   */
  private void retryLoadFile(String message) {
    boolean badFile;
    displayError(message);
    do {
      badFile = false;
      try {
        myTerminal.loadCodeFromFile(chooseTXTFile());
      } catch (IOException e) {
        displayError(message);
        badFile = true;
      }
    } while (badFile);
  }

  /**
   * Creates the error dialog box which pops up on handling different forms of exceptions
   * for an invalid XML file
   * @param message - The message to be displayed by the error dialog
   */
  private void displayError(String message) {
    Alert errorAlert = new Alert(AlertType.ERROR);
    errorAlert.setHeaderText(message);
    errorAlert.setContentText("Please select another file");
    errorAlert.showAndWait();
  }
  
  private Node animationControls(){
    HBox controlBox = new HBox();
    Button pause = myStyler.createButton("Pause", e->myVisualizer.getCurrentTurtle().pause());
    Button play = myStyler.createButton("Play", e->myVisualizer.getCurrentTurtle().play());
    Button step = myStyler.createButton("Step", e->myVisualizer.getCurrentTurtle().step());
    Button reset = myStyler.createButton("ResetCommand", e-> myVisualizer.getCurrentTurtle().resetAnimation());
    controlBox.getChildren().addAll(pause, play, step, reset, createSlider());
    return controlBox;
  }
}
