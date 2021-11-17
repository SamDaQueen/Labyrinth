package dungeon;

import java.util.List;
import java.util.Random;

/**
 * Driver class for generating sample runs of the dungeon model.
 */
public class Driver {

  /**
   * Main method for entry point to the program.
   *
   * @param args accepts the row, col, interconnectivity, wrapping, and treasure
   */
  public static void main(String[] args) {

    if (args.length < 4) {
      System.out.println(
          "Please provide all valid arguments! row col interconnectivity wrapping treasure.");
      System.exit(1);
    }

    System.out.println(
        "*****Welcome to the Labyrinth! Navigate this maze while collecting"
            + " treasures and trying to find the exit!*****\n");

    System.out.println("Initializing dungeon and player...");

    try {
      Dungeon dungeon = new DungeonImpl(
          new int[]{Integer.parseInt(args[0]), Integer.parseInt(args[1])},
          Integer.parseInt(args[2]),
          Boolean.parseBoolean(args[3]), Integer.parseInt(args[4]), 1);

      Random rand = new Random();

      while (!dungeon.hasReachedGoal()) {
        System.out.println(dungeon.printCurrentLocation());
        dungeon.pickTreasure();
        System.out.println(dungeon.printPlayerStatus());
        List<Direction> possibleMoves = dungeon.getPossibleMoves();
        Direction direction = possibleMoves.get(rand.nextInt(possibleMoves.size()));
        System.out.println("You have chosen to move " + direction);
        dungeon.movePlayer(direction);
      }
      System.out.println("\n\nHurray! You have found the exit of the dungeon and your status is: "
          + dungeon.printPlayerStatus());

      System.out.println(
          "\n\nPrinting the dungeon at the end for testing and checking purposes only!!");
      System.out.println(dungeon);
    } catch (NumberFormatException e) {
      System.out.println(
          "Please provide all valid arguments! row col interconnectivity wrapping treasure.");
      System.exit(1);
    }
  }
}

