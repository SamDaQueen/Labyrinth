package dungeon.view;

import javax.swing.JTextArea;

public class CustomJTextArea extends JTextArea {

  public CustomJTextArea(String text) {
    super(text);
    setEditable(false);
    setCursor(null);
    setOpaque(false);
    setFocusable(false);
    setWrapStyleWord(true);
    setLineWrap(true);
  }
}
