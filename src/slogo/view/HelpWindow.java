package slogo.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class HelpWindow {

    public final String TITLE = "Command Reference Page";
    public static final Paint BACKGROUND = Color.AZURE;
    public static final int SIZE_WIDTH = 400;
    public static final int SIZE_HEIGHT = 500;

    Scene myScene;
    Group root;

    public HelpWindow(){
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
