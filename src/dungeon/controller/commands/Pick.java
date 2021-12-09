package dungeon.controller.commands;

import dungeon.controller.Controller;
import dungeon.model.Dungeon;
import java.io.IOException;

/**
 * Class for executing the pick command to pick up all the treasure and arrows in the current
 * location.
 */
public class Pick implements Controller {

  @Override
  public void execute(Dungeon model, Appendable out) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    try {
      boolean pickedArrows = model.pickArrows();
      boolean pickedTreasure = model.pickTreasure();

      if (pickedArrows || pickedTreasure) {
        out.append("Added to inventory :D ");
      } else {
        out.append("Oh no!! Nothing to pick here!");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("\nAppend failed ", ioe);
    }
  }
}
