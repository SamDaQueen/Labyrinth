package dungeon.view;

import dungeon.controller.Features;

public interface IView {

  void drawDungeon();

  void setFeatures(Features f);

  void refresh();

  void resetFocus();
}
