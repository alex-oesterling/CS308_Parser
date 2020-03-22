package slogo.model.command;

import java.util.List;

/**
 * @author Dana Mulligan
 */
abstract public class CommandWithReturningList extends Command{

  protected static final Double DEFAULT = 0.0;

  /**
   * Return the list of commands held by a command
   * @return commands
   */
  //abstract public List<Command> getCommandList();
}
