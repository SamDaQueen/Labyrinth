package dungeon.controller;

import dungeon.model.Direction;
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
    view.resetFocus();
  }

  private void drawDungeon() {
    view.drawDungeon();
    view.refresh();
    view.resetFocus();
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void restartGame() {

  }

  @Override
  public void move(Direction d) {
    System.out.println(d);
    try {
      model.movePlayer(d);
    } catch (IllegalStateException ise) {
      // ignore mode
    }
    view.refresh();
  }
}
