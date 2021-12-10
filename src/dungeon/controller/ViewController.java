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
    if (m.getDungeon().length > 0) {
      dungeonCopy = new DungeonImpl(model.getDungeon(), model.getStart(), model.getEnd());
    }
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
    if (model.getDungeon().length > 0) {
      dungeonCopy = new DungeonImpl(model.getDungeon(), model.getStart(), model.getEnd());
    }
    view.setModel(model);
    view.replay();
    view.resetFocus();
    view.refresh();
  }

  @Override
  public void resetGame() {
    if (dungeonCopy != null) {
      model = new DungeonImpl(dungeonCopy.getDungeon(), dungeonCopy.getStart(),
          dungeonCopy.getEnd());
    }
    view.setModel(model);
    view.replay();
    view.resetFocus();
    view.refresh();
  }

  @Override
  public void move(Direction d) {
    try {
      model.movePlayer(d);
    } catch (IllegalStateException ise) {
      // ignore move
    }

    // check results
    if (model.metShadow()) {
      String status = model.printCurrentLocation();
      StringBuilder message = new StringBuilder(status.substring(status.indexOf("**********")));
      if (model.playerDead()) {
        message.append("Sadly, you could not survive the combat and are dead.");
        view.endGame("Video games and movies did not help... Your adventure ends :(");
      }
      view.showDialog(message.toString(), "You have encountered the Shadow!");
    } else if (model.playerDead()) {
      view.endGame("Sadly, you were devoured by the hungry Otyugh!! Your adventure ends :( ");
    } else if (model.hasReachedGoal()) {
      view.endGame("Hurray! You have found the exit of the dungeon!!");
    }
    view.resetFocus();
    view.refresh();
  }

  @Override
  public void pick() {
    model.pickTreasure();
    model.pickArrows();
    view.refresh();
  }

  @Override
  public void shoot(Direction d, int distance) {
    try {
      int shot = model.shoot(d, distance);
      if (shot == 0) {
        view.showDialog("Your arrow could not reach the Otyugh. What a waste!", "Shooting Result!");
      } else if (shot == 1) {
        view.showDialog("Your arrow hit the Otyugh and you hear a piercing howl!",
            "Shooting Result!");
      } else {
        view.showDialog("Your have successfully hit the Otyugh twice and it is now dead!",
            "Shooting Result!");
      }
    } catch (IllegalStateException ise) {
      view.resetShoot();
    }
    view.resetFocus();
    view.refresh();
  }

  @Override
  public void showHelp() {
    view.showHelp();
  }

  @Override
  public void setUpSettings() {
    view.setUpSettings(this);
  }


}
