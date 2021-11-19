package dungeon.control;

import dungeon.model.Dungeon;
import dungeon.model.DungeonImpl;
import java.io.InputStreamReader;

/**
 * Main class for implementing the Model-Controller design for the Dungeon Model.
 */
public class Main {

  /**
   * The main method to act as the driver
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {

    if (args.length < 5) {
      System.out.println("Please provide all valid arguments!"
                         + " row col interconnectivity wrapping treasure difficulty.");
      System.exit(1);
    }

    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;

    System.out.println(
        "*****Welcome to the Labyrinth! Navigate this maze while collecting"
        + " treasures and trying to find the exit! But beware of the Otyugh!! "
        + "As they might try to eat you :) *****");

    Dungeon model = new DungeonImpl(new int[]{Integer.parseInt(args[0]), Integer.parseInt(args[1])},
        Integer.parseInt(args[2]),
        Boolean.parseBoolean(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));

    new CommandController(input).execute(model, output);
  }

}
