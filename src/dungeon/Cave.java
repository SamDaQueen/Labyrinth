package dungeon;

import java.util.List;

class Cave {

  private static int count = 0;
  private final int id;
  private boolean isTunnel;
  private List<Treasure> treasures;
  private List<Direction> directions;

  Cave() {
    this.id = count++;
  }

  List<Treasure> getTreasure() {
    return null;
  }

  List<Direction> getOpenings() {
    return null;
  }

  void setOpenings(List<Direction> directions) {
    this.directions = directions;
  }

  boolean isTunnel() {
    return isTunnel;
  }

  @Override
  public String toString() {
    return isTunnel() ? String.format("Tunnel%d", id) : String.format("Cave%d", id);
  }
}
