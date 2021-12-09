package dungeon.controller;

import dungeon.model.Direction;

public interface Features {

  void exitProgram();

  void restartGame();

  void move(Direction north);
}
