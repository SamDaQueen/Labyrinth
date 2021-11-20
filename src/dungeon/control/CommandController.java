package dungeon.control;

import dungeon.control.commands.Move;
import dungeon.control.commands.Pick;
import dungeon.control.commands.Shoot;
import dungeon.model.Dungeon;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

/**
 * A command controller class for running the game based on the commands given by the user and
 * executing them accordingly. Has Readable and Appendable for the input and output.
 */
public class CommandController implements Controller {

  private final Scanner scan;

  /**
   * Constructor to create the command based controller.
   *
   * @param in the readable (System.in)
   */
  public CommandController(Readable in) {
    if (in == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    scan = new Scanner(in);
  }

  @Override
  public void execute(Dungeon model, Appendable out) {

    if (out == null) {
      throw new IllegalArgumentException("Appendable cannot be null!");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }

    Controller cmd = null;

    try {
      while (!(model.hasReachedGoal() || model.playerDead())) {
        out.append("\n\n");
        out.append(model.printPlayerStatus());
        out.append(model.printCurrentLocation());
        out.append(model.printSmell());
        out.append(
            "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/ quit: ");
        if (!scan.hasNext()) {
          break;
        }
        try {
          String in = scan.next();
          switch (in.toLowerCase(Locale.ROOT)) {
            case "q":
            case "quit":
              out.append("You have given up and chose to quit. Good bye!!");
              return;
            case "m":
            case "move":
              cmd = new Move(scan.next());
              out.append("You are brave and attempt to advance in the dungeon... ");
              break;
            case "p":
            case "pick":
              cmd = new Pick();
              out.append("This will add a fine addition to your collection... ");
              break;
            case "s":
            case "shoot":
              cmd = new Shoot(scan.next(), scan.nextInt());
              out.append("You have attempted to slay an Otyugh... ");
              break;
            default:
              out.append("Unknown command!");
              break;
          }
          if (cmd != null) {
            try {
              cmd.execute(model, out);
            } catch (IllegalStateException ise) {
              out.append(ise.getMessage());
              continue;
            }
            cmd = null;
          }
        } catch (InputMismatchException ime) {
          out.append("\nInvalid input!");
        }
      }

      if (model.playerDead()) {
        out.append("\n\nSadly, you were devoured by the hungry Otyugh!! Your adventure ends :( ")
            .append(model.printPlayerStatus());
      } else if (model.hasReachedGoal()) {
        out.append("\n\nHurray! You have found the exit of the dungeon and your status is: ")
            .append(model.printPlayerStatus());
      }

    } catch (IOException ioe) {
      throw new IllegalStateException("\nAppend failed ", ioe);
    }
  }
}
