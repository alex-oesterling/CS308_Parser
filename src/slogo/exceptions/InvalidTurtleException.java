package slogo.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InvalidTurtleException extends RuntimeException{
  public InvalidTurtleException(Throwable cause) {
    super(cause);
  }

  public InvalidTurtleException(String message, Throwable cause){
    super(message, cause);
  }

  public void displayError(String content) {
    Alert errorAlert = new Alert(AlertType.ERROR);
    errorAlert.setHeaderText("Turtle already exists");
    errorAlert.setContentText(this.getMessage() + "\n" + content);
    errorAlert.showAndWait();
  }
}
