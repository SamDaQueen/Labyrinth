package dungeon.view;

import dungeon.controller.Features;
import dungeon.model.ReadOnlyModel;

/**
 * Mock view for testing the functionality of the controller.
 */
public class MockView implements IView {

  private final StringBuilder log;

  public MockView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void setModel(ReadOnlyModel model) {
    log.append("model updated, ");
  }

  @Override
  public void setFeatures(Features f) {
    log.append("controller features added, ");
  }

  @Override
  public void refresh() {
    log.append("refresh, ");
  }

  @Override
  public void resetFocus() {
    log.append("focus reset, ");

  }

  @Override
  public void endGame(String message) {
    log.append("game ended, ");

  }

  @Override
  public void replay() {
    log.append("game replayed, ");
  }

  @Override
  public void showDialog(String message, String title) {
    log.append("dialog asked, ");
  }

  @Override
  public int[] getPlayerRowCol() {
    log.append("asked for player position, ");
    return new int[0];
  }

  @Override
  public int getCellSize() {
    log.append("asked for cell size, ");
    return 0;
  }

  @Override
  public void setUpSettings(Features f) {
    log.append("set up settings, ");
  }

  @Override
  public void showHelp() {
    log.append("set up help view, ");
  }

  @Override
  public void resetShoot() {
    log.append("reset shoot sequence, ");
  }
}
