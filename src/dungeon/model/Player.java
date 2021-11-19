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

  Player(int row, int col) {
    this.currentPosition = new int[2];
    this.currentPosition[0] = row;
    this.currentPosition[1] = col;
    this.collectedTreasure = new ArrayList<>();
    this.score = 0;
    this.arrows = 3;
    this.alive = true;
  }

  int[] getCurrentPosition() {
    return currentPosition;
  }

  void setCurrentPosition(int row, int col) {
    this.currentPosition[0] = row;
    this.currentPosition[1] = col;
  }

  void updateCollectedTreasure(List<Treasure> newTreasures) {
    this.collectedTreasure.addAll(newTreasures);
    for (Treasure t : newTreasures) {
      score += t.getValue();
    }
  }

  int getArrows() {
    return arrows;
  }

  void useArrow() {
    if (arrows > 0) {
      arrows--;
    }
  }

  void addArrow(int arrows) {
    this.arrows += arrows;
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

  public boolean isAlive() {
    return alive;
  }

  public void kill() {
    this.alive = false;
  }
}
