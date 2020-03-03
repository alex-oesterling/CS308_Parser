package slogo.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.util.Map;
import java.util.TreeMap;

public class ShapePalette {

    public final String TITLE = "Shape Palette";
    public static final Paint BACKGROUND = Color.AZURE;
    public static final int SIZE_WIDTH = 150;
    public static final int SIZE_HEIGHT = 500;
    public static final int VBOX_SPACING = 10;
    public static final int HBOX_SPACING = 10;
    public static final int TURTLE_WIDTH = 40;
    public static final int TURTLE_HEIGHT = 40;

    private Group root;
    private Scene myScene;
    private VBox vbox;
    private Map<Double, ImageView> map;

    public ShapePalette(){
        Stage stage = new Stage();
        stage.setScene(setScene());
        stage.setTitle(TITLE);
        stage.show();
    }

    private Scene setScene(){
        root = new Group();
        createGrid();
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(SIZE_WIDTH, SIZE_HEIGHT);
        s1.setContent(vbox);
        root.getChildren().add(s1);
        return myScene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
    }

    private void createGrid(){
        vbox = new VBox();
        vbox.setSpacing(VBOX_SPACING);
        map = new TreeMap<>();
        map.put(0.0, addTurtle("turtle1.png"));
        map.put(1.0, addTurtle("turtle2.png"));
        map.put(2.0, addTurtle("turtle3.png"));
        map.put(3.0, addTurtle("turtle4.png"));
        map.put(4.0, addTurtle("turtle5.png"));
        map.put(5.0, addTurtle("turtle6.png"));
        map.put(6.0, addTurtle("turtle7.png"));
        map.put(7.0, addTurtle("heart.png"));
        map.put(8.0, addTurtle("smile.png"));
        map.put(9.0, addTurtle("star.png"));
        for (Double key : map.keySet()) {
            HBox hbox = new HBox();
            hbox.setSpacing(HBOX_SPACING);
            Label index = new Label(Double.toString(key));
            hbox.getChildren().addAll(map.get(key), index);
            vbox.getChildren().add(hbox);
        }
    }

    private ImageView addTurtle(String turtle){
        String string = "resources/turtles/" + turtle;
        ImageView turtleImage = new ImageView(string);
        turtleImage.setFitWidth(TURTLE_WIDTH);
        turtleImage.setFitHeight(TURTLE_HEIGHT);
        return turtleImage;
    }

    public ImageView getShapeMapValue(double value){
        return map.get(value);
    }
}
