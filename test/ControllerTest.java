import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dungeon.controller.CommandController;
import dungeon.model.Dungeon;
import dungeon.model.DungeonImpl;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Class for testing the Controller and the Model together.
 */
public class ControllerTest {

  Dungeon model;
  Appendable out;
  Readable in;

  @Before
  public void setUp() {
    in = new StringReader("");
    out = new StringBuilder();
    model = new DungeonImpl();
  }

  @Test
  public void checkModel() {
    assertEquals("\n                   \n"
                 + "   M   P---O---O   \n"
                 + "   |           |   \n"
                 + "   M---T---M---O   \n"
                 + "   |       |       \n"
                 + "   O   O---H---O   \n"
                 + "   |       |       \n"
                 + "   O   M---O---X   \n"
                 + "                   \n", model.toString());
  }

  @Test
  public void noSmell() {
    in = new StringReader("move east");
    new CommandController(in).execute(model, out);
    assertFalse(out.toString().contains("There is a faint stench... An Otyugh must be close!!"));
    assertFalse(out.toString().contains(
        "There is a strong stench... An Otyugh must be in the next cell!! Or perhaps "
        + "there are more than"
        + " one Otyughs nearby hungry for your flesh!!\n"));
  }

  @Test
  public void faintSmell() {
    in = new StringReader("move east move east");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("There is a faint stench... An Otyugh must be close!!"));
  }

  @Test
  public void strongSmell() {
    in = new StringReader("move east move east move south");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains(
        "There is a strong stench... An Otyugh must be in the next cell!! Or perhaps "
        + "there are more than"
        + " one Otyughs nearby hungry for your flesh!!\n"));
  }

  @Test
  public void pick() {
    in = new StringReader("move east pick");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Added to inventory :D"));
    assertTrue(out.toString().contains("Player has collected no treasures and has 4 arrows."));
  }

  @Test
  public void nothingToPick() {
    in = new StringReader("pick");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Oh no!! Nothing to pick here!"));
  }

  @Test
  public void initialArrows() {
    in = new StringReader("q");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Player has collected no treasures and has 3 arrows."));
  }

  @Test
  public void wasteArrow() {
    in = new StringReader("shoot east 3");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Your arrow could not reach the Otyugh. What a waste!"));
  }

  @Test
  public void noMoreArrows() {
    in = new StringReader("shoot east 3 shoot east 3 shoot east 3 shoot east 3");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString()
        .contains("No arrows to shoot!! Should we throw you at the Otyugh instead??"));
  }

  @Test
  public void crookedArrow() {
    // travels through 3 tunnels and reaches 1 cave where it hits the Otyugh
    in = new StringReader("shoot east 1");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Your arrow hit the Otyugh and you hear a piercing howl!"));
  }

  @Test
  public void crookedArrow2() {
    // travels through 3 tunnels, 1 cave, and 1 tunnel to each 1 cave where it hits the Otyugh
    in = new StringReader("shoot east 2");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Your arrow hit the Otyugh and you hear a piercing howl!"));
  }

  @Test
  public void crookedArrow3() {
    // travels through 3 tunnels, 1 cave, and 1 tunnel and then stops at cave without hitting
    in = new StringReader("shoot east 3");
    new CommandController(in).execute(model, out);
    assertFalse(out.toString().contains("Your arrow hit the Otyugh and you hear a piercing howl!"));
  }

  @Test
  public void killOtyugh() {
    in = new StringReader("shoot east 1 move east shoot east 1");
    new CommandController(in).execute(model, out);
    assertTrue(
        out.toString().contains("Your have successfully hit the Otyugh twice and it is now dead!"));
  }

  @Test
  public void invalidCommandMove() {
    in = new StringReader("mive east");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Unknown command!"));
  }

  @Test
  public void invalidMove() {
    in = new StringReader("move west");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains(
        "This move is not possible! Please choose from available moves only!"));

    in = new StringReader("move north");
    out = new StringBuilder();
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains(
        "This move is not possible! Please choose from available moves only!"));

    in = new StringReader("move south");
    out = new StringBuilder();
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains(
        "This move is not possible! Please choose from available moves only!"));
  }


  @Test
  public void invalidCommandPick() {
    in = new StringReader("pack");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Unknown command!"));
  }


  @Test
  public void invalidCommandShoot() {
    in = new StringReader("sheet");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Unknown command!"));
  }

  @Test
  public void invalidShoot() {
    in = new StringReader("shoot x y");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Invalid input!"));
  }

  @Test
  public void invalidShoot2() {
    in = new StringReader("shoot west y");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Invalid input!"));
  }

  @Test
  public void invalidDistance() {
    in = new StringReader("shoot west 10");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString()
        .contains("You aren't Artemis here!! Please give a shooting distance between 1 and 5"));
  }

  @Test
  public void zeroDistance() {
    in = new StringReader("shoot west 0");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString()
        .contains("You aren't Artemis here!! Please give a shooting distance between 1 and 5"));
  }

  @Test
  public void negativeDistance() {
    in = new StringReader("shoot west -10");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString()
        .contains("You aren't Artemis here!! Please give a shooting distance between 1 and 5"));
  }

  @Test
  public void noOtyughAtStart() {
    in = new StringReader("");
    new CommandController(in).execute(model, out);
    assertFalse(model.playerDead());
  }

  @Test
  public void lose() {
    in = new StringReader("move east pick move east shoot south 1 move south pick shoot west 1"
                          + " move west shoot west 1 move west shoot west 1 move west move north");
    new CommandController(in).execute(model, out);
    assertTrue(model.playerDead());
    assertFalse(model.hasReachedGoal());
  }

  @Test
  public void invalidCommandsInBetween() {
    in = new StringReader(
        "move east pick move east pick move north shoot max south 1 move south pick shoot west 1"
        + " move west shoot west 1 move west shoot west 1 move west move north");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains(
        "This move is not possible! Please choose from available moves only!"));
    assertTrue(out.toString().contains("Unknown command!"));
    assertTrue(out.toString().contains("Oh no!! Nothing to pick here!"));
  }

}
