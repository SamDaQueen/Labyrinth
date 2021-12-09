package dungeon.model;

import java.util.List;

/**
 * Mock Dungeon Model for testing the controller separately.
 */
public class MockModel implements Dungeon {

  private final StringBuilder log;

  public MockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void movePlayer(Direction d) {
    log.append("\nDirection: ").append(d).append("\n");
  }

  @Override
  public int shoot(Direction direction, int steps) {
    log.append("\nDirection:").append(direction).append(" Distance: ").append(steps).append("\n");
    return 0;
  }

  @Override
  public boolean hasBreeze() {
    return false;
  }

  @Override
  public boolean metThief() {
    return false;
  }

  @Override
  public boolean metShadow() {
    return false;
  }

  @Override
  public Cave[][] getDungeon() {
    return new Cave[0][];
  }

  @Override
  public int[] getShadowPos() {
    return new int[0];
  }

  @Override
  public int[] getEnd() {
    return new int[0];
  }

  @Override
  public String printPlayerStatus() {
    return null;
  }

  @Override
  public String printCurrentLocation() {
    return null;
  }

  @Override
  public boolean pickTreasure() {
    log.append("pick treasures, ");
    return false;
  }

  @Override
  public boolean pickArrows() {
    log.append("pick arrows, ");
    return false;
  }

  @Override
  public int[] getSize() {
    return new int[2];
  }

  @Override
  public int[] getStart() {
    return new int[2];
  }

  @Override
  public int[] getPos() {
    return new int[2];
  }

  @Override
  public boolean isWrapping() {
    return false;
  }

  @Override
  public boolean hasReachedGoal() {
    return false;
  }

  @Override
  public boolean playerDead() {
    return false;
  }

  @Override
  public List<Direction> getPossibleMoves() {
    return null;
  }

}
