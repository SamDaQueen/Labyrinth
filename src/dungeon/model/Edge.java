package dungeon.model;

/**
 * An edge represented as two caves. Edge from A to B is equivalent to edge from B to A.
 */
class Edge {

  private final CaveImpl cave1;
  private final CaveImpl cave2;

  /**
   * Constructor for creating an edge.
   *
   * @param cave1 cave 1
   * @param cave2 cave 2
   */
  Edge(CaveImpl cave1, CaveImpl cave2) {
    this.cave1 = cave1;
    this.cave2 = cave2;
  }

  /**
   * Return one end of the edge.
   *
   * @return the cave
   */
  CaveImpl getCave1() {
    return cave1;
  }

  /**
   * Return second end of the edge.
   *
   * @return the cave
   */
  CaveImpl getCave2() {
    return cave2;
  }

  @Override
  public String toString() {
    return cave1 + " <--> " + cave2;
  }

  @Override
  public int hashCode() {
    return cave1.hashCode() + cave2.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof Edge) {
      Edge e = (Edge) obj;
      return (this.cave1 == e.cave1 && this.cave2 == e.cave2) || (this.cave1 == e.cave2
                                                                  && this.cave2 == e.cave1);
    }
    return false;
  }
}
