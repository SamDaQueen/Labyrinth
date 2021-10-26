package dungeon;

public interface Dungeon {

  void movePlayer(Direction d);

  String printPlayerStatus();

  String printCurrentLocation();

  void pickTreasure();

  int[] getSize();

  int[] getStart();

  boolean hasReachedGoal();

  Dungeon getLabyrinth();

}
