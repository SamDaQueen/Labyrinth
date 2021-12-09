package dungeon.view;

import dungeon.controller.Features;
import dungeon.model.Dungeon;

public interface IView {

  void setModel(Dungeon model);

  void setFeatures(Features f);

  void refresh();

  void resetFocus();

  void endGame(String message);

  void showDialog(String message);

  int[] getPlayerRowCol();
}
