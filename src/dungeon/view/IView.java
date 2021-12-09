package dungeon.view;

import dungeon.controller.Features;
import dungeon.model.ReadOnlyModel;

public interface IView {

  void drawDungeon();

  void setFeatures(Features f);

  void refresh();

}
