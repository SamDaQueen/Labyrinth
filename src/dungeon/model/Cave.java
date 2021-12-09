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
 * cave to the adjacent caves. Caves may also contain a monster called an Otyugh that can eat the
 * player. The Otyugh can be killed by using two arrows shot by the player. The Cave could also have
 * a pit that the player can fall into. A pit will have a breeze in the adjacent caves.
 */
public class Cave implements ReadOnlyCave {

  private final List<Treasure> treasures;
  private final Set<Direction> directions;
  private Otyugh otyugh;
  private int arrows;
  private boolean pit;
  private boolean visited;

  /**
   * Constructor for creating a new instance of the Cave class.
   */
  Cave() {
    this.directions = new HashSet<>();
    this.treasures = new ArrayList<>();
    otyugh = null;
    arrows = 0;
    this.pit = false;
  }

  @Override
  public List<Treasure> getTreasure() {
    return new ArrayList<>(treasures);
  }

  @Override
  public Set<Direction> getOpenings() {
    return new HashSet<>(directions);
  }

  /**
   * Add the list of directions to the openings in the cave.
   *
   * @param directions the list of directions
   * @throws IllegalStateException if direction is null
   */
  void setOpenings(Set<Direction> directions) throws IllegalStateException {
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

  @Override
  public boolean hasOtyugh() {
    if (otyugh == null) {
      return false;
    }
    return otyugh.getHealth() != 0;
  }

  @Override
  public int getArrows() {
    return arrows;
  }

  /**
   * Add arrows to the cave.
   *
   * @param arrows the number of arrows to add
   * @throws IllegalStateException if cave is pit
   */
  void setArrows(int arrows) throws IllegalStateException {
    if (pit && arrows > 0) {
      throw new IllegalStateException("Cannot add arrows to cave with pit!");
    }
    this.arrows = arrows;
  }

  @Override
  public boolean visited() {
    return visited;
  }

  @Override
  public boolean hasPit() {
    return pit;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (isTunnel()) {
      builder.append("Tunnel");
    } else {
      builder.append("ReadOnlyCave");
    }
    List<Direction> sorted = new ArrayList<>(directions);
    Collections.sort(sorted);
    builder.append("\nAvailable directions: ").append(sorted);
    if (!isTunnel()) {
      Set<Treasure> distinct = new HashSet<>(treasures);
      if (distinct.size() > 0) {
        builder.append("\nTreasures: ");
        List<Treasure> sortedTreasures = new ArrayList<>(distinct);
        Collections.sort(sortedTreasures);
        for (Treasure t : sortedTreasures) {
          builder.append(t).append("(").append(Collections.frequency(treasures, t))
              .append("), ");
        }
      }
    }
    builder.append("\nArrows: ").append(arrows).append(" ");

    if (pit) {
      builder.append(
          "\nOh no! You have fallen into a deep pit in the dungeon!\nYou climb out of the pit... "
          + "But while doing so you lose all the treasures and arrows that you have collected so"
          + " far! Sed life :(");
    }

    return builder.toString();
  }

  /**
   * Add the list of treasures to the cave.
   *
   * @param treasures treasures to add
   * @throws IllegalStateException if the current location is a tunnel
   */
  void setTreasures(List<Treasure> treasures) throws IllegalStateException {
    if (pit && treasures.size() > 0) {
      throw new IllegalStateException("Cannot add treasures to cave with pit!");
    }
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
  void setTreasures(Treasure treasure) throws IllegalStateException {
    if (pit) {
      throw new IllegalStateException("Cannot add treasure to cave with pit!");
    }
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
   * Check whether cave is a tunnel.
   *
   * @return true if it is a tunnel
   */
  boolean isTunnel() {
    return directions.size() == 2;
  }

  /**
   * Add an Otyugh to the cave.
   *
   * @throws IllegalStateException if cave has a pit
   */
  void addOtyugh() throws IllegalStateException {
    if (pit) {
      throw new IllegalStateException("Cannot add otyugh to cave with pit!");
    }
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

  void setVisited() {
    visited = true;
  }

  /**
   * Set the state of pit in the cave.
   *
   * @param pit true if pit exists
   */
  void setPit(boolean pit) {
    this.pit = pit;
  }


  /**
   * Create and return a copy of the given cave.
   *
   * @return cave copy
   */
  Cave copy() {
    Cave cave = new Cave();
    cave.setTreasures(this.getTreasure());
    cave.setOpenings(this.getOpenings());
    cave.otyugh = this.otyugh;
    cave.arrows = this.arrows;
    cave.pit = this.pit;
    return cave;
  }
}