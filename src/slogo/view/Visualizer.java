package slogo.view;

import java.util.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.controller.Controller;
import slogo.exceptions.InvalidTurtleException;

/**
 * The main class of the view. This class is called as soon as the program is run and effectively calls all the other classes
 * that make up the entire SLogo project. This class connects all the parts of the project.
 */
public class Visualizer{

  public static final int TURTLE_SCREEN_WIDTH = 500;
  public static final int TURTLE_SCREEN_HEIGHT = 500;
  public static final String STYLESHEET = "styling.css";
  public static final String RESOURCES = "resources";
  public static final String DEFAULT_RESOURCE_FOLDER = RESOURCES + "/formats/";
  public static final int VIEWPANE_PADDING = 10;
  public static final int VIEWPANE_MARGIN = 0;
  public static final String FORMAT_PACKAGE = RESOURCES + ".formats.";
  public static final String DEFAULT_LANGUAGE = "English";
  public static final double UNSELECTED_OPACITY = .5;
  public static final int SELECTED_OPACITY = 1;
  public static final String DEFAULT_COLOR_RESOURCE_PACKAGE = FORMAT_PACKAGE + ".Colors";

  private Controller myController;
  private ViewExternal viewExternal;
  private CommandLine commandLine;
  private PenProperties penProperties;
  private ColorPalette colorPalette;
  private ShapePalette shapePalette;
  private Map<String, TurtleView> turtleMap;
  private ResourceBundle myResources;
  private String language;
  private Map<String, String> varMap;
  private Map<String, String> cmdMap;
  private SimpleObjectProperty<ObservableList<String>> myTurtlesProperty;
  private TurtleView currentTurtle;
  private ToolBar myToolBar;
  private Stage myStage;
  private UserDefined userDefined;
  private UserInterface userInterface;


  /**
   * This constructor initializes all instances of other classes and important variables and data structures that will be used
   * throughout the rest of the class as well as be passed into other calls to other classes.
   * @param stage - takes in the stage from the SlogoApp class
   */
  public Visualizer (Stage stage){
    language = "English";
    myResources = ResourceBundle.getBundle(FORMAT_PACKAGE + language);
    userDefined = new UserDefined(myResources);
    turtleMap = new TreeMap<>();
    varMap = new TreeMap<>();
    cmdMap = new TreeMap<>();
    viewExternal = new ViewExternal(this);
    myController = new Controller(viewExternal, DEFAULT_LANGUAGE);
    commandLine = new CommandLine(this, myResources);
    myToolBar = new ToolBar(stage, this, myResources);
    myTurtlesProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    userInterface = new UserInterface(this, myResources);
    colorPalette = new ColorPalette(createColorMap());
    shapePalette = new ShapePalette();
    penProperties = new PenProperties(this, myResources);
    myStage = stage;
  }

  /**
   * Sets the scene of the entire view and uses the css styling sheet
   * @return scene in which all the main features are added
   */
  public Scene setupScene() {
    Scene myScene = new Scene(createView());
    myScene.getStylesheets()
        .add(getClass().getClassLoader().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET)
            .toExternalForm());
    myScene.addEventFilter(KeyEvent.KEY_PRESSED, e->commandLine.scrollHistory(e.getCode()));
    return myScene;
  }

  private BorderPane createView(){
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
   * Adds a command to the user defined history once a command is added. Additionally is used in saving the XML file.
   * @param command
   * @param syntax
   */
  public void addCommand(String command, String syntax){
    Label recentCommand = new Label(command);
    Label syntaxLabel = new Label(syntax); //modify based on what model wants it to do
    HBox commandAndSyntax = new HBox();
    commandAndSyntax.setOnMouseClicked(commandLine.setOnClick(command));
    commandAndSyntax.setMinWidth(TURTLE_SCREEN_WIDTH / 2);
    final Pane spacer = new Pane();
    commandAndSyntax.setHgrow(spacer, Priority.ALWAYS);
    commandAndSyntax.getChildren().addAll(recentCommand, spacer, syntaxLabel);
    cmdMap.put(command, syntax);
    userDefined.addCommand(commandAndSyntax);
    myController.addUserCommand(command, syntax);
  }

  /**
   * Adds a variable to the user defined history once a variable is added. Additionally is used in saving the XML file.
   * @param variable
   * @param value
   */
  public void addVariable(String variable, String value){
    Label recentCommand = new Label(variable);
    Label valueLabel = new Label(value);
    HBox variableAndValue = new HBox();
    variableAndValue.setMinWidth(TURTLE_SCREEN_WIDTH / 2);
    final Pane spacer = new Pane();
    variableAndValue.setHgrow(spacer, Priority.ALWAYS);
    variableAndValue.getChildren().addAll(recentCommand, spacer, valueLabel);
    varMap.put(variable, value);
    variableAndValue.setOnMouseClicked(e->updateVariable(variable, valueLabel));
    userDefined.addVariable(variableAndValue);
    myController.addUserVariable(variable, value);
  }

  //FIXME variable types :right now all it handles is doubles and poorly at that
  private void updateVariable(String variableName, Label value){
    TextInputDialog updateVariable = new TextInputDialog();
    updateVariable.setTitle("Update Variable");
    updateVariable.setHeaderText("Update " + variableName + " value by entering in a valid number:");
    updateVariable.setContentText("Enter new number here:");
    Optional<String> result = updateVariable.showAndWait();
    if(result.isPresent()){
      Double number = null;
      try{
        number = Double.valueOf(result.get());
        varMap.put(variableName, number.toString());
        value.setText(result.get());
        myController.updateCommandVariable(variableName, number.toString());
      } catch (NumberFormatException e){
        number = Double.parseDouble(value.getText());

      }
    }
  }

  private Map<Double, String> createColorMap(){
    ResourceBundle myColorResources = ResourceBundle.getBundle(DEFAULT_COLOR_RESOURCE_PACKAGE);
    Enumeration e = myColorResources.getKeys();
    TreeMap<Double, String> treeMap = new TreeMap<>();
    while (e.hasMoreElements()) {
      String keyStr = (String) e.nextElement();
      treeMap.put(Double.valueOf(keyStr), myColorResources.getString(keyStr));
    }
    return treeMap;
  }

  /**
   * Allows the user to create many simulations at once. It can do this by uploading an XML file or just creating a new
   * instance of the SlogoApp all together.
   */
  public void addTurtle(){
    try {
      myController.addTurtle();
    } catch (InvalidTurtleException e){
      e.displayError("Please add unique turtle:");
    }
    TurtleView tempTurtle = new TurtleView(userDefined.getTurtles(), userDefined.getTurtlePaths(), myController.getTurtleName());
    turtleMap.putIfAbsent(myController.getTurtleName(), tempTurtle);
    myTurtlesProperty.getValue().add(myController.getTurtleName());
    setTurtle(myController.getTurtleName());
  }

  /**
   * Allows the user to add another turtle to a single turtle area / turtle group. The user is then able to control the
   * turtles at different times.
   * @param name - name or ID of new turtle used as an identifier
   * @param startingX - starting x position
   * @param startingY - starting y position
   * @param heading - starting orientation
   */
  public void addTurtle(String name, double startingX, double startingY, double heading){
    try {
      myController.addTurtle(name, startingX, startingY, heading);
    } catch (InvalidTurtleException e){
      e.displayError("Please fix XML to contain unique turtles:");
      System.out.println("YEET");
      return;
    }
    TurtleView tempTurtle = new TurtleView(userDefined.getTurtles(), userDefined.getTurtlePaths(), myController.getTurtleName());
    tempTurtle.set(startingX, startingY, heading);
    turtleMap.putIfAbsent(myController.getTurtleName(), tempTurtle);
    myTurtlesProperty.getValue().add(myController.getTurtleName());

    setTurtle(myController.getTurtleName());
  }

  /**
   * Given a turtle's name or id, sets that turtle to be the current active turtle. In addition, it changes the opacity
   * of all the turtles to indicate which are active and which aren't.
   * @param name
   */
  public void setTurtle(String name){
    userInterface.getList().itemsProperty().unbind();
    if(currentTurtle != null) {
      currentTurtle.setOpacity(UNSELECTED_OPACITY);
    }
    currentTurtle = turtleMap.get(name);
    if(penProperties !=null){
      penProperties.getColorPicker().setValue(currentTurtle.getColor());
    }
    currentTurtle.setOpacity(SELECTED_OPACITY);
    myController.chooseTurtle(name);
    userInterface.getList().itemsProperty().bind(currentTurtle.turtleStats());
  }

  /**
   * Retrieves the turtle view instance of the current turtle in order to manipulate its information.
   * @return a turtle view instance
   */
  public TurtleView getCurrentTurtle(){return currentTurtle;}

//  public void reset(){
//    clear();
//    myController.resetAll();
//    for(TurtleView turtle : turtleMap.values()) {
//      turtle.resetTurtle();
//    }
//  }

  /**
   * Clears all the turtle paths on the screen.
   */
  public void clear(){userDefined.getTurtlePaths().getChildren().clear();}

  /**
   * Sets the turtle's pen color given a color found by the index in the color palette.
   * @param value - the index corresponding to the desired color
   */
  public void setPenColor(double value){
    currentTurtle.updatePen(Color.web(colorPalette.getColorMapValue(value)));
  }

  /**
   * Shows the color palette when the color palette button is clicked
   */
  public EventHandler showColorPalette(){
    return e->colorPalette.showPalette();
  }

  /**
   * Shows the shape palette when the shape palette button is clicked
   */
  public EventHandler showShapePalette() {
    return e->shapePalette.showPalette();
  }

  /**
   * Shows the pen properties when the pen properties button is clicked
   */
  public EventHandler createPenProperties() {
    return e->penProperties.showProperties();
  }

  /**
   * Sets the background color based on a value that the user types into the command text area, this value corresponds to
   * a color in the color palette map
   * @param value - defined by the user in their command
   */
  public void setBackgroundColorFromPalette(double value){
      setBackgroundColor(colorPalette.getColorMapValue(value));
  }

  /**
   * Sets the pen size based on a value that the user types into the command text area
   * @param value - defined by the user in their command
   */
  public void setPenSize(double value){currentTurtle.setPenSize(value);}

  /**
   * Sets the turtle shape based on a value that the user types into the command text area, this value corresponds to an image
   * in the shape palette map
   * @param value - defined by the user in their command
   */
  public void setShape(double value){
      currentTurtle.setShape(shapePalette.getShapeMapValue(value));
  }

  /**
   * Based on the language selected in the combobox, adds the language to the controller so that commands can be understood
   * in that language.
   * @param newLanguage - language from combobox
   */
  public void setLanguage(String newLanguage){
    language = newLanguage;
    myController.addLanguage(language);
    myResources = ResourceBundle.getBundle(FORMAT_PACKAGE + language);
    commandLine = new CommandLine(this, myResources);
    myToolBar = new ToolBar(myStage, this, myResources);
    userInterface = new UserInterface(this, myResources);
    myStage.setScene(setupScene());
  }

  //FIXME why do we have so many colorpicker methods? can we combine any in any way

  /**
   * Sets the background based on a hex value and is used to set the background color from an XML file.
   * @param hexColor - string to be converted into java color
   */
  public void setBackgroundColor(String hexColor){
    userDefined.setFill(Color.web(hexColor));
    userInterface.setBackgroundPicker(hexColor);
  }

  /**
   * @return the current commandline for the XML file
   */
  public CommandLine getTerminal(){return commandLine;}

  /**
   * @return the current language for the XML file
   */
  public String getLanguage(){
    return language;
  }

  /**
   * @return the current background color for the XML file
   */
  public Color getBackground(){
    return userDefined.getFill();
  }

  /**
   * @return the current map of turtles for the XML file
   */
  public Map<String, TurtleView> getTurtles(){
    return turtleMap;
  }

  /**
   * @return the user defined variables for the XML file
   */
  public Map<String, String> getUserVariables(){
    return varMap;
  }

  /**
   * @return the user defined commands for the XML file
   */
  public Map<String, String> getUserCommands(){
    return cmdMap;
  }

  /**
   * Sends a command that the user typed into the text area to the controller for it to then be parsed.
   * @param command
   */
  public void sendCommands(String command){myController.sendCommands(command);}

  /**
   * @return Gets the userdefined view objects to then be added to the visualizer.
   */
  public UserDefined getUserDefined(){return userDefined;}

  /**
   * @return the turtles property which is binded to the turtlebox to update the values
   */
  public SimpleObjectProperty<ObservableList<String>> getTurtlesProperty(){return myTurtlesProperty;}
}
