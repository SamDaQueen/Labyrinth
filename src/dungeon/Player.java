package dungeon;

import java.util.ArrayList;
import java.util.List;

class Player {

  private final int[] currentPosition;
  private final List<Treasure> collectedTreasure;
  private int score;

  Player(int row, int col) {
    this.currentPosition = new int[2];
    this.currentPosition[0] = row;
    this.currentPosition[1] = col;
    this.collectedTreasure = new ArrayList<>();
    this.score = 0;
  }

  public int[] getCurrentPosition() {
    return currentPosition;
  }

  public void setCurrentPosition(int row, int col) {
    this.currentPosition[0] = row;
    this.currentPosition[1] = col;
  }

  public List<Treasure> getCollectedTreasure() {
    return collectedTreasure;
  }

  public void updateCollectedTreasure(List<Treasure> newTreasures) {
    this.collectedTreasure.addAll(newTreasures);
    for (Treasure t : newTreasures) {
      score += t.getValue();
    }
  }

  public int getScore() {
    return score;
  }
}
