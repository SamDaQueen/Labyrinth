package dungeon;

/**
 * An edge represented as two caves. Edge from A to B is equivalent to edge from B to A.
 */
class Edge {

  private final Cave cave1;
  private final Cave cave2;

  Edge(Cave cave1, Cave cave2) {
    this.cave1 = cave1;
    this.cave2 = cave2;
  }

  Cave getCave1() {
    return cave1;
  }

  Cave getCave2() {
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
