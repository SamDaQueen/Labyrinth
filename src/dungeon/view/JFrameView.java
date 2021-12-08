package dungeon.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class JFrameView extends JFrame implements IView {

  private final int MAX_WIDTH = 1250;
  private final int MAX_HEIGHT = 750;
  private final JPanel innerPane;
  private final JPanel outerPane;
  private JLabel caveDesc;
  private JLabel playerDesc;
  private JButton resetBtn;
  private JButton restartBtn;
  private JButton quitBtn;


  public JFrameView() {
    super("Labyrinth: The Game");

    // set size and location
    setSize(MAX_WIDTH, MAX_HEIGHT);
    setLocation(150, 30);

    // to avoid resizing
    setResizable(false);

    // make sure it does not run in background on quitting
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    setLayout(new BorderLayout());

    outerPane = new JPanel();
    // align objects vertically
    outerPane.setLayout(new BoxLayout(outerPane, BoxLayout.PAGE_AXIS));
    outerPane.setPreferredSize(new Dimension(MAX_WIDTH, 700));
    outerPane.setBorder(BorderFactory.createLineBorder(Color.GREEN));

    // create border around the outer panel
    int margin = 20;
    outerPane.setBorder(
        BorderFactory.createCompoundBorder(
            new EmptyBorder(margin, margin, margin, margin),
            new EtchedBorder()));

    innerPane = new JPanel();
    // align objects horizontally
    innerPane.setLayout(new BoxLayout(innerPane, BoxLayout.LINE_AXIS));
    innerPane.setMaximumSize(new Dimension(MAX_WIDTH, 600));
    innerPane.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

    outerPane.add(innerPane);
    // gap between inner pane and button view
    outerPane.add(Box.createRigidArea(new Dimension(0, 10)));

    setUpCaveView();
    setUpDungeonView();
    setUpPlayerView();
    setUpButtonView();

    add(outerPane, BorderLayout.CENTER);

    setVisible(true);
  }

  private void setUpCaveView() {

    // cave view dimensions
    Dimension caveDim = new Dimension(280, 560);

    // add the cave description panel to the left
    JPanel caveDescView = new JPanel();
    caveDescView.setBorder(BorderFactory.createEtchedBorder());

    // add the label heading field
    JTextArea caveDescHeading = new CustomJTextArea(
        "Cave Description Area. All the information about the status of the dungeon"
        + " will be displayed here.");
    caveDescView.add(caveDescHeading);

    // add scroll controls
    JScrollPane caveScroll = new JScrollPane(caveDescView,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    caveDescHeading.setPreferredSize(caveDim);
    caveDescView.setMaximumSize(caveDim);
    caveScroll.setMaximumSize(new Dimension(300, 600));

    innerPane.add(caveScroll);
    innerPane.add(Box.createRigidArea(new Dimension(10, 0)));
  }

  private void setUpDungeonView() {

    // dungeon view dimensions
    Dimension dungeonDim = new Dimension(570, 570);

    // add the dungeon panel to the center
    JPanel dungeonView = new JPanel();
    dungeonView.setBorder(BorderFactory.createEtchedBorder());

    // add scroll controls
    JScrollPane dungeonScroll = new JScrollPane(dungeonView,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    dungeonView.setPreferredSize(dungeonDim);
    dungeonScroll.setMaximumSize(new Dimension(600, 600));

    innerPane.add(dungeonScroll);
    innerPane.add(Box.createRigidArea(new Dimension(10, 0)));
  }

  private void setUpPlayerView() {

    // player view dimensions
    Dimension playerDim = new Dimension(280, 560);

    // add the player description panel to the right
    JPanel playerDescView = new JPanel();
    playerDescView.setBorder(BorderFactory.createEtchedBorder());

    // add the label field
    JTextArea playerDescHeading = new CustomJTextArea(
        "Player Description Area. All the information about the status of the"
        + " player will be displayed here.");
    playerDescView.add(playerDescHeading);

    // add scroll controls
    JScrollPane playerScroll = new JScrollPane(playerDescView,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    playerDescHeading.setPreferredSize(playerDim);
    playerDescView.setMaximumSize(playerDim);
    playerScroll.setMaximumSize(new Dimension(300, 600));
    innerPane.add(playerScroll);
  }

  private void setUpButtonView() {

    // button view dimensions dimensions
    Dimension buttonDim = new Dimension(MAX_WIDTH, 75);

    // add the buttons panel to the bottom
    JPanel buttonView = new JPanel();
    buttonView.setLayout(new BoxLayout(buttonView, BoxLayout.LINE_AXIS));
    buttonView.setPreferredSize(buttonDim);

    int margin = 10;

    resetBtn = new JButton("Reset Dungeon");
    resetBtn.setMargin(new Insets(margin, margin, margin, margin));
    buttonView.add(resetBtn);
    buttonView.add(Box.createRigidArea(new Dimension(50, 0)));

    restartBtn = new JButton("Restart Game");
    restartBtn.setMargin(new Insets(margin, margin, margin, margin));
    buttonView.add(restartBtn);
    buttonView.add(Box.createRigidArea(new Dimension(50, 0)));

    quitBtn = new JButton("Quit Game");
    quitBtn.setMargin(new Insets(margin, margin, margin, margin));
    buttonView.add(quitBtn);

    buttonView.setAlignmentX(Component.CENTER_ALIGNMENT);

    outerPane.add(buttonView);
  }
}

