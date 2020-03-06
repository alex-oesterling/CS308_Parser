package slogo.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UserDefined {

    public static final int TURTLE_SCREEN_WIDTH = 500;
    public static final int TURTLE_SCREEN_HEIGHT = 500;
    public static final int TURTLE_SCREEN_STROKEWIDTH = 3;
    public static final int VBOX_SPACING = 10;
    public static final int VIEWPANE_PADDING = 10;


    private Rectangle turtleArea;
    private Color backgroundColor;
    private Group turtlePaths;
    private Group turtles;
    private VBox commandHistory;
    private VBox varHistory;
    private Styler styler;

    public UserDefined(){
        turtlePaths = new Group();
        turtles = new Group();
        backgroundColor = Color.WHITE;
        styler = new Styler();
    }

    public VBox showUserDefined(){
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

    private Group createBox() {
        turtleArea = new Rectangle(TURTLE_SCREEN_WIDTH, TURTLE_SCREEN_HEIGHT);
        turtleArea.setFill(backgroundColor);
        turtleArea.setStroke(Color.BLACK);
        turtleArea.setStrokeWidth(TURTLE_SCREEN_STROKEWIDTH);
        Group view = new Group();
        view.getChildren().addAll(turtleArea, turtlePaths, turtles);
        return view;
    }

    private Node makeHistory(VBox history) {
        ScrollPane userCommands = new ScrollPane();
        userCommands.setContent(history);
        userCommands.setPrefSize(TURTLE_SCREEN_WIDTH/2,TURTLE_SCREEN_HEIGHT/4);
        history.heightProperty().addListener((obs, old, newValue) -> userCommands.setVvalue((Double)newValue));
        return userCommands;
    }

    public void setFill(Color color){
        turtleArea.setFill(color);
        backgroundColor = color;
    }

    public Color getFill(){
        return backgroundColor;
    }

    public void addCommand(Node newCommand){
        commandHistory.getChildren().add(newCommand);
    }

    public void addVariable(Node newVariable){
        varHistory.getChildren().add(newVariable);
    }

    public Group getTurtlePaths(){
        return turtlePaths;
    }

    public Group getTurtles(){
        return turtles;
    }
}
