package dungeon.model;

import java.util.List;

public interface ReadOnlyModel {

  /**
   * Print the player status including the treasure that the player has collected and the score.
   *
   * @return printable string
   */
  String printPlayerStatus();

  /**
   * Print the details about the current location of the player such as possible moves and treasure
   * in the current location.
   *
   * @return printable string
   */
  String printCurrentLocation();

  /**
   * Returns the size of the dungeon as an int array.
   *
   * @return the size
   */
  int[] getSize();

  /**
   * Returns the start point of the dungeon as an int array.
   *
   * @return the starting location
   */
  int[] getStart();

  /**
   * Returns the current position of the player as an int array.
   *
   * @return the current location
   */
  int[] getPos();

  /**
   * Return true if the dungeon is wrapping.
   *
   * @return true if wrapping
   */
  boolean isWrapping();

  /**
   * Return true if the player has reached the exit.
   *
   * @return true if exit reached
   */
  boolean hasReachedGoal();

  /**
   * Check if the player is dead.
   *
   * @return true if player is dead
   */
  boolean playerDead();

  /**
   * Gets a list of possible moves from the current location for the player to choose from.
   *
   * @return available moves
   */
  List<Direction> getPossibleMoves();

  /**
   * Know if the current location has a breeze, indicating a pit in any of the neighboring caves.
   *
   * @return true if breeze present
   */
  boolean hasBreeze();

  /**
   * Check whether the thief and the player are in the same cell.
   *
   * @return true if thief met player
   */
  boolean metThief();

  /**
   * Check whether the shadow and the player are in the same cell.
   *
   * @return true if shadow found player
   */
  boolean metShadow();

  /**
   * Returns the dungeon
   *
   * @return the caves in the dungeon
   */
  CaveImpl[][] getDungeon();


  int[] getEnd();

  int[] getShadowPos();
}
