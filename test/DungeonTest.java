import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dungeon.Dungeon;
import dungeon.DungeonImpl;
import org.junit.Test;

public class DungeonTest {

  Dungeon dungeon;

  @Test
  public void movePlayer() {
  }

  @Test
  public void printPlayerStatus() {
  }

  @Test
  public void printCurrentLocation() {
  }

  @Test
  public void pickTreasure() {
  }

  @Test
  public void getSize() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 3, true, 20);
    assertEquals(5, dungeon.getSize()[0]);
    assertEquals(5, dungeon.getSize()[1]);
  }

  @Test
  public void getStart() {
    for (int i = 0; i < 20; i++) {
      dungeon = new DungeonImpl(new int[]{25, 30}, 3, true, 20);
      assertTrue(dungeon.getStart()[0] >= 0);
      assertTrue(dungeon.getStart()[0] <= 25);
      assertTrue(dungeon.getStart()[1] >= 0);
      assertTrue(dungeon.getStart()[1] <= 30);
    }
  }

  @Test
  public void hasReachedGoal() {
  }

  @Test
  public void getLabyrinth() {
  }
}