package dungeon.model;

/**
 * An Otyugh is a monster of the dungeon. It has a health and can eat the player.
 */
class Otyugh {

  private int health;

  /**
   * Constructs an Otyugh object.
   */
  Otyugh() {
    health = 2;
  }

  /**
   * Get the current health of the Otyugh.
   *
   * @return 2 = full health, 1 = half health, 0 = dead
   */
  int getHealth() {
    return health;
  }

  /**
   * Hit the Otyugh and reduce its health.
   */
  void hit() {
    this.health--;
  }

}
