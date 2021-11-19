package dungeon.control.commands;

import dungeon.control.Controller;
import dungeon.model.Direction;
import dungeon.model.Dungeon;
import java.io.IOException;
import java.util.Locale;


/**
 * Class for executing the move command in a specific direction.
 */
public class Move implements Controller {

  private Direction direction;

  /**
   * Constructor for the Move command
   *
   * @param d the direction to move in
   */
  public Move(String d) {
    switch (d.toLowerCase(Locale.ROOT)) {
      case "north":
        direction = Direction.NORTH;
        break;
      case "south":
        direction = Direction.SOUTH;
        break;
      case "west":
        direction = Direction.WEST;
        break;
      case "east":
        direction = Direction.EAST;
        break;
      default:
        break;
    }
  }

  @Override
  public void execute(Dungeon model, Appendable out) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    try {
      model.movePlayer(direction);
      out.append("Moved to direction ").append(direction.toString());
    } catch (IllegalStateException ise) {
      throw new IllegalStateException(
          "\nThis move is not possible! Please choose from available moves only!");
    } catch (IOException ioe) {
      throw new IllegalStateException("\nAppend failed ", ioe);
    }
  }
}