import dungeon.model.CaveImpl;
import dungeon.model.Direction;
import dungeon.model.Dungeon;
import java.util.List;

/**
 * Mock Dungeon Model for testing the controller separately.
 */
public class MockDungeon implements Dungeon {

  private final int uniqueCode;
  private StringBuilder log;

  public MockDungeon(int uniqueCode, StringBuilder log) {
    this.uniqueCode = uniqueCode;
    this.log = log;
  }

  @Override
  public void movePlayer(Direction d) {
    log.append("Direction: ").append(d);
  }

  @Override
  public int shoot(Direction direction, int steps) {
    log.append("Direction:").append(direction).append(" Distance: ").append(steps);
    return uniqueCode;
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
  public boolean metNekker() {
    return false;
  }

  @Override
  public CaveImpl[][] getDungeon() {
    return new CaveImpl[0][];
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
    return false;
  }

  @Override
  public boolean pickArrows() {
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
