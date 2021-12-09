import static org.junit.Assert.assertEquals;

import dungeon.controller.CommandController;
import dungeon.model.Dungeon;
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
    int code = 100;
    dungeon = new MockDungeon(code, log);
    out = new StringBuilder();
  }

  @Test
  public void moveEast() {
    in = new StringReader("move east");
    out = new StringBuilder();
    new CommandController(in).execute(dungeon, out);
    assertEquals("Direction: EAST", log.toString());
  }

  @Test
  public void moveWest() {
    in = new StringReader("move west");
    out = new StringBuilder();
    new CommandController(in).execute(dungeon, out);
    assertEquals("Direction: WEST", log.toString());
  }

  @Test
  public void moveSouth() {
    in = new StringReader("move south");
    out = new StringBuilder();
    new CommandController(in).execute(dungeon, out);
    assertEquals("Direction: SOUTH", log.toString());
  }

  @Test
  public void moveNorth() {
    in = new StringReader("move north");
    out = new StringBuilder();
    new CommandController(in).execute(dungeon, out);
    assertEquals("Direction: NORTH", log.toString());
  }

  @Test
  public void shoot() {
    in = new StringReader("shoot west 1");
    out = new StringBuilder();
    new CommandController(in).execute(dungeon, out);
    assertEquals("Direction:WEST Distance: 1", log.toString());
  }

  @Test
  public void shoot2() {
    in = new StringReader("shoot north 10");
    out = new StringBuilder();
    new CommandController(in).execute(dungeon, out);
    assertEquals("Direction:NORTH Distance: 10", log.toString());
  }

}
