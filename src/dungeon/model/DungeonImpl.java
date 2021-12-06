package dungeon.model;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Implementation of the Dungeon interface represented as the size, degree of interconnectivity,
 * whether the dungeon is wrapping or not, the percentage of caves with treasure, starting and
 * ending positions, 2-D array of Caves as the nodes of the dungeon, the player, and the edges
 * between the caves.
 */
public class DungeonImpl implements Dungeon {

  private final int[] size;
  private final int interconnectivity;
  private final boolean wrapping;
  private final int perOfCavesWTreasure;
  private final int[] start;
  private final int[] end;
  private final int difficulty;
  private final Cave[][] dungeon;
  private final Player player;
  private final Set<Edge> edges;
  private final Thief thief;
  private final Random rand;

  /**
   * Constructs a new Dungeon object.
   *
   * @param size                the size
   * @param interconnectivity   the degree of interconnectivity
   * @param wrapping            whether it is wrapping
   * @param perOfCavesWTreasure percentage of caves with treasure
   * @param difficulty          number of Otyugh
   */
  public DungeonImpl(int[] size, int interconnectivity, boolean wrapping,
      int perOfCavesWTreasure, int difficulty) {
    if (size == null) {
      throw new IllegalArgumentException("Size of the maze cannot be null!");
    }
    if (size[0] + size[1] < 7) {
      throw new IllegalArgumentException("Size too small!");
    }
    if (perOfCavesWTreasure <= 0) {
      throw new IllegalArgumentException("Please provide positive value for % of treasure!");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Please provide positive value for interconnectivity!");
    }
    if (difficulty < 1) {
      throw new IllegalArgumentException("There must be at least one Otyugh!");
    }
    this.rand = new Random();
    this.size = size;
    this.interconnectivity = interconnectivity;
    this.wrapping = wrapping;
    this.perOfCavesWTreasure = perOfCavesWTreasure;
    this.difficulty = difficulty;
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

    setPitInDungeon();
    setTreasureInCaves();
    setMonstersInCaves();
    setArrows();

    this.thief = addThief();
    this.player = new Player(start[0], start[1]);

  }

  /**
   * Alternate constructor to generate dungeon with the given 2-D array. Used for testing.
   *
   * @param caves 2-D array of Caves
   * @param start the starting node
   * @param end   the ending node
   */
  public DungeonImpl(Cave[][] caves, int[] start, int[] end) {
    this.dungeon = caves;
    this.size = new int[]{caves.length, caves[0].length};
    this.wrapping = false;
    this.interconnectivity = 0;
    this.perOfCavesWTreasure = 20;
    this.start = start;
    this.end = end;
    this.player = new Player(this.start[0], this.start[1]);
    this.edges = new HashSet<>();
    this.difficulty = 1;
    this.thief = new Thief(1, 1);
    this.rand = new Random();
  }

  /**
   * Alternate constructor to get fixed dungeon.
   */
  public DungeonImpl() {
    this.dungeon = getFixedDungeon();
    this.size = new int[]{4, 4};
    this.wrapping = false;
    this.interconnectivity = 0;
    this.perOfCavesWTreasure = 30;
    this.start = new int[]{0, 1};
    this.end = new int[]{3, 3};
    this.player = new Player(0, 1);
    this.edges = new HashSet<>();
    this.difficulty = 5;
    this.thief = new Thief(1, 1);
    this.rand = new Random();

  }

  private void setTreasureInCaves() {
    Set<Cave> withTreasure = new HashSet<>();
    List<Cave> caves = getCaves();

    while (withTreasure.size() <= perOfCavesWTreasure * getNumberOfCaves() / 100) {
      if (caves.size() > 0) {
        int choose = rand.nextInt(caves.size());
        Cave cave = caves.get(choose);
        if (cave.hasPit()) {
          continue;
        }
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
          cave.setTreasures(treasures);
          withTreasure.add(cave);
          caves.remove(cave);
        }
      } else {
        break;
      }
    }
  }

  private void setMonstersInCaves() {
    Set<Cave> withMonster = new HashSet<>();
    List<Cave> caves = getCaves();

    // add monster at the end
    dungeon[end[0]][end[1]].addOtyugh();
    caves.remove(dungeon[end[0]][end[1]]);
    withMonster.add(dungeon[end[0]][end[1]]);

    // remove start from possible monster caves
    caves.remove(dungeon[start[0]][start[1]]);

    while (withMonster.size() < Math.min(difficulty, getNumberOfCaves() - 2)) {
      if (caves.size() > 0) {
        int choose = rand.nextInt(caves.size());
        Cave cave = caves.get(choose);
        if (cave.hasPit()) {
          continue;
        }
        if (!withMonster.contains(cave)) {
          cave.addOtyugh();
          withMonster.add(cave);
          caves.remove(cave);
        }
      } else {
        break;
      }
    }
  }

  private void setPitInDungeon() {
    List<Cave> caves = getCaves();
    // remove start and end from possible pit caves
    caves.remove(dungeon[end[0]][end[1]]);
    caves.remove(dungeon[start[0]][start[1]]);
    caves.get(rand.nextInt(caves.size())).setPit(true);
  }

  private void setArrows() {
    Set<Cave> withArrows = new HashSet<>();

    while (withArrows.size() <= perOfCavesWTreasure * size[0] * size[1] / 100) {
      int row = rand.nextInt(size[0]);
      int col = rand.nextInt(size[1]);
      if (dungeon[row][col].hasPit()) {
        continue;
      }
      if (!withArrows.contains(dungeon[row][col])) {
        int arrows = rand.nextInt(3);
        dungeon[row][col].setArrows(arrows + 1);
        withArrows.add(dungeon[row][col]);
      }
    }
  }

  private Thief addThief() {
    int row = rand.nextInt(size[0]);
    int col = rand.nextInt(size[1]);

    while (row == start[0] && col == start[1] || row == end[0] && col == end[1]) {
      row = rand.nextInt(size[0]);
      col = rand.nextInt(size[1]);
    }
    return new Thief(row, col);
  }

  private List<Cave> getCaves() {
    List<Cave> caves = new ArrayList<>();
    for (int i = 0; i < size[0]; i++) {
      for (int j = 0; j < size[1]; j++) {
        if (!dungeon[i][j].isTunnel()) {
          caves.add(dungeon[i][j]);
        }
      }
    }
    return caves;
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
  }

  private int[] pickCave() {
    int row = rand.nextInt(size[0]);
    int col = rand.nextInt(size[1]);
    while (dungeon[row][col].isTunnel()) {
      row = rand.nextInt(size[0]);
      col = rand.nextInt(size[1]);
    }
    return new int[]{row, col};
  }

  private int[] getNextCave(int[] pos, Direction direction) {
    if (!dungeon[pos[0]][pos[1]].getOpenings().contains(direction)) {
      throw new IllegalStateException("That is a wall! Cannot move through walls!");
    }
    switch (direction) {
      case NORTH:
        if (pos[0] == 0) {
          return new int[]{size[0] - 1, pos[1]};
        }
        return new int[]{pos[0] - 1, pos[1]};

      case EAST:
        if (pos[1] == size[1] - 1) {
          return new int[]{pos[0], 0};
        }
        return new int[]{pos[0], pos[1] + 1};

      case WEST:
        if (pos[1] == 0) {
          return new int[]{pos[0], size[1] - 1};
        }
        return new int[]{pos[0], pos[1] - 1};

      case SOUTH:
        if (pos[0] == size[0] - 1) {
          return new int[]{0, pos[1]};
        }
        return new int[]{pos[0] + 1, pos[1]};

      default:
        break;
    }
    return new int[]{0, 0};
  }

  private void updatePlayerHealth() {
    int[] current = player.getCurrentPosition();
    if (dungeon[current[0]][current[1]].hasOtyugh()) {
      if (dungeon[current[0]][current[1]].getOtyugh().getHealth() == 2) {
        player.kill();
      } else if (dungeon[current[0]][current[1]].getOtyugh().getHealth() == 1) {
        int random = rand.nextInt(2);
        if (random == 0) {
          player.kill();
        }
      }
    }
  }

  @Override
  public int shoot(Direction direction, int steps) {
    if (steps < 1 || steps > 5) {
      throw new IllegalStateException(
          "You aren't Artemis here!! Please give a shooting distance between 1 and 5");
    }
    if (player.getArrows() < 1) {
      throw new IllegalStateException(
          "No arrows to shoot!! Should we throw you at the Otyugh instead??");
    }
    player.useArrow();
    int[] pos = player.getCurrentPosition();
    int[] arrowPos;
    if (!dungeon[pos[0]][pos[1]].getOpenings().contains(direction)) {
      return 0;
    } else {
      arrowPos = getNextCave(pos, direction);
      if (!dungeon[arrowPos[0]][arrowPos[1]].isTunnel()) {
        steps--;
      }
      while (steps > 0) {
        List<Direction> directionList = new ArrayList<>(
            dungeon[arrowPos[0]][arrowPos[1]].getOpenings());
        if (dungeon[arrowPos[0]][arrowPos[1]].isTunnel()) {
          directionList.remove(direction.invert(direction));
          direction = directionList.get(0);
        } else {
          if (!directionList.contains(direction)) {
            break;
          }
        }
        arrowPos = getNextCave(arrowPos, direction);
        if (!dungeon[arrowPos[0]][arrowPos[1]].isTunnel()) {
          steps--;
        }
      }
      if (dungeon[arrowPos[0]][arrowPos[1]].hasOtyugh() && steps == 0) {
        dungeon[arrowPos[0]][arrowPos[1]].shootOtyugh();
        if (!dungeon[arrowPos[0]][arrowPos[1]].hasOtyugh()) {
          return 2;
        }
        return 1;
      }
    }
    return 0;
  }

  @Override
  public boolean hasBreeze() {
    int[] current = player.getCurrentPosition();
    for (Direction d : getPossibleMoves()) {
      int[] next = getNextCave(current, d);
      if (dungeon[next[0]][next[1]].hasPit()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean hasThief() {
    return Arrays.equals(player.getCurrentPosition(), thief.getCurrentPosition());
  }

  @Override
  public List<Direction> getPossibleMoves() {
    return new ArrayList<>(
        dungeon[player.getCurrentPosition()[0]][player.getCurrentPosition()[1]].getOpenings());
  }

  @Override
  public int[] getPos() {
    return new int[]{
        player.getCurrentPosition()[0], player.getCurrentPosition()[1]
    };
  }

  @Override
  public void movePlayer(Direction d) {
    if (d == null) {
      throw new IllegalArgumentException("Direction cannot be null!");
    }
    int[] current = player.getCurrentPosition();
    if (!dungeon[current[0]][current[1]].getOpenings().contains(d)) {
      throw new IllegalStateException("Move not possible!");
    }

    int[] next = getNextCave(current, d);
    player.setCurrentPosition(next[0], next[1]);

    // move the thief with 50% probability
    if (rand.nextInt(2) == 1) {
      moveThief();
    }

    updatePlayerHealth();
    updatePlayerTreasure();

  }

  private void moveThief() {
    int[] current = thief.getCurrentPosition();
    Cave thiefCave = dungeon[current[0]][current[1]];
    Direction d = new ArrayList<>(thiefCave.getOpenings()).get(
        rand.nextInt(thiefCave.getOpenings().size()));
    int[] next = getNextCave(current, d);
    thief.setCurrentPosition(next[0], next[1]);
  }

  private void updatePlayerTreasure() {
    int[] current = player.getCurrentPosition();
    if (dungeon[current[0]][current[1]].hasPit()) {
      player.clearTreasure();
      player.clearArrows();
    }
    if (hasThief()) {
      player.clearTreasure();
    }
  }

  @Override
  public String printPlayerStatus() {
    return player.toString();
  }

  @Override
  public String printCurrentLocation() {
    StringBuilder builder = new StringBuilder();
    int row = player.getCurrentPosition()[0];
    int col = player.getCurrentPosition()[1];
    builder.append("Player is at location: [").append(row).append(",").append(col)
        .append("]: ").append(dungeon[row][col]).append("\n");

    if (getSmell() == 1) {
      builder.append("There is a faint stench... An Otyugh must be close!!\n");
    } else if (getSmell() == 2) {
      builder.append("There is a strong stench... An Otyugh must be in the next cell!!"
                     + " Or perhaps there are more than one Otyughs nearby hungry"
                     + " for your flesh!!\n");
    }

    if (hasBreeze()) {
      builder.append(
          "You feel a cool breeze hitting your face... There must be a pit in any of the"
          + " neighboring caves... Move carefully...\n");
    }

    if (hasThief()) {
      builder.append(
          "Uh-oh! The thief of the dungeon has found you and will take all of your treasures!");
    }

    return builder.toString();
  }

  @Override
  public boolean pickTreasure() {
    List<Treasure> treasures =
        dungeon[player.getCurrentPosition()[0]][player.getCurrentPosition()[1]].getTreasure();
    if (treasures.isEmpty()) {
      return false;
    }
    player.updateCollectedTreasure(treasures);
    dungeon[player.getCurrentPosition()[0]][player.getCurrentPosition()[1]].clearTreasure();
    return true;
  }

  @Override
  public boolean pickArrows() {
    int arrows =
        dungeon[player.getCurrentPosition()[0]][player.getCurrentPosition()[1]].getArrows();
    if (arrows == 0) {
      return false;
    }
    player.addArrow(arrows);
    dungeon[player.getCurrentPosition()[0]][player.getCurrentPosition()[1]].setArrows(0);
    return true;
  }

  @Override
  public int[] getSize() {
    return new int[]{size[0], size[1]};
  }

  @Override
  public int[] getStart() {
    return new int[]{start[0], start[1]};
  }

  @Override
  public boolean isWrapping() {
    return wrapping;
  }

  @Override
  public boolean hasReachedGoal() {
    return end[0] == player.getCurrentPosition()[0] && end[1] == player.getCurrentPosition()[1];
  }

  @Override
  public boolean playerDead() {
    return !player.isAlive();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    int[] playerPos = player.getCurrentPosition();
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
        if (row == playerPos[0] && col == playerPos[1]) {
          builder.append("P");
        } else if (row == start[0] && col == start[1]) {
          builder.append("S");
        } else if (row == end[0] && col == end[1]) {
          builder.append("X");
        } else if (dungeon[row][col].hasOtyugh()) {
          builder.append("M");
        } else if (dungeon[row][col].hasPit()) {
          builder.append("H");
        } else if (thief.getCurrentPosition()[0] == row && thief.getCurrentPosition()[1] == col) {
          builder.append("T");
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

    return builder.toString();
  }

  /**
   * Package-private method for getting the smell at the given cave position.
   *
   * @return 0: no smell, 1: faint smell: 2: strong smell
   */
  int getSmell() {
    int smell = 0;
    int[] pos = player.getCurrentPosition();
    for (Direction direction : dungeon[pos[0]][pos[1]].getOpenings()) {
      int[] next = getNextCave(pos, direction);
      if (dungeon[next[0]][next[1]].hasOtyugh()) {
        smell += 2;
      }
      for (Direction innerDirection : dungeon[next[0]][next[1]].getOpenings()) {
        int[] innerNext = getNextCave(next, innerDirection);
        if (!(innerNext[0] == pos[0] && innerNext[1] == pos[1])) {
          if (dungeon[innerNext[0]][innerNext[1]].hasOtyugh()) {
            smell++;
          }
        }
      }
    }
    return Math.min(smell, 2);
  }

  /**
   * Package-private method for asserting the ending location.
   *
   * @return the end as int array
   */
  int[] getEnd() {
    return end;
  }

  /**
   * Package-private method for getting the number of edges in the dungeon.
   *
   * @return number of edges
   */
  int getNumberOfEdges() {
    if (dungeon != null) {
      return edges.size();
    } else {
      return 0;
    }
  }

  /**
   * Package-private method for getting the number of caves in the dungeon.
   *
   * @return number of caves
   */
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

  /**
   * Package-private method for getting the number of caves containing treasure.
   *
   * @return number of caves with treasure
   */
  int getNumberOfCavesWithTreasures() {
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

  /**
   * Package-private method for getting the 2-D array of the caves in the dungeon.
   *
   * @return copy of caves
   */
  Cave[][] getDungeon() {
    return Arrays.copyOf(dungeon, size[0]);
  }

  /**
   * Package-private method for getting the player.
   *
   * @return the player
   */
  Player getPlayer() {
    return player;
  }

  /**
   * Generate fake dungeon for empty constructor used for testing.
   *
   * @return the dungeon
   */
  private Cave[][] getFixedDungeon() {
    Cave[][] caves = new Cave[4][4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        caves[row][col] = new Cave();
      }
    }

    // Create edges

    // row 0
    caves[0][0].setOpenings(Direction.SOUTH);
    caves[0][0].addOtyugh();
    caves[0][0].setArrows(2);

    caves[0][1].setOpenings(Direction.EAST);

    caves[0][2].setOpenings(Direction.EAST);
    caves[0][2].setOpenings(Direction.WEST);
    caves[0][2].setArrows(1);

    caves[0][3].setOpenings(Direction.WEST);
    caves[0][3].setOpenings(Direction.SOUTH);

    // row 1
    caves[1][0].setOpenings(Direction.NORTH);
    caves[1][0].setOpenings(Direction.EAST);
    caves[1][0].setOpenings(Direction.SOUTH);
    caves[1][0].addOtyugh();

    caves[1][1].setOpenings(Direction.WEST);
    caves[1][1].setOpenings(Direction.EAST);

    caves[1][2].setOpenings(Direction.EAST);
    caves[1][2].setOpenings(Direction.WEST);
    caves[1][2].setOpenings(Direction.SOUTH);
    caves[1][2].addOtyugh();

    caves[1][3].setOpenings(Direction.NORTH);
    caves[1][3].setOpenings(Direction.WEST);
    caves[1][3].setArrows(2);

    // row 2
    caves[2][0].setOpenings(Direction.NORTH);
    caves[2][0].setOpenings(Direction.SOUTH);
    caves[2][0].setArrows(3);

    caves[2][1].setOpenings(Direction.EAST);

    caves[2][2].setOpenings(Direction.NORTH);
    caves[2][2].setOpenings(Direction.SOUTH);
    caves[2][2].setOpenings(Direction.WEST);
    caves[2][2].setOpenings(Direction.EAST);
    caves[2][2].setTreasures(Treasure.RUBY);
    caves[2][2].setTreasures(Treasure.SAPPHIRE);
    caves[2][2].setTreasures(Treasure.DIAMOND);
    caves[2][2].setPit(true);

    caves[2][3].setOpenings(Direction.WEST);
    caves[2][3].setTreasures(Treasure.RUBY);
    caves[2][3].setTreasures(Treasure.DIAMOND);

    // row 3
    caves[3][0].setOpenings(Direction.NORTH);

    caves[3][1].setOpenings(Direction.EAST);

    caves[3][2].setOpenings(Direction.WEST);
    caves[3][2].setOpenings(Direction.EAST);
    caves[3][2].setOpenings(Direction.NORTH);
    caves[3][2].setArrows(3);
    caves[3][1].addOtyugh();
    caves[3][2].setTreasures(Treasure.DIAMOND);
    caves[3][2].setTreasures(Treasure.DIAMOND);
    caves[3][2].setTreasures(Treasure.RUBY);
    caves[3][2].setTreasures(Treasure.RUBY);

    caves[3][3].setOpenings(Direction.WEST);
    caves[3][3].addOtyugh();

    return caves;
  }


}
