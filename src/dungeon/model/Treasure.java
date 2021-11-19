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

  Treasure(int value) {
    this.value = value;
  }

  int getValue() {
    return value;
  }
}
