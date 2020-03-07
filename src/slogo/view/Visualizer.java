package slogo.view;

import java.util.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import slogo.controller.Controller;
import slogo.exceptions.InvalidCommandException;
import slogo.exceptions.InvalidTurtleException;
import slogo.view.graphics.ColorPalette;
import slogo.view.graphics.CommandLine;
import slogo.view.graphics.PenProperties;
import slogo.view.graphics.ShapePalette;
import slogo.view.graphics.ToolBar;
import slogo.view.graphics.UserDefined;
import slogo.view.graphics.UserInterface;
import slogo.view.turtles.TurtleView;

/**
 * The main class of the view. This class is called as soon as the program is run and effectively
 * calls all the other classes that make up the entire SLogo project. This class connects all the
 * parts of the project.
 */
public class Visualizer{
  private static final int TURTLE_SCREEN_WIDTH = 500;
  private static final int TURTLE_SCREEN_HEIGHT = 500;
  private static final String STYLESHEET = "styling.css";
  private static final String RESOURCES = "resources";
  private static final String DEFAULT_RESOURCE_FOLDER = RESOURCES + "/formats/";
  private static final int VIEWPANE_PADDING = 10;
  private static final int VIEWPANE_MARGIN = 0;
  private static final String FORMAT_PACKAGE = RESOURCES + ".formats.";
  private static final String DEFAULT_LANGUAGE = "English";
  private static final double UNSELECTED_OPACITY = .5;
  private static final int SELECTED_OPACITY = 1;

  private Controller myController;
  private ViewExternal viewExternal;
  private CommandLine commandLine;
  private PenProperties penProperties;
  private ColorPalette colorPalette;
  private ShapePalette shapePalette;
  private Map<String, TurtleView> turtleMap;
  private ResourceBundle myResources;
  private String language = "English";
  private Map<String, String> varMap;
  private Map<String, String> cmdMap;
  private SimpleObjectProperty<ObservableList<String>> myTurtlesProperty;
  private TurtleView currentTurtle;
//  private Map<String, TurtleView> activeTurtles;
  private slogo.view.graphics.ToolBar myToolBar;
  private Stage myStage;
  private UserDefined userDefined;
  private UserInterface userInterface;


  /**
   * This constructor initializes all instances of other classes and important variables and data
   * structures that will be used throughout the rest of the class as well as be passed into other
   * calls to other classes.
   *
   * @param stage - takes in the stage from the SlogoApp class
   */
  public Visualizer (Stage stage){
    myStage = stage;
    myResources = ResourceBundle.getBundle(FORMAT_PACKAGE + language);
    myTurtlesProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    turtleMap = new TreeMap<>();
//    activeTurtles = new TreeMap<>();
    varMap = new TreeMap<>();
    cmdMap = new TreeMap<>();
    viewExternal = new ViewExternal(this);
    userDefined = new UserDefined(myResources);
    commandLine = new CommandLine(this, myResources);
    myToolBar = new ToolBar(stage, this, myResources);
    userInterface = new UserInterface(this, myResources);
    colorPalette = new ColorPalette();
    shapePalette = new ShapePalette();
    penProperties = new PenProperties(this, myResources);
    myController = new Controller(viewExternal, DEFAULT_LANGUAGE);
  }

  /**
   * Sets the scene of the entire view and uses the css styling sheet
   *
   * @return scene in which all the main features are added
   */
  public Scene setupScene() {
    Scene myScene = new Scene(createView());
    myScene.getStylesheets()
        .add(getClass().getClassLoader().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
            .toExternalForm());
    myScene.addEventFilter(KeyEvent.KEY_PRESSED, e -> commandLine.scrollHistory(e.getCode()));
    return myScene;
  }

  private BorderPane createView() {
    BorderPane viewPane = new BorderPane();
    viewPane.setPadding(new Insets(VIEWPANE_MARGIN, VIEWPANE_PADDING, VIEWPANE_PADDING, VIEWPANE_PADDING));
    Node toolBar = myToolBar.setupToolBar();
    Node turtleView = userDefined.showUserDefined();
    Node cLine = commandLine.setupCommandLine();
    Node uInterface = userInterface.createTotalUI();

    viewPane.setMargin(cLine, new Insets(VIEWPANE_MARGIN, VIEWPANE_PADDING, VIEWPANE_MARGIN, VIEWPANE_PADDING));
    viewPane.setTop(toolBar);
    viewPane.setLeft(turtleView);
    viewPane.setCenter(cLine);
    viewPane.setRight(uInterface);
    return viewPane;
  }

  /**
   * Adds a command to the user defined history once a command is added. Additionally is used in
   * saving the XML file.
   * @param command
   * @param syntax
   */
  public void addCommand(String command, String syntax){
    Node commandAndSyntax = makeUserDefined(command, syntax, commandLine.setOnClick(command), new Label());
    cmdMap.put(command, syntax);
    userDefined.addCommand(commandAndSyntax);
    myController.addUserCommand(command, syntax);
  }

  private Node makeUserDefined(String key, String value, EventHandler event, Label mutable){
    Label keyLabel = new Label(key);
    mutable.setText(value); //modify based on what model wants it to do
    HBox keyAndValue = new HBox();
    keyAndValue.setMinWidth(TURTLE_SCREEN_WIDTH / 2);
    final Pane spacer = new Pane();
    keyAndValue.setHgrow(spacer, Priority.ALWAYS);
    keyAndValue.getChildren().addAll(keyLabel, spacer, mutable);
    keyAndValue.setOnMouseClicked(event);
    return keyAndValue;
  }
  /**
   * Adds a variable to the user defined history once a variable is added. Additionally is used in
   * saving the XML file.
   *
   * @param variable
   * @param value
   */
  public void addVariable(String variable, String value){
    Label valueLabel = new Label();
    Node variableAndValue = makeUserDefined(variable, value, e->updateVariable(variable, valueLabel), valueLabel);
    varMap.put(variable, value);
    userDefined.addVariable(variableAndValue);
    myController.addUserVariable(variable, value);
  }

  private void updateVariable(String variableName, Label value){
    TextInputDialog updateVariable = new TextInputDialog();
    updateVariable.setTitle("Update Variable");
    updateVariable
        .setHeaderText("Update " + variableName + " value by entering in a valid number:");
    updateVariable.setContentText("Enter new number here:");
    Optional<String> result = updateVariable.showAndWait();
    if (result.isPresent()) {
      Double number = null;
      try {
        number = Double.valueOf(result.get());
        varMap.put(variableName, number.toString());
        value.setText(result.get());
        myController.updateCommandVariable(variableName, number.toString());
      } catch (NumberFormatException e) {
        number = Double.parseDouble(value.getText());
      }
    }
  }
  /**
   * Allows the user to add another turtle to a single turtle area / turtle group. The user is then able to control the
   * turtles at different times.
   */
  public void addTurtle() {
    try {
      myController.addTurtle();
    } catch (InvalidTurtleException e) {
      e.displayError("Please add unique turtle:");
    }
    TurtleView tempTurtle = new TurtleView(userDefined.getTurtles(), userDefined.getTurtlePaths(), myController.getTurtleName(), this);
    turtleMap.putIfAbsent(myController.getTurtleName(), tempTurtle);
    myTurtlesProperty.getValue().add(myController.getTurtleName());
    setTurtle(myController.getTurtleName());
  }

  /**
   * Allows the user to add another turtle with specified coordinates and a name
   * to a single turtle area / turtle group. The user is then able to control the
   * turtles at different times.
   * @param name - name or ID of new turtle used as an identifier
   * @param startingX - starting x position
   * @param startingY - starting y position
   * @param heading   - starting orientation
   */
  public void addTurtle(String name, double startingX, double startingY, double heading) {
    try {
      myController.addTurtle(name, startingX, startingY, heading);
    } catch (InvalidTurtleException e) {
      e.displayError("Please fix XML to contain unique turtles:");
      System.out.println("YEET");
      return;
    }
    TurtleView tempTurtle = new TurtleView(userDefined.getTurtles(), userDefined.getTurtlePaths(), myController.getTurtleName(), this);
    tempTurtle.set(startingX, startingY, heading);
    turtleMap.putIfAbsent(myController.getTurtleName(), tempTurtle);
    myTurtlesProperty.getValue().add(myController.getTurtleName());

    setTurtle(myController.getTurtleName());
  }

  /**
   * Given a turtle's name or id, sets that turtle to be the current active turtle. In addition, it
   * changes the opacity of all the turtles to indicate which are active and which aren't.
   *
   * @param name
   */
  public void setTurtle(String name) {
    userInterface.getList().itemsProperty().unbind();
    if (currentTurtle != null) {
      currentTurtle.setOpacity(UNSELECTED_OPACITY);
    }
    currentTurtle = turtleMap.get(name);
    if (penProperties != null) {
      penProperties.getColorPicker().setValue(currentTurtle.getColor());
    }
    currentTurtle.setOpacity(SELECTED_OPACITY);
    myController.chooseTurtle(name);
    userInterface.getList().itemsProperty().bind(currentTurtle.turtleStats());
  }

  /**
   * Retrieves the turtle view instance of the current turtle in order to manipulate its
   * information.
   *
   * @return a turtle view instance
   */
  public TurtleView getCurrentTurtle() {
    return currentTurtle;
  }

  /**
   * Clears all the turtle paths on the screen.
   */
  public void clear() {
    userDefined.getTurtlePaths().getChildren().clear();
  }

  /**
   * Sets the turtle's pen color given a color found by the index in the color palette.
   *
   * @param value - the index corresponding to the desired color
   */
  public void setPenColor(double value){
    try {
      currentTurtle.updatePen(Color.web(colorPalette.getColorMapValue(value)));
    } catch (NullPointerException e){
      throw new InvalidCommandException(new Throwable(), "Index:", ""+value);
    }
  }

  /**
   * Shows the color palette when the color palette button is clicked
   */
  public EventHandler showColorPalette() {
    return e -> colorPalette.showPalette();
  }

  /**
   * Shows the shape palette when the shape palette button is clicked
   */
  public EventHandler showShapePalette() {
    return e -> shapePalette.showPalette();
  }

  /**
   * Shows the pen properties when the pen properties button is clicked
   */
  public EventHandler createPenProperties() {
    return e -> penProperties.showProperties();
  }

  /**
   * Sets the background color based on a value that the user types into the command text area, this
   * value corresponds to a color in the color palette map
   *
   * @param value - defined by the user in their command
   */
  public void setBackgroundColorFromPalette(double value) {
    setBackgroundColor(colorPalette.getColorMapValue(value));
  }

  /**
   * Sets the pen size based on a value that the user types into the command text area
   *
   * @param value - defined by the user in their command
   */
  public void setPenSize(double value){
    currentTurtle.changePenWidth(value);
  }

  /**
   * Sets the turtle shape based on a value that the user types into the command text area, this
   * value corresponds to an image in the shape palette map
   *
   * @param value - defined by the user in their command
   */
  public void setShape(double value) {
    currentTurtle.setShape(shapePalette.getShapeMapValue(value));
  }

  /**
   * Based on the language selected in the combobox, adds the language to the controller so that
   * commands can be understood in that language.
   *
   * @param newLanguage - language from combobox
   */
  public void setLanguage(String newLanguage) {
    language = newLanguage;
    myController.addLanguage(language);
    myResources = ResourceBundle.getBundle(FORMAT_PACKAGE + language);
    commandLine = new CommandLine(this, myResources);
    myToolBar = new ToolBar(myStage, this, myResources);
    userInterface = new UserInterface(this, myResources);
    myStage.setScene(setupScene());
  }

  /**
   * Sets the background based on a hex value and is used to set the background color from an XML
   * file.
   *
   * @param hexColor - string to be converted into java color
   */
  public void setBackgroundColor(String hexColor) {
    userDefined.setFill(Color.web(hexColor));
    userInterface.setBackgroundPicker(hexColor);
  }

  /**
   * @return the current commandline for the XML file
   */
  public CommandLine getTerminal() {
    return commandLine;
  }

  /**
   * @return the current language for the XML file
   */
  public String getLanguage() {
    return language;
  }

  /**
   * @return the current background color for the XML file
   */
  public Color getBackground() {
    return userDefined.getFill();
  }

  /**
   * @return the current map of turtles for the XML file
   */
  public Map<String, TurtleView> getTurtles() {
    return turtleMap;
  }

  /**
   * @return the user defined variables for the XML file
   */
  public Map<String, String> getUserVariables() {
    return varMap;
  }

  /**
   * @return the user defined commands for the XML file
   */
  public Map<String, String> getUserCommands() {
    return cmdMap;
  }

  /**
   * Sends a command that the user typed into the text area to the controller for it to then be
   * parsed.
   *
   * @param command
   */
  public void sendCommands(String command) {
    myController.sendCommands(command);
  }

  /**
   * @return Gets the userdefined view objects to then be added to the visualizer.
   */
  public UserDefined getUserDefined() {
    return userDefined;
  }

  /**
   * @return the turtles property which is binded to the turtlebox to update the values
   */
  public SimpleObjectProperty<ObservableList<String>> getTurtlesProperty(){
    return myTurtlesProperty;
  }

  /**
   * @return the width of the canvas the turtle can move in
   */
  public int getArenaWidth(){
    return TURTLE_SCREEN_WIDTH;
  }

  /**
   * @return the height of the canvas the turtle can move in
   */
  public int getArenaHeight() {
    return TURTLE_SCREEN_HEIGHT;
  }

  /**
   * Updates an indicated entry in the colormap to a new specified value based on a given hex color
   * @param index - the entry to be overwritten
   * @param hex - the hex color of the new color
   */
  public void updateColorMap(double index, String hex){
    colorPalette.updateColorMap(index, hex);
  }

  /**
   * @return the map between index and hex valued color held within the colorPalette
   * for writing to the XML
   */
  public Map<Double, String> getColorMap(){
    return colorPalette.getColorMap();
  }


  /**
   * Directly repositions the model-side turtle when being modified by commands
   * undo/reset on the view end (IN THE MODEL's COORDINATE SYSTEM -- 0,0 is the center)
   * @param newX - the newX position for the turtle to move to
   * @param newY - the new Y position for the turtle to move to
   * @param heading - the new heading for the turtle to move to
   */
  public void orientTurtle(double newX, double newY, double heading){myController.orientTurtle(newX, newY, heading);}

  /**
   * @return - A list of path objects to be modified and inspected to be written into the XML
   */
  public List<Path> getPaths(){
    return userDefined.getPathList();
  }

  /**
   * @return - A group of paths that is rendered in the scene so the XML
   * can draw paths when loading a new scene
   */
  public Group getTurtlePaths(){return userDefined.getTurtlePaths();}
}
