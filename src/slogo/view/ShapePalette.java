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

    public static final String TITLE = "Shape Palette";
    public static final Paint BACKGROUND = Color.AZURE;
    public static final int SIZE_WIDTH = 150;
    public static final int SIZE_HEIGHT = 500;
    public static final int VBOX_SPACING = 10;
    public static final int HBOX_SPACING = 10;
    public static final int TURTLE_WIDTH = 40;
    public static final int TURTLE_HEIGHT = 40;

    private VBox vbox;
    private Map<Double, String> map;

    public ShapePalette(){
        Stage stage = new Stage();
        stage.setScene(setScene());
        stage.setTitle(TITLE);
        stage.show();
    }

    private Scene setScene(){
        createGrid();
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(SIZE_WIDTH, SIZE_HEIGHT);
        s1.setContent(vbox);
        return new Scene(s1, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
    }

    private void createGrid(){
        vbox = new VBox();
        vbox.setSpacing(VBOX_SPACING);
        map = new TreeMap<>();
        map.put(0.0, "turtle1.png");
        map.put(1.0, "turtle2.png");
        map.put(2.0, "turtle3.png");
        map.put(3.0, "turtle4.png");
        map.put(4.0, "turtle5.png");
        map.put(5.0, "turtle6.png");
        map.put(6.0, "turtle7.png");
        map.put(7.0, "heart.png");
        map.put(8.0, "smile.png");
        map.put(9.0, "star.png");
        map.put(10.0, "Coach_k.png");
        map.put(11.0, "Alex_OConnell.png");
        map.put(12.0, "Cassius_Stanley.png");
        map.put(13.0, "Goldwire_Jordan.png");
        map.put(14.0, "Jack_White.png");
        map.put(15.0, "Javon_DeLaurier.png");
        map.put(16.0, "Joey_Baker.png");
        map.put(17.0, "Justin_Robinson.png");
        map.put(18.0, "Keenan_Worthington.png");
        map.put(19.0, "Matt_hurt.png");
        map.put(20.0, "Michael_Savarino.png");
        map.put(21.0, "Mike_Buckmire.png");
        map.put(22.0, "Tre_Jones.png");
        map.put(23.0, "Vernon_Carey.png");
        map.put(24.0, "Wendell_Moore.png");
        for (Double key : map.keySet()) {
            HBox hbox = new HBox();
            hbox.setSpacing(HBOX_SPACING);
            Label index = new Label(Double.toString(key));
            hbox.getChildren().addAll(addTurtle(map.get(key)), index);
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
        return addTurtle(map.get(value));
    }
}
