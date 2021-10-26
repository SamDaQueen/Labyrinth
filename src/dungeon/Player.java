package dungeon;

import java.util.List;

class Player {

  private int[] currentPosition;
  private List<Treasure> collectedTreasure;

  Player(int[] start) {

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

  public void updateCollectedTreasure(List<Treasure> newTreasure) {
    this.collectedTreasure = newTreasure;
  }
}
