package dungeon;

import dungeon.controller.CommandController;
import dungeon.controller.Features;
import dungeon.controller.ViewController;
import dungeon.model.Dungeon;
import dungeon.model.DungeonImpl;
import dungeon.view.IView;
import dungeon.view.JFrameView;
import java.io.InputStreamReader;

public class Main {

  public static void main(String[] args) {

    if (args.length == 0) {
      // View mode
      Dungeon model = new DungeonImpl(new int[]{8, 8}, 10, true, 30, 5);
      IView view = new JFrameView(model);
      Features controller = new ViewController(model, view);

    } else if (args.length < 6) {

      System.out.println("Please provide all valid arguments!"
                         + " row col interconnectivity wrapping treasure difficulty.");
      System.exit(1);

    } else {
      // Console mode
      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;

      System.out.println(
          "\n*****Welcome to the Labyrinth! Navigate this maze while collecting"
          + " treasures and trying to find the exit!*****\nBut beware of the Otyughs!! "
          + "As they might try to eat you.\nP.S. There are Nekkers roaming in the dungeon"
          + " that scream and slash at you, you can try to fight them...\nAlso, "
          + "there is a thief that would take all your treasures if you meet them.\n"
          + "Good luck, brave explorer!:)");

      Dungeon model = new DungeonImpl(
          new int[]{Integer.parseInt(args[0]), Integer.parseInt(args[1])},
          Integer.parseInt(args[2]),
          Boolean.parseBoolean(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));

      new CommandController(input).execute(model, output);
    }
  }


}
