package dungeon.model;

import java.util.List;
import java.util.Set;

public interface Cave {


  /**
   * Get the list of treasures present in the cave.
   *
   * @return the treasures
   */
  List<Treasure> getTreasure();

  /**
   * Get a set of the available directions from the current cave.
   *
   * @return the directions
   */
  Set<Direction> getOpenings();

  /**
   * Check whether cave is a tunnel.
   *
   * @return true if it is a tunnel
   */
  boolean isTunnel();

  /**
   * Check whether the cave has an otyugh.
   *
   * @return true if otyugh present.
   */
  boolean hasOtyugh();

  /**
   * Check whether the cave has a pit.
   *
   * @return true if pit exists
   */
  boolean hasPit();

  /**
   * Get the number of arrows present in the cave.
   *
   * @return the number of arrows
   */
  int getArrows();

  boolean visited();

}
