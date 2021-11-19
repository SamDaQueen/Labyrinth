package dungeon.control.commands;

import dungeon.control.Controller;
import dungeon.model.Dungeon;

/**
 * Class for executing the pick command to pick up all the treasure and arrows in the current
 * location.
 */
public class Pick implements Controller {

  @Override
  public void execute(Dungeon model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (!(model.pickTreasure() || model.pickArrows())) {
      throw new IllegalStateException(" Oh no!! No treasure to pick!");
    }

  }
}
