package dungeon.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Internal test for verifying the functioning of the Otyughs.
 */
public class InternalOtyughTest {

  private DungeonImpl dungeon;

  @Test
  public void endOtyugh() {
    for (int i = 0; i < 100; i++) {
      dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 1);
      int endRow = dungeon.getEnd()[0];
      int endCol = dungeon.getEnd()[1];
      assertTrue(dungeon.getDungeon()[endRow][endCol].hasOtyugh());
    }
  }

  @Test
  public void atLeastOneOtyugh() {
    for (int i = 0; i < 100; i++) {
      dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 1);
      assertTrue(countOtyughs(dungeon) > 0);
    }
  }

  @Test
  public void correctNumberOfOtyughs() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 5);
    assertEquals(5, countOtyughs(dungeon));

    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 3);
    assertEquals(3, countOtyughs(dungeon));

    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 10);
    assertEquals(10, countOtyughs(dungeon));

    dungeon = new DungeonImpl(new int[]{5, 5}, 10, true, 25, 7);
    assertEquals(7, countOtyughs(dungeon));
  }


  @Test
  public void maxOtyugh() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 50);
    dungeon.getNumberOfCaves();
    assertEquals(countOtyughs(dungeon), dungeon.getNumberOfCaves() - 2);
  }

  @Test
  public void startOtyugh() {
    for (int i = 0; i < 100; i++) {
      dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 1);
      int startRow = dungeon.getStart()[0];
      int startCol = dungeon.getStart()[1];
      assertFalse(dungeon.getDungeon()[startRow][startCol].hasOtyugh());
    }
  }

  @Test
  public void otyughInTunnels() {
    boolean inTunnel = false;
    for (int i = 0; i < 100; i++) {
      dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 1);
      CaveImpl[][] caves = dungeon.getDungeon();
      for (int row = 0; row < 5; row++) {
        for (int col = 0; col < 5; col++) {
          if (caves[row][col].hasOtyugh()) {
            if (caves[row][col].isTunnel()) {
              inTunnel = true;
            }
          }
        }
      }
      assertFalse(inTunnel);
    }
  }


  private int countOtyughs(DungeonImpl dungeon) {
    CaveImpl[][] caves = dungeon.getDungeon();
    int otyughs = 0;
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        if (caves[row][col].hasOtyugh()) {
          otyughs++;
        }
      }
    }
    return otyughs;
  }
}
