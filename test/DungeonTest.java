import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.Test;

/**
 * Test class for validating the external working of the dungeon model.
 */
public class DungeonTest {

  private Dungeon dungeon;

  @Test(expected = IllegalArgumentException.class)
  public void invalidSize() {
    dungeon = new DungeonImpl(new int[]{2, 3}, 8, true, 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeSize() {
    dungeon = new DungeonImpl(new int[]{-1, -3}, 8, true, 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeInterconnectivity() {
    dungeon = new DungeonImpl(new int[]{5, 5}, -3, true, 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeTreasure() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 5, true, -15);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullSize() {
    dungeon = new DungeonImpl(null, 5, true, -15);
  }

  @Test
  public void testSingleRow() {
    dungeon = new DungeonImpl(new int[]{1, 6}, 3, false, 20);
    Random rand = new Random();
    while (!dungeon.hasReachedGoal()) {
      dungeon.pickTreasure();
      List<Direction> possibleMoves = dungeon.getPossibleMoves();
      Direction direction = possibleMoves.get(rand.nextInt(possibleMoves.size()));
      dungeon.movePlayer(direction);
    }
    System.out.println(dungeon);
    assertTrue(dungeon.hasReachedGoal());
  }

  @Test
  public void testSingleCol() {
    dungeon = new DungeonImpl(new int[]{6, 1}, 0, true, 20);
    Random rand = new Random();
    while (!dungeon.hasReachedGoal()) {
      dungeon.pickTreasure();
      List<Direction> possibleMoves = dungeon.getPossibleMoves();
      Direction direction = possibleMoves.get(rand.nextInt(possibleMoves.size()));
      dungeon.movePlayer(direction);
    }
    System.out.println(dungeon);
    assertTrue(dungeon.hasReachedGoal());
  }

  @Test
  public void smallestDungeon() {
    dungeon = new DungeonImpl(new int[]{3, 4}, 0, false, 20);
    Random rand = new Random();
    while (!dungeon.hasReachedGoal()) {
      dungeon.pickTreasure();
      List<Direction> possibleMoves = dungeon.getPossibleMoves();
      Direction direction = possibleMoves.get(rand.nextInt(possibleMoves.size()));
      dungeon.movePlayer(direction);
    }
    System.out.println(dungeon);
    assertTrue(dungeon.hasReachedGoal());
  }

  @Test
  public void smallestDungeon2() {
    dungeon = new DungeonImpl(new int[]{4, 3}, 0, false, 20);
    Random rand = new Random();
    while (!dungeon.hasReachedGoal()) {
      dungeon.pickTreasure();
      List<Direction> possibleMoves = dungeon.getPossibleMoves();
      Direction direction = possibleMoves.get(rand.nextInt(possibleMoves.size()));
      dungeon.movePlayer(direction);
    }
    System.out.println(dungeon);
    assertTrue(dungeon.hasReachedGoal());
  }


  @Test
  public void testLargeDungeon()  {
    dungeon = new DungeonImpl(new int[]{50, 50}, 30, true, 50);
  }

  @Test
  public void movePlayer() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 3, false, 20);
    int[] start = dungeon.getStart();
    List<Direction> directions = dungeon.getPossibleMoves();
    dungeon.movePlayer(directions.get(0));
    if (directions.get(0) == Direction.NORTH) {
      assertEquals(start[0] - 1, dungeon.getPlayerPosition()[0]);
    } else if (directions.get(0) == Direction.SOUTH) {
      assertEquals(start[0] + 1, dungeon.getPlayerPosition()[0]);
    } else if (directions.get(0) == Direction.EAST) {
      assertEquals(start[1] + 1, dungeon.getPlayerPosition()[1]);
    } else if (directions.get(0) == Direction.WEST) {
      assertEquals(start[1] - 1, dungeon.getPlayerPosition()[1]);
    }
  }

  @Test
  public void getSize() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 3, true, 20);
    assertEquals(5, dungeon.getSize()[0]);
    assertEquals(5, dungeon.getSize()[1]);

    dungeon = new DungeonImpl(new int[]{20, 30}, 3, true, 20);
    assertEquals(20, dungeon.getSize()[0]);
    assertEquals(30, dungeon.getSize()[1]);
  }

  @Test
  public void getStart() {
    for (int i = 0; i < 20; i++) {
      dungeon = new DungeonImpl(new int[]{25, 30}, 5, true, 20);
      assertTrue(dungeon.getStart()[0] >= 0);
      assertTrue(dungeon.getStart()[0] <= 25);
      assertTrue(dungeon.getStart()[1] >= 0);
      assertTrue(dungeon.getStart()[1] <= 30);
    }
  }

  @Test
  public void isWrapping() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 3, true, 20);
    assertTrue(dungeon.isWrapping());
    dungeon = new DungeonImpl(new int[]{5, 5}, 3, false, 20);
    assertFalse(dungeon.isWrapping());
    dungeon = new DungeonImpl(new int[]{10, 12}, 3, true, 20);
    assertTrue(dungeon.isWrapping());
    dungeon = new DungeonImpl(new int[]{6, 8}, 3, false, 20);
    assertFalse(dungeon.isWrapping());
  }

  @Test
  public void initialPlayerPosition() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, true, 50);
    String location = dungeon.printCurrentLocation();
    String size = dungeon.printCurrentLocation()
        .substring(location.indexOf(":") + 3, location.indexOf("w") - 2);
    int[] row_col = Arrays.stream(size.split(","))
        .mapToInt(Integer::parseInt)
        .toArray();
    assertEquals(dungeon.getStart()[0], row_col[0]);
    assertEquals(dungeon.getStart()[1], row_col[1]);

  }

}