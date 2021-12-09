package dungeon.controller;

import dungeon.model.Direction;

public interface Features {

  void exitProgram();

  void restartGame(int[] size, int interconnectivity, boolean wrapping, int treasures,
      int difficulty);

  void resetGame();

  void move(Direction north);

  void pick();

  void shoot(Direction d, int distance);

  void showHelp();

  void setUpSettings();
}
