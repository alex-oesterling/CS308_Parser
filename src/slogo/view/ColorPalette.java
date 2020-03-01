package slogo.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class ColorPalette {

    public final String TITLE = "Color Palette";
    public static final int SIZE_WIDTH = 500;
    public static final int SIZE_HEIGHT = 500;
    public static final Paint BACKGROUND = Color.AZURE;

    private Group root;
    private Scene myScene;

    public ColorPalette(){
        Stage stage = new Stage();
        stage.setScene(setScene());
        stage.setTitle(TITLE);
        stage.show();
    }

    private Scene setScene(){
        root = new Group();
        return myScene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT, BACKGROUND);
    }
}
