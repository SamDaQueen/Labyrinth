package dungeon.view;

import java.awt.Font;
import javax.swing.JTextArea;
// https://community.oracle.com/tech/developers/discussion/1374176/jtextarea-to-look-like-jlabel

/**
 * A custom text area made for the cave view and the player view using the JTextArea with custom
 * settings.
 */
public class CustomJTextArea extends JTextArea {

  /**
   * Constructor for creating an instance of the class with the given string as text.
   *
   * @param text the given text
   */
  public CustomJTextArea(String text) {
    super(text);
    setEditable(false);
    setCursor(null);
    setOpaque(false);
    setFont(new Font("Century Gothic", Font.PLAIN, 20));
    setFocusable(false);
    setWrapStyleWord(true);
    setLineWrap(true);
  }
}
