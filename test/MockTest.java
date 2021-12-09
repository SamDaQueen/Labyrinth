import static org.junit.Assert.assertEquals;

import dungeon.controller.CommandController;
import dungeon.model.Dungeon;
import dungeon.model.MockModel;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Class for isolating the Controller and then testing it using a mock model.
 */
public class MockTest {

  private Dungeon dungeon;
  private Readable in;
  private Appendable out;
  private StringBuilder log;

  @Before
  public void setUp() {
    log = new StringBuilder();
    dungeon = new MockModel(log);
    out = new StringBuilder();
  }

  @Test
  public void moveEast() {
    in = new StringReader("move east");
    out = new StringBuilder();
    new CommandController(in).execute(dungeon, out);
    assertEquals("\nDirection: EAST\n", log.toString());
  }

  @Test
  public void moveWest() {
    in = new StringReader("move west");
    out = new StringBuilder();
    new CommandController(in).execute(dungeon, out);
    assertEquals("\nDirection: WEST\n", log.toString());
  }

  @Test
  public void moveSouth() {
    in = new StringReader("move south");
    out = new StringBuilder();
    new CommandController(in).execute(dungeon, out);
    assertEquals("\nDirection: SOUTH\n", log.toString());
  }

  @Test
  public void moveNorth() {
    in = new StringReader("move north");
    out = new StringBuilder();
    new CommandController(in).execute(dungeon, out);
    assertEquals("\nDirection: NORTH\n", log.toString());
  }

  @Test
  public void shoot() {
    in = new StringReader("shoot west 1");
    out = new StringBuilder();
    new CommandController(in).execute(dungeon, out);
    assertEquals("\nDirection:WEST Distance: 1\n", log.toString());
  }

  @Test
  public void shoot2() {
    in = new StringReader("shoot north 10");
    out = new StringBuilder();
    new CommandController(in).execute(dungeon, out);
    assertEquals("\nDirection:NORTH Distance: 10\n", log.toString());
  }

}
