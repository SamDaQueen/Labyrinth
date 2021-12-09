package dungeon.view;

import dungeon.controller.Features;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickAdapter extends MouseAdapter {

  private final Features listener;
  private final IView view;

  public ClickAdapter(Features listener, IView view) {
    this.listener = listener;
    this.view = view;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);

    // get click direction
    int x = e.getX();
    int y = e.getY();

    // convert x and y to row and col



    // get player co-ordinates
    int playerRow = view.getPlayerRowCol()[0];
    int playerCol = view.getPlayerRowCol()[1];

    System.out.println(x + " " + y + " " + playerRow + " " + playerCol);

//    if (y > playerY && x > -y && x < y) {
//      listener.move(Direction.NORTH);
//    } else if (y < playerY && x > -y && x < y) {
//      listener.move(Direction.SOUTH);
//    } else if (x > playerX && y > -x && y < x) {
//      listener.move(Direction.WEST);
//    } else if (x < playerX && y > -x && y < x) {
//      listener.move(Direction.EAST);
//    }

  }
}
