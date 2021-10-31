package dungeon;

public class Driver {

  public static void main(String[] args) {

    Dungeon dungeon = new DungeonImpl(new int[]{5, 5}, 3, true, 20);
    System.out.println(dungeon);

  }

}
