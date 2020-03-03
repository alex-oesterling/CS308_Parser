package slogo.view;

import java.util.*;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ListBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
import slogo.controller.Controller;
import slogo.exceptions.InvalidTurtleException;
import slogo.model.Turtle;

public class Visualizer{

  public static final Paint BACKGROUND = Color.AZURE;
  public static final int TURTLE_SCREEN_WIDTH = 500;
  public static final int TURTLE_SCREEN_HEIGHT = 500;
  public static final int TURTLE_SCREEN_STROKEWIDTH = 3;
  public static final int COLORPICKER_HEIGHT = 30;
  private static final String STYLESHEET = "styling.css";
  private static final String RESOURCES = "resources";
  public static final String DEFAULT_RESOURCE_FOLDER = RESOURCES + "/formats/";


  public static final int VIEWPANE_PADDING = 10;
  public static final int VIEWPANE_MARGIN = 0;
  public static final int VBOX_SPACING = 10;
  public static final String FORMAT_PACKAGE = RESOURCES + ".formats.";
  private static final String DEFAULT_LANGUAGE = "English";

  private Controller myController;
  private BorderPane root;
  private HelpWindow helpWindow;
  private ViewExternal viewExternal;
  private CommandLine commandLine;
  private Styler styler;
  private ColorPalette colorPalette;
  private ShapePalette shapePalette;
  private VBox variables;
  private VBox commands;
  private Rectangle turtleArea;
  private Map<String, TurtleView> turtleList; //FIXME Map between name and turtle instead of list (number to turtle)
  private ResourceBundle myResources;
  private String language;
  private Group turtlePaths;
  private Group turtles;

  private VBox commandHistory;
  private VBox varHistory;
  private Map<String, String> varMap;
  private SimpleObjectProperty<ObservableList<String>> myTurtlesProperty;
  private TurtleView currentTurtle;
  private ColorPicker colorPicker;


  public Visualizer (){
    turtlePaths = new Group();
    turtleList = new HashMap<>();
    turtles = new Group();
    varMap = new HashMap<>();
    viewExternal = new ViewExternal(this);
    myController = new Controller(viewExternal, DEFAULT_LANGUAGE);
    commandLine = new CommandLine(myController);
    myTurtlesProperty = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    colorPicker = new ColorPicker();
    styler = new Styler();
  }

  public Scene setupScene() {
    myResources = ResourceBundle.getBundle(FORMAT_PACKAGE + "Buttons");
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
    viewPane.setBackground(new Background(new BackgroundFill(BACKGROUND, null, null)));
    viewPane.setPadding(new Insets(VIEWPANE_PADDING, VIEWPANE_PADDING, VIEWPANE_PADDING, VIEWPANE_PADDING));

    Node turtleView = showUserDefined();
    Node cLine = commandLine.setupCommandLine();
    Node userInterface = createUI();

    viewPane.setMargin(cLine, new Insets(VIEWPANE_MARGIN, VIEWPANE_PADDING, VIEWPANE_MARGIN, VIEWPANE_PADDING));
    viewPane.setLeft(turtleView);
    viewPane.setCenter(cLine);
    viewPane.setRight(userInterface);

    return viewPane;
  }

  private Group createBox() {
    turtleArea = new Rectangle(TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
    turtleArea.setFill(Color.WHITE);
    turtleArea.setStroke(Color.BLACK);
    turtleArea.setStrokeWidth(TURTLE_SCREEN_STROKEWIDTH);
    addTurtle();
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

  private Node createUI() {
    VBox ui = new VBox();

    ui.setSpacing(VBOX_SPACING);
    ui.getChildren().addAll(styler.createLabel(myResources.getString("BackgroundColor")),
            backgroundColor(),
            styler.createLabel(myResources.getString("PenColor")),
            penColor(),
            styler.createLabel(myResources.getString("ChooseLanguage")),
            languageSelect(),
            styler.createButton(myResources.getString("ChooseTurtle"), e->turtleList.get(0).chooseTurtle()),
            styler.createButton(myResources.getString("AddTurtle"), e-> addTurtle()),
            makeTurtleSelector(),
            styler.createButton(myResources.getString("ColorPalette"), e->colorPalette = new ColorPalette()),
            styler.createButton(myResources.getString("ShapePalette"), e->shapePalette = new ShapePalette()),
            styler.createButton(myResources.getString("HelpCommand"), e->helpWindow = new HelpWindow(language)),
            styler.createButton(myResources.getString("ResetCommand"),
                    e->{ clear(); myController.reset(); turtleList.get(0).resetTurtle(); }));
    return ui;
  }

  private ColorPicker backgroundColor(){
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setMaxHeight(COLORPICKER_HEIGHT);
    colorPicker.setOnAction(e -> turtleArea.setFill(colorPicker.getValue()));
    return colorPicker;
  }

  private ColorPicker penColor(){
    colorPicker.setValue(Color.BLACK);
    colorPicker.setMaxHeight(COLORPICKER_HEIGHT);
    colorPicker.setOnAction(e -> viewExternal.updatePenColor(colorPicker.getValue()));
    return colorPicker;
  }

//  private HBox addTurtleInfo(){
//    HBox hbox = new HBox();
//    ListView<String> list = new ListView<>();
////    list.set
//    list.itemsProperty().bind(currentTurtle.turtleStats());
//    hbox.getChildren().add(list);
//    return hbox;
//  }

  private ComboBox<String> makeTurtleSelector(){
    ComboBox<String> turtleBox = new ComboBox();
    turtleBox.setPromptText("Pick Turtle");
    turtleBox.valueProperty().addListener((o, old, neww) -> setTurtle(neww));
    turtleBox.itemsProperty().bind(myTurtlesProperty);
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
    comboBox.setValue(myResources.getString("English"));
    language = comboBox.getValue().toString();
    comboBox.setOnAction(event -> {
      language = comboBox.getValue().toString();
      myController.addLanguage(language);
    });
    return comboBox;
  }

  public void clear(){
    turtlePaths.getChildren().clear();
  }

  public TurtleView getCurrentTurtle(){return currentTurtle;}

  public void addCommand(String command, String syntax){
    Label recentCommand = new Label(command);
    Label syntaxLabel = new Label(syntax); //modify based on what model wants it to do
    HBox commandAndSyntax = new HBox();
    commandAndSyntax.setOnMouseClicked(commandLine.setOnClick(command));
    commandAndSyntax.setMinWidth(commandHistory.getWidth());
    final Pane spacer = new Pane();
    commandAndSyntax.setHgrow(spacer, Priority.ALWAYS);
    commandAndSyntax.getChildren().addAll(recentCommand, spacer, syntaxLabel);
    commandHistory.getChildren().add(commandAndSyntax);
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
  }

  //FIXME variable types :right now all it handles is doubles and poorly at that
  private void updateVariable(String variableName, Label value){
    TextInputDialog updateVariable = new TextInputDialog();
    updateVariable.setTitle("Update Variable");
    updateVariable.setHeaderText("Update " + variableName + " value by entering in a valid number:");
    updateVariable.setContentText("Enter new number here:");
    Optional<String> result = updateVariable.showAndWait();
    if(result.isPresent()){
      varMap.put(variableName, result.get());
      value.setText(result.get());
      myController.updateCommandVariable(variableName, result.get());
    }
  }

  private void addTurtle(){
    try {
      myController.addTurtle();
    } catch (InvalidTurtleException e){
      //ERROR DIALOG: Turtle Already Exists!
    }
    currentTurtle = new TurtleView(turtles, turtlePaths);
    colorPicker.setValue(currentTurtle.getColor());
    turtleList.putIfAbsent(myController.getTurtleName(), currentTurtle);
    myTurtlesProperty.getValue().add(myController.getTurtleName());
  }

  private void setTurtle(String name){
    currentTurtle = turtleList.get(name);
    colorPicker.setValue(currentTurtle.getColor());
    myController.chooseTurtle(name);
  }

  public TurtleView getCurrentTurtle(){return currentTurtle;}

  public void addCommand(String command){
    Label recentCommand = new Label(command);
    commandLine.setOnClick(recentCommand, recentCommand.getText()); //modify based on what model wants it to do
    commandHistory.getChildren().add(recentCommand);
  }

  public void clear(){turtlePaths.getChildren().clear();}

  public void setPenColor(double value){
    currentTurtle.updatePen(Color.web(colorPalette.getColorMapValue(value)));
  }

  public void setBackgroundColor(double value){
    turtleArea.setFill(Color.web(colorPalette.getColorMapValue(value)));
  }

  public void setPenSize(double value){currentTurtle.setPenSize(value);}

  public void setShape(double value){
    currentTurtle.setShape(shapePalette.getShapeMapValue(value));
  }

  public void addVariable(String variable, double value){
    Label recentCommand = new Label(variable);
    varMap.put(variable, value);
    recentCommand.setOnMouseClicked(e->updateVariable(variable));
    commandHistory.getChildren().add(recentCommand);
  }
}
