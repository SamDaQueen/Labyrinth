package dungeon.controller;

import dungeon.model.Direction;

/**
 * Offers the features of the controller that can be used to set up the playable game using a model
 * and a view.
 */
public interface Features {

  /**
   * End the game.
   */
  void exitProgram();

  /**
   * Restart the game with a new model with the given configurations.
   *
   * @param size              the new size of the dungeon
   * @param interconnectivity new interconnectivity
   * @param wrapping          new wrapping
   * @param treasures         new treasures %
   * @param difficulty        new difficulty
   */
  void restartGame(int[] size, int interconnectivity, boolean wrapping, int treasures,
      int difficulty);

  /**
   * Reset the game with the same model.
   */
  void resetGame();

  /**
   * Move the player in the dungeon.
   *
   * @param direction the given direction
   */
  void move(Direction direction);

  /**
   * Pick the treasures and arrows in the current cave.
   */
  void pick();

  /**
   * Shoot an arrow if available in the given direction for given distance.
   *
   * @param d        the direction
   * @param distance the distance
   */
  void shoot(Direction d, int distance);

  /**
   * Show the instructions on how to play the game to the user.
   */
  void showHelp();

  /**
   * Show the settings to the user for configuring the game.
   */
  void setUpSettings();
}
