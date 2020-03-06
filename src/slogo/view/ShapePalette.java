package slogo.view;

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

/**
 * This class creates a palette of shapes and corresponding doubles as indices. In this way, the user is able to use the
 * command box to change the the shape of the turtle by using the doubles.
 */
public class ShapePalette {

    public static final String TITLE = "Shape Palette";
    public static final Paint BACKGROUND = Color.AZURE;
    public static final double[] SHAPE_INDICES = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0,
                                                    15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0, 26.0};
    public static final String[] SHAPE_NAMES = {"turtle1.png", "turtle2.png", "turtle3.png", "turtle4.png", "turtle5.png",
            "turtle6.png", "turtle7.png", "heart.png", "smile.png", "star.png", "rcd_old.png", "basketball.png", "Coach_k.png", "Alex_OConnell.png", "Cassius_Stanley.png",
            "Goldwire_Jordan.png", "Jack_White.png", "Javon_DeLaurier.png", "Joey_Baker.png", "Justin_Robinson.png", "Keenan_Worthington.png",
            "Matt_hurt.png", "Michael_Savarino.png", "Mike_Buckmire.png", "Tre_Jones.png", "Vernon_Carey.png", "Wendell_Moore.png"};
    public static final int SIZE_WIDTH = 150;
    public static final int SIZE_HEIGHT = 500;
    public static final int VBOX_SPACING = 10;
    public static final int HBOX_SPACING = 10;
    public static final int TURTLE_WIDTH = 40;
    public static final int TURTLE_HEIGHT = 40;

    private VBox vbox;
    private Map<Double, String> map;

    /**
     * Here, a new stage is set. The ShapePalette class creates a new window and displays the palette using a map of doubles
     * mapped to strings which then correspond to images in the resource file.
     */
    public ShapePalette(){ }

    /**
     * Only called when the shape palette needs to be shown. In this way, without the shape palette showing, the user can
     * still access the shapes and the indices.
     */
    public void showPalette(){
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
        for(int i=0; i<SHAPE_INDICES.length; i++){
            map.put(SHAPE_INDICES[i], SHAPE_NAMES[i]);
        }
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

    /**
     * In order to change the turtle image, this class is called to receive the necessary image and update it in TurtleView.
     * @param value - the double that the user types in that corresponds to the desired image.
     * @return - creates a new instance of the image which is then returned.
     */
    public ImageView getShapeMapValue(double value){
        return addTurtle(map.get(value));
    }
}
