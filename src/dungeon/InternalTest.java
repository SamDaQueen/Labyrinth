package dungeon;

import static java.lang.Math.E;
import static java.lang.Math.abs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for validating the internal working of the package-private classes and methods that do
 * not need to be exposed to the user.
 */
public class InternalTest {

  private DungeonImpl dungeon;
  private DungeonImpl dungeonFixed;

  @Test
  public void printDungeon() {
    dungeonFixed = new DungeonImpl(getCaves(), new int[]{4, 3}, new int[]{0, 1});
    assertEquals("                       \n"
                 + "   O---X   O---O---O   \n"
                 + "   |           |   |   \n"
                 + "   O---O   O---O---O   \n"
                 + "       |       |   |   \n"
                 + "   O   O---O---O---O   \n"
                 + "   |   |       |   |   \n"
                 + "   O---O---O   O   O   \n"
                 + "           |   |       \n"
                 + "   O---O---O---S---O   \n"
                 + "                       \n"
                 + "[4, 3] [0, 1]", this.dungeonFixed.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void invalidMove() {
    dungeonFixed = new DungeonImpl(getCaves(), new int[]{4, 3}, new int[]{0, 1});
    dungeonFixed.movePlayer(Direction.SOUTH);
  }

  @Test
  public void getEnd() {
    dungeonFixed = new DungeonImpl(getCaves(), new int[]{4, 3}, new int[]{0, 1});
    assertEquals(0, dungeonFixed.getEnd()[0]);
    assertEquals(1, dungeonFixed.getEnd()[1]);
  }

  @Test
  public void hasReachedGoal() {
    dungeonFixed = new DungeonImpl(getCaves(), new int[]{4, 3}, new int[]{0, 1});
    dungeonFixed.movePlayer(Direction.WEST);
    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.movePlayer(Direction.WEST);
    dungeonFixed.movePlayer(Direction.NORTH);
    assertFalse(dungeonFixed.hasReachedGoal());
    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.movePlayer(Direction.WEST);
    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.movePlayer(Direction.EAST);
    assertTrue(dungeonFixed.hasReachedGoal());
  }

  @Test
  public void checkMinimumPath() {
    for (int i = 0; i < 1000; i++) {
      dungeon = new DungeonImpl(new int[]{5, 6}, 3, true, 20);
      assertTrue(
          abs(dungeon.getStart()[0] - dungeon.getEnd()[0]) + abs(
              dungeon.getStart()[1]
              - dungeon.getEnd()[1])
          >= 5);
    }
  }

  @Test
  public void checkInterconnectivity() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 3, true, 20);
    assertEquals(dungeon.getNumberOfEdges(), 25 + 3 - 1);

    dungeon = new DungeonImpl(new int[]{5, 5}, 2, false, 20);
    assertEquals(dungeon.getNumberOfEdges(), 25 + 2 - 1);

    dungeon = new DungeonImpl(new int[]{6, 6}, 0, true, 20);
    assertEquals(dungeon.getNumberOfEdges(), 36 - 1);
  }

  @Test
  public void numberOfTreasureCaves() {
    for (int i = 0; i < 100; i++) {
      dungeon = new DungeonImpl(new int[]{5, 5}, 3, true, 20);
      assertTrue(dungeon.getNumberOfCavesWithTreasures() > 20L * dungeon.getNumberOfCaves() / 100);

      dungeon = new DungeonImpl(new int[]{6, 7}, 3, false, 30);
      assertTrue(dungeon.getNumberOfCavesWithTreasures() > 30L * dungeon.getNumberOfCaves() / 100);
    }
  }

  @Test
  public void testWrapping() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, true, 20);
    Cave[][] cave = dungeon.getDungeon();
    assertTrue(checkWrapping(cave));

    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20);
    cave = dungeon.getDungeon();
    assertFalse(checkWrapping(cave));
  }

  @Test
  public void testTreasure() {
    boolean diamond = false;
    boolean ruby = false;
    boolean sapphire = false;
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 25);
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        if (dungeon.getDungeon()[row][col].getTreasure().contains(Treasure.RUBY)) {
          ruby = true;
        } else if (dungeon.getDungeon()[row][col].getTreasure().contains(Treasure.DIAMOND)) {
          diamond = true;
        } else if (dungeon.getDungeon()[row][col].getTreasure().contains(Treasure.SAPPHIRE)) {
          sapphire = true;
        }
      }
    }
    assertTrue(ruby || diamond || sapphire);
  }

  @Test
  public void allTreasure() {
    dungeon = new DungeonImpl(new int[]{6, 7}, 0, false, 100);
    boolean all = true;
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        if (!dungeon.getDungeon()[row][col].isTunnel()) {
          if (dungeon.getDungeon()[row][col].getTreasure().size() == 0) {
            all = false;
          }
        }
      }
    }
    assertTrue(all);
  }

  @Test
  public void printPlayerStatus() {
    dungeonFixed = new DungeonImpl(getCaves(), new int[]{4, 3}, new int[]{0, 1});
    assertEquals("Player has collected with a score of 0", dungeonFixed.printPlayerStatus());
    dungeonFixed.movePlayer(Direction.WEST);
    dungeonFixed.pickTreasure();
    assertEquals("Player has collected DIAMOND(1), RUBY(1), SAPPHIRE(2), with a score of 90",
        dungeonFixed.printPlayerStatus());
  }

  @Test
  public void printCurrentLocation() {
    dungeonFixed = new DungeonImpl(getCaves(), new int[]{4, 3}, new int[]{0, 1});
    assertEquals(
        "Player is at location: [4,3] with possible moves and treasures: Cave [NORTH, EAST, WEST] []",
        dungeonFixed.printCurrentLocation());
    dungeonFixed.movePlayer(Direction.EAST);
    assertEquals(
        "Player is at location: [4,4] with possible moves and treasures: Cave [WEST] [RUBY, RUBY, SAPPHIRE]",
        dungeonFixed.printCurrentLocation());
  }

  @Test(expected = IllegalStateException.class)
  public void tunnelTreasure() {
    dungeonFixed = new DungeonImpl(getCaves(), new int[]{4, 3}, new int[]{0, 1});
    dungeonFixed.getDungeon()[4][1].setTreasures(Treasure.RUBY);
  }

  @Test
  public void pickTreasure() {
    dungeonFixed = new DungeonImpl(getCaves(), new int[]{4, 3}, new int[]{0, 1});
    dungeonFixed.movePlayer(Direction.EAST);
    dungeonFixed.pickTreasure();
    assertEquals("Player has collected RUBY(2), SAPPHIRE(1), with a score of 70",
        dungeonFixed.printPlayerStatus());
  }

  @Test
  public void numberOfCaves() {
    dungeonFixed = new DungeonImpl(getCaves(), new int[]{4, 3}, new int[]{0, 1});
    assertEquals(16, dungeonFixed.getNumberOfCaves());
  }

  @Test
  public void startAtCave() {
    for (int i = 0; i < 100; i++) {
      dungeon = new DungeonImpl(new int[]{8, 7}, 0, true, 20);
      assertFalse(dungeon.getDungeon()[dungeon.getStart()[0]][dungeon.getStart()[1]].isTunnel());
    }
  }

  @Test
  public void endAtCave() {
    for (int i = 0; i < 100; i++) {
      dungeon = new DungeonImpl(new int[]{5, 6}, 0, false, 20);
      assertFalse(dungeon.getDungeon()[dungeon.getEnd()[0]][dungeon.getEnd()[1]].isTunnel());
    }
  }

  @Test
  public void traversal() {
    Cave[][] caves = new Cave[3][4];
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 4; col++) {
        caves[row][col] = new Cave();
      }
    }

    caves[0][0].setOpenings(Direction.EAST);
    caves[0][1].setOpenings(Direction.WEST);
    caves[0][1].setOpenings(Direction.EAST);
    caves[0][2].setOpenings(Direction.WEST);
    caves[0][2].setOpenings(Direction.EAST);
    caves[0][3].setOpenings(Direction.WEST);
    caves[0][3].setOpenings(Direction.SOUTH);

    caves[1][0].setOpenings(Direction.EAST);
    caves[1][0].setOpenings(Direction.SOUTH);
    caves[1][1].setOpenings(Direction.WEST);
    caves[1][1].setOpenings(Direction.EAST);
    caves[1][2].setOpenings(Direction.WEST);
    caves[1][2].setOpenings(Direction.EAST);
    caves[1][3].setOpenings(Direction.NORTH);
    caves[1][3].setOpenings(Direction.WEST);

    caves[2][0].setOpenings(Direction.NORTH);
    caves[2][0].setOpenings(Direction.EAST);
    caves[2][1].setOpenings(Direction.WEST);
    caves[2][1].setOpenings(Direction.EAST);
    caves[2][2].setOpenings(Direction.EAST);
    caves[2][2].setOpenings(Direction.WEST);
    caves[2][3].setOpenings(Direction.WEST);

    dungeon = new DungeonImpl(caves, new int[]{0, 0}, new int[]{2, 3});

    assertEquals(0, dungeon.getPlayerPosition()[0]);
    assertEquals(0, dungeon.getPlayerPosition()[1]);
    dungeon.movePlayer(Direction.EAST);
    assertEquals(0, dungeon.getPlayerPosition()[0]);
    assertEquals(1, dungeon.getPlayerPosition()[1]);
    dungeon.movePlayer(Direction.EAST);
    assertEquals(0, dungeon.getPlayerPosition()[0]);
    assertEquals(2, dungeon.getPlayerPosition()[1]);
    dungeon.movePlayer(Direction.EAST);
    assertEquals(0, dungeon.getPlayerPosition()[0]);
    assertEquals(3, dungeon.getPlayerPosition()[1]);
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(1, dungeon.getPlayerPosition()[0]);
    assertEquals(3, dungeon.getPlayerPosition()[1]);
    dungeon.movePlayer(Direction.WEST);
    assertEquals(1, dungeon.getPlayerPosition()[0]);
    assertEquals(2, dungeon.getPlayerPosition()[1]);
    dungeon.movePlayer(Direction.WEST);
    assertEquals(1, dungeon.getPlayerPosition()[0]);
    assertEquals(1, dungeon.getPlayerPosition()[1]);
    dungeon.movePlayer(Direction.WEST);
    assertEquals(1, dungeon.getPlayerPosition()[0]);
    assertEquals(0, dungeon.getPlayerPosition()[1]);
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(2, dungeon.getPlayerPosition()[0]);
    assertEquals(0, dungeon.getPlayerPosition()[1]);
    dungeon.movePlayer(Direction.EAST);
    assertEquals(2, dungeon.getPlayerPosition()[0]);
    assertEquals(1, dungeon.getPlayerPosition()[1]);
    dungeon.movePlayer(Direction.EAST);
    assertEquals(2, dungeon.getPlayerPosition()[0]);
    assertEquals(2, dungeon.getPlayerPosition()[1]);
    dungeon.movePlayer(Direction.EAST);
    assertEquals(2, dungeon.getPlayerPosition()[0]);
    assertEquals(3, dungeon.getPlayerPosition()[1]);

    assertTrue(dungeon.hasReachedGoal());
  }

  @Test
  public void downToUp() {
    Cave[][] caves = getCaves();
    makeWrapping(caves);
    dungeonFixed = new DungeonImpl(caves, new int[]{4, 3}, new int[]{0, 1});
    dungeonFixed.movePlayer(Direction.WEST);
    dungeonFixed.movePlayer(Direction.SOUTH);
    assertEquals(0, dungeonFixed.getPlayerPosition()[0]);
  }

  @Test
  public void upToDown() {
    Cave[][] caves = getCaves();
    makeWrapping(caves);
    dungeonFixed = new DungeonImpl(caves, new int[]{4, 3}, new int[]{0, 1});
    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.movePlayer(Direction.WEST);
    dungeonFixed.movePlayer(Direction.NORTH);
    assertEquals(4, dungeonFixed.getPlayerPosition()[0]);
  }

  @Test
  public void leftToRight() {
    Cave[][] caves = getCaves();
    makeWrapping(caves);
    dungeonFixed = new DungeonImpl(caves, new int[]{4, 3}, new int[]{0, 1});
    dungeonFixed.movePlayer(Direction.WEST);
    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.movePlayer(Direction.WEST);
    dungeonFixed.movePlayer(Direction.WEST);
    dungeonFixed.movePlayer(Direction.WEST);
    assertEquals(4, dungeonFixed.getPlayerPosition()[1]);
  }

  @Test
  public void rightToLeft() {
    Cave[][] caves = getCaves();
    makeWrapping(caves);
    dungeonFixed = new DungeonImpl(caves, new int[]{4, 3}, new int[]{0, 1});
    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.movePlayer(Direction.EAST);
    dungeonFixed.movePlayer(Direction.SOUTH);
    dungeonFixed.movePlayer(Direction.EAST);
    assertEquals(0, dungeonFixed.getPlayerPosition()[1]);
  }

  @Test
  public void allDirections() {
    dungeonFixed = new DungeonImpl(getCaves(), new int[]{4, 3}, new int[]{0, 1});

    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.movePlayer(Direction.WEST);
    dungeonFixed.movePlayer(Direction.EAST);
    dungeonFixed.movePlayer(Direction.EAST);
    dungeonFixed.movePlayer(Direction.WEST);
    dungeonFixed.movePlayer(Direction.SOUTH);
    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.movePlayer(Direction.SOUTH);
    assertEquals(2, dungeonFixed.getPlayerPosition()[0]);
    assertEquals(3, dungeonFixed.getPlayerPosition()[1]);
  }

  private Cave[][] getCaves() {
    Cave[][] caves = new Cave[5][5];
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        caves[row][col] = new Cave();
      }
    }

    // Create edges

    // row 0
    caves[0][0].setOpenings(Direction.EAST);
    caves[0][0].setOpenings(Direction.SOUTH);

    caves[0][1].setOpenings(Direction.WEST);
    caves[0][1].setTreasures(Treasure.RUBY);
    caves[0][1].setTreasures(Treasure.DIAMOND);

    caves[0][2].setOpenings(Direction.EAST);

    caves[0][3].setOpenings(Direction.WEST);
    caves[0][3].setOpenings(Direction.EAST);
    caves[0][3].setOpenings(Direction.SOUTH);

    caves[0][4].setOpenings(Direction.WEST);
    caves[0][4].setOpenings(Direction.SOUTH);

    // row 1
    caves[1][0].setOpenings(Direction.NORTH);
    caves[1][0].setOpenings(Direction.EAST);

    caves[1][1].setOpenings(Direction.WEST);
    caves[1][1].setOpenings(Direction.SOUTH);

    caves[1][2].setOpenings(Direction.EAST);

    caves[1][3].setOpenings(Direction.NORTH);
    caves[1][3].setOpenings(Direction.WEST);
    caves[1][3].setOpenings(Direction.EAST);
    caves[1][3].setOpenings(Direction.SOUTH);

    caves[1][4].setOpenings(Direction.NORTH);
    caves[1][4].setOpenings(Direction.WEST);
    caves[1][4].setOpenings(Direction.SOUTH);

    // row 2
    caves[2][0].setOpenings(Direction.SOUTH);

    caves[2][1].setOpenings(Direction.EAST);
    caves[2][1].setOpenings(Direction.NORTH);
    caves[2][1].setOpenings(Direction.SOUTH);

    caves[2][2].setOpenings(Direction.WEST);
    caves[2][2].setOpenings(Direction.EAST);

    caves[2][3].setOpenings(Direction.NORTH);
    caves[2][3].setOpenings(Direction.WEST);
    caves[2][3].setOpenings(Direction.EAST);
    caves[2][3].setOpenings(Direction.SOUTH);

    caves[2][4].setOpenings(Direction.NORTH);
    caves[2][4].setOpenings(Direction.WEST);
    caves[2][4].setOpenings(Direction.SOUTH);

    // row 3
    caves[3][0].setOpenings(Direction.NORTH);
    caves[3][0].setOpenings(Direction.EAST);

    caves[3][1].setOpenings(Direction.NORTH);
    caves[3][1].setOpenings(Direction.EAST);
    caves[3][1].setOpenings(Direction.WEST);
    caves[3][1].setTreasures(Treasure.DIAMOND);
    caves[3][1].setTreasures(Treasure.DIAMOND);
    caves[3][1].setTreasures(Treasure.RUBY);
    caves[3][1].setTreasures(Treasure.RUBY);

    caves[3][2].setOpenings(Direction.WEST);
    caves[3][2].setOpenings(Direction.SOUTH);

    caves[3][3].setOpenings(Direction.NORTH);
    caves[3][3].setOpenings(Direction.SOUTH);

    caves[3][4].setOpenings(Direction.NORTH);
    caves[3][4].setTreasures(Treasure.SAPPHIRE);
    caves[3][4].setTreasures(Treasure.SAPPHIRE);
    caves[3][4].setTreasures(Treasure.RUBY);
    caves[3][4].setTreasures(Treasure.DIAMOND);
    caves[3][4].setTreasures(Treasure.DIAMOND);

    // row 4
    caves[4][0].setOpenings(Direction.EAST);

    caves[4][1].setOpenings(Direction.EAST);
    caves[4][1].setOpenings(Direction.WEST);

    caves[4][2].setOpenings(Direction.NORTH);
    caves[4][2].setOpenings(Direction.EAST);
    caves[4][2].setOpenings(Direction.WEST);
    caves[4][2].setTreasures(Treasure.DIAMOND);
    caves[4][2].setTreasures(Treasure.RUBY);
    caves[4][2].setTreasures(Treasure.SAPPHIRE);
    caves[4][2].setTreasures(Treasure.SAPPHIRE);

    caves[4][3].setOpenings(Direction.NORTH);
    caves[4][3].setOpenings(Direction.EAST);
    caves[4][3].setOpenings(Direction.WEST);

    caves[4][4].setOpenings(Direction.WEST);
    caves[4][4].setTreasures(Treasure.RUBY);
    caves[4][4].setTreasures(Treasure.RUBY);
    caves[4][4].setTreasures(Treasure.SAPPHIRE);

    return caves;
  }

  private void makeWrapping(Cave[][] caves) {
    caves[0][0].setOpenings(Direction.NORTH);
    caves[0][0].setOpenings(Direction.WEST);
    caves[0][1].setOpenings(Direction.NORTH);
    caves[0][2].setOpenings(Direction.NORTH);
    caves[0][4].setOpenings(Direction.EAST);

    caves[1][0].setOpenings(Direction.WEST);
    caves[1][4].setOpenings(Direction.EAST);

    caves[3][0].setOpenings(Direction.WEST);
    caves[3][4].setOpenings(Direction.EAST);

    caves[4][0].setOpenings(Direction.SOUTH);
    caves[4][1].setOpenings(Direction.SOUTH);
    caves[4][2].setOpenings(Direction.SOUTH);
  }

  private boolean checkWrapping(Cave[][] cave) {
    boolean wrapping = false;
    for (int row = 0; row < 5; row++) {
      if (cave[row][0].getOpenings().contains(Direction.WEST)) {
        wrapping = true;
        break;
      }
    }
    if (!wrapping) {
      for (int col = 0; col < 5; col++) {
        if (cave[0][col].getOpenings().contains(Direction.NORTH)) {
          wrapping = true;
          break;
        }
      }
    }
    return wrapping;
  }
}
