package dungeon;

import java.util.List;

public class Labyrinth implements Dungeon {

  private final int[] size;
  private final int interconnectivity;
  private final boolean wrapping;
  private final int treasure;
  private final int[] start;
  private final int[] end;
  private final List<List<Cave>> labyrinth;
  private final Player player;

  public Labyrinth(int[] size, int interconnectivity, boolean wrapping, int treasure) {
    this.size = size;
    this.interconnectivity = interconnectivity;
    this.wrapping = wrapping;
    this.treasure = treasure;
    this.start = pickStart();
    this.end = pickEnd();
    this.labyrinth = createLabyrinth();
    this.player = new Player(this.start);
  }

  private List<List<Cave>> createLabyrinth() {
    return null;
  }

  private int[] pickStart() {
    return new int[]{0,0};
  }

  private int[] pickEnd() {
    return new int[]{0,0};
  }

  @Override
  public void movePlayer(Direction d) {

  }

  @Override
  public String printPlayerStatus() {
    return null;
  }

  @Override
  public String printCurrentLocation() {
    return null;
  }

  @Override
  public void pickTreasure() {

  }

  @Override
  public int[] getSize() {
    return new int[0];
  }

  @Override
  public int[] getStart() {
    return new int[0];
  }

  @Override
  public boolean hasReachedGoal() {
    return false;
  }

  @Override
  public Dungeon getLabyrinth() {
    return null;
  }
}
