import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dungeon.control.CommandController;
import dungeon.model.Dungeon;
import dungeon.model.DungeonImpl;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Class for testing the Controller and the Model together.
 */
public class ControllerTest {

  Dungeon model;
  Appendable out;
  Readable in;

  @Before
  public void setUp() {
    in = new StringReader("");
    out = new StringBuilder();
    model = new DungeonImpl();
  }

  @Test
  public void checkModel() {
    assertEquals("\n                   \n"
                 + "   M   P---O---O   \n"
                 + "   |           |   \n"
                 + "   M---T---M---O   \n"
                 + "   |       |       \n"
                 + "   O   O---H---O   \n"
                 + "   |       |       \n"
                 + "   O   M---O---X   \n"
                 + "                   \n", model.toString());
  }

//  @Test
//  public void controllerCompleteRun() {
//    in = new StringReader("move east pick move east shoot south 1 move south pick shoot west 1"
//                          + " move west shoot west 1 move west shoot west 1 move west shoot north 1"
//                          + " shoot north 1 move north move south move south pick move south"
//                          + " move north move north move east move east move south pick move west"
//                          + " move east move south pick shoot east 1 shoot east 1 move east");
//    new CommandController(in).execute(model, out);
//    assertEquals("\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [0,1]: Cave Available directions: [EAST] Treasures:"
//                 + " [] Arrows: 0 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You are brave and attempt to advance in the dungeon... Moved to "
//                 + "direction EAST\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [0,2]: Tunnel Available directions: [EAST, WEST] "
//                 + "Arrows: 1 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: This will add a fine addition to your collection... Added to "
//                 + "inventory :D \n"
//                 + "\n"
//                 + "Player has collected no treasures and has 4 arrows.\n"
//                 + "Player is at location: [0,2]: Tunnel Available directions: [EAST, WEST] "
//                 + "Arrows: 0 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]"
//                 + "/ quit: You are brave and attempt to advance in the dungeon... Moved to "
//                 + "direction EAST\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 4 arrows.\n"
//                 + "Player is at location: [0,3]: Tunnel Available directions: [SOUTH, WEST] "
//                 + "Arrows: 0 \n"
//                 + "There is a faint stench... An Otyugh must be close!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]"
//                 + "/ quit: You have attempted to slay an Otyugh... Your arrow hit the Otyugh "
//                 + "and you hear a piercing howl!\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [0,3]: Tunnel Available directions: [SOUTH, WEST] "
//                 + "Arrows: 0 \n"
//                 + "There is a faint stench... An Otyugh must be close!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You are brave and attempt to advance in the dungeon... Moved to "
//                 + "direction SOUTH\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [1,3]: Tunnel Available directions: [NORTH, WEST] "
//                 + "Arrows: 2 \n"
//                 + "There is a strong stench... An Otyugh must be in the next cell!! Or perhaps "
//                 + "there are more than one Otyughs nearby hungry for your flesh!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: This will add a fine addition to your collection... Added to"
//                 + " inventory :D \n"
//                 + "\n"
//                 + "Player has collected no treasures and has 5 arrows.\n"
//                 + "Player is at location: [1,3]: Tunnel Available directions: [NORTH, WEST]"
//                 + " Arrows: 0 \n"
//                 + "There is a strong stench... An Otyugh must be in the next cell!! Or perhaps"
//                 + " there are more than one Otyughs nearby hungry for your flesh!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] "
//                 + "[distance]/ quit: You have attempted to slay an Otyugh... Your have "
//                 + "successfully hit the Otyugh twice and it is now dead!\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 4 arrows.\n"
//                 + "Player is at location: [1,3]: Tunnel Available directions: [NORTH,"
//                 + " WEST] Arrows: 0 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] "
//                 + "[distance]/ quit: You are brave and attempt to advance in the dungeon... "
//                 + "Moved to direction WEST\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 4 arrows.\n"
//                 + "Player is at location: [1,2]: Cave Available directions: [EAST, SOUTH, WEST]"
//                 + " Treasures: [] Arrows: 0 \n"
//                 + "There is a faint stench... An Otyugh must be close!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You have attempted to slay an Otyugh... Your arrow hit the Otyugh and "
//                 + "you hear a piercing howl!\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [1,2]: Cave Available directions: [EAST, SOUTH, WEST]"
//                 + " Treasures: [] Arrows: 0 \n"
//                 + "There is a faint stench... An Otyugh must be close!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You are brave and attempt to advance in the dungeon... Moved to "
//                 + "direction WEST\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [1,1]: Tunnel Available directions: [EAST, WEST]"
//                 + " Arrows: 0 \n"
//                 + "There is a strong stench... An Otyugh must be in the next cell!! Or perhaps"
//                 + " there are more than one Otyughs nearby hungry for your flesh!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You have attempted to slay an Otyugh... Your have successfully hit the"
//                 + " Otyugh twice and it is now dead!\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 2 arrows.\n"
//                 + "Player is at location: [1,1]: Tunnel Available directions: [EAST, WEST] "
//                 + "Arrows: 0 \n"
//                 + "There is a faint stench... An Otyugh must be close!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You are brave and attempt to advance in the dungeon... "
//                 + "Moved to direction WEST\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 2 arrows.\n"
//                 + "Player is at location: [1,0]: Cave Available directions: [NORTH, EAST, SOUTH]"
//                 + " Treasures: [] Arrows: 0 \n"
//                 + "There is a strong stench... An Otyugh must be in the next cell!! Or perhaps"
//                 + " there are more than one Otyughs nearby hungry for your flesh!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You have attempted to slay an Otyugh... Your arrow hit the Otyugh"
//                 + " and you hear a piercing howl!\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 1 arrows.\n"
//                 + "Player is at location: [1,0]: Cave Available directions: [NORTH, EAST, SOUTH]"
//                 + " Treasures: [] Arrows: 0 \n"
//                 + "There is a strong stench... An Otyugh must be in the next cell!! Or perhaps"
//                 + " there are more than one Otyughs nearby hungry for your flesh!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You have attempted to slay an Otyugh... Your have successfully "
//                 + "hit the Otyugh twice and it is now dead!\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 0 arrows.\n"
//                 + "Player is at location: [1,0]: Cave Available directions: [NORTH, EAST, SOUTH]"
//                 + " Treasures: [] Arrows: 0 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You are brave and attempt to advance in the dungeon... "
//                 + "Moved to direction NORTH\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 0 arrows.\n"
//                 + "Player is at location: [0,0]: Cave Available directions: [SOUTH] Treasures:"
//                 + " [] Arrows: 2 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You are brave and attempt to advance in the dungeon... "
//                 + "Moved to direction SOUTH\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 0 arrows.\n"
//                 + "Player is at location: [1,0]: Cave Available directions: [NORTH, EAST, SOUTH]"
//                 + " Treasures: [] Arrows: 0 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] "
//                 + "[distance]/ quit: You are brave and attempt to advance in the dungeon..."
//                 + " Moved to direction SOUTH\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 0 arrows.\n"
//                 + "Player is at location: [2,0]: Tunnel Available directions: [NORTH, SOUTH]"
//                 + " Arrows: 3 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: This will add a fine addition to your collection... "
//                 + "Added to inventory :D \n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [2,0]: Tunnel Available directions: [NORTH, SOUTH]"
//                 + " Arrows: 0 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You are brave and attempt to advance in the dungeon... "
//                 + "Moved to direction SOUTH\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [3,0]: Cave Available directions: [NORTH] Treasures:"
//                 + " [] Arrows: 0 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/ "
//                 + "quit: You are brave and attempt to advance in the dungeon... "
//                 + "Moved to direction NORTH\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [2,0]: Tunnel Available directions: [NORTH, "
//                 + "SOUTH] Arrows: 0 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] "
//                 + "[distance]/ quit: You are brave and attempt to advance in the dungeon... "
//                 + "Moved to direction NORTH\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [1,0]: Cave Available directions: [NORTH, EAST, "
//                 + "SOUTH] Treasures: [] Arrows: 0 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] "
//                 + "[distance]/ quit: You are brave and attempt to advance in the dungeon... "
//                 + "Moved to direction EAST\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [1,1]: Tunnel Available directions: [EAST, WEST] "
//                 + "Arrows: 0 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/ "
//                 + "quit: You are brave and attempt to advance in the dungeon..."
//                 + " Moved to direction EAST\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [1,2]: Cave Available directions: [EAST, SOUTH, WEST]"
//                 + " Treasures: [] Arrows: 0 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You are brave and attempt to advance in the dungeon... "
//                 + "Moved to direction SOUTH\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [2,2]: Cave Available directions: [NORTH, EAST, "
//                 + "SOUTH, WEST] Treasures: [RUBY, SAPPHIRE, DIAMOND] Arrows: 0 \n"
//                 + "There is a strong stench... An Otyugh must be in the next cell!! "
//                 + "Or perhaps there are more than one Otyughs nearby hungry for your flesh!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/ "
//                 + "quit: This will add a fine addition to your collection... "
//                 + "Added to inventory :D \n"
//                 + "\n"
//                 + "Player has collected DIAMOND(1), RUBY(1), SAPPHIRE(1), with a score of "
//                 + "60 and has 3 arrows.\n"
//                 + "Player is at location: [2,2]: Cave Available directions: [NORTH, EAST, "
//                 + "SOUTH, WEST] Treasures: [] Arrows: 0 \n"
//                 + "There is a strong stench... An Otyugh must be in the next cell!! "
//                 + "Or perhaps there are more than one Otyughs nearby hungry for your flesh!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You are brave and attempt to advance in the dungeon... "
//                 + "Moved to direction WEST\n"
//                 + "\n"
//                 + "Player has collected DIAMOND(1), RUBY(1), SAPPHIRE(1), with a score of 60"
//                 + " and has 3 arrows.\n"
//                 + "Player is at location: [2,1]: Cave Available directions: [EAST] Treasures:"
//                 + " [] Arrows: 0 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/ "
//                 + "quit: You are brave and attempt to advance in the dungeon... "
//                 + "Moved to direction EAST\n"
//                 + "\n"
//                 + "Player has collected DIAMOND(1), RUBY(1), SAPPHIRE(1), with a score of 60 "
//                 + "and has 3 arrows.\n"
//                 + "Player is at location: [2,2]: Cave Available directions: [NORTH, EAST, "
//                 + "SOUTH, WEST] Treasures: [] Arrows: 0 \n"
//                 + "There is a strong stench... An Otyugh must be in the next cell!! "
//                 + "Or perhaps there are more than one Otyughs nearby hungry for your flesh!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You are brave and attempt to advance in the dungeon... "
//                 + "Moved to direction SOUTH\n"
//                 + "\n"
//                 + "Player has collected DIAMOND(1), RUBY(1), SAPPHIRE(1), with a score of 60 "
//                 + "and has 3 arrows.\n"
//                 + "Player is at location: [3,2]: Cave Available directions: [NORTH, EAST, WEST]"
//                 + " Treasures: [DIAMOND, DIAMOND, RUBY, RUBY] Arrows: 3 \n"
//                 + "There is a strong stench... An Otyugh must be in the next cell!!"
//                 + " Or perhaps there are more than one Otyughs nearby hungry for your flesh!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: This will add a fine addition to your collection... "
//                 + "Added to inventory :D \n"
//                 + "\n"
//                 + "Player has collected DIAMOND(3), RUBY(3), SAPPHIRE(1), with a score of 120 and"
//                 + " has 6 arrows.\n"
//                 + "Player is at location: [3,2]: Cave Available directions: [NORTH, EAST, WEST]"
//                 + " Treasures: [] Arrows: 0 \n"
//                 + "There is a strong stench... An Otyugh must be in the next cell!! Or perhaps "
//                 + "there are more than one Otyughs nearby hungry for your flesh!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You have attempted to slay an Otyugh... Your arrow hit the Otyugh and"
//                 + " you hear a piercing howl!\n"
//                 + "\n"
//                 + "Player has collected DIAMOND(3), RUBY(3), SAPPHIRE(1), with a score of 120 and"
//                 + " has 5 arrows.\n"
//                 + "Player is at location: [3,2]: Cave Available directions: [NORTH, EAST, WEST]"
//                 + " Treasures: [] Arrows: 0 \n"
//                 + "There is a strong stench... An Otyugh must be in the next cell!! Or perhaps"
//                 + " there are more than one Otyughs nearby hungry for your flesh!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You have attempted to slay an Otyugh... Your have successfully hit the "
//                 + "Otyugh twice and it is now dead!\n"
//                 + "\n"
//                 + "Player has collected DIAMOND(3), RUBY(3), SAPPHIRE(1), with a score of 120 "
//                 + "and has 4 arrows.\n"
//                 + "Player is at location: [3,2]: Cave Available directions: [NORTH, EAST, WEST]"
//                 + " Treasures: [] Arrows: 0 \n"
//                 + "There is a strong stench... An Otyugh must be in the next cell!! Or perhaps"
//                 + " there are more than one Otyughs nearby hungry for your flesh!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: You are brave and attempt to advance in the dungeon..."
//                 + " Moved to direction EAST\n"
//                 + "\n"
//                 + "Hurray! You have found the exit of the dungeon and your status is: Player"
//                 + " has collected DIAMOND(3), RUBY(3), SAPPHIRE(1), with a score of 120 and has"
//                 + " 4 arrows.\n",
//        out.toString());
//
//  }
//
//  @Test
//  public void controllerKilledByOtyugh() {
//    in = new StringReader("move east pick move east shoot south 1 move south pick shoot west 1"
//                          + " move west shoot west 1 move west shoot west 1 move west move north");
//    new CommandController(in).execute(model, out);
//    assertEquals("\n\n"
//                 + "Player has collected no treasures and has 3 arrows.\nPlayer is at"
//                 + " location: [0,1]: Cave Available directions: [EAST] Treasures: [] Arrows:"
//                 + " 0 \nWhat do you wish to do? move [direction]/ pick/ shoot [direction] "
//                 + "[distance]/ quit: You are brave and attempt to advance in the dungeon..."
//                 + " Moved to direction EAST\n\nPlayer has collected no treasures and has "
//                 + "3 arrows.\nPlayer is at location: [0,2]: Tunnel Available directions: [EAST,"
//                 + " WEST] Arrows: 1 \nWhat do you wish to do? move [direction]/ pick/ shoot"
//                 + " [direction] [distance]/ quit: This will add a fine addition to your"
//                 + " collection... Added to inventory :D \n\nPlayer has collected with a "
//                 + "score of 0 and has 4 arrows.\nPlayer is at location: [0,2]: Tunnel"
//                 + " Available directions: [EAST, WEST] Arrows: 0 \nWhat do you wish to do?"
//                 + " move [direction]/ pick/ shoot [direction] [distance]/ quit: You are brave"
//                 + " and attempt to advance in the dungeon... Moved to direction EAST\n\nPlayer"
//                 + " has collected no treasures and has 4 arrows.\nPlayer is at location: "
//                 + "[0,3]: Tunnel Available directions: [SOUTH, WEST] Arrows: 0 \nThere is a "
//                 + "faint stench... An Otyugh must be close!!\nWhat do you wish to do? move "
//                 + "[direction]/ pick/ shoot [direction] [distance]/ quit: You have attempted "
//                 + "to slay an Otyugh... Your arrow hit the Otyugh and you hear a piercing "
//                 + "howl!\n\nPlayer has collected no treasures and has 3 arrows.\nPlayer"
//                 + " is at location: [0,3]: Tunnel Available directions: [SOUTH, WEST] Arrows: "
//                 + "0 \nThere is a faint stench... An Otyugh must be close!!\nWhat do you wish "
//                 + "to do? move [direction]/ pick/ shoot [direction] [distance]/ quit: You are "
//                 + "brave and attempt to advance in the dungeon... Moved to direction "
//                 + "SOUTH\n\nPlayer has collected no treasures and has 3 arrows.\nPlayer"
//                 + " is at location: [1,3]: Tunnel Available directions: [NORTH, WEST] Arrows:"
//                 + " 2 \nThere is a strong stench... An Otyugh must be in the next cell!! Or"
//                 + " perhaps there are more than one Otyughs nearby hungry for your flesh!!"
//                 + "\nWhat do you wish to do? move [direction]/ pick/ shoot [direction] "
//                 + "[distance]/ quit: This will add a fine addition to your collection... "
//                 + "Added to inventory :D \n\nPlayer has collected no treasures and has"
//                 + " 5 arrows.\nPlayer is at location: [1,3]: Tunnel Available directions: "
//                 + "[NORTH, WEST] Arrows: 0 \nThere is a strong stench... An Otyugh must be "
//                 + "in the next cell!! Or perhaps there are more than one Otyughs nearby "
//                 + "hungry for your flesh!!\nWhat do you wish to do? move [direction]/ "
//                 + "pick/ shoot [direction] [distance]/ quit: You have attempted to slay an "
//                 + "Otyugh... Your have successfully hit the Otyugh twice and it is now "
//                 + "dead!\n\nPlayer has collected no treasures and has 4 arrows.\nPlayer"
//                 + " is at location: [1,3]: Tunnel Available directions: [NORTH, WEST] Arrows:"
//                 + " 0 \nWhat do you wish to do? move [direction]/ pick/ shoot [direction] "
//                 + "[distance]/ quit: You are brave and attempt to advance in the dungeon... "
//                 + "Moved to direction WEST\n\nPlayer has collected no treasures and has "
//                 + "4 arrows.\nPlayer is at location: [1,2]: Cave Available directions: [EAST,"
//                 + " SOUTH, WEST] Treasures: [] Arrows: 0 \nThere is a faint stench... "
//                 + "An Otyugh must be close!!\nWhat do you wish to do? move [direction]/ "
//                 + "pick/ shoot [direction] [distance]/ quit: You have attempted to slay an "
//                 + "Otyugh... Your arrow hit the Otyugh and you hear a piercing howl!\n\nPlayer"
//                 + " has collected no treasures and has 3 arrows.\nPlayer is at location:"
//                 + " [1,2]: Cave Available directions: [EAST, SOUTH, WEST] Treasures: [] "
//                 + "Arrows: 0 \nThere is a faint stench... An Otyugh must be close!!\nWhat "
//                 + "do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/ "
//                 + "quit: You are brave and attempt to advance in the dungeon... Moved to "
//                 + "direction WEST\n\nPlayer has collected no treasures and has 3 arrows."
//                 + "\nPlayer is at location: [1,1]: Tunnel Available directions: [EAST, WEST]"
//                 + " Arrows: 0 \nThere is a strong stench... An Otyugh must be in the next cell!!"
//                 + " Or perhaps there are more than one Otyughs nearby hungry for your flesh!!"
//                 + "\nWhat do you wish to do? move [direction]/ pick/ shoot [direction] "
//                 + "[distance]/ quit: You have attempted to slay an Otyugh... Your have "
//                 + "successfully hit the Otyugh twice and it is now dead!\n\nPlayer has "
//                 + "collected no treasures and has 2 arrows.\nPlayer is at location:"
//                 + " [1,1]: Tunnel Available directions: [EAST, WEST] Arrows: 0 \nThere is"
//                 + " a faint stench... An Otyugh must be close!!\nWhat do you wish to do? "
//                 + "move [direction]/ pick/ shoot [direction] [distance]/ quit: You are brave"
//                 + " and attempt to advance in the dungeon... Moved to direction WEST\n\nPlayer"
//                 + " has collected no treasures and has 2 arrows.\nPlayer is at location:"
//                 + " [1,0]: Cave Available directions: [NORTH, EAST, SOUTH] Treasures: [] "
//                 + "Arrows: 0 \nThere is a strong stench... An Otyugh must be in the next cell!!"
//                 + " Or perhaps there are more than one Otyughs nearby hungry for your flesh!!"
//                 + "\nWhat do you wish to do? move [direction]/ pick/ shoot [direction] "
//                 + "[distance]/ quit: You are brave and attempt to advance in the dungeon..."
//                 + " Moved to direction NORTH\n\nSadly, you were devoured by the hungry Otyugh!!"
//                 + " Your adventure ends :( Player has collected no treasures and has "
//                 + "2 arrows.\n",
//        out.toString());
//  }

  @Test
  public void noSmell() {
    in = new StringReader("move east");
    new CommandController(in).execute(model, out);
    assertFalse(out.toString().contains("There is a faint stench... An Otyugh must be close!!"));
    assertFalse(out.toString().contains(
        "There is a strong stench... An Otyugh must be in the next cell!! Or perhaps "
        + "there are more than"
        + " one Otyughs nearby hungry for your flesh!!\n"));
  }

  @Test
  public void faintSmell() {
    in = new StringReader("move east move east");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("There is a faint stench... An Otyugh must be close!!"));
  }

  @Test
  public void strongSmell() {
    in = new StringReader("move east move east move south");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains(
        "There is a strong stench... An Otyugh must be in the next cell!! Or perhaps "
        + "there are more than"
        + " one Otyughs nearby hungry for your flesh!!\n"));
  }

  @Test
  public void strongSmellTwoOtyughs() {
    in = new StringReader(
        "move east pick move east shoot south 1 shoot south 1 move south move west move south");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains(
        "There is a strong stench... An Otyugh must be in the next cell!! Or perhaps "
        + "there are more than"
        + " one Otyughs nearby hungry for your flesh!!\n"));
  }

  @Test
  public void pick() {
    in = new StringReader("move east pick");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Added to inventory :D"));
    assertTrue(out.toString().contains("Player has collected no treasures and has 4 arrows."));
  }

  @Test
  public void nothingToPick() {
    in = new StringReader("pick");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Oh no!! Nothing to pick here!"));
  }

  @Test
  public void initialArrows() {
    in = new StringReader("q");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Player has collected no treasures and has 3 arrows."));
  }

  @Test
  public void wasteArrow() {
    in = new StringReader("shoot east 3");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Your arrow could not reach the Otyugh. What a waste!"));
  }

  @Test
  public void noMoreArrows() {
    in = new StringReader("shoot east 3 shoot east 3 shoot east 3 shoot east 3");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString()
        .contains("No arrows to shoot!! Should we throw you at the Otyugh instead??"));
  }

  @Test
  public void crookedArrow() {
    // travels through 3 tunnels and reaches 1 cave where it hits the Otyugh
    in = new StringReader("shoot east 1");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Your arrow hit the Otyugh and you hear a piercing howl!"));
  }

  @Test
  public void crookedArrow2() {
    // travels through 3 tunnels, 1 cave, and 1 tunnel to each 1 cave where it hits the Otyugh
    in = new StringReader("shoot east 2");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Your arrow hit the Otyugh and you hear a piercing howl!"));
  }

  @Test
  public void crookedArrow3() {
    // travels through 3 tunnels, 1 cave, and 1 tunnel and then stops at cave without hitting
    in = new StringReader("shoot east 3");
    new CommandController(in).execute(model, out);
    assertFalse(out.toString().contains("Your arrow hit the Otyugh and you hear a piercing howl!"));
  }

  @Test
  public void killOtyugh() {
    in = new StringReader("shoot east 1 move east shoot east 1");
    new CommandController(in).execute(model, out);
    assertTrue(
        out.toString().contains("Your have successfully hit the Otyugh twice and it is now dead!"));
  }

  @Test
  public void killPlayer() {
    in = new StringReader("move east move east move south move west");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString()
        .contains("Sadly, you were devoured by the hungry Otyugh!! Your adventure ends :("));
  }

  @Test
  public void halfInjuredOtyugh() {
    in = new StringReader("move east move east move south shoot west 1 move west move west");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains(
        "Sadly, you were devoured by the hungry Otyugh!! Your adventure ends :(")
               || out.toString().contains(
        "Player is at location: [1,2]: Cave Available directions: [EAST, SOUTH, WEST] "
        + "Treasures: [] Arrows: 0"));
  }

  @Test
  public void invalidCommandMove() {
    in = new StringReader("mive east");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Unknown command!"));
  }

  @Test
  public void invalidMove() {
    in = new StringReader("move west");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains(
        "This move is not possible! Please choose from available moves only!"));

    in = new StringReader("move north");
    out = new StringBuilder();
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains(
        "This move is not possible! Please choose from available moves only!"));

    in = new StringReader("move south");
    out = new StringBuilder();
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains(
        "This move is not possible! Please choose from available moves only!"));
  }


  @Test
  public void invalidCommandPick() {
    in = new StringReader("pack");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Unknown command!"));
  }


  @Test
  public void invalidCommandShoot() {
    in = new StringReader("sheet");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Unknown command!"));
  }

  @Test
  public void invalidShoot() {
    in = new StringReader("shoot x y");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Invalid input!"));
  }

  @Test
  public void invalidShoot2() {
    in = new StringReader("shoot west y");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains("Invalid input!"));
  }

  @Test
  public void invalidDistance() {
    in = new StringReader("shoot west 10");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString()
        .contains("You aren't Artemis here!! Please give a shooting distance between 1 and 5"));
  }

  @Test
  public void zeroDistance() {
    in = new StringReader("shoot west 0");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString()
        .contains("You aren't Artemis here!! Please give a shooting distance between 1 and 5"));
  }

  @Test
  public void negativeDistance() {
    in = new StringReader("shoot west -10");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString()
        .contains("You aren't Artemis here!! Please give a shooting distance between 1 and 5"));
  }

  @Test
  public void noOtyughAtStart() {
    in = new StringReader("");
    new CommandController(in).execute(model, out);
    assertFalse(model.playerDead());
  }

  @Test
  public void win() {
    in = new StringReader("move east pick move east shoot south 1 move south pick shoot west 1"
                          + " move west shoot west 1 move west shoot west 1 move west shoot north 1"
                          + " shoot north 1 move north move south move south pick move south"
                          + " move north move north move east move east move south pick move west"
                          + " move east move south pick shoot east 1 shoot east 1 move east");
    new CommandController(in).execute(model, out);
    assertTrue(model.hasReachedGoal());
    assertFalse(model.playerDead());
  }

  @Test
  public void beforeWin() {
    in = new StringReader("move east pick move east shoot south 1 move south pick shoot west 1"
                          + " move west shoot west 1 move west shoot west 1 move west shoot north 1"
                          + " shoot north 1 move north move south move south pick move south"
                          + " move north move north move east move east move south pick"
                          + " move east move south pick move west shoot east 1 shoot east 2"
                          + " shoot east 2 move east");
    new CommandController(in).execute(model, out);
    assertFalse(model.hasReachedGoal());
    assertFalse(model.playerDead());
  }

  @Test
  public void lose() {
    in = new StringReader("move east pick move east shoot south 1 move south pick shoot west 1"
                          + " move west shoot west 1 move west shoot west 1 move west move north");
    new CommandController(in).execute(model, out);
    assertTrue(model.playerDead());
    assertFalse(model.hasReachedGoal());
  }

//  @Test
//  public void extraCommands() {
//    // after player is dead, remaining commands are ignored
//    in = new StringReader("move east move east move south move west pick move north");
//    new CommandController(in).execute(model, out);
//    assertEquals("\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [0,1]: Cave Available directions: [EAST] Treasures: []"
//                 + " Arrows: 0 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: "
//                 + "You are brave and attempt to advance in the dungeon... Moved to direction "
//                 + "EAST\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [0,2]: Tunnel Available directions: [EAST, WEST] "
//                 + "Arrows: 1 \n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: "
//                 + "You are brave and attempt to advance in the dungeon... Moved to direction "
//                 + "EAST\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [0,3]: Tunnel Available directions: [SOUTH, WEST] "
//                 + "Arrows: 0 \n"
//                 + "There is a faint stench... An Otyugh must be close!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: "
//                 + "You are brave and attempt to advance in the dungeon... Moved to direction"
//                 + " SOUTH\n"
//                 + "\n"
//                 + "Player has collected no treasures and has 3 arrows.\n"
//                 + "Player is at location: [1,3]: Tunnel Available directions: [NORTH, WEST] "
//                 + "Arrows: 2 \n"
//                 + "There is a strong stench... An Otyugh must be in the next cell!! Or perhaps"
//                 + " there are more than one Otyughs nearby hungry for your flesh!!\n"
//                 + "What do you wish to do? move [direction]/ pick/ shoot [direction] [distance]/"
//                 + " quit: "
//                 + "You are brave and attempt to advance in the dungeon... Moved to direction "
//                 + "WEST\n"
//                 + "\n"
//                 + "Sadly, you were devoured by the hungry Otyugh!! Your adventure ends :( "
//                 + "\nPlayer has "
//                 + "collected no treasures and has 3 arrows.\n", out.toString());
//  }

  @Test
  public void invalidCommandsInBetween() {
    in = new StringReader(
        "move east pick move east pick move north shoot max south 1 move south pick shoot west 1"
        + " move west shoot west 1 move west shoot west 1 move west move north");
    new CommandController(in).execute(model, out);
    assertTrue(out.toString().contains(
        "This move is not possible! Please choose from available moves only!"));
    assertTrue(out.toString().contains("Unknown command!"));
    assertTrue(out.toString().contains("Oh no!! Nothing to pick here!"));
  }

}
