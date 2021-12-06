package dungeon.model;

import java.util.Scanner;

/**
 * Driver class for generating sample runs of the dungeon model.
 */
public class Driver2 {

  /**
   * Main method for entry point to the program.
   *
   * @param args empty
   */
  public static void main(String[] args) {

    System.out.println(
        "*****Welcome to the Labyrinth! Navigate this maze while collecting"
        + " treasures and trying to find the exit!*****\n");

    System.out.println("Initializing dungeon and player...");

    Dungeon dungeon = new DungeonImpl(new int[]{4, 4}, 3, false, 20, 1);

//    System.out.println(
//        "\n\nPrinting the dungeon for testing and checking purposes only!!");
//    System.out.println(dungeon);

    Scanner scanner = new Scanner(System.in);
    Direction direction = Direction.NORTH;

    while (!(dungeon.hasReachedGoal() || dungeon.playerDead())) {

      while (true) {
        System.out.println("Please select direction from: " + dungeon.getPossibleMoves()
                           + " Type -> (1.NORTH 2.EAST 3.SOUTH 4.WEST)");
        int option = scanner.nextInt();

        switch (option) {
          case 1:
            direction = Direction.NORTH;
            break;
          case 2:
            direction = Direction.EAST;
            break;
          case 3:
            direction = Direction.SOUTH;
            break;
          case 4:
            direction = Direction.WEST;
            break;
          default:
            break;
        }

        if (dungeon.getPossibleMoves().contains(direction)) {
          break;
        }
        System.out.println("Please select direction from possible moves only!");
      }
      System.out.println("You have chosen to move " + direction);
      dungeon.movePlayer(direction);
      System.out.println(dungeon.printCurrentLocation());
      dungeon.pickTreasure();
      System.out.println(dungeon.printPlayerStatus());
    }
    System.out.println("\n\nHurray! You have found the exit of the dungeon and your status is: "
                       + dungeon.printPlayerStatus());


  }

}
