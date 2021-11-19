package dungeon.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InternalArrowTest {

  DungeonImpl dungeon;

  @Test
  public void startWithThreeArrows() {
    for (int i = 0; i < 100; i++) {
      dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 1);
      assertEquals(3, dungeon.getPlayer().getArrows());
    }
  }

  @Test
  public void reduceArrows() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 1);
    dungeon.shoot(Direction.EAST, 2);
    assertEquals(2, dungeon.getPlayer().getArrows());

    dungeon.shoot(Direction.WEST, 5);
    assertEquals(1, dungeon.getPlayer().getArrows());

    dungeon.shoot(Direction.SOUTH, 1);
    assertEquals(0, dungeon.getPlayer().getArrows());
  }

  @Test(expected = IllegalStateException.class)
  public void shootWithZeroArrows() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 1);
    dungeon.shoot(Direction.EAST, 2);
    dungeon.shoot(Direction.WEST, 5);
    dungeon.shoot(Direction.SOUTH, 1);
    dungeon.shoot(Direction.SOUTH, 3);
  }

  @Test(expected = IllegalStateException.class)
  public void shootWithNegativeSteps() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 1);
    dungeon.shoot(Direction.EAST, -5);
  }

  @Test(expected = IllegalStateException.class)
  public void shootWithZeroSteps() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 1);
    dungeon.shoot(Direction.EAST, 0);
  }

}
