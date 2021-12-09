package dungeon.view;

import dungeon.controller.Features;
import dungeon.controller.ViewController;
import dungeon.model.Dungeon;

public interface IView {

  void setModel(Dungeon model);

  void setFeatures(Features f);

  void refresh();

  void resetFocus();

  void endGame(String message);

  void showDialog(String message);

  int[] getPlayerRowCol();

  int getCellSize();

  void setUpSettings(Features f);

  void showHelp();
}
