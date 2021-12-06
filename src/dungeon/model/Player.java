package dungeon.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for representing a player navigating through the dungeon. A player is represented as its
 * current position, the treasure it has collected so far, and the total score so far.
 */
class Player {

  private final int[] currentPosition;
  private final List<Treasure> collectedTreasure;
  private int score;
  private int arrows;
  private boolean alive;

  /**
   * Constructor for creating an instance of the Player.
   *
   * @param row the current row where the player is
   * @param col the current column where the player is
   */
  Player(int row, int col) {
    this.currentPosition = new int[2];
    this.currentPosition[0] = row;
    this.currentPosition[1] = col;
    this.collectedTreasure = new ArrayList<>();
    this.score = 0;
    this.arrows = 3;
    this.alive = true;
  }

  public Player(Player player) {
    this.currentPosition = new int[2];
    this.currentPosition[0] = player.getCurrentPosition()[0];
    this.currentPosition[1] = player.getCurrentPosition()[1];
    this.collectedTreasure = new ArrayList<>(player.collectedTreasure);
    this.score = player.score;
    this.arrows = player.getArrows();
    this.alive = player.isAlive();
  }

  /**
   * Get the current position of the player in the dungeon.
   *
   * @return [row, col]
   */
  int[] getCurrentPosition() {
    return currentPosition;
  }

  /**
   * Update the current position of the player.
   *
   * @param row the new roe
   * @param col the new col
   */
  void setCurrentPosition(int row, int col) {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Row and col cannot be negative!");
    }
    this.currentPosition[0] = row;
    this.currentPosition[1] = col;
  }

  /**
   * Update the treasure owned by the player when the pick command is executed.
   *
   * @param newTreasures list of the new treasures.
   */
  void updateCollectedTreasure(List<Treasure> newTreasures) {
    if (newTreasures == null) {
      throw new IllegalArgumentException("Cannot update null treasures!");
    }
    this.collectedTreasure.addAll(newTreasures);
    for (Treasure t : newTreasures) {
      score += t.getValue();
    }
  }

  /**
   * Get the list of treasures held by the player.
   *
   * @return list of treasures
   */
  List<Treasure> getCollectedTreasure() {
    return new ArrayList<>(collectedTreasure);
  }

  /**
   * Clear all the treasure the player has when they fall into a pit or encounter a thief.
   */
  void clearTreasure() {
    this.collectedTreasure.clear();
    this.score = 0;
  }

  /**
   * Clear all the arrows the player has when they fall into a pit.
   */
  void clearArrows() {
    this.arrows = 0;
  }

  /**
   * Get the number of arrows owned by the player.
   *
   * @return number of arrows
   */
  int getArrows() {
    return arrows;
  }

  /**
   * Use one arrow and reduce the number by 1.
   */
  void useArrow() {
    if (arrows > 0) {
      arrows--;
    }
  }

  /**
   * Add arrows to the player's inventory when the pick command is used.
   *
   * @param arrows the number of arrows received
   */
  void addArrow(int arrows) {
    this.arrows += arrows;
  }

  /**
   * Check whether the player was eaten by the Otyugh or is still alive.
   *
   * @return true if player still alive
   */
  public boolean isAlive() {
    return alive;
  }

  /**
   * Kill the player and set the boolean alive to false.
   */
  public void kill() {
    this.alive = false;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Player has collected ");
    Set<Treasure> distinct = new HashSet<>(collectedTreasure);
    List<Treasure> sorted = new ArrayList<>(distinct);
    Collections.sort(sorted);
    for (Treasure t : sorted) {
      builder.append(t).append("(").append(Collections.frequency(collectedTreasure, t))
          .append("), ");
    }
    builder.append("with a score of ").append(score);
    builder.append(" and has ").append(arrows).append(" arrows.\n");
    return builder.toString();
  }
}
