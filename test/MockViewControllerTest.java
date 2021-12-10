import static org.junit.Assert.assertEquals;

import dungeon.controller.Features;
import dungeon.controller.ViewController;
import dungeon.model.Direction;
import dungeon.model.MockModel;
import dungeon.view.MockView;
import org.junit.Before;
import org.junit.Test;

/**
 * Class fpr testing view controller using a mock model and a mock view.
 */
public class MockViewControllerTest {

  private StringBuilder log;
  private Features controller;

  @Before
  public void setUp() {

    log = new StringBuilder();

    MockModel mockModel = new MockModel(log);
    MockView mockView = new MockView(log);
    controller = new ViewController(mockModel, mockView);
  }

  @Test
  public void constructorSetFeaturesAndResetFocus() {
    assertEquals("controller features added, focus reset, ", log.toString());
  }

  @Test
  public void moveNORTH() {
    controller.move(Direction.NORTH);
    assertEquals("controller features added, focus reset, \n"
                 + "Direction: NORTH\n"
                 + "focus reset, refresh, ", log.toString());
  }

  @Test
  public void moveSOUTH() {
    controller.move(Direction.SOUTH);
    assertEquals("controller features added, focus reset, \n"
                 + "Direction: SOUTH\n"
                 + "focus reset, refresh, ", log.toString());
  }

  @Test
  public void moveWEST() {
    controller.move(Direction.WEST);
    assertEquals("controller features added, focus reset, \n"
                 + "Direction: WEST\n"
                 + "focus reset, refresh, ", log.toString());
  }

  @Test
  public void moveEAST() {
    controller.move(Direction.EAST);
    assertEquals("controller features added, focus reset, \n"
                 + "Direction: EAST\n"
                 + "focus reset, refresh, ", log.toString());
  }

  @Test
  public void pick() {
    controller.pick();
    assertEquals("controller features added, focus reset, "
                 + "pick treasures, pick arrows, refresh, ",
        log.toString());
  }

  @Test
  public void shoot() {
    controller.shoot(Direction.EAST, 3);
    assertEquals("controller features added, focus reset, \n"
                 + "Direction:EAST Distance: 3\n"
                 + "dialog asked, focus reset, refresh, ", log.toString());
  }

  @Test
  public void shoot2() {
    controller.shoot(Direction.SOUTH, 5);
    assertEquals("controller features added, focus reset, \n"
                 + "Direction:SOUTH Distance: 5\n"
                 + "dialog asked, focus reset, refresh, ", log.toString());
  }

  @Test
  public void help() {
    controller.showHelp();
    assertEquals("controller features added, focus reset, set up help view, ", log.toString());
  }

  @Test
  public void settings() {
    controller.setUpSettings();
    assertEquals("controller features added, focus reset, set up settings, ", log.toString());
  }

  @Test
  public void restart() {
    controller.restartGame(new int[]{5, 5}, 5, true, 10, 10);
    assertEquals(
        "controller features added, focus reset, model updated, "
        + "game replayed, focus reset, refresh, ",
        log.toString());
  }

  @Test
  public void reset() {
    controller.resetGame();
    assertEquals(
        "controller features added, focus reset, model updated, "
        + "game replayed, focus reset, refresh, ",
        log.toString());
  }

}
