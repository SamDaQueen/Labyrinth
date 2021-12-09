package dungeon.view;

import javax.swing.JTextArea;

// https://community.oracle.com/tech/developers/discussion/1374176/jtextarea-to-look-like-jlabel
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
