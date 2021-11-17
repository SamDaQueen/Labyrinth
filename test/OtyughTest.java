import dungeon.Dungeon;
import dungeon.DungeonImpl;
import org.junit.Test;

public class OtyughTest {

  private Dungeon dungeon;

  @Test(expected = IllegalArgumentException.class)
  public void invalidOtyugh() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 0);
    System.out.println(dungeon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeOtyugh() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, -5);
    System.out.println(dungeon);
  }

  @Test
  public void singleOtyugh() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 1);
    System.out.println(dungeon);
  }

  @Test
  public void maxOtyugh() {
    dungeon = new DungeonImpl(new int[]{5, 5}, 0, false, 20, 50);
    System.out.println(dungeon);
  }


}
