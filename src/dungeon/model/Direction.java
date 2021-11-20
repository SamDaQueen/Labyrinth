package dungeon.model;

/**
 * Enumeration for the four possible directions in our dungeon.
 */
public enum Direction {
  NORTH,
  EAST,
  SOUTH,
  WEST;

  /**
   * Inverts the current direction.
   *
   * @param d the old direction
   * @return the inverted direction
   */
  Direction invert(Direction d) {
    switch (d) {
      case NORTH:
        return SOUTH;
      case SOUTH:
        return NORTH;
      case WEST:
        return EAST;
      case EAST:
        return WEST;
      default:
        return d;
    }
  }
}
