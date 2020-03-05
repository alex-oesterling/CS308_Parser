package slogo.view;

import java.util.*;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import slogo.controller.Controller;
import slogo.exceptions.InvalidTurtleException;

public class Visualizer{

  public static final Paint BACKGROUND = Color.AZURE;
  public static final int TURTLE_SCREEN_WIDTH = 500;
  public static final int TURTLE_SCREEN_HEIGHT = 500;
  public static final int TURTLE_SCREEN_STROKEWIDTH = 3;
  public static final int COLORPICKER_HEIGHT = 30;
  public static final int LISTVIEW_WIDTH = 100;
  public static final int LISTVIEW_HEIGHT  = 250;
  private static final String STYLESHEET = "styling.css";
  private static final String RESOURCES = "resources";
  public static final String DEFAULT_RESOURCE_FOLDER = RESOURCES + "/formats/";
  public static final String COLOR_RESOURCE = "resources.formats";
  public static final String DEFAULT_COLOR_RESOURCE_PACKAGE = COLOR_RESOURCE + ".Colors";


  public static final int VIEWPANE_PADDING = 10;
  public static final int VIEWPANE_MARGIN = 0;
  public static final int VBOX_SPACING = 10;
  public static final int HBOX_SPACING = 10;
  public static final String FORMAT_PACKAGE = RESOURCES + ".formats.";
  private static final String DEFAULT_LANGUAGE = "English";

  private Controller myController;
  private BorderPane root;
  private ViewExternal viewExternal;
  private CommandLine commandLine;
  private PenProperties penProperties;
  private Styler styler;
  private ColorPalette colorPalette;
  private ShapePalette shapePalette;
  private Rectangle turtleArea;
  private Map<String, TurtleView> turtleMap; //FIXME Map between name and turtle instead of list (number to turtle)
  private ResourceBundle myResources;
  private ResourceBundle myColorResources;
  private String language;
  private Group turtlePaths;
  private Group turtles;
  private VBox commandHistory;
  private VBox varHistory;
  private Map<String, String> varMap;
  private Map<String, String> cmdMap;
  private SimpleObjectProperty<ObservableList<String>> myTurtlesProperty;
  private TurtleView currentTurtle;
  private ColorPicker colorPicker;
  private ColorPicker backgroundColorPicker;
  private ComboBox<String> turtleBox;
  private ToolBar myToolBar;
  private ListView<String> myList;
  private Stage myStage;


  public Visualizer (Stage stage){
    language = "English";
    myResources = ResourceBundle.getBundle(FORMAT_PACKAGE + language);
    turtlePaths = new Group();
    turtleMap = new TreeMap<>();
    turtles = new Group();
    varMap = new TreeMap<>();
    cmdMap = new TreeMap<>();
    viewExternal = new ViewExternal(this);
    myController = new Controller(viewExternal, DEFAULT_LANGUAGE);
    commandLine = new CommandLine(myController, myResources);
    myList = new ListView<>();
    myToolBar = new ToolBar(stage, this, myResources);
    myTurtlesProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    colorPicker = new ColorPicker();
    styler = new Styler();
    colorPalette = new ColorPalette(createColorMap());
    shapePalette = new ShapePalette();
    myStage = stage;
  }

  public Scene setupScene() {
    root = createView();
    Scene myScene = new Scene(root);
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
    Node turtleView = showUserDefined();
    Node cLine = commandLine.setupCommandLine();
    Node userInterface = createSettingsUI();
    Node userTurtleInterface = createTurtleUI();
    HBox hbox = new HBox();
    hbox.setSpacing(HBOX_SPACING);
    hbox.getChildren().addAll(userTurtleInterface, userInterface);

    viewPane.setMargin(cLine, new Insets(VIEWPANE_MARGIN, VIEWPANE_PADDING, VIEWPANE_MARGIN, VIEWPANE_PADDING));
    viewPane.setTop(toolBar);
    viewPane.setLeft(turtleView);
    viewPane.setCenter(cLine);
    viewPane.setRight(hbox);
    return viewPane;
  }

  private Group createBox() {
    turtleArea = new Rectangle(TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
    turtleArea.setFill(Color.WHITE);
    turtleArea.setStroke(Color.BLACK);
    turtleArea.setStrokeWidth(TURTLE_SCREEN_STROKEWIDTH);
    Group view = new Group();
    view.getChildren().addAll(turtleArea, turtlePaths, turtles);
    return view;
  }

  private VBox showUserDefined(){
    VBox group = new VBox();
    group.setSpacing(VBOX_SPACING);
    group.getChildren().add(createBox());

    BorderPane userDefined = new BorderPane();

    commandHistory = new VBox();
    varHistory = new VBox();
    Node varScroll = makeHistory(varHistory);
    Node commandScroll = makeHistory(commandHistory);

    commandHistory.setPrefWidth(turtleArea.getWidth()/2-VIEWPANE_PADDING);
    varHistory.setPrefWidth(turtleArea.getWidth()/2-VIEWPANE_PADDING);

    Label varLabel = styler.createLabel("Variables");
    VBox variables = new VBox();
    variables.getChildren().addAll(varLabel, varScroll);
    variables.setVgrow(commandScroll, Priority.ALWAYS);

    Label cmdLabel = styler.createLabel("Commands");
    VBox commands = new VBox();
    commands.getChildren().addAll(cmdLabel, commandScroll);
    commands.setVgrow(commandScroll, Priority.ALWAYS);

    userDefined.setLeft(commands);
    userDefined.setRight(variables);

    group.getChildren().addAll(userDefined);
    group.setVgrow(userDefined, Priority.ALWAYS);
    return group;
  }

  private Node makeHistory(VBox history) {
    ScrollPane userCommands = new ScrollPane();
    userCommands.setContent(history);
    userCommands.setPrefSize(TURTLE_SCREEN_WIDTH/2,TURTLE_SCREEN_HEIGHT/4);
    history.heightProperty().addListener((obs, old, newValue) -> userCommands.setVvalue((Double)newValue));
    return userCommands;
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
                    for(String s : turtleMap.keySet()) {
                      turtleMap.get(s).resetTurtle();
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
    backgroundColorPicker.setOnAction(e -> turtleArea.setFill(backgroundColorPicker.getValue()));
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
//    myList.itemsProperty().bind(currentTurtle.turtleStats());
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
      setLanguage(comboBox.getValue().toString());
      myResources = ResourceBundle.getBundle(FORMAT_PACKAGE + language);
      commandLine = new CommandLine(myController, myResources);
      myToolBar = new ToolBar(myStage, this, myResources);
      myStage.setScene(setupScene());
    });

    return comboBox;
  }

  private Map<Double, String> createColorMap(){
    myColorResources = ResourceBundle.getBundle(DEFAULT_COLOR_RESOURCE_PACKAGE);
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
    commandAndSyntax.setMinWidth(commandHistory.getWidth());
    final Pane spacer = new Pane();
    commandAndSyntax.setHgrow(spacer, Priority.ALWAYS);
    commandAndSyntax.getChildren().addAll(recentCommand, spacer, syntaxLabel);
    cmdMap.put(command, syntax);
    commandHistory.getChildren().add(commandAndSyntax);
    myController.addUserCommand(command, syntax);
  }

  public void addVariable(String variable, String value){
    Label recentCommand = new Label(variable);
    Label valueLabel = new Label(value);
    HBox variableAndValue = new HBox();
    variableAndValue.setMinWidth(varHistory.getWidth());
    final Pane spacer = new Pane();
    variableAndValue.setHgrow(spacer, Priority.ALWAYS);
    variableAndValue.getChildren().addAll(recentCommand, spacer, valueLabel);
    varMap.put(variable, value);
    variableAndValue.setOnMouseClicked(e->updateVariable(variable, valueLabel));
    varHistory.getChildren().add(variableAndValue);
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
      } catch (NullPointerException e){
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText("Please enter a double:");
        errorAlert.setContentText("Restoring variable");
        errorAlert.showAndWait();
        number = Double.parseDouble(value.getText());
      }
      varMap.put(variableName, number.toString());
      value.setText(result.get());
      myController.updateCommandVariable(variableName, number.toString());
    }
  }

  public void addTurtle(){
    try {
      myController.addTurtle();
    } catch (InvalidTurtleException e){
      Alert errorAlert = new Alert(AlertType.ERROR);
      errorAlert.setHeaderText("Turtle already exists!");
      errorAlert.setContentText("Please choose a unique Turtle name");
      errorAlert.showAndWait();
    }
    TurtleView tempTurtle = new TurtleView(turtles, turtlePaths, myController.getTurtleName());
    turtleMap.putIfAbsent(myController.getTurtleName(), tempTurtle);
    myTurtlesProperty.getValue().add(myController.getTurtleName());
    setTurtle(myController.getTurtleName());
  }

  public void addTurtle(String name, double startingX, double startingY, double heading){
    System.out.println(startingX + " " + startingY + " " + heading);
    try {
      myController.addTurtle(name, startingX, startingY, heading);
    } catch (InvalidTurtleException e){
      Alert errorAlert = new Alert(AlertType.ERROR);
      errorAlert.setHeaderText("Turtle already exists!");
      errorAlert.setContentText("Please fix XML file to specify unique turtle");
      errorAlert.showAndWait();
      return;
    }
    TurtleView tempTurtle = new TurtleView(turtles, turtlePaths, myController.getTurtleName());
    tempTurtle.set(startingX, startingY, heading);
    turtleMap.putIfAbsent(myController.getTurtleName(), tempTurtle);
    myTurtlesProperty.getValue().add(myController.getTurtleName());

    setTurtle(myController.getTurtleName());
  }

  private void setTurtle(String name){
    myList.itemsProperty().unbind();
    if(currentTurtle != null) {
      currentTurtle.setOpacity(.5);
    }
    currentTurtle = turtleMap.get(name);
    if( penProperties !=null){
      penProperties.getColorPicker().setValue(currentTurtle.getColor());
    }
    //FIXME Shitty way to do things
    if(turtleBox != null){
      turtleBox.getSelectionModel().select(name);
    }
    currentTurtle.setOpacity(1);
    myController.chooseTurtle(name);
    myList.itemsProperty().bind(currentTurtle.turtleStats());
  }

  public TurtleView getCurrentTurtle(){return currentTurtle;}


  public void clear(){turtlePaths.getChildren().clear();}

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
  }

  public void setBackgroundColor(String hexColor){
    turtleArea.setFill(Color.web(hexColor));
    backgroundColorPicker.setValue(Color.web(hexColor));
  }

  public CommandLine getTerminal(){return commandLine;}

  public String getLanguage(){
    return language;
  }

  public String getBackground(){
    return turtleArea.getFill().toString();
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
