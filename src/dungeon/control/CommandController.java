package dungeon.control;

import dungeon.control.commands.Move;
import dungeon.control.commands.Pick;
import dungeon.control.commands.Shoot;
import dungeon.model.Dungeon;
import java.io.IOException;
import java.util.Scanner;

/**
 * A command controller class for running the game based on the commands given by the user and
 * executing them accordingly. Has Readable and Appendable for the input and output.
 */
public class CommandController implements Controller {

  private final Appendable out;
  private final Scanner scan;

  public CommandController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  @Override
  public void execute(Dungeon model) {

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }

    Controller cmd = null;

    try {
      while (!(model.hasReachedGoal() || model.playerDead())) {
        out.append("\n\n");
        out.append(model.toString());
        out.append(model.printPlayerStatus());
        out.append(model.printCurrentLocation());
        if (model.getSmell(new int[]{model.getPos()[0], model.getPos()[1]}) == 1) {
          out.append("There is a faint stench... An Otyugh must be close!!\n");
        } else if (model.getSmell(new int[]{model.getPos()[0], model.getPos()[1]}) == 2) {
          out.append(
              "There is a strong stench... An Otyugh must be in the next cell!! "
              + "Or perhaps there are more than one Otyughs nearby hungry for your flesh!!\n");
        }
        out.append(
            "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/ quit: ");
        if (!scan.hasNext()) {
          break;
        }

        String in = scan.next();
        switch (in) {
          case "q":
          case "quit":
            out.append("You have given up and chose to quit. Good bye!!");
            return;
          case "move":
            cmd = new Move(scan.next());
            out.append("You are brave and attempt to advance in the dungeon...");
            break;
          case "pick":
            cmd = new Pick();
            out.append("This will add a fine addition to your collection...");
            break;
          case "shoot":
            cmd = new Shoot(scan.next(), scan.nextInt());
            out.append("You have attempted to slay an Otyugh...");
            break;
          default:
            out.append("Unknown command!");
            break;
        }
        if (cmd != null) {
          try {
            cmd.execute(model);
          } catch (IllegalStateException ise) {
            out.append(ise.getMessage());
            continue;
          }
          cmd = null;
        }
      }

      if (model.hasReachedGoal()) {
        out.append("\n\nHurray! You have found the exit of the dungeon and your status is: ")
            .append(model.printPlayerStatus());
      } else if (model.playerDead()) {
        out.append("\n\nSadly, you were devoured by the hungry Otyugh!! Your adventure ends :( ")
            .append(model.printPlayerStatus());
      }

    } catch (IOException ioe) {
      throw new IllegalStateException("\nAppend failed ", ioe);
    }
  }
}
