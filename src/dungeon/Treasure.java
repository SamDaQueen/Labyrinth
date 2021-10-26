package dungeon;

public enum Treasure {
  DIAMOND(10),
  RUBY(20),
  SAPPHIRE(30);

  private final int value;

  Treasure(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
