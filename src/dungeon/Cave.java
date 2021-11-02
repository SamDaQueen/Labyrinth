package dungeon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Cave {

  private static int count = 0;
  private final int id;
  private final List<Treasure> treasures;
  private final Set<Direction> directions;

  Cave() {
    this.id = count++;
    this.directions = new HashSet<>();
    this.treasures = new ArrayList<>();
  }

  List<Treasure> getTreasure() {
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

  void setTreasures(List<Treasure> treasures) {
    this.treasures.addAll(treasures);
  }

  void setTreasures(Treasure treasure) {
    this.treasures.add(treasure);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (isTunnel()) {
      builder.append("Tunnel");
    } else {
      builder.append("Cave");
    }
    builder.append(id).append(" ").append(directions).append(" ").append(treasures);
    return builder.toString();
  }

}
