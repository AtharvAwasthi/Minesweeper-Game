=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: 59538827
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays - I use 2D arrays of integers in Minesweeper.java, my Non-GUI model
                 of Minesweeper, to represent the true 10 x 10 gameboard and the
                 the 10 x 10 gameboard which the user sees. Mines are represented by
                 -1, flagged cells are represented by -2, unrevealed cells (in the
                 userBoard) are represented by -3, and numbered cells are represented
                 by their respective numbers. The user interacts with the userBoard
                 array which then derives values from the trueBoard array. 2D arrays 
                 are ideal for representing a grid-like board since they represent a 
                 2D matrix of elements, which is what a grid essentially is.


  2. Recursion – I used a BFS recursive algorithm in Minesweeper.java in the helper 
                 method revealEmptySquares, which is called when an empty (0) cell is 
                 revealed by the player. This method then recursively reveals the
                 empty cells around that empty cell until it has revealed a group of
                 empty cells bordered by numbered cells. 

                 For each of the 8 cells around the empty cell that revealEmptySquares
                 is called on, the base case is that the cell is not empty and so
                 its contents are revealed. In the recursive case, the cell is empty,
                 so its contents are revealed and then revealEmptySquares is called on
                 it. We maintain a 2D boolean array which marks which cells we have
                 already traversed so that we don't repeatedly revisit a cell and generate
                 a stackOverflow. This BFS algorithm thus starts at the original empty cell
                 it is called on (the tree root), and it searches the surrounding cells
                 like a tree.

                 Recursion is ideal for this process since we are handling a problem which
                 can be broken down into a group of identical sub-problems.


  3. JUnit Testable Component – Minesweeper.java creates a model of the Minesweeper game
                                completely independent of the view and controller. We
                                can play Minesweeper from start to finish without drawing
                                anything and without utilizing Java Swing. As previously
                                explained, we do this by representing the gameboard and
                                the player's interaction with the gameboard using arrays
                                and methods independent of the GUI. We also have JUnit
                                tests in MinesweeperTests.java that test the functionality
                                of the model. This feature is important because it enables
                                us to assess the logic implemented in the game indenpedent of
                                its low-level and graphical components. 


  4. File I/O – I enabled persistent state between runs of my game by storing the game state
                to a file named persistent.txt every time a method alters the userBoard or
                trueBoard. persistent.txt contains the number of flags left to be placed,
                whether or not the game is over, whether or not the player has won, and
                comma-separated values representing the elements in the userBoard and
                trueBoard arrays. The method saveState() saves the current state of
                the game to the persistent.txt, and the method resume() sets the game
                state to the state the player left the game in by reading from persistent.txt.


===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

Minesweeper.java: This class is a Non-GUI, JUnit Testable model for the Minesweeper
                  game which contains all the logic for the game. We model the true
                  game board and the user's perspective of the game board with 2D
                  arrays userBoard and trueBoard. The key methods in this class include
                  revealSquare(), flagSquare(), and unflagSquare(), which represent the
                  the three main actions a player can do when playing a game of Minesweeper.
                  It also contains methods like gameOutcome() that determines whether the
                  player has won, lost, or is still playing and methods like reset() which
                  set/reset the userBoard and trueBoard to a new/pre-loaded setting. A key
                  feature is that the trueBoard in a new game is actually set after the
                  first revealSquare() through a helper function named firstRevealProcedure().
                  This is because we do not want the any square in the 1-square radius of
                  the first-revealed square to contain a mine. The method saveState() is
                  called at the end of most other methods in order to save the game state to
                  persistent.txt.

GameLayout.java: This class instantiates a Minesweeper object, which is the model for the game.
                 It paints the game board as a 10 x 10 grid, and as the user clicks the game board, 
                 the model is updated. Whenever the model is updated, the game board repaints itself 
                 and updates its status JLabel which contains the number of flags remaining to reflect
                 the current state of the model. The MouseListener calls Minesweeper.revealSquare() when
                 it detects a left click and calls Minesweeper.flagSquare() or Minesweeper.unflagSquare()
                 when it detects a right click. The KeyListener calls the flipShowInstructions() method
                 when it detects that SPACE is pressed, which toggles the visibility of the instructions
                 on the screen. The paintComponent() method represents the userBoard of the Minesweeper
                 object, painting unrevealed squares as black squares, flagged squares as red flags, empty
                 squares as empty white squares, numbered squares as white squares with big numbers, and
                 mines as cartoon bombs. It draws the instructions in front of everything when the
                 showInstructions boolean is true. This can be toggled with the SPACE bar.
                 

RunMinesweeper.java: This class sets up the top-level frame and widgets for the GUI. In a Model-View-Controller
                     framework, Game initializes the view, implements a bit of controller functionality through
                     the reset button, and then instantiates a GameLayout. The GameLayout will handle the rest
                     of the game's view and controller functionality, and it will instantiate a Minesweeper
                     object to serve as the game's model. This format is quite similar to that of the 
                     RunTicTacToe class.

MinesweeperTests.java: This class contains tests for Minesweeper.java. These tests operate completely independent
                       of the GUI.

Game.java: This contains the main method which starts and runs the game. It instantiates and runs the RunMinesweeper
           Runnable game class.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

Setting up the non-GUI core logic of Minesweeper in Minesweeper.java was complex because of the various
scenarios to account for. My original game set up the trueBoard at the absolute  start of a new game,
but this made it almost difficult to win since the user had nothing to start with and could often lose on
the first click. So, I was forced to go back and edit the code so that the first square reveal set up the
trueBoard and ensured that no square in a 1-square radius of the first revealed square contained a mine. This
is how most online Minesweeper games work. 

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I have an effective design with satisfactory separation of functionality and a well-encapsulated
private state. In retrospect, I would consider using a 2D array of JButtons to represent the gameboard.
This would make my code more sophisticated. I would also consider simplifying my reset() commands across
classes. Depending on the boolean it is inputted, my reset() method either resets the game state for a new 
game or sets the game state to that from the last saved game. Splitting this into two separate methods
would make my code more readable.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/io/BufferedWriter.html
https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html