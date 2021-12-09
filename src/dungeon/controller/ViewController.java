package dungeon.controller;

import dungeon.model.Direction;
import dungeon.model.Dungeon;
import dungeon.model.DungeonImpl;
import dungeon.view.IView;

public class ViewController implements Features {

  private final IView view;
  private Dungeon model;
  private Dungeon dungeonCopy;

  public ViewController(Dungeon m, IView v) {
    if (m == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    if (v == null) {
      throw new IllegalArgumentException("View cannot be null!");
    }
    model = m;
    dungeonCopy = new DungeonImpl(model.getDungeon(), model.getStart(), model.getEnd());
    view = v;
    view.setFeatures(this);
    view.resetFocus();
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void restartGame(int[] size, int interconnectivity, boolean wrapping, int treasures,
      int difficulty) {
    model = new DungeonImpl(size, interconnectivity, wrapping, treasures, difficulty);
    view.setModel(model);
    view.refresh();
    view.resetFocus();
  }

  @Override
  public void resetGame() {
    System.out.println("reset game");
    model = new DungeonImpl(dungeonCopy.getDungeon(), dungeonCopy.getStart(), dungeonCopy.getEnd());
    view.setModel(model);
    view.refresh();
    view.resetFocus();
  }

  @Override
  public void move(Direction d) {
    System.out.println(d);
    try {
      model.movePlayer(d);
    } catch (IllegalStateException ise) {
      // ignore move
    }
    if (model.playerDead()) {
      if (model.metNekker()) {
        view.endGame(
            "Sadly, you could not survive the combat and are dead."
            + " Video games and movies did not help... Your adventure ends :(");
      } else {
        view.endGame("Sadly, you were devoured by the hungry Otyugh!! Your adventure ends :( ");
      }
    } else if (model.hasReachedGoal()) {
      view.endGame("Hurray! You have found the exit of the dungeon and your status is: ");
    }
    view.refresh();
  }

  @Override
  public void pick() {
    model.pickTreasure();
    model.pickArrows();
    view.refresh();
  }


}
