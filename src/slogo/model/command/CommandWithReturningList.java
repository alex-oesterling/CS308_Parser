package slogo.model.command;

import java.util.List;

abstract public class CommandWithReturningList extends Command{

  /**
   * Return the list of commands held by a command
   * @return commands
   */
  abstract public List<Command> getCommandList();
}
