package slogo.view;

import slogo.controller.Controller;
import slogo.model.Parser;

public class Visualizer {
  Parser myParser;
  Controller myController;

  public Visualizer(Parser parser, Controller controller){
    myParser = parser;
    myController = controller;
  }
}
