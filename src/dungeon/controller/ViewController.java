package dungeon.controller;

import dungeon.model.Dungeon;
import dungeon.view.IView;

public class ViewController implements Features {

  private final Dungeon model;
  private final IView view;

  public ViewController(Dungeon m, IView v) {
    if (m == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    if (v == null) {
      throw new IllegalArgumentException("View cannot be null!");
    }
    model = m;
    view = v;
    view.setFeatures(this);
  }

  private void drawDungeon() {
    view.drawDungeon();
    view.refresh();
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void restartGame() {

  }
}
