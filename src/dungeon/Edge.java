package dungeon;

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
}
