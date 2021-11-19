import dungeon.model.Dungeon;
import dungeon.model.DungeonImpl;
import org.junit.Test;

public class OtyughTest {

  private Dungeon dungeon;

  @Test(expected = IllegalArgumentException.class)
  public void invalidOtyugh() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeOtyugh() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, -5);
  }


}
