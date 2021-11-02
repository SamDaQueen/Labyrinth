package dungeon;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DungeonImpl implements Dungeon {

  private final int[] size;
  private final int interconnectivity;
  private final boolean wrapping;
  private final int treasure;
  private final int[] start;
  private final int[] end;
  private final Cave[][] dungeon;
  private final Player player;
  private final Set<Edge> edges;

  public DungeonImpl(int[] size, int interconnectivity, boolean wrapping, int treasure) {
    if (size == null) {
      throw new IllegalArgumentException("Size of the maze cannot be null!");
    }
    if (size[0] < 4 || size[1] < 4) {
      throw new IllegalArgumentException("Size has to be minimum 4X4!");
    }
    if (treasure <= 0) {
      throw new IllegalArgumentException("Please provide positive value for % of treasure!");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Please provide positive value for interconnectivity!");
    }
    this.size = size;
    this.interconnectivity = interconnectivity;
    this.wrapping = wrapping;
    this.treasure = treasure;
    this.edges = new HashSet<>();
    this.dungeon = createDungeon();

    int[] start = new int[2];
    int[] end = new int[2];
    while (abs(end[0] - start[0]) + abs(end[1] - start[1]) < 5) {
      start = pickCave();
      end = pickCave();
    }

    this.start = start;
    this.end = end;

    setTreasureInCaves();

    this.player = new Player(start[0], start[1]);

  }

  public DungeonImpl(int[] size, int interconnectivity, boolean wrapping, int treasure,
      Cave[][] caves, int[] start, int[] end) {
    this.dungeon = caves;
    this.size = size;
    this.wrapping = wrapping;
    this.interconnectivity = interconnectivity;
    this.treasure = treasure;
    this.start = start;
    this.end = end;
    this.player = new Player(this.start[0], this.start[1]);
    this.edges = new HashSet<>();
  }

  private void setTreasureInCaves() {

    Set<Cave> withTreasure = new HashSet<>();
    Random rand = new Random();

    while (withTreasure.size() <= treasure * getNumberOfCaves() / 100) {
      int row = rand.nextInt(size[0]);
      int col = rand.nextInt(size[1]);
      Cave cave = dungeon[row][col];
      if (!cave.isTunnel()) {
        if (!withTreasure.contains(cave)) {
          List<Treasure> treasures = new ArrayList<>();
          treasures.add(Treasure.DIAMOND);
          for (int i = 0; i < rand.nextInt(5); i++) {
            treasures.add(Treasure.RUBY);
          }
          for (int i = 0; i < rand.nextInt(5); i++) {
            treasures.add(Treasure.DIAMOND);
          }
          for (int i = 0; i < rand.nextInt(5); i++) {
            treasures.add(Treasure.SAPPHIRE);
          }
          dungeon[row][col].setTreasures(treasures);
          withTreasure.add(cave);
        }
      }
    }
  }

  @Override
  public List<Direction> getPossibleMoves() {
    return new ArrayList<>(
        dungeon[player.getCurrentPosition()[0]][player.getCurrentPosition()[1]].getOpenings());
  }

  @Override
  public int[] getPlayerPosition() {
    return new int[]{
        player.getCurrentPosition()[0], player.getCurrentPosition()[1]
    };
  }

  private Cave[][] createDungeon() {
    Cave[][] dungeon = new Cave[size[0]][size[1]];

    for (int i = 0; i < size[0]; i++) {
      for (int j = 0; j < size[1]; j++) {
        dungeon[i][j] = new Cave();
      }
    }

    createPaths(dungeon);
    setCaveNeighbours(dungeon);
    return dungeon;
  }

  private void createPaths(Cave[][] dungeon) {

    List<Edge> potentialEdges = new ArrayList<>();

    // adding non-edge links to the potential links list
    for (int row = 0; row < size[0] - 1; row++) {
      for (int col = 0; col < size[1] - 1; col++) {
        potentialEdges.add(new Edge(dungeon[row][col], dungeon[row][col + 1])); // right
        potentialEdges.add(new Edge(dungeon[row][col], dungeon[row + 1][col])); // down
      }
    }

    // adding the edges of the right-most column and bottom-most row
    for (int row = 0; row < size[0] - 1; row++) {
      potentialEdges.add(
          new Edge(dungeon[row][size[1] - 1], dungeon[row + 1][size[1] - 1])); // down
    }
    for (int col = 0; col < size[1] - 1; col++) {
      potentialEdges.add(
          new Edge(dungeon[size[0] - 1][col], dungeon[size[0] - 1][col + 1])); // right
    }

    // add wrapping edges
    if (wrapping) {
      for (int row = 0; row < size[0]; row++) {
        potentialEdges.add(new Edge(dungeon[row][0], dungeon[row][size[1] - 1])); // left-right
      }
      for (int col = 0; col < size[1]; col++) {
        potentialEdges.add(new Edge(dungeon[0][col], dungeon[size[0] - 1][col])); // top-bottom
      }
    }

    kruskals(potentialEdges, dungeon);
    //System.out.println(potentialEdges);
  }

  private void setCaveNeighbours(Cave[][] dungeon) {

    // add neighbors for caves
    for (int row = 0; row < size[0]; row++) {
      for (int col = 0; col < size[1]; col++) {
        Set<Direction> directions = new HashSet<>();
        if (row != 0) {
          Edge north = new Edge(dungeon[row][col], dungeon[row - 1][col]); // up
          if (edges.contains(north)) {
            directions.add(Direction.NORTH);
          }
        }
        if (col != size[1] - 1) {
          Edge east = new Edge(dungeon[row][col], dungeon[row][col + 1]); // right
          if (edges.contains(east)) {
            directions.add(Direction.EAST);
          }
        }
        if (col != 0) {
          Edge west = new Edge(dungeon[row][col], dungeon[row][col - 1]); // left
          if (edges.contains(west)) {
            directions.add(Direction.WEST);
          }
        }
        if (row != size[0] - 1) {
          Edge south = new Edge(dungeon[row][col], dungeon[row + 1][col]); // down
          if (edges.contains(south)) {
            directions.add(Direction.SOUTH);
          }
        }
        dungeon[row][col].setOpenings(directions);
      }
    }
    if (wrapping) {
      for (int row = 0; row < size[0]; row++) {
        Edge west = new Edge(dungeon[row][0], dungeon[row][size[1] - 1]); // left-right
        if (edges.contains(west)) {
          dungeon[row][0].setOpenings(Direction.WEST);
          dungeon[row][size[1] - 1].setOpenings(Direction.EAST);
        }
      }
      for (int col = 0; col < size[1]; col++) {
        Edge north = new Edge(dungeon[0][col], dungeon[size[0] - 1][col]); // top-bottom
        if (edges.contains(north)) {
          dungeon[0][col].setOpenings(Direction.NORTH);
          dungeon[size[0] - 1][col].setOpenings(Direction.SOUTH);
        }
      }
    }
  }

  private void kruskals(List<Edge> potentialEdges, Cave[][] dungeon) {

    // create set of all the caves
    Set<Set<Cave>> sets = new HashSet<>();
    for (Cave[] caves : dungeon) {
      for (Cave c : caves) {
        Set<Cave> set = new HashSet<>();
        set.add(c);
        sets.add(set);
      }
    }

    Random rand = new Random();
    Set<Set<Cave>> newSetOfSets = new HashSet<>(sets);
    List<Edge> leftovers = new ArrayList<>();

    while (newSetOfSets.size() > 1) {
      int random = rand.nextInt(potentialEdges.size());
      Edge edge = potentialEdges.get(random);

      Set<Cave> newSet = new HashSet<>();

      for (Set<Cave> set : sets) {
        if (set.contains(edge.getCave1()) && set.contains(edge.getCave2())) {
          leftovers.add(edge);
          potentialEdges.remove(edge);
          break;
        }
        if (set.contains(edge.getCave1())) {
          newSet.addAll(set);
          newSetOfSets.remove(set);
        } else if (set.contains(edge.getCave2())) {
          newSet.addAll(set);
          newSetOfSets.remove(set);
        }
        if (newSet.contains(edge.getCave1()) && newSet.contains(edge.getCave2())) {
          newSetOfSets.add(newSet);
          potentialEdges.remove(edge);
          edges.add(edge);
          break;
        }
      }
      sets = new HashSet<>(newSetOfSets);
    }

    for (int i = 0; i < interconnectivity; i++) {
      if (leftovers.size() > 0) {
        Edge left = leftovers.get(rand.nextInt(leftovers.size()));
        edges.add(left);
        leftovers.remove(left);
      }
    }
    //System.out.println("selected edges: " + edges.size() + edges);
  }

  private int[] pickCave() {
    Random rand = new Random();
    int row = rand.nextInt(size[0]);
    int col = rand.nextInt(size[1]);
    while (dungeon[row][col].isTunnel()) {
      row = rand.nextInt(size[0]);
      col = rand.nextInt(size[1]);
    }
    return new int[]{row, col};
  }

  @Override
  public void movePlayer(Direction d) {
    int[] current = player.getCurrentPosition();
    if (!dungeon[current[0]][current[1]].getOpenings().contains(d)) {
      throw new IllegalStateException("Move not possible!");
    }
    switch (d) {
      case NORTH:
        if (current[0] == 0) {
          player.setCurrentPosition(size[0] - 1, current[1]);
          break;
        }
        player.setCurrentPosition(current[0] - 1, current[1]);
        break;

      case EAST:
        if (current[1] == size[1] - 1) {
          player.setCurrentPosition(current[0], 0);
          break;
        }
        player.setCurrentPosition(current[0], current[1] + 1);
        break;

      case WEST:
        if (current[1] == 0) {
          player.setCurrentPosition(current[0], size[1] - 1);
          break;
        }
        player.setCurrentPosition(current[0], current[1] - 1);
        break;

      case SOUTH:
        if (current[0] == size[0] - 1) {
          player.setCurrentPosition(0, current[1]);
          break;
        }
        player.setCurrentPosition(current[0] + 1, current[1]);
        break;

      default:
        break;
    }
  }

  @Override
  public String printPlayerStatus() {
    StringBuilder builder = new StringBuilder();
    List<Treasure> treasures = player.getCollectedTreasure();
    builder.append("Player has collected ");
    for (Treasure t : new HashSet<>(treasures)) {
      builder.append(t).append("(").append(Collections.frequency(treasures, t)).append("), ");
    }
    builder.append(" with a score of ").append(player.getScore());
    return builder.toString();
  }

  @Override
  public String printCurrentLocation() {
    StringBuilder builder = new StringBuilder();
    int row = player.getCurrentPosition()[0];
    int col = player.getCurrentPosition()[1];
    builder.append("Player is at location: [").append(row).append(",").append(col)
        .append("] with possible moves and treasures: ").append(dungeon[row][col]);
    return builder.toString();
  }

  @Override
  public void pickTreasure() {
    player.updateCollectedTreasure(
        dungeon[player.getCurrentPosition()[0]][player.getCurrentPosition()[1]].getTreasure());
  }

  @Override
  public int[] getSize() {
    return size;
  }

  @Override
  public int[] getStart() {
    return start;
  }

  @Override
  public boolean isWrapping() {
    return wrapping;
  }

  int[] getEnd() {
    return end;
  }

  int numberOfEdges() {
    if (dungeon != null) {
      return edges.size();
    } else {
      return 0;
    }
  }

  int getNumberOfCaves() {
    int counter = 0;
    for (int row = 0; row < size[0]; row++) {
      for (int col = 0; col < size[1]; col++) {
        if (!dungeon[row][col].isTunnel()) {
          counter++;
        }
      }
    }
    return counter;
  }

  int getCavesWithTreasures() {
    int counter = 0;
    for (int row = 0; row < size[0]; row++) {
      for (int col = 0; col < size[1]; col++) {
        if (dungeon[row][col].getTreasure().size() > 0) {
          counter++;
        }
      }
    }
    return counter;
  }

  Set<Edge> getEdges() {
    return new HashSet<>(edges);
  }

  Cave[][] getDungeon() {
    return Arrays.copyOf(dungeon, 5);
  }

  @Override
  public boolean hasReachedGoal() {
    return end[0] == player.getCurrentPosition()[0] && end[1] == player.getCurrentPosition()[1];
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("   ");
    for (int col = 0; col < size[1]; col++) {
      if (dungeon[0][col].getOpenings().contains(Direction.NORTH)) {
        builder.append("|   ");
      } else {
        builder.append("    ");
      }
    }
    builder.append("\n");
    for (int row = 0; row < size[0]; row++) {
      if (dungeon[row][0].getOpenings().contains(Direction.WEST)) {
        builder.append("---");
      } else {
        builder.append("   ");
      }
      for (int col = 0; col < size[1]; col++) {
        if (row == start[0] && col == start[1]) {
          builder.append("S");
        } else if (row == end[0] && col == end[1]) {
          builder.append("X");
        } else {
          builder.append("O");
        }
        if (dungeon[row][col].getOpenings().contains(Direction.EAST)) {
          builder.append("---");
        } else {
          builder.append("   ");
        }
      }
      builder.append("\n");
      builder.append("   ");
      for (int col = 0; col < size[1]; col++) {
        if (dungeon[row][col].getOpenings().contains(Direction.SOUTH)) {
          builder.append("|   ");
        } else {
          builder.append("    ");
        }
      }
      builder.append("\n");
    }

    builder.append(Arrays.toString(start)).append(" ").append(Arrays.toString(end));

    return builder.toString();
  }

}
