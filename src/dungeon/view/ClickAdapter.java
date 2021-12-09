package dungeon.view;

import dungeon.controller.Features;
import dungeon.model.Direction;
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
    int clickRow = y / view.getCellSize();
    int clickCol = x / view.getCellSize();

    // get player co-ordinates
    int playerRow = view.getPlayerRowCol()[0];
    int playerCol = view.getPlayerRowCol()[1];

    if (clickRow < playerRow && clickCol == playerCol) {
      listener.move(Direction.NORTH);
    } else if (clickRow > playerRow && clickCol == playerCol) {
      listener.move(Direction.SOUTH);
    } else if (clickCol < playerCol && clickRow == playerRow) {
      listener.move(Direction.WEST);
    } else if (clickCol > playerCol && clickRow == playerRow) {
      listener.move(Direction.EAST);
    }

  }
}
