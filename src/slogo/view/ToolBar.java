package slogo.view;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import slogo.SlogoApp;

public class ToolBar {
  private static final String RESOURCES = "resources";
  public static final String FORMAT_PACKAGE = RESOURCES + ".formats.";

  private MenuBar menuBar;
  private Menu menu;
  private MenuItem newWindow;
  private MenuItem exit;
  private MenuItem restart;
  private ResourceBundle myResources;
  private Stage myStage;

  public ToolBar(Stage stage){
    myStage = stage;
    myResources = ResourceBundle.getBundle(FORMAT_PACKAGE + "Buttons");
  }

  public Node setupToolBar(){
    HBox tools = new HBox();
    menuBar = new MenuBar();
    menu = makeMenu("Menu");
    newWindow = makeMenuItem("New", e-> makeNewWindow());
    exit = makeMenuItem("Exit", e-> closeWindow());
    restart = makeMenuItem("Restart", e->{
      closeWindow();
      makeNewWindow();
    });
    menuBar.getMenus().add(menu);
    menu.getItems().addAll(newWindow, restart, exit);
    tools.getChildren().add(menuBar);
    return tools;
  }

  private void closeWindow() {
    myStage.close();
  }

  private void makeNewWindow() {
    SlogoApp newApp = new SlogoApp(new Stage());
  }

  /**
   * Handles the creation of MenuItem objects, applies a label to them as specified in the createLabel method and
   * gives them an action on click.
   * @param property - the label to be applied to the object specified in the .properties file
   * @param handler - the action to occur when the menu item is clicked
   * @return a MenuItem to be placed in a Menu object with a label and action on click
   */
  private MenuItem makeMenuItem(String property, EventHandler<ActionEvent> handler) {
    MenuItem result = new MenuItem();
    createLabel(property, result);
    result.setOnAction(handler);
    return result;
  }

  /**
   * Handles the creation of Menu objects, applies a label to them as specified in the createLabel method
   * @param property - the label to be applied to the menu, from the .properties file
   * @return a Menu to be rendered on stage with a label
   */
  private Menu makeMenu(String property) {
    Menu result = new Menu();
    createLabel(property, result);
    return result;
  }

  /**
   * Handles the creation of Menu and MenuItem labels
   * @param property - the label to be applied to the items, from the .properties file
   * @param result - the menu item to which the label is applied
   */
  private void createLabel(String property, MenuItem result) {
    String label = myResources.getString(property);
    result.setText(label);
  }
}
