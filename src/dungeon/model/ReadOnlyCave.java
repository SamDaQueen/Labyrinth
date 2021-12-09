package dungeon.model;

import java.util.List;
import java.util.Set;

public interface ReadOnlyCave {

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

  /**
   * Checks if the cave was visited before
   *
   * @return true if visited
   */
  boolean visited();

}
