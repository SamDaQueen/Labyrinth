package dungeon.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Class for testing the new features added to the model such as arrows and Otyugh.
 */
public class InternalNewTests {

  private DungeonImpl dungeon;
  private DungeonImpl fixedDungeon;

  @Before
  public void setUp() {
    fixedDungeon = new DungeonImpl();
    System.out.println(fixedDungeon);
  }

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

  @Test
  public void noSmell() {
    assertEquals(0, fixedDungeon.getSmell());
  }

  @Test
  public void faintSmell() {
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.movePlayer(Direction.EAST);
    assertEquals(1, fixedDungeon.getSmell());
  }

  @Test
  public void strongSmell() {
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.movePlayer(Direction.SOUTH);
    assertEquals(2, fixedDungeon.getSmell());
  }

  @Test
  public void strongSmellTwoOtyughs() {
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.pickTreasure();
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.shoot(Direction.SOUTH, 1);
    fixedDungeon.shoot(Direction.SOUTH, 1);
    fixedDungeon.movePlayer(Direction.SOUTH);
    fixedDungeon.movePlayer(Direction.WEST);
    fixedDungeon.movePlayer(Direction.SOUTH);
    assertEquals(2, fixedDungeon.getSmell());
  }

  @Test
  public void pickArrows() {
    fixedDungeon.movePlayer(Direction.EAST);
    assertTrue(fixedDungeon.pickArrows());
    assertEquals(4, fixedDungeon.getPlayer().getArrows());
  }

  @Test
  public void noArrowsToPick() {
    assertFalse(fixedDungeon.pickArrows());
    assertEquals(3, fixedDungeon.getPlayer().getArrows());
  }

  @Test
  public void noTreasureToPick() {
    assertFalse(fixedDungeon.pickTreasure());
  }

  @Test
  public void pickTreasure() {
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.shoot(Direction.SOUTH, 1);
    fixedDungeon.shoot(Direction.SOUTH, 1);
    fixedDungeon.movePlayer(Direction.SOUTH);
    fixedDungeon.movePlayer(Direction.WEST);
    fixedDungeon.movePlayer(Direction.SOUTH);
    assertTrue(fixedDungeon.pickTreasure());
    assertEquals("[RUBY, SAPPHIRE, DIAMOND]",
        fixedDungeon.getPlayer().getCollectedTreasure().toString());
  }

  @Test
  public void noHit() {
    assertEquals(0, fixedDungeon.shoot(Direction.EAST, 3));
  }

  @Test
  public void oneHit() {
    assertEquals(1, fixedDungeon.shoot(Direction.EAST, 1));
  }

  @Test
  public void twoHit() {
    assertEquals(1, fixedDungeon.shoot(Direction.EAST, 1));
    assertEquals(2, fixedDungeon.shoot(Direction.EAST, 1));
  }

  @Test
  public void eaten() {
    assertFalse(fixedDungeon.playerDead());
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.movePlayer(Direction.SOUTH);
    fixedDungeon.movePlayer(Direction.WEST);
    assertTrue(fixedDungeon.playerDead());
  }

  @Test(expected = IllegalStateException.class)
  public void zeroDistance() {
    fixedDungeon.shoot(Direction.EAST, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void negativeDistance() {
    fixedDungeon.shoot(Direction.EAST, -10);
  }

  @Test(expected = IllegalStateException.class)
  public void invalidDistance() {
    fixedDungeon.shoot(Direction.EAST, 6);
  }

  @Test
  public void escapeInjuredOtyugh() {
    boolean playerDied = false;
    boolean playerEscaped = false;
    for (int i = 0; i < 1000; i++) {
      fixedDungeon = new DungeonImpl();
      fixedDungeon.movePlayer(Direction.EAST);
      fixedDungeon.movePlayer(Direction.EAST);
      fixedDungeon.movePlayer(Direction.SOUTH);
      fixedDungeon.shoot(Direction.WEST, 1);
      fixedDungeon.movePlayer(Direction.WEST);
      if (fixedDungeon.playerDead()) {
        playerDied = true;
      } else {
        playerEscaped = true;
      }
    }
    assertTrue(playerDied && playerEscaped);
  }

  @Test
  public void testBreeze() {
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.movePlayer(Direction.SOUTH);
    fixedDungeon.shoot(Direction.WEST, 1);
    fixedDungeon.shoot(Direction.WEST, 1);
    assertFalse(fixedDungeon.hasBreeze());
    fixedDungeon.movePlayer(Direction.WEST);
    assertTrue(fixedDungeon.hasBreeze());
    fixedDungeon.movePlayer(Direction.SOUTH);
    assertFalse(fixedDungeon.hasBreeze());
    fixedDungeon.movePlayer(Direction.WEST);
    assertTrue(fixedDungeon.hasBreeze());
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.movePlayer(Direction.EAST);
    assertTrue(fixedDungeon.hasBreeze());
    fixedDungeon.movePlayer(Direction.WEST);
    fixedDungeon.movePlayer(Direction.SOUTH);
    assertTrue(fixedDungeon.hasBreeze());
  }

  @Test
  public void testPitFall() {
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.movePlayer(Direction.SOUTH);
    fixedDungeon.shoot(Direction.WEST, 1);
    fixedDungeon.shoot(Direction.WEST, 1);
    fixedDungeon.movePlayer(Direction.WEST);
    fixedDungeon.movePlayer(Direction.SOUTH);
    assertEquals(0, fixedDungeon.getPlayer().getArrows());
    assertEquals(0, fixedDungeon.getPlayer().getCollectedTreasure().size());
  }

  @Test
  public void singleThief() {
    for (int i = 0; i < 100; i++) {
      boolean thief = false;

      dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 10, 1);
      for (int row = 0; row < 5; row++) {
        for (int col = 0; col < 5; col++) {
          dungeon.getPlayer().setCurrentPosition(row, col);
          if (dungeon.hasThief()) {
            thief = true;
            break;
          }
        }
        if (thief) {
          break;
        }
      }
      assertTrue(thief);
    }
  }

  @Test
  public void thiefSteals() {
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.movePlayer(Direction.EAST);
    fixedDungeon.movePlayer(Direction.SOUTH);
    fixedDungeon.pickArrows();
    fixedDungeon.shoot(Direction.WEST, 1);
    fixedDungeon.shoot(Direction.WEST, 1);
    fixedDungeon.movePlayer(Direction.WEST);
    fixedDungeon.movePlayer(Direction.WEST);
    if (fixedDungeon.hasThief()) {
      assertTrue(fixedDungeon.printCurrentLocation().contains("Uh-oh!"));
    } else {
      assertFalse(fixedDungeon.printCurrentLocation().contains("Uh-oh!"));
    }
  }

}


