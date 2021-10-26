package dungeon;

import java.util.List;

public interface Cave {

  List<Treasure> getTreasure();

  List<Direction> getOpenings();

  boolean isTunnel();

}
