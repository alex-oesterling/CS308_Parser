package slogo.view;

import java.util.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.controller.Controller;
import slogo.exceptions.InvalidTurtleException;

public class Visualizer{

  public static final int TURTLE_SCREEN_WIDTH = 500;
  public static final int TURTLE_SCREEN_HEIGHT = 500;
  public static final int COLORPICKER_HEIGHT = 30;
  public static final int LISTVIEW_WIDTH = 100;
  public static final int LISTVIEW_HEIGHT  = 250;
  public static final String STYLESHEET = "styling.css";
  public static final String RESOURCES = "resources";
  public static final String DEFAULT_RESOURCE_FOLDER = RESOURCES + "/formats/";
  public static final String COLOR_RESOURCE = "resources.formats";
  public static final String DEFAULT_COLOR_RESOURCE_PACKAGE = COLOR_RESOURCE + ".Colors";
  public static final int VIEWPANE_PADDING = 10;
  public static final int VIEWPANE_MARGIN = 0;
  public static final int VBOX_SPACING = 10;
  public static final int HBOX_SPACING = 10;
  public static final String FORMAT_PACKAGE = RESOURCES + ".formats.";
  public static final String DEFAULT_LANGUAGE = "English";
  public static final double UNSELECTED_OPACITY = .5;
  public static final int SELECTED_OPACITY = 1;

  private Controller myController;
  private ViewExternal viewExternal;
  private CommandLine commandLine;
  private PenProperties penProperties;
  private Styler styler;
  private ColorPalette colorPalette;
  private ShapePalette shapePalette;
  private Map<String, TurtleView> turtleMap;
  private ResourceBundle myResources;
  private String language;
  private Map<String, String> varMap;
  private Map<String, String> cmdMap;
  private SimpleObjectProperty<ObservableList<String>> myTurtlesProperty;
  private TurtleView currentTurtle;
  private ColorPicker backgroundColorPicker;
  private ComboBox<String> turtleBox;
  private ToolBar myToolBar;
  private ListView<String> myList;
  private Stage myStage;
  private Color backgroundColor;
  private UserDefined userDefined;


  public Visualizer (Stage stage){
    language = "English";
    backgroundColor = Color.WHITE;
    myResources = ResourceBundle.getBundle(FORMAT_PACKAGE + language);
    userDefined = new UserDefined();
    turtleMap = new TreeMap<>();
    varMap = new TreeMap<>();
    cmdMap = new TreeMap<>();
    viewExternal = new ViewExternal(this);
    myController = new Controller(viewExternal, DEFAULT_LANGUAGE);
    commandLine = new CommandLine(myController, myResources);
    myList = new ListView<>();
    myToolBar = new ToolBar(stage, this, myResources);
    myTurtlesProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    styler = new Styler();
    colorPalette = new ColorPalette(createColorMap());
    shapePalette = new ShapePalette();
    myStage = stage;
  }

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
    HBox hbox = new HBox();
    hbox.setSpacing(HBOX_SPACING);
    hbox.getChildren().addAll(createTurtleUI(), createSettingsUI());

    viewPane.setMargin(cLine, new Insets(VIEWPANE_MARGIN, VIEWPANE_PADDING, VIEWPANE_MARGIN, VIEWPANE_PADDING));
    viewPane.setTop(toolBar);
    viewPane.setLeft(turtleView);
    viewPane.setCenter(cLine);
    viewPane.setRight(hbox);
    return viewPane;
  }

  private Node createTurtleUI() {
    VBox ui = new VBox();
    ui.setSpacing(VBOX_SPACING);
    ui.getChildren().addAll( styler.createButton(myResources.getString("AddTurtle"), e-> addTurtle()),
            makeTurtleSelector(),
            styler.createButton(myResources.getString("ChooseTurtle"), e-> getCurrentTurtle().chooseTurtle(getCurrentTurtle().getTurtleImage(new Stage()))),
            styler.createButton(myResources.getString("ResetCommand"), e->{
                    clear();
                    myController.resetAll();
                    for(TurtleView turtle : turtleMap.values()) {
                      turtle.resetTurtle();
                    }
            }),
            styler.createButton(myResources.getString("MoveTurtle"), e-> new MoveTurtle(myController)),
            addTurtleInfo());
    return ui;
  }

  private Node createSettingsUI() {
    VBox ui = new VBox();
    ui.setSpacing(VBOX_SPACING);
    ui.getChildren().addAll(styler.createLabel(myResources.getString("BackgroundColor")),
            backgroundColor(),
            styler.createLabel(myResources.getString("ChooseLanguage")),
            languageSelect(),
            styler.createButton(myResources.getString("PenProperties"), e->penProperties = new PenProperties(this)),
            styler.createButton(myResources.getString("ColorPalette"), e->colorPalette = new ColorPalette(createColorMap())),
            styler.createButton(myResources.getString("ShapePalette"), e->shapePalette = new ShapePalette()),
            styler.createButton(myResources.getString("HelpCommand"), e-> new HelpWindow(language)));
    return ui;
  }

  private ColorPicker backgroundColor(){
    backgroundColorPicker = new ColorPicker();
    backgroundColorPicker.setMaxHeight(COLORPICKER_HEIGHT);
    backgroundColorPicker.setValue(backgroundColor);
    backgroundColorPicker.setOnAction(e -> {
      backgroundColor = backgroundColorPicker.getValue();
      userDefined.setFill(backgroundColor);
    });
    return backgroundColorPicker;
  }

  private HBox addTurtleInfo(){
    HBox hbox = new HBox();
    VBox vbox = new VBox();
    vbox.setSpacing(VBOX_SPACING);
    vbox.getChildren().addAll(styler.createLabel(myResources.getString("ID")),
            styler.createLabel(myResources.getString("XCord")),
            styler.createLabel(myResources.getString("YCord")),
            styler.createLabel(myResources.getString("Angle")),
            styler.createLabel(myResources.getString("PenColor")),
            styler.createLabel(myResources.getString("PenWidth")),
            styler.createLabel(myResources.getString("PenDownLabel")));
    myList.setPrefSize(LISTVIEW_WIDTH, LISTVIEW_HEIGHT);
    hbox.getChildren().addAll(vbox, myList);
    return hbox;
  }

  private ComboBox<String> makeTurtleSelector(){
    turtleBox = new ComboBox();
    turtleBox.setPromptText("Pick Turtle");
    turtleBox.valueProperty().addListener((o, old, neww) -> setTurtle(neww));
    turtleBox.itemsProperty().bind(myTurtlesProperty);
    turtleBox.getSelectionModel().selectFirst();
    return turtleBox;
  }

  private ComboBox languageSelect(){
    String languages[] = { myResources.getString("English"),
            myResources.getString("Chinese"),
            myResources.getString("French"),
            myResources.getString("German"),
            myResources.getString("Italian"),
            myResources.getString("Portuguese"),
            myResources.getString("Spanish"),
            myResources.getString("Russian"),
            myResources.getString("Urdu")
    };
    ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(languages));
    comboBox.setValue(myResources.getString(language));
    comboBox.setOnAction(event -> {
      for(String key : myResources.keySet()){
        if(comboBox.getValue().toString().equals(myResources.getObject(key))){
          setLanguage(key);
        }
      }
    });
    return comboBox;
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

  public void addTurtle(String name, double startingX, double startingY, double heading){
    System.out.println(startingX + " " + startingY + " " + heading);
    try {
      myController.addTurtle(name, startingX, startingY, heading);
    } catch (InvalidTurtleException e){
      e.displayError("Please fix XML to contain unique turtles:");
      return;
    }
    TurtleView tempTurtle = new TurtleView(userDefined.getTurtles(), userDefined.getTurtlePaths(), myController.getTurtleName());
    tempTurtle.set(startingX, startingY, heading);
    turtleMap.putIfAbsent(myController.getTurtleName(), tempTurtle);
    myTurtlesProperty.getValue().add(myController.getTurtleName());

    setTurtle(myController.getTurtleName());
  }

  private void setTurtle(String name){
    myList.itemsProperty().unbind();
    if(currentTurtle != null) {
      currentTurtle.setOpacity(UNSELECTED_OPACITY);
    }
    currentTurtle = turtleMap.get(name);
    if( penProperties !=null){
      penProperties.getColorPicker().setValue(currentTurtle.getColor());
    }
    //FIXME Shitty way to do things
    if(turtleBox != null){
      turtleBox.getSelectionModel().select(name);
    }
    currentTurtle.setOpacity(SELECTED_OPACITY);
    myController.chooseTurtle(name);
    myList.itemsProperty().bind(currentTurtle.turtleStats());
  }

  public TurtleView getCurrentTurtle(){return currentTurtle;}


  public void clear(){userDefined.getTurtlePaths().getChildren().clear();}

  public void setPenColor(double value){
    if(colorPalette!=null){
      currentTurtle.updatePen(Color.web(colorPalette.getColorMapValue(value)));
    }
  }

  public void setBackgroundColorFromPalette(double value){
      setBackgroundColor(colorPalette.getColorMapValue(value));
  }

  public void setPenSize(double value){currentTurtle.setPenSize(value);}

  public void setShape(double value){
      currentTurtle.setShape(shapePalette.getShapeMapValue(value));
  }

  public void setLanguage(String newLanguage){
    language = newLanguage;
    myController.addLanguage(language);
    myResources = ResourceBundle.getBundle(FORMAT_PACKAGE + language);
    commandLine = new CommandLine(myController, myResources);
    myToolBar = new ToolBar(myStage, this, myResources);
    myStage.setScene(setupScene());
  }
  //FIXME why do we have so many colorpicker methods? can we combine any in any way
  public void setBackgroundColor(String hexColor){
    backgroundColor = Color.web(hexColor);
    userDefined.setFill(backgroundColor);
    backgroundColorPicker.setValue(Color.web(hexColor));
  }

  public CommandLine getTerminal(){return commandLine;}

  public String getLanguage(){
    return language;
  }

  public String getBackground(){
    return userDefined.getFill();
  }

  public Map<String, TurtleView> getTurtles(){
    return turtleMap;
  }

  public Map<String, String> getUserVariables(){
    return varMap;
  }

  public Map<String, String> getUserCommands(){
    return cmdMap;
  }
}
