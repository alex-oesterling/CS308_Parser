package slogo.view;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
import slogo.controller.Controller;
import slogo.model.Parser;
import java.util.ResourceBundle;
import slogo.model.command.booleancommand.And;

public class Visualizer implements ViewExternalAPI{

  public static final Paint BACKGROUND = Color.AZURE;
  public static final int TURTLE_SCREEN_WIDTH = 500;
  public static final int TURTLE_SCREEN_HEIGHT = 500;
  public static final int TURTLE_SCREEN_STROKEWIDTH = 3;
  public static final int COLORPICKER_HEIGHT = 30;

  public static final int VIEWPANE_PADDING = 10;
  public static final int VIEWPANE_MARGIN = 0;
  public static final int VBOX_SPACING = 10;
  public static final String RESOURCE = "resources.languages";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCE + ".";

  private Scene myScene;
  private Parser myParser;
  private Controller myController;
  private HelpWindow helpWindow;
  private BorderPane root;
  private Group view;
  private VBox variables;
  private VBox commands;
  private VBox group;
  private BorderPane viewPane;
  private Rectangle turtleArea;
  private List<TurtleView> turtleList; //FIXME Map between name and turtle instead of list (number to turtle)
  private ResourceBundle myResources;
  private String language;
  private Group turtlePaths;
  private Group turtles;

  public Visualizer (Parser parser){
    view = new Group();
    turtlePaths = new Group();
    turtleList = new ArrayList<>();
    turtles = new Group();
    myParser = parser; //FIXME
    myController = new Controller(new And(0, 0), this); //FIXME  just made a random command
    myController.addLanguage("English"); //FIXME
    myController.addLanguage("Syntax"); //FIXME
  }

  public Scene setupScene() {
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Buttons");
    root = createView();
    myScene = new Scene(root);
    return myScene;
  }

  private BorderPane createView(){
    viewPane = new BorderPane();
    viewPane.setBackground(new Background(new BackgroundFill(BACKGROUND, null, null)));
    viewPane.setPadding(new Insets(VIEWPANE_PADDING, VIEWPANE_PADDING, VIEWPANE_PADDING, VIEWPANE_PADDING));

    Node turtleView = showUserDefined();
    Node commandLine = new CommandLine(myController).setupCommandLine();
    Node userInterface = createUI();

    viewPane.setMargin(commandLine, new Insets(VIEWPANE_MARGIN, VIEWPANE_PADDING, VIEWPANE_MARGIN, VIEWPANE_PADDING));
    viewPane.setLeft(turtleView);
    viewPane.setCenter(commandLine);
    viewPane.setRight(userInterface);

    return viewPane;
  }

  private Group createBox() { //FIXME discuss creating objects here or in config
    turtleArea = new Rectangle(TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
    turtleArea.setFill(Color.WHITE);
    turtleArea.setStroke(Color.BLACK);
    turtleArea.setStrokeWidth(TURTLE_SCREEN_STROKEWIDTH);
    turtleList.add(new TurtleView(turtles, turtlePaths));
    view.getChildren().addAll(turtleArea, turtlePaths, turtles);
    return view;
  }

  private VBox showUserDefined(){
    group = new VBox();
    group.setSpacing(VBOX_SPACING);
    group.getChildren().add(createBox());

    BorderPane userDefined = new BorderPane();
    variables = new VBox();
    ScrollPane userVariables = new ScrollPane();
    userVariables.setContent(variables);
    userVariables.setPrefSize(TURTLE_SCREEN_WIDTH/2,TURTLE_SCREEN_HEIGHT/4 ); //FIXME dependency on turtlearea width
    variables.heightProperty().addListener((obs, old, newValue) -> userVariables.setVvalue((Double)newValue));

    commands = new VBox();
    ScrollPane userCommands = new ScrollPane();
    userCommands.setContent(commands);
    userCommands.setPrefSize(TURTLE_SCREEN_WIDTH/2,TURTLE_SCREEN_HEIGHT/4);
    commands.heightProperty().addListener((obs, old, newValue) -> userCommands.setVvalue((Double)newValue));
    //fixme duplicated code to create boxes for saved commands -- extract method?
    userDefined.setLeft(userVariables);
    userDefined.setRight(userCommands);

    Label variablesLabel = new Label(myResources.getString("Variables"));
    Label commandsLabel = new Label(myResources.getString("Commands"));
    GridPane grid = new GridPane();
    grid.setHgap(TURTLE_SCREEN_WIDTH/3);
    GridPane.setConstraints(variablesLabel, 0, 0);
    GridPane.setConstraints(commandsLabel, 1, 0);
    grid.getChildren().addAll(variablesLabel, commandsLabel);

    group.getChildren().addAll(grid, userDefined);
    group.setVgrow(userDefined, Priority.ALWAYS);
    return group;
  }

  private Node createUI() {
    VBox ui = new VBox();
    Label background = new Label(myResources.getString("BackgroundColor"));
    Label pen = new Label(myResources.getString("PenColor"));
    Label chooseLanguage = new Label(myResources.getString("ChooseLanguage"));
    Button chooseTurtle = new Button(myResources.getString("ChooseTurtle"));
    chooseTurtle.setOnAction(e-> {
      turtleList.get(0).chooseTurtle();
            });
    ui.setSpacing(VBOX_SPACING);
    ui.getChildren().addAll(background, backgroundColor(), pen, penColor(), chooseLanguage, languageSelect(), chooseTurtle,
            help(), testUpdate());
    return ui;
  }

  private ColorPicker backgroundColor(){
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setMaxHeight(COLORPICKER_HEIGHT);
    colorPicker.setOnAction(e -> turtleArea.setFill(colorPicker.getValue()));
    return colorPicker;
  }

  private ColorPicker penColor(){
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setValue(Color.BLACK);
    colorPicker.setMaxHeight(COLORPICKER_HEIGHT);
    colorPicker.setOnAction(e -> turtleList.get(0).updatePen(colorPicker.getValue()));
    return colorPicker;
  }


  private Button help(){
    Button help = new Button(myResources.getString("HelpCommand"));
    help.setOnAction(e-> helpWindow = new HelpWindow(language));
    return help;
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
      //TODO: pass in value of combobox to some method to change the language
      language = comboBox.getValue().toString();
    });
    return comboBox;
  }

  private Button testUpdate(){
    Button test = new Button("Test");
    test.setOnAction(e->update(200, 200, 90));
    return test;
  }

  @Override
  public void update(double newX, double newY, double orientation){
    turtleList.get(0).update(newX, newY, orientation);
  }

  @Override
  public void updatePenColor() {

  }

  @Override
  public void updateSceneColor() {

  }

  @Override
  public void clear() {

  }

  @Override
  public void update() {

  }
}
