package dungeon.control.commands;

import dungeon.control.Controller;
import dungeon.model.Direction;
import dungeon.model.Dungeon;
import java.io.IOException;
import java.util.Locale;

/**
 * Class for executing the shoot command to attempt shooting an arrow in a given direction for a given distance.
 */
public class Shoot implements Controller {

  private final int steps;
  private Direction direction;

  /**
   * Constructor for the Shoot command
   *
   * @param dir   the direction to shoot the arrow in
   * @param steps the distance to shoot the arrow till
   */
  public Shoot(String dir, int steps) {
    switch (dir.toLowerCase(Locale.ROOT)) {
      case "n":
      case "north":
        direction = Direction.NORTH;
        break;
      case "s":
      case "south":
        direction = Direction.SOUTH;
        break;
      case "w":
      case "west":
        direction = Direction.WEST;
        break;
      case "e":
      case "east":
        direction = Direction.EAST;
        break;
      default:
        break;
    }
    this.steps = steps;
  }

  @Override
  public void execute(Dungeon model, Appendable out) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    try {
      int shot = model.shoot(direction, steps);
      if (shot == 0) {
        out.append("Your arrow could not reach the Otyugh. What a waste!");
      } else if (shot == 1) {
        out.append("Your arrow hit the Otyugh and you hear a piercing howl!");
      } else {
        out.append("Your have successfully hit the Otyugh twice and it is now dead!");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("\nAppend failed ", ioe);
    }
  }
}
