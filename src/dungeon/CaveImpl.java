package dungeon;

import java.util.List;

public class CaveImpl implements Cave {

  private List<Treasure> treasures;
  private final List<Direction> directions;

  public CaveImpl() {
  }

  @Override
  public List<Treasure> getTreasure() {
    return null;
  }

  @Override
  public List<Direction> getOpenings() {
    return null;
  }

  @Override
  public boolean isTunnel() {
    return false;
  }
}
