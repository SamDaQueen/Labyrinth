package dungeon.view;

import dungeon.controller.Features;
import dungeon.model.ReadOnlyModel;

/**
 * Interface for the GUI view. Offers functionality to the controller to modify the model and update
 * the view accordingly.
 */
public interface IView {

  /**
   * Update the read-only model when the controller asks it in case of a reset or restart.
   *
   * @param model the new model
   */
  void setModel(ReadOnlyModel model);

  /**
   * Set the features provided by the controller so that the view can set action listeners.
   *
   * @param f the features
   */
  void setFeatures(Features f);

  /**
   * Refresh the view and its components.
   */
  void refresh();

  /**
   * Give focus back to the JFrame so that key events can be registered.
   */
  void resetFocus();

  /**
   * Update the view to inform the player that the game has ended along with a message.
   *
   * @param message the message to be displayed
   */
  void endGame(String message);

  /**
   * Update the view to replay the game after a reset or restart.
   */
  void replay();

  /**
   * Pop up a dialog box with the given information.
   *
   * @param message the contents of the dialog
   * @param title   the title of the dialog
   */
  void showDialog(String message, String title);

  /**
   * Get the position of the player in the dungeon.
   *
   * @return the current row and column
   */
  int[] getPlayerRowCol();

  /**
   * Get the size of the cells in the dungeon.
   *
   * @return the size in pixels.
   */
  int getCellSize();

  /**
   * Open the Settings Menu to let the user input configurations for a new game.
   *
   * @param f the features for calling the appropriate method
   */
  void setUpSettings(Features f);

  /**
   * Show the how to play dialog box to user for instructions on the game.
   */
  void showHelp();

  /**
   * If the player is not able to fire an arrow, the shoot sequence is reset.
   */
  void resetShoot();
}
