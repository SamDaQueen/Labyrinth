package dungeon.control.commands;

import dungeon.control.Controller;
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

      if (!(model.pickTreasure() || model.pickArrows())) {
        out.append("Oh no!! Nothing to pick here!");
      } else {
        out.append("Added to inventory :D ");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("\nAppend failed ", ioe);
    }
  }
}