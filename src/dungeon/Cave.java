package dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Package-private class to represent each node of the dungeon. A node is a tunnel if it has exactly
 * two paths connected to it, otherwise it is a cave. A cave may contain treasure. Each tunnel and
 * cave has a list of all the treasures it contains, and a set of the directions connecting that
 * cave to the adjacent caves.
 */
class Cave {

  private final List<Treasure> treasures;
  private final Set<Direction> directions;

  Cave() {
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
    List<Direction> sorted = new ArrayList<>(directions);
    Collections.sort(sorted);
    builder.append(" ").append(sorted).append(" ").append(treasures);
    return builder.toString();
  }

}
