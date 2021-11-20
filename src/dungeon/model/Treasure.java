package dungeon.model;

/**
 * Enumeration for the three possible treasures in our dungeon along with the scores that they give
 * the player if picked.
 */
enum Treasure {
  DIAMOND(10),
  RUBY(20),
  SAPPHIRE(30);

  private final int value;

  /**
   * Constructor for creating a new treasure enum.
   *
   * @param value the score
   */
  Treasure(int value) {
    this.value = value;
  }

  /**
   * Get the value associated with the particular treasure
   *
   * @return the score
   */
  int getValue() {
    return value;
  }
}
