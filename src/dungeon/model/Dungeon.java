package dungeon.model;

/**
 * The dungeon interface represents a maze with caves and tunnels. Caves can have treasures. A
 * player can move around the dungeon to find the exit as well as collect the treasure along the
 * way.
 */
public interface Dungeon extends ReadOnlyModel {

  /**
   * Moves the player in the given direction from among NORTH, EAST, SOUTH, WEST.
   *
   * @param d the direction
   */
  void movePlayer(Direction d);

  /**
   * Provides the player the functionality to pick up the treasure in the current location.
   */
  boolean pickTreasure();

  /**
   * Provides the player the functionality to pick up the arrows in the current location.
   */
  boolean pickArrows();

  /**
   * Shoot the arrow in the given direction for the given distance.
   *
   * @param direction the direction
   * @param steps     the distance
   * @return whether an Otyugh was hit, 0 if no hit, 1 if first hit, 2 if second hit
   */
  int shoot(Direction direction, int steps);

}
