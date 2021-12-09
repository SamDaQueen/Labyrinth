package dungeon.view;

import dungeon.controller.Features;
import dungeon.model.CaveImpl;
import dungeon.model.Direction;
import dungeon.model.ReadOnlyModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class JFrameView extends JFrame implements IView {

  private final int MAX_WIDTH = 1250;
  private final int MAX_HEIGHT = 750;
  private final JPanel innerPane, outerPane;
  ReadOnlyModel model;
  private JMenu menu;
  private JMenuItem settingsMenu, restartMenu, resetMenu, quitMenu;
  private JTextArea caveDesc, playerDesc;
  private JButton resetBtn, restartBtn, quitBtn;


  public JFrameView(ReadOnlyModel model) {
    super("Labyrinth: The Game");

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }

    this.model = model;

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

    outerPane.add(innerPane);
    // gap between inner pane and button view
    outerPane.add(Box.createRigidArea(new Dimension(0, 10)));

    setUpCaveView();
    setUpDungeonView();
    setUpPlayerView();
    setUpButtonView();
    setUpMenu();

    add(outerPane, BorderLayout.CENTER);

    setVisible(true);
  }

  private void setUpCaveView() {

    // cave view dimensions
    Dimension caveDim = new Dimension(280, 560);

    // add the cave description panel to the left
    JPanel caveDescView = new JPanel();
    caveDescView.setBorder(BorderFactory.createTitledBorder("Current Cave Description"));

    // add the label heading field
    caveDesc = new CustomJTextArea(model.printCurrentLocation());
    caveDescView.add(caveDesc);

    // add scroll controls
    JScrollPane caveScroll = new JScrollPane(caveDescView,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    caveDesc.setPreferredSize(caveDim);
    caveDescView.setMaximumSize(caveDim);
    caveScroll.setMaximumSize(new Dimension(300, 600));

    innerPane.add(caveScroll);
    innerPane.add(Box.createRigidArea(new Dimension(10, 0)));
  }

  private void setUpDungeonView() {
    drawDungeon();
  }

  private void setUpPlayerView() {

    // player view dimensions
    Dimension playerDim = new Dimension(260, 560);

    // add the player description panel to the right
    JPanel playerDescView = new JPanel();
    playerDescView.setBorder(BorderFactory.createTitledBorder("Player Status"));

    // add the label field
    playerDesc = new CustomJTextArea(model.printPlayerStatus());
    playerDescView.add(playerDesc);

    // add scroll controls
    JScrollPane playerScroll = new JScrollPane(playerDescView,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    playerDesc.setPreferredSize(playerDim);
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
    resetBtn.setActionCommand("Reset Button");
    buttonView.add(resetBtn);
    buttonView.add(Box.createRigidArea(new Dimension(30, 0)));

    restartBtn = new JButton("Restart Game");
    restartBtn.setMargin(new Insets(margin, margin, margin, margin));
    restartBtn.setActionCommand("Restart Button");
    buttonView.add(restartBtn);
    buttonView.add(Box.createRigidArea(new Dimension(30, 0)));

    quitBtn = new JButton("Quit Game");
    quitBtn.setMargin(new Insets(margin, margin, margin, margin));
    quitBtn.setActionCommand("Quit Button");
    buttonView.add(quitBtn);

    buttonView.setAlignmentX(Component.CENTER_ALIGNMENT);

    outerPane.add(buttonView);
  }

  private void setUpMenu() {

    JMenuBar menuBar = new JMenuBar();

    menu = new JMenu("Menu");

    settingsMenu = new JMenuItem("Settings");
    resetMenu = new JMenuItem("Reset Dungeon");
    restartMenu = new JMenuItem("Restart Game");
    quitMenu = new JMenuItem("Quit Game");

    menu.add(settingsMenu);
    menu.add(resetMenu);
    menu.add(restartMenu);
    menu.add(quitMenu);

    menuBar.add(menu);
    setJMenuBar(menuBar);
  }

  @Override
  public void drawDungeon() {
    // dungeon view dimensions
    Dimension dungeonDim = new Dimension(550, 550);

    innerPane.remove(1);

    // to wrap the dungeon
    JPanel outerArea = new JPanel();
    outerArea.setMaximumSize(dungeonDim);

    // add the dungeon panel to the center
    JPanel dungeonView = new JPanel();
    dungeonView.setBorder(BorderFactory.createEtchedBorder());
    outerArea.add(dungeonView);

    int row = model.getSize()[0];
    int col = model.getSize()[1];
    System.out.println(model.getSize()[0] + "" + model.getSize()[1]);
    CaveImpl[][] caves = model.getDungeon();

    // add grid layout for the dungeon
    GridLayout dungeonGrid = new GridLayout(row, col);
    dungeonView.setLayout(dungeonGrid);

    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        try {
          BufferedImage image;
          if (!caves[i][j].visited()) {
            image = ImageIO.read(new File("icons/blank.png"));
          } else {
            Set<Direction> directionList = caves[i][j].getOpenings();
            StringBuilder stringBuilder = new StringBuilder("icons/");
            if (directionList.contains(Direction.NORTH)) {
              stringBuilder.append("N");
            }
            if (directionList.contains(Direction.SOUTH)) {
              stringBuilder.append("S");
            }
            if (directionList.contains(Direction.EAST)) {
              stringBuilder.append("E");
            }
            if (directionList.contains(Direction.WEST)) {
              stringBuilder.append("W");
            }

            stringBuilder.append(".png");

            image = ImageIO.read(new File(stringBuilder.toString()));
          }

          JLabel place = new JLabel(new ImageIcon(image));
          dungeonView.add(place);

        } catch (
            IOException e) {
          System.out.println("Could not set up dungeon view: " + e.getMessage());
        }
      }
    }

    // add scroll controls
    JScrollPane dungeonScroll = new JScrollPane(outerArea,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    dungeonView.setMaximumSize(dungeonDim);
    dungeonScroll.setMaximumSize(new Dimension(600, 600));

    innerPane.add(dungeonScroll);
    innerPane.add(Box.createRigidArea(new Dimension(10, 0)));
  }

  @Override
  public void setFeatures(Features f) {
    quitBtn.addActionListener(l -> f.exitProgram());
    restartBtn.addActionListener(l -> f.restartGame());
  }

  @Override
  public void refresh() {
    repaint();
  }
}

