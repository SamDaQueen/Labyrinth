package dungeon;

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
    dungeonFixed = new DungeonImpl(new int[]{5, 5}, 3, false, 20, getCaves(), new int[]{4, 3},
        new int[]{0, 1});
    assertEquals("""
                              \s
           O---X   O---O---O  \s
           |           |   |  \s
           O---O   O---O---O  \s
               |       |   |  \s
           O   O---O---O---O  \s
           |   |       |   |  \s
           O---O---O   O   O  \s
                   |   |      \s
           O---O---O---S---O  \s
                              \s
        [4, 3] [0, 1]""", this.dungeonFixed.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void invalidMove() {
    dungeonFixed = new DungeonImpl(new int[]{5, 5}, 6, false, 20, getCaves(), new int[]{4, 3},
        new int[]{0, 1});
    dungeonFixed.movePlayer(Direction.SOUTH);
  }

  @Test
  public void getEnd() {
    dungeonFixed = new DungeonImpl(new int[]{5, 5}, 6, false, 20, getCaves(), new int[]{4, 3},
        new int[]{0, 1});
    assertEquals(0, dungeonFixed.getEnd()[0]);
    assertEquals(1, dungeonFixed.getEnd()[1]);
  }

  @Test
  public void hasReachedGoal() {
    dungeonFixed = new DungeonImpl(new int[]{5, 5}, 6, false, 20, getCaves(), new int[]{4, 3},
        new int[]{0, 1});
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
    assertEquals(dungeon.numberOfEdges(), 25 + 3 - 1);

    dungeon = new DungeonImpl(new int[]{5, 5}, 5, true, 20);
    assertEquals(dungeon.numberOfEdges(), 25 + 5 - 1);

    dungeon = new DungeonImpl(new int[]{6, 6}, 0, true, 20);
    assertEquals(dungeon.numberOfEdges(), 36 - 1);
  }

  @Test
  public void numberOfTreasureCaves() {
    for (int i = 0; i < 100; i++) {
      dungeon = new DungeonImpl(new int[]{5, 5}, 3, true, 20);
      assertTrue(dungeon.getCavesWithTreasures() > 20L * dungeon.getNumberOfCaves() / 100);

      dungeon = new DungeonImpl(new int[]{6, 7}, 3, false, 30);
      assertTrue(dungeon.getCavesWithTreasures() > 30L * dungeon.getNumberOfCaves() / 100);
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
    dungeonFixed = new DungeonImpl(new int[]{5, 5}, 3, false, 20, getCaves(), new int[]{4, 3},
        new int[]{0, 1});
    assertEquals("Player has collected with a score of 0", dungeonFixed.printPlayerStatus());
    dungeonFixed.movePlayer(Direction.NORTH);
    dungeonFixed.pickTreasure();
    assertEquals("Player has collected DIAMOND(1), RUBY(1), SAPPHIRE(2), with a score of 90",
        dungeonFixed.printPlayerStatus());
  }

  @Test
  public void printCurrentLocation() {
    dungeonFixed = new DungeonImpl(new int[]{5, 5}, 6, false, 20, getCaves(), new int[]{4, 3},
        new int[]{0, 1});
    assertEquals(
        "Player is at location: [4,3] with possible moves and treasures: Cave [NORTH, EAST, WEST] []",
        dungeonFixed.printCurrentLocation());
    dungeonFixed.movePlayer(Direction.NORTH);
    assertEquals(
        "Player is at location: [3,3] with possible moves and treasures: "
            + "Tunnel [NORTH, SOUTH] [SAPPHIRE, SAPPHIRE, RUBY, DIAMOND]",
        dungeonFixed.printCurrentLocation());
  }

  @Test
  public void pickTreasure() {
    dungeonFixed = new DungeonImpl(new int[]{5, 5}, 3, false, 20, getCaves(), new int[]{4, 3},
        new int[]{0, 1});
    dungeonFixed.movePlayer(Direction.EAST);
    dungeonFixed.pickTreasure();
    assertEquals("Player has collected RUBY(2), SAPPHIRE(1), with a score of 70",
        dungeonFixed.printPlayerStatus());
  }

  @Test
  public void numberOfCaves() {
    dungeonFixed = new DungeonImpl(new int[]{5, 5}, 3, false, 20, getCaves(), new int[]{4, 3},
        new int[]{0, 1});
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
    caves[0][0].setTreasures(Treasure.RUBY);
    caves[0][0].setTreasures(Treasure.DIAMOND);

    caves[0][1].setOpenings(Direction.WEST);

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
    caves[3][3].setTreasures(Treasure.SAPPHIRE);
    caves[3][3].setTreasures(Treasure.SAPPHIRE);
    caves[3][3].setTreasures(Treasure.RUBY);
    caves[3][3].setTreasures(Treasure.DIAMOND);

    caves[3][4].setOpenings(Direction.NORTH);

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
