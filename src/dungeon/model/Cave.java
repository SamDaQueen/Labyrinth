package dungeon.model;

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
  private Otyugh otyugh;
  private int arrows;

  /**
   * Constructor for creating a new instance of the Cave class.
   */
  Cave() {
    this.directions = new HashSet<>();
    this.treasures = new ArrayList<>();
    otyugh = null;
    arrows = 0;
  }

  /**
   * Get the list of treasures present in the cave.
   *
   * @return the treasures
   */
  List<Treasure> getTreasure() {
    return treasures;
  }

  /**
   * Add the list of treasures to the cave.
   *
   * @param treasures treasures to add
   * @throws IllegalStateException if the current location is a tunnel
   */
  void setTreasures(List<Treasure> treasures) throws IllegalStateException {
    if (isTunnel()) {
      throw new IllegalStateException("Treasure cannot be added to tunnels!");
    }
    this.treasures.addAll(treasures);
  }

  /**
   * Add a single treasure to the cave.
   *
   * @param treasure treasure to add
   * @throws IllegalStateException if the current location is a tunnel
   */
  void setTreasures(Treasure treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("Cannot update null treasures!");
    }
    if (isTunnel()) {
      throw new IllegalStateException("Treasure cannot be added to tunnels!");
    }
    this.treasures.add(treasure);
  }

  /**
   * Clear all the treasure in the cave.
   */
  void clearTreasure() {
    treasures.clear();
  }

  /**
   * Get a set of the available directions from the current cave.
   *
   * @return the directions
   */
  Set<Direction> getOpenings() {
    return directions;
  }

  /**
   * Add the list of directions to the openings in the cave.
   *
   * @param directions the list of directions
   */
  void setOpenings(Set<Direction> directions) {
    if (directions == null) {
      throw new IllegalArgumentException("Cannot update null directions!");
    }
    this.directions.addAll(directions);
  }

  /**
   * Add the direction to the openings in the cave.
   *
   * @param direction the list of directions
   */
  void setOpenings(Direction direction) {
    this.directions.add(direction);
  }

  /**
   * Check whether cave is a tunnel.
   *
   * @return true if it is a tunnel
   */
  boolean isTunnel() {
    return directions.size() == 2;
  }

  /**
   * Check whether the cave has an otyugh.
   *
   * @return true if otyugh present.
   */
  boolean hasOtyugh() {
    if (otyugh == null) {
      return false;
    }
    return otyugh.getHealth() != 0;
  }

  /**
   * Add an Otyugh to the cave.
   */
  void addOtyugh() {
    this.otyugh = new Otyugh();
  }

  /**
   * Get the otyugh present in the current cave.
   *
   * @return the UOtyugh if present
   * @throws IllegalArgumentException if otyugh is null
   */
  Otyugh getOtyugh() throws IllegalArgumentException {
    if (otyugh == null) {
      throw new IllegalArgumentException("No Otyugh present at this location!!");
    }
    return otyugh;
  }

  /**
   * Shoot the otyugh and reduce its health.
   *
   * @throws IllegalArgumentException if otyugh is null
   */
  void shootOtyugh() {
    if (otyugh == null) {
      throw new IllegalArgumentException("No Otyugh present at this location!!");
    }
    otyugh.hit();
    if (otyugh.getHealth() <= 0) {
      otyugh = null;
    }
  }

  /**
   * Get the number of arrows present in the cave.
   *
   * @return the number of arrows
   */
  int getArrows() {
    return arrows;
  }

  /**
   * Add arrows to the cave.
   *
   * @param arrows the number of arrows to add
   */
  void setArrows(int arrows) {
    this.arrows = arrows;
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
    builder.append(" Available directions: ").append(sorted);
    if (!isTunnel()) {
      builder.append(" Treasures: ").append(treasures);
    }
    builder.append(" Arrows: ").append(arrows).append(" ");

    return builder.toString();
  }

}
