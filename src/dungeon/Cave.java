package dungeon;

import java.util.HashSet;
import java.util.Set;

class Cave {

  private static int count = 0;
  private final int id;
  private Set<Treasure> treasures;
  private Set<Direction> directions;

  Cave() {
    this.id = count++;
    this.directions = new HashSet<>();
  }

  Set<Treasure> getTreasure() {
    return treasures;
  }

  Set<Direction> getOpenings() {
    return directions;
  }

  void setOpenings(Set<Direction> directions) {
    this.directions.addAll(directions);
  }

  void setOpenings(Direction direction) {
    this.directions.add(direction);
  }

  boolean isTunnel() {
    return directions.size() == 2;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (isTunnel()) {
      builder.append("Tunnel");
    } else {
      builder.append("Cave");
    }
    builder.append(id).append(directions);
    return builder.toString();
  }
}
