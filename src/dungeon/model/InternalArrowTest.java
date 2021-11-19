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


}
