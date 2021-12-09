package dungeon.view;

import static dungeon.model.Direction.EAST;
import static dungeon.model.Direction.NORTH;
import static dungeon.model.Direction.SOUTH;
import static dungeon.model.Direction.WEST;

import dungeon.controller.Features;
import dungeon.model.CaveImpl;
import dungeon.model.Direction;
import dungeon.model.Dungeon;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class JFrameView extends JFrame implements IView {

  private final int MAX_WIDTH = 1400;
  private final int MAX_HEIGHT = 800;
  private final JPanel innerPane, outerPane;
  private final Map<String, BufferedImage> imageMap;
  ReadOnlyModel model;
  private JMenu menu;
  private JMenuItem settingsMenu, quitMenu;
  private JTextArea caveDesc, playerDesc;
  private JButton resetBtn, restartBtn, quitBtn, helpButton;
  private JPanel dungeonView;
  private boolean gameOver;
  private boolean shootFlag;
  private boolean shootDirectionFlag;
  private Direction shootDirection;
  private int playerRow;
  private int playerCol;
  private int cellSize;

  public JFrameView(ReadOnlyModel model) {
    super("Labyrinth: The Game");

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }

    this.model = model;

    // set size and location
    setSize(MAX_WIDTH, MAX_HEIGHT);
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

  private void setUpImageMap() {
    try {

      // directions
      imageMap.put("N", ImageIO.read(new File("icons/N.png")));
      imageMap.put("E", ImageIO.read(new File("icons/E.png")));
      imageMap.put("S", ImageIO.read(new File("icons/S.png")));
      imageMap.put("W", ImageIO.read(new File("icons/W.png")));
      imageMap.put("NE", ImageIO.read(new File("icons/NE.png")));
      imageMap.put("NS", ImageIO.read(new File("icons/NS.png")));
      imageMap.put("NW", ImageIO.read(new File("icons/NW.png")));
      imageMap.put("SE", ImageIO.read(new File("icons/SE.png")));
      imageMap.put("SW", ImageIO.read(new File("icons/SW.png")));
      imageMap.put("EW", ImageIO.read(new File("icons/EW.png")));
      imageMap.put("NSE", ImageIO.read(new File("icons/NSE.png")));
      imageMap.put("NEW", ImageIO.read(new File("icons/NEW.png")));
      imageMap.put("NSW", ImageIO.read(new File("icons/NSW.png")));
      imageMap.put("SEW", ImageIO.read(new File("icons/SEW.png")));
      imageMap.put("NSEW", ImageIO.read(new File("icons/NSEW.png")));

      // black image
      imageMap.put("black", ImageIO.read(new File("icons/black.png")));

      // arrow images
      imageMap.put("arrowB", ImageIO.read(new File("icons/arrow-black.png")));
      imageMap.put("arrowW", ImageIO.read(new File("icons/arrow-white_small.png")));

      // treasures
      int treasureSize = 10;
      imageMap.put("diamond",
          resize(ImageIO.read(new File("icons/diamond.png")), treasureSize));
      imageMap.put("emerald",
          resize(ImageIO.read(new File("icons/emerald.png")), treasureSize));
      imageMap.put("ruby",
          resize(ImageIO.read(new File("icons/ruby.png")), treasureSize));

      // monsters
      imageMap.put("otyugh", ImageIO.read(new File("icons/otyugh_small.png")));
      imageMap.put("shadow", ImageIO.read(new File("icons/shadow.png")));

      // stench
      imageMap.put("weakStench", ImageIO.read(new File("icons/stench01.png")));
      imageMap.put("strongStench", ImageIO.read(new File("icons/stench02.png")));

      // player
      imageMap.put("sam", ImageIO.read(new File("icons/sam.png")));

      // breeze
      imageMap.put("breeze", ImageIO.read(new File("icons/breeze.png")));

      // pit
      imageMap.put("pit", ImageIO.read(new File("icons/pit.png")));

      // thief
      imageMap.put("thief", ImageIO.read(new File("icons/thief.png")));


    } catch (
        IOException e) {
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
    caveScroll.setMaximumSize(new Dimension(300, 800));

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

    menu = new JMenu("Menu");

    settingsMenu = new JMenuItem("Settings");
    quitMenu = new JMenuItem("Quit Game");

    menu.add(settingsMenu);
    menu.add(quitMenu);

    menuBar.add(menu);
    setJMenuBar(menuBar);
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
    caveDesc.setText(model.printCurrentLocation());
    playerDesc.setText(model.printPlayerStatus());
    dungeonView.updateUI();
    repaint();
  }

  private BufferedImage[][] getDungeonImages() {
    int row = model.getSize()[0];
    int col = model.getSize()[1];
    CaveImpl[][] caves = model.getDungeon();

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
            image = overlay(image, imageMap.get("arrowW"), 15, 55);
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

  BufferedImage resize(BufferedImage starting, int scale) {
    BufferedImage newImage = new BufferedImage(scale, scale,
        BufferedImage.TYPE_INT_ARGB);
    Graphics g = newImage.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(starting, 0, 0, scale, scale, null);
    return newImage;
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
  public void showDialog(String message) {
    JOptionPane.showMessageDialog(this, message, "Shooting Result!",
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
  public void setModel(Dungeon model) {
    this.model = model;
  }

  @Override
  public void setFeatures(Features f) {
    quitBtn.addActionListener(l -> f.exitProgram());
    restartBtn.addActionListener(l -> f.restartGame(new int[]{5, 5}, 3, true, 25, 3));
    resetBtn.addActionListener(l -> f.resetGame());
    quitMenu.addActionListener(l -> f.exitProgram());
    helpButton.addActionListener(l -> f.showHelp());
    settingsMenu.addActionListener(l -> f.setUpSettings());

    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
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
          } else if (e.getKeyChar() == 'p') {
            f.pick();
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
              System.out.println("shoot down");
              shootDirectionFlag = true;
              shootDirection = SOUTH;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
              System.out.println("shoot left");
              shootDirectionFlag = true;
              shootDirection = WEST;
            }
          }
          if (shootFlag && shootDirectionFlag) {
            if (e.getKeyCode() == KeyEvent.VK_1 || e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
              shootFlag = false;
              shootDirectionFlag = false;
              f.shoot(shootDirection, 1);
            } else if (e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
              shootFlag = false;
              shootDirectionFlag = false;
              f.shoot(shootDirection, 2);
            } else if (e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
              shootFlag = false;
              shootDirectionFlag = false;
              f.shoot(shootDirection, 1);
            } else if (e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
              shootFlag = false;
              shootDirectionFlag = false;
              f.shoot(shootDirection, 1);
            } else if (e.getKeyCode() == KeyEvent.VK_5 || e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
              shootFlag = false;
              shootDirectionFlag = false;
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
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader("res/help.txt"));
      String line;
      StringBuilder stringBuilder = new StringBuilder();
      while ((line = bufferedReader.readLine()) != null) {
        stringBuilder.append(line);
      }
      JPanel panel = new JPanel();
      JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
          JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      JOptionPane.showMessageDialog(scrollPane, stringBuilder, "How To Play",
          JOptionPane.PLAIN_MESSAGE);
    } catch (FileNotFoundException fne) {
      System.out.println("File not found!: " + fne);
    } catch (IOException ioe) {
      System.out.println("Could not read file!: " + ioe);
    }

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

    } else {
      System.out.println("canceled");
    }
  }
}

