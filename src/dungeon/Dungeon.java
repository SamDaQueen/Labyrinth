package dungeon;

import java.util.List;

public interface Dungeon {

  void movePlayer(Direction d);

  String printPlayerStatus();

  String printCurrentLocation();

  void pickTreasure();

  int[] getSize();

  int[] getStart();

  boolean isWrapping();

  boolean hasReachedGoal();

  List<Direction> getPossibleMoves();

  int[] getPlayerPosition();

}
