package slogo.view;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import slogo.SlogoApp;
import slogo.config.XMLReader;
import slogo.config.XMLWriter;

public class ToolBar {
  private static final String RESOURCES = "resources";
  public static final String FORMAT_PACKAGE = RESOURCES + ".formats.";
  private static final String XML_FILEPATH = "user.dir";

  private MenuBar menuBar;
  private Menu menu;
  private MenuItem newWindow;
  private MenuItem exit;
  private MenuItem restart;
  private MenuItem loadWorkspace;
  private MenuItem loadCode;
  private MenuItem saveWorkspace;
  private ResourceBundle myResources;
  private Stage myStage;
  private CommandLine myTerminal;
  private Visualizer myVisualizer;

  public ToolBar(Stage stage, Visualizer visualizer, ResourceBundle newResources){
    myStage = stage;
    myVisualizer = visualizer;
    myTerminal = visualizer.getTerminal();
    myResources = newResources;
  }

  public Node setupToolBar(){
    HBox tools = new HBox();
    menuBar = new MenuBar();
    menu = makeMenu("Menu");
    newWindow = makeMenuItem("New", e-> makeNewWindow());
    exit = makeMenuItem("Exit", e-> closeWindow());
    restart = makeMenuItem("Restart", e->{
      closeWindow();
      makeNewWindow();
    });
    loadWorkspace = makeMenuItem("LoadWorkspace", e-> new XMLReader(chooseXMLFile(), myStage));
    loadCode = makeMenuItem("LoadCode", e-> tryLoadCodeFromFile());
    saveWorkspace = makeMenuItem("SaveWorkspace", e-> pickAndSaveFile());
    menuBar.getMenus().add(menu);
    menu.getItems().addAll(newWindow, saveWorkspace, loadWorkspace, loadCode, restart, exit);
    tools.getChildren().add(menuBar);
    return tools;
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
   * Handles the creation of MenuItem objects, applies a label to them as specified in the createLabel method and
   * gives them an action on click.
   * @param property - the label to be applied to the object specified in the .properties file
   * @param handler - the action to occur when the menu item is clicked
   * @return a MenuItem to be placed in a Menu object with a label and action on click
   */
  private MenuItem makeMenuItem(String property, EventHandler<ActionEvent> handler) {
    MenuItem result = new MenuItem();
    createLabel(property, result);
    result.setOnAction(handler);
    return result;
  }

  /**
   * Handles the creation of Menu objects, applies a label to them as specified in the createLabel method
   * @param property - the label to be applied to the menu, from the .properties file
   * @return a Menu to be rendered on stage with a label
   */
  private Menu makeMenu(String property) {
    Menu result = new Menu();
    createLabel(property, result);
    return result;
  }

  /**
   * Handles the creation of Menu and MenuItem labels
   * @param property - the label to be applied to the items, from the .properties file
   * @param result - the menu item to which the label is applied
   */
  private void createLabel(String property, MenuItem result) {
    String label = myResources.getString(property);
    result.setText(label);
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
}
