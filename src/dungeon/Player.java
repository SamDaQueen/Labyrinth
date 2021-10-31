package dungeon;

import java.util.ArrayList;
import java.util.List;

class Player {

  private int[] currentPosition;
  private List<Treasure> collectedTreasure;

  Player(int[] start) {
    this.currentPosition = start;
    this.collectedTreasure = new ArrayList<>();
  }

  public int[] getCurrentPosition() {
    return currentPosition;
  }

  public void setCurrentPosition(int[] newPosition) {
    this.currentPosition = newPosition;
  }

  public List<Treasure> getCollectedTreasure() {
    return collectedTreasure;
  }

  public void updateCollectedTreasure(List<Treasure> newTreasures) {
    this.collectedTreasure.addAll(newTreasures);
  }
}
