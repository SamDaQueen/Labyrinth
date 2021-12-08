package dungeon.controller;

import dungeon.model.Dungeon;

/**
 * Interface for the command based controller on the Dungeon class.
 */
public interface TextController {

  /**
   * Method to execute the given command on the model.
   *
   * @param model the dungeon model
   */
  void execute(Dungeon model, Appendable out);

}
