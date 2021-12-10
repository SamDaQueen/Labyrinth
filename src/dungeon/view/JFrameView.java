package dungeon.view;

import static dungeon.model.Direction.EAST;
import static dungeon.model.Direction.NORTH;
import static dungeon.model.Direction.SOUTH;
import static dungeon.model.Direction.WEST;

import dungeon.controller.Features;
import dungeon.model.Direction;
import dungeon.model.ReadOnlyCave;
import dungeon.model.ReadOnlyModel;
import dungeon.model.Treasure;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/**
 * Class to set up the JFrame in which the game will be displayed. Implements the IView class that
 * offers functionalities to the controller to update the view. Contains components of the view as
 * well as a read-only copy of the model to update the contents of the view using it.
 */
public class JFrameView extends JFrame implements IView {

  private final int MAX_WIDTH = 1400;
  private final JPanel innerPane;
  private final JPanel outerPane;
  private final Map<String, BufferedImage> imageMap;
  private ReadOnlyModel model;
  private JMenuItem settingsMenu;
  private JMenuItem resetMenu;
  private JMenuItem restartMenu;
  private JMenuItem helpMenu;
  private JMenuItem quitMenu;
  private JTextArea caveDesc;
  private JTextArea playerDesc;
  private JButton resetBtn;
  private JButton restartBtn;
  private JButton quitBtn;
  private JButton helpButton;
  private JPanel dungeonView;
  private boolean gameOver;
  private boolean shootFlag;
  private boolean shootDirectionFlag;
  private Direction shootDirection;
  private int playerRow;
  private int playerCol;
  private int cellSize;

  /**
   * Constructor for creating an instance of the JFrameView class with the given read-only model.
   *
   * @param model the read-only model
   */
  public JFrameView(ReadOnlyModel model) {
    super("Labyrinth: The Game");

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }

    this.model = model;

    // set size and location
    setSize(MAX_WIDTH, 800);
    setLocation(60, 10);

    // to avoid resizing
    setResizable(false);

    // make sure it does not run in background on quitting
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    setLayout(new BorderLayout());

    outerPane = new JPanel();
    // align objects vertically
    outerPane.setLayout(new BoxLayout(outerPane, BoxLayout.PAGE_AXIS));
    outerPane.setPreferredSize(new Dimension(MAX_WIDTH, 750));

    // create border around the outer panel
    int margin = 5;
    outerPane.setBorder(
        BorderFactory.createCompoundBorder(
            new EmptyBorder(margin, margin, margin, margin),
            new EtchedBorder()));

    innerPane = new JPanel();
    // align objects horizontally
    innerPane.setLayout(new BoxLayout(innerPane, BoxLayout.LINE_AXIS));
    innerPane.setMaximumSize(new Dimension(MAX_WIDTH, 700));

    outerPane.add(innerPane);
    // gap between inner pane and button view
    outerPane.add(Box.createRigidArea(new Dimension(0, 10)));

    imageMap = new HashMap<>();
    setUpImageMap();
    setUpCaveView();
    setUpDungeonView();
    setUpPlayerView();
    setUpButtonView();
    setUpMenu();

    add(outerPane, BorderLayout.CENTER);

    gameOver = false;
    setVisible(true);
    resetFocus();
  }

  @Override
  public void refresh() {

    dungeonView.removeAll();

    // add grid layout for the dungeon
    GridLayout dungeonGrid = new GridLayout(model.getSize()[0], model.getSize()[1]);
    dungeonView.setLayout(dungeonGrid);
    BufferedImage[][] images = getDungeonImages();
    int row = model.getSize()[0];
    int col = model.getSize()[1];
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        JLabel place = new JLabel(new ImageIcon(images[i][j]));
        dungeonView.add(place);
      }
    }
    if (!model.playerDead()) {
      caveDesc.setText(model.printCurrentLocation());
      playerDesc.setText(model.printPlayerStatus());
    } else {
      caveDesc.setText("Nothing to show here, you died. Treasures and weapons mean nothing now :)");
      playerDesc.setText("You are dead :) Restart a new game or reset the same dungeon");
    }
    dungeonView.updateUI();
    repaint();
  }

  @Override
  public void resetFocus() {
    setFocusable(true);
    requestFocus();
  }

  @Override
  public void endGame(String message) {
    gameOver = true;
    JOptionPane.showMessageDialog(this, message, "GAME OVER!",
        JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void replay() {
    gameOver = false;
  }

  @Override
  public void showDialog(String message, String title) {
    JOptionPane.showMessageDialog(this, message, title,
        JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public int[] getPlayerRowCol() {
    return new int[]{playerRow, playerCol};
  }

  @Override
  public int getCellSize() {
    return cellSize;
  }

  @Override
  public void setModel(ReadOnlyModel model) {
    this.model = model;
  }

  @Override
  public void setFeatures(Features f) {
    quitBtn.addActionListener(l -> f.exitProgram());
    restartBtn.addActionListener(l -> f.restartGame(new int[]{5, 5}, 3, true, 25, 3));
    resetBtn.addActionListener(l -> f.resetGame());
    resetMenu.addActionListener(l -> f.resetGame());
    restartMenu.addActionListener(l -> f.restartGame(new int[]{5, 5}, 3, true, 25, 3));
    helpMenu.addActionListener(l -> f.showHelp());
    quitMenu.addActionListener(l -> f.exitProgram());
    helpButton.addActionListener(l -> f.showHelp());
    settingsMenu.addActionListener(l -> f.setUpSettings());

    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'p') {
          f.pick();
        }
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (!gameOver && !shootFlag) {
          if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'w') {
            f.move(NORTH);
          } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's') {
            f.move(Direction.SOUTH);
          } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'a') {
            f.move(Direction.WEST);
          } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd') {
            f.move(Direction.EAST);
          }
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        if (!gameOver) {
          if (e.getKeyChar() == 'k') {
            shootFlag = true;
          }
          if (shootFlag) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
              shootDirectionFlag = true;
              shootDirection = NORTH;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
              shootDirectionFlag = true;
              shootDirection = EAST;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
              shootDirectionFlag = true;
              shootDirection = SOUTH;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
              shootDirectionFlag = true;
              shootDirection = WEST;
            }
          }
          if (shootFlag && shootDirectionFlag) {
            if (e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
              resetShoot();
              f.shoot(shootDirection, 1);
            } else if (e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
              resetShoot();
              f.shoot(shootDirection, 2);
            } else if (e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
              resetShoot();
              f.shoot(shootDirection, 1);
            } else if (e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
              resetShoot();
              f.shoot(shootDirection, 1);
            } else if (e.getKeyCode() == KeyEvent.VK_5 || e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
              resetShoot();
              f.shoot(shootDirection, 1);
            }
          }
        }
      }
    });

    MouseAdapter mouseAdapter = new ClickAdapter(f, this);
    dungeonView.addMouseListener(mouseAdapter);
  }

  @Override
  public void showHelp() {
    Scanner scanner = new Scanner(getClass().getResourceAsStream("/help.txt"));
    StringBuilder stringBuilder = new StringBuilder();
    while (scanner.hasNextLine()) {
      stringBuilder.append(scanner.nextLine());
    }
    JOptionPane.showMessageDialog(this, stringBuilder, "How To Play",
        JOptionPane.PLAIN_MESSAGE);
    resetFocus();
  }

  @Override
  public void resetShoot() {
    shootFlag = false;
    shootDirectionFlag = false;
  }

  @Override
  public void setUpSettings(Features f) {
    JTextField rows = new JTextField();
    JTextField cols = new JTextField();
    JTextField interconnectivity = new JTextField();

    JRadioButton wrappingTrue = new JRadioButton("true");
    wrappingTrue.setSelected(true);
    JRadioButton wrappingFalse = new JRadioButton("false");

    ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add(wrappingTrue);
    buttonGroup.add(wrappingFalse);

    JTextField treasure = new JTextField();
    JTextField difficulty = new JTextField();

    final JComponent[] fields = new JComponent[]{
        new JLabel("Rows: "), rows,
        new JLabel("Columns: "), cols,
        new JLabel("Interconnectivity: "), interconnectivity,
        new JLabel("Wrapping: "), wrappingTrue, wrappingFalse,
        new JLabel("Treasure"), treasure,
        new JLabel("Difficulty"), difficulty
    };
    int selected = JOptionPane.showConfirmDialog(this, fields, "Settings",
        JOptionPane.OK_CANCEL_OPTION);

    if (selected == JOptionPane.OK_OPTION) {
      try {
        int row = Integer.parseInt(rows.getText());
        int col = Integer.parseInt(cols.getText());
        int inter = Integer.parseInt(interconnectivity.getText());
        boolean wrapping = wrappingTrue.isSelected();
        int treas = Integer.parseInt(treasure.getText());
        int diff = Integer.parseInt(difficulty.getText());

        try {
          f.restartGame(new int[]{row, col}, inter, wrapping, treas, diff);
        } catch (IllegalArgumentException iae) {
          JOptionPane.showMessageDialog(this, "Dungeon cannot be created"
                                              + " with these values! Please try again!",
              "Error",
              JOptionPane.ERROR_MESSAGE);
          setUpSettings(f);
        }

      } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(this, "Invalid values entered!"
                                            + " Please enter all valid numbers!",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        setUpSettings(f);
      }
    }
  }

  private void setUpImageMap() {
    try {

      // directions
      imageMap.put("N", ImageIO.read(getClass().getResourceAsStream("/images/N.png")));
      imageMap.put("E", ImageIO.read(getClass().getResourceAsStream("/images/E.png")));
      imageMap.put("S", ImageIO.read(getClass().getResourceAsStream("/images/S.png")));
      imageMap.put("W", ImageIO.read(getClass().getResourceAsStream("/images/W.png")));
      imageMap.put("NE", ImageIO.read(getClass().getResourceAsStream("/images/NE.png")));
      imageMap.put("NS", ImageIO.read(getClass().getResourceAsStream("/images/NS.png")));
      imageMap.put("NW", ImageIO.read(getClass().getResourceAsStream("/images/NW.png")));
      imageMap.put("SE", ImageIO.read(getClass().getResourceAsStream("/images/SE.png")));
      imageMap.put("SW", ImageIO.read(getClass().getResourceAsStream("/images/SW.png")));
      imageMap.put("EW", ImageIO.read(getClass().getResourceAsStream("/images/EW.png")));
      imageMap.put("NSE", ImageIO.read(getClass().getResourceAsStream("/images/NSE.png")));
      imageMap.put("NEW", ImageIO.read(getClass().getResourceAsStream("/images/NEW.png")));
      imageMap.put("NSW", ImageIO.read(getClass().getResourceAsStream("/images/NSW.png")));
      imageMap.put("SEW", ImageIO.read(getClass().getResourceAsStream("/images/SEW.png")));
      imageMap.put("NSEW", ImageIO.read(getClass().getResourceAsStream("/images/NSEW.png")));

      // black image
      imageMap.put("black", ImageIO.read(getClass().getResourceAsStream("/images/black.png")));

      // exit cave door
      imageMap.put("door", ImageIO.read(getClass().getResourceAsStream("/images/door.png")));

      // arrow image
      imageMap.put("arrow", ImageIO.read(getClass().getResourceAsStream("/images/arrow.png")));

      // treasures
      int treasureSize = 10;
      imageMap.put("diamond",
          resize(ImageIO.read(getClass().getResourceAsStream("/images/diamond.png")),
              treasureSize));
      imageMap.put("emerald",
          resize(ImageIO.read(getClass().getResourceAsStream("/images/emerald.png")),
              treasureSize));
      imageMap.put("ruby",
          resize(ImageIO.read(getClass().getResourceAsStream("/images/ruby.png")), treasureSize));

      // monsters
      imageMap.put("otyugh", ImageIO.read(getClass().getResourceAsStream("/images/otyugh.png")));
      imageMap.put("shadow", ImageIO.read(getClass().getResourceAsStream("/images/shadow.png")));

      // stench
      imageMap.put("weakStench",
          ImageIO.read(getClass().getResourceAsStream("/images/stench01.png")));
      imageMap.put("strongStench",
          ImageIO.read(getClass().getResourceAsStream("/images/stench02.png")));

      // player
      imageMap.put("sam", ImageIO.read(getClass().getResourceAsStream("/images/sam.png")));

      // breeze
      imageMap.put("breeze", ImageIO.read(getClass().getResourceAsStream("/images/breeze.png")));

      // pit
      imageMap.put("pit", ImageIO.read(getClass().getResourceAsStream("/images/pit.png")));

      // thief
      imageMap.put("thief", ImageIO.read(getClass().getResourceAsStream("/images/thief.png")));


    } catch (IOException e) {
      System.out.println("Could not set up dungeon view: " + e.getMessage());
    }

  }

  private void setUpCaveView() {

    // cave view dimensions
    Dimension caveDim = new Dimension(250, 1000);

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
    caveScroll.setPreferredSize(new Dimension(250, 800));

    innerPane.add(caveScroll);
    innerPane.add(Box.createRigidArea(new Dimension(10, 0)));
  }

  private void setUpDungeonView() {

    // to wrap the dungeon
    JPanel outerArea = new JPanel();
    outerArea.removeAll();

    // add the dungeon panel to the center
    dungeonView = new JPanel();
    dungeonView.setBorder(BorderFactory.createEtchedBorder());

    int row = model.getSize()[0];
    int col = model.getSize()[1];

    // add grid layout for the dungeon
    GridLayout dungeonGrid = new GridLayout(row, col);
    dungeonView.setLayout(dungeonGrid);

    BufferedImage[][] images = getDungeonImages();

    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        JLabel place = new JLabel(new ImageIcon(images[i][j]));
        dungeonView.add(place);
      }
    }

    outerArea.add(dungeonView);

    // add scroll controls
    JScrollPane dungeonScroll = new JScrollPane(outerArea,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    dungeonView.setMaximumSize(new Dimension(700, 700));
    outerArea.setMaximumSize(new Dimension(700, 900));
    dungeonScroll.setPreferredSize(new Dimension(700, 900));

    innerPane.add(dungeonScroll);
    innerPane.add(Box.createRigidArea(new Dimension(10, 0)));
  }

  private void setUpPlayerView() {

    // player view dimensions
    Dimension playerDim = new Dimension(260, 500);

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
    playerScroll.setMaximumSize(new Dimension(300, 800));
    innerPane.add(playerScroll);
  }

  private void setUpButtonView() {

    // button view dimensions dimensions
    Dimension buttonDim = new Dimension(MAX_WIDTH, 50);

    // add the buttons panel to the bottom
    JPanel buttonView = new JPanel();
    buttonView.setLayout(new BoxLayout(buttonView, BoxLayout.LINE_AXIS));
    buttonView.setPreferredSize(buttonDim);

    int margin = 10;

    helpButton = new JButton("How to Play");
    helpButton.setMargin(new Insets(margin, margin, margin, margin));
    helpButton.setActionCommand("Help Button");
    buttonView.add(helpButton);
    buttonView.add(Box.createRigidArea(new Dimension(30, 0)));

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

    JMenu configure = new JMenu("Menu");
    settingsMenu = new JMenuItem("Settings");
    resetMenu = new JMenuItem("Reset Dungeon");
    restartMenu = new JMenuItem("Restart Game");

    configure.add(settingsMenu);
    configure.add(resetMenu);
    configure.add(restartMenu);

    JMenu options = new JMenu("Options");
    helpMenu = new JMenuItem("Help");
    quitMenu = new JMenuItem("Quit");

    options.add(helpMenu);
    options.add(quitMenu);

    menuBar.add(configure);
    menuBar.add(options);

    setJMenuBar(menuBar);
  }

  private BufferedImage[][] getDungeonImages() {
    int row = model.getSize()[0];
    int col = model.getSize()[1];
    ReadOnlyCave[][] caves = model.getDungeon();

    int[] current = model.getPos();
    int[] shadowPos = model.getShadowPos();

    BufferedImage[][] images = new BufferedImage[row][col];
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        BufferedImage image;
        if (!caves[i][j].visited()) {
          image = imageMap.get("black");
        } else {
          Set<Direction> directionList = caves[i][j].getOpenings();
          StringBuilder stringBuilder = new StringBuilder();
          if (directionList.contains(NORTH)) {
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

          image = imageMap.get(stringBuilder.toString());

          // pit
          if (caves[i][j].hasPit()) {
            image = overlay(image, imageMap.get("pit"), 5, 5);
          }

          // exit cave
          if (model.getEnd()[0] == i && model.getEnd()[1] == j) {
            image = overlay(image, imageMap.get("door"), 5, 5);
          }

          // player's current location
          if (i == current[0] && j == current[1]) {
            image = overlay(image, imageMap.get("sam"), 12, 6);
            playerRow = i;
            playerCol = j;
          }

          // treasures
          List<Treasure> treasures = caves[i][j].getTreasure();
          if (treasures.contains(Treasure.DIAMOND)) {
            image = overlay(image, imageMap.get("diamond"), 15, 30);
          }
          if (treasures.contains(Treasure.RUBY)) {
            image = overlay(image, imageMap.get("ruby"), 25, 35);
          }
          if (treasures.contains(Treasure.EMERALD)) {
            image = overlay(image, imageMap.get("emerald"), 15, 40);
          }
          if (caves[i][j].getArrows() > 0) {
            image = overlay(image, imageMap.get("arrow"), 15, 55);
          }

          // check for monsters
          if (caves[i][j].hasOtyugh()) {
            image = overlay(image, imageMap.get("otyugh"), 10, 5);
          }
          if (shadowPos != null) {
            if (shadowPos[0] == i && shadowPos[1] == j) {
              image = overlay(image, imageMap.get("shadow"), 10, 5);
            }
          }
          if (model.printCurrentLocation().contains("strong stench") && current[0] == i
              && current[1] == j) {
            image = overlay(image, imageMap.get("strongStench"), 0, 0);
          } else if (model.printCurrentLocation().contains("faint stench") && current[0] == i
                     && current[1] == j) {
            image = overlay(image, imageMap.get("weakStench"), 0, 0);
          }
          if (model.metThief() && current[0] == i && current[1] == j) {
            image = overlay(image, imageMap.get("thief"), 30, 5);
          }

          // breeze
          if (model.hasBreeze() && current[0] == i && current[1] == j) {
            image = overlay(image, imageMap.get("breeze"), 0, 0);
          }

        }
        if (row < 8 || col < 8) {
          if (row < col) {
            images[i][j] = resize(image, 650 / row);
            cellSize = images[i][j].getWidth();
          } else {
            images[i][j] = resize(image, 650 / col);
          }
        } else {
          images[i][j] = image;
        }
        cellSize = images[i][j].getWidth();
      }
    }
    return images;
  }

  private BufferedImage overlay(BufferedImage starting, BufferedImage overlay, int offsetX,
      int offsetY) {
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, offsetX, offsetY, null);
    return combined;
  }

  private BufferedImage resize(BufferedImage starting, int scale) {
    BufferedImage newImage = new BufferedImage(scale, scale,
        BufferedImage.TYPE_INT_ARGB);
    Graphics g = newImage.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(starting, 0, 0, scale, scale, null);
    return newImage;
  }
}

