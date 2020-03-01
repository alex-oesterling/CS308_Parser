package slogo.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
import slogo.controller.Controller;
import slogo.model.Parser;
import java.util.ResourceBundle;

public class Visualizer{

  public static final Paint BACKGROUND = Color.AZURE;
  public static final int TURTLE_SCREEN_WIDTH = 500;
  public static final int TURTLE_SCREEN_HEIGHT = 500;
  public static final int TURTLE_SCREEN_STROKEWIDTH = 3;
  public static final int COLORPICKER_HEIGHT = 30;
  private static final String STYLESHEET = "styling.css";
  private static final String RESOURCES = "resources";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  public static final String DEFAULT_RESOURCE_FOLDER = RESOURCES + "/formats/";


  public static final int VIEWPANE_PADDING = 10;
  public static final int VIEWPANE_MARGIN = 0;
  public static final int VBOX_SPACING = 10;
  public static final String LANGUAGE_PACKAGE = RESOURCES + ".languages.";
  public static final String FORMAT_PACKAGE = RESOURCES + ".formats.";
  private static final String DEFAULT_LANGUAGE = "English";

  private Scene myScene;
  private File turtleFile;
  private Parser myParser;
  private Controller myController;
  private HelpWindow helpWindow;
  private BorderPane root;
//  private Group view;
  private VBox variables;
  private VBox commands;
//  private VBox group;
//  private BorderPane viewPane;
  private Rectangle turtleArea;
  private List<TurtleView> turtleList; //FIXME Map between name and turtle instead of list (number to turtle)
  private ResourceBundle myResources;
  private String language;
  private Group turtlePaths;
  private Group turtles;
  private ViewExternal viewExternal;
  private CommandLine commandLine;
  private ColorPalette colorPalette;

  public Visualizer (){
    turtlePaths = new Group();
    turtleList = new ArrayList<>();
    turtles = new Group();
    viewExternal = new ViewExternal(this);
    myController = new Controller(viewExternal, DEFAULT_LANGUAGE);
    colorPalette = new ColorPalette();
    commandLine = new CommandLine(myController);
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
    turtleList.add(new TurtleView(turtles, turtlePaths));
    Group view = new Group();
    view.getChildren().addAll(turtleArea, turtlePaths, turtles);
    return view;
  }

  private VBox showUserDefined(){
    VBox group = new VBox();
    group.setSpacing(VBOX_SPACING);
    group.getChildren().add(createBox());

    BorderPane userDefined = new BorderPane();

    variables = makeHistory("Variables");
    commands = makeHistory("Commands");

    userDefined.setLeft(commands);
    userDefined.setRight(variables);

    group.getChildren().addAll(userDefined);
    group.setVgrow(userDefined, Priority.ALWAYS);
    return group;
  }

  private VBox makeHistory(String labelname) {
    VBox total = new VBox();
    VBox history = new VBox();
    ScrollPane userCommands = new ScrollPane();
    userCommands.setContent(history);
    userCommands.setPrefSize(TURTLE_SCREEN_WIDTH/2,TURTLE_SCREEN_HEIGHT/4); //fixme
    history.heightProperty().addListener((obs, old, newValue) -> userCommands.setVvalue((Double)newValue));
    Label label = new Label(myResources.getString(labelname));
    total.getChildren().addAll(label, userCommands);
    total.setVgrow(userCommands, Priority.ALWAYS);
    return total;
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
    Button reset = new Button(myResources.getString("ResetCommand"));
    reset.setOnAction(e->{
      clear();
      myController.reset();
      turtleList.get(0).resetTurtle();
    });
    ui.setSpacing(VBOX_SPACING);
    ui.getChildren().addAll(background, backgroundColor(), pen, penColor(), chooseLanguage, languageSelect(), chooseTurtle,
            reset, help());
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
    colorPicker.setOnAction(e -> viewExternal.updatePenColor(colorPicker.getValue()));
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
      language = comboBox.getValue().toString();
      myController.addLanguage(language);
    });
    return comboBox;
  }
  public void clear(){
    turtlePaths.getChildren().clear();
  }
  public List<TurtleView> getTurtleList(){return turtleList;}
}
