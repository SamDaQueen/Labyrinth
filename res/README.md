# Labyrinth Dungeon Model

#### Samreen Reyaz Ansari

##### Programming Design Paradigm

1. **About:** The Labyrinth Dungeon Model represents a maze game where we have a player navigating through the maze. There is a start and an exit to the maze. It is a user-driven program and the user can guide the player to the exit by choosing each step of the player. The player can also collect treasures along the path.

2. **List of features**:

   - A dungeon is a grid of caves and tunnels and the grid size is provided by the user. 
   - Each cave or tunnel is connected by all other caves and tunnels by at least one path.
   - For more than a single path, we can increase the degree of connectivity between the nodes. The degree of interconnectivity for our dungeon is provided by the user.
   - A tunnel has exactly two adjacent neighbors whereas a cave can have one, three or four neighbors.
   - A player starts at a cave in the dungeon, and may move in four possible directions, if they are connected to the adjacent caves/tunnels.
   - The minimum number of steps between the starting and ending point will always be at least 5.
   - Caves may contain treasures. The percentage of caves with treasures is given by the user. The player can pick the treasure at the current position.
   - The dungeon can by wrapping or non-wrapping as required by the user. Wrapping dungeons have some edges that connect the top-bottom and left-right of the dungeon.
   - At every node, the player status and details about the location can be printed.

3. **How To Run**: 

   - The Dungeon.jar file can be found in the /res directory.
   - To run it, type "java -jar Dungeon.jar <height> <width> <interconnectivity> <isWrapping> <percentage of caves with treasure>" on the terminal within the directory.  For example, "java -jar Dungeon.jar 5 5 3 true 25".
   
4. **How to Use the Program**: The program will select moves for the player and move it in the dungeon while collecting treasure. The DungeonInput (Driver2: could not be included in res due to size constraint) accepts user input for the moves. The moves can be given to the program as numbers 1.NORTH 2.EAST 3.SOUTH 4.WEST.

5. **Description of Example:**

   **Run -- run_allnodes.txt:**

   1. Introductory message
   2. Initialize the dungeon with the caves, their treasures, and the player
   3. Print the dungeon for move selection
   4. Print current status, player location, available treasure, and possible moves
   5. Take input from user for the next move
   6. Visit all the nodes in the dungeon
   7. Update status and reprint messages
   8. Stop when exit has been reached
   9. Display congratulatory message and status of player

   **Run -- run_nonwrapping.txt:**

   1. Introductory message
   2. Initialize the non-wrapping dungeon with the caves, their treasures, and the player
   3. Print current status, player location, available treasure, and possible moves
   4. Pick a random move from the available moves
   5. Update status and reprint messages
   6. Stop when exit has been reached
   7. Display congratulatory message and status of player
   8. Print the dungeon for verification

   **Run -- run_wrapping.txt:**

   1. Introductory message
   2. Initialize the wrapping dungeon with the caves, their treasures, and the player
   3. Print current status, player location, available treasure, and possible moves
   4. Pick a random move from the available moves
   5. Update status and reprint messages
   6. Stop when exit has been reached
   7. Display congratulatory message and status of player
   8. Print the dungeon for verification

6. **Design/Model Changes**: 

   - Added Edge class as a pair of two Caves for implementing the Kruskal's algorithm for maze generation
   - Moved constructors from interface to concrete class as suggested by instructor
   - Changed the representation of the dungeon from a list of list of Caves to a 2-D array of Caves.
   - Removed redundant isCave() method from the Cave class
   - Added Player to DungeonImpl class
   - Added helper methods in DungeonImpl for internal calculations
   - Added package-private methods for debugging and testing
   - Added score for player

7. **Assumptions:** 

   - Moving in a cave does not mean that the treasure in it will be picked
   - Picking treasure is a separate feature
   - User can pick treasure only from the current node
   - If the user wishes to, they are allowed to move the player even after the exit has reached.
   - A player can only move to adjacent nodes
   - A player can only move one node at a time
   - A player can only move to a neighbor if there is an edge from current node to the neighbor
   - All edges are bi-directional
   - A player can stop at at tunnel

8. **Limitations**: 

   - Player can only view neighboring nodes
   - Dungeon of size smaller than 4*4 cannot be created
   - Dungeons of large sizes might take a lot of time to generate 
   - It is possible for the program to never end if the user keeps getting stuck in a loop deliberately

9. **Citations:** 

   - *Array of arrays in Java*. TutorialKart. (2020, November 23) from https://www.tutorialkart.com/java/java-array/array-of-arrays-in-java/. 
   - *The Map Interface*. (The Java™ Tutorials > Collections > Interfaces) from https://docs.oracle.com/javase/tutorial/collections/interfaces/map.html. 
   - *Lesson: Interfaces*. (The Java™ Tutorials > Collections) from https://docs.oracle.com/javase/tutorial/collections/interfaces/index.html. 
   - *HashMap and treemap in Java*. GeeksforGeeks. (2018, December 11) from https://www.geeksforgeeks.org/hashmap-treemap-java/. 
   - Fejér, A. *Command-line arguments in Java*. Baeldung (2020, April 27) from https://www.baeldung.com/java-command-line-arguments. 
   - Hasija, A. (2021, September 10). *Dijsktra's algorithm*. GeeksforGeeks from https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/. 

