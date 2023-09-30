package org.cis1200.minesweeper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * CIS 120 HW09 - Minesweeper
 * Atharv Awasthi
 */

/**
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of Minesweeper,
 * visualized with Strings printed to the console.
 */
public class Minesweeper {

    private int[][] userBoard;
    private int[][] trueBoard;
    private int numFlagsLeft;
    private boolean gameOver;
    private boolean gameWon;

    /**
     * Constructor sets up game state.
     */
    public Minesweeper() {
        // note that we reload the past saved game
        reset(true);
    }

    /**
     * revealSquare allows players to unveil the contents of a square. 
     *
     * @param c column to play in
     * @param r row to play in
     */
    public void revealSquare(int c, int r) {
        firstRevealProcedure(c, r);
        if (userBoard[c][r] != -3 || gameOver) {
            return;
        } else {
            userBoard[c][r] = trueBoard[c][r];
            if (userBoard[c][r] == -1) {
                for (int x = 0; x < 10; x++) {
                    for (int y = 0; y < 10; y++) {
                        // All mines are revealed to the player
                        if (trueBoard[x][y] == -1) {
                            userBoard[x][y] = trueBoard[x][y];
                        }
                    }
                }
                gameOver = true;
            } else if (userBoard[c][r] == 0) {
                // Boolean array to make sure our recursion doesn't generate a stackOverflow
                boolean[][] v = new boolean[10][10];
                v[c][r] = true;
                revealEmptySquares(c, r, v);
            }
        }
        saveState();
    }

    /**
     * firstRevealProcedure is a helper that defines the trueBoard
     * when the user first calls the revealSquare method. This is to
     * ensure that the game doesn't end on the first call of revealSquare.
     *
     *
     * @param c column of first call of revealSquare
     * @param r row of first call of revealSquare
     */
    private void firstRevealProcedure(int c, int r) {
        boolean firstReveal = true;
        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 10; b++) {
                if (userBoard[a][b] != -3 && userBoard[a][b] != -2) {
                    firstReveal = false;
                }
            }
        }

        if (firstReveal) {
            // pick 25 random locations for mines (indicated by -1)
            int i = 1;
            while (i <= 25) {
                
                int x = (int) (trueBoard.length * Math.random());
                int y = (int) (trueBoard[x].length * Math.random());

                // mine cannot exist in a 1-cell radius of the first revealed cell
                if (trueBoard[x][y] != -1 && 
                    !(x >= c - 1 && x <= c + 1 && y >= r - 1 && y <= r + 1)) {
                    trueBoard[x][y] = -1;
                    i++;
                }
            }

            // create a functional true gameboard and user-view gameboard
            for (int a = 0; a < 10; a++) {
                for (int b = 0; b < 10; b++) {

                    int squareNumber = 0;
                    if (!(trueBoard[a][b] == -1)) {
                        // account for out of bounds indices
                        for (int x = Math.max(0, a - 1); x <= Math.min(9, a + 1); x++) {
                            for (int y = Math.max(0, b - 1); y <= Math.min(9, b + 1); y++) {
                                if (trueBoard[x][y] == -1) { 
                                    squareNumber++;
                                }
                            }
                        }
                        trueBoard[a][b] = squareNumber;
                    } 
                }
            }
        }
    }

    /**
     * revealEmptySquares is a helper function that allows players to reveals
     * the empty squares around a square at (c, r). The method also reveals a 
     * border of numbered squares around the revealed empty squares.
     *
     * @param c column of empty square called on
     * @param r row of empty square called on
     * @param v boolean array to assist recursion
     */
    private void revealEmptySquares(int c, int r, boolean[][] v) {
        // recursive BFS
        boolean[][] visited = v;
        // Iterate through the squares around the square at (c,r)
        for (int x = Math.max(0, c - 1); x <= Math.min(9, c + 1); x++) {
            for (int y = Math.max(0, r - 1); y <= Math.min(9, r + 1); y++) {
                if (!visited[x][y]) {
                    visited[x][y] = true;
                    // base case
                    if (trueBoard[x][y] != 0) {
                        /* remove misplaced flags and reveal numbered squares
                        ** bordering group of empty squares
                        */
                        if (userBoard[x][y] == -2) {
                            numFlagsLeft++; 
                        }
                        userBoard[x][y] = trueBoard[x][y];
                    // recursive case
                    } else {
                        if (userBoard[x][y] == -2) {
                            numFlagsLeft++; 
                        }
                        userBoard[x][y] = 0;
                        // recursively reveals other empty squares
                        revealEmptySquares(x, y, visited);
                    }
                }
            }
        }
    }

    /**
     * flagSquare allows players to flag a square that they think is a mine. 
     * The value in the userBoard is changed from -3 (unknown) to -2 (flagged)
     *
     * @param c column to play in
     * @param r row to play in
     */
    public void flagSquare(int c, int r) {
        if (userBoard[c][r] != -3 || gameOver) {
            return;
        } else {
            userBoard[c][r] = -2;
            numFlagsLeft--;
        }
        saveState();
    }

    /**
     * unflagSquare allows players to remove a flag from a square. 
     * The value in the userBoard is changed from -2 (flagged) to -3 (unknown).
     *
     * @param c column to play in
     * @param r row to play in
     */
    public void unflagSquare(int c, int r) {
        if (userBoard[c][r] != -2 || gameOver) {
            return;
        } else {
            userBoard[c][r] = -3;
            numFlagsLeft++;
        }
        saveState();
    }


    /**
     * gameOutcome checks whether the game has finished in a win.
     *
     * @return 0 if game is not over yet, 1 if player won, and 2 if player
     *         has lost
     */
    public int gameOutcome() {

        // Check if player already lost.
        if (gameOver && !gameWon) {
            return 2;
        }

        // If a mine has been revealed, the earlier condition will already have considered that.
        // This loop checks if the proper squares have been flagged and if ever other square has
        // been revealed. If there is a mismatch, the game is not finished.
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (!(trueBoard[x][y] == userBoard[x][y] ||
                     (trueBoard[x][y] == -1 && userBoard[x][y] == -2))) {
                    return 0;
                }
            }
        }

        if (numFlagsLeft == 0) {
            gameOver = true;
            gameWon = true;
            return 1;
        } else { 
            return 0; 
        }
    }

    /**
     * reset (re-)sets the game state to start or resume a game.
     *
     * @param resumeGame whether or not we are resuming a game
     */
    public void reset(boolean resumeGame) {
        
        // if we are resuming pre-loaded game
        if (resumeGame) {
            resume();

        // if we are resetting the game
        } else {
            numFlagsLeft = 25;
            gameOver = false;
            gameWon = false;

            userBoard = new int[10][10];
            trueBoard = new int[10][10];

            // temporary values until the first call of reveal sets the board
            for (int a = 0; a < 10; a++) {
                for (int b = 0; b < 10; b++) {
                    
                    // -3 represents unrevealed
                    userBoard[a][b] = -3;
                    trueBoard[a][b] = 0;
                }
            }
        }
        saveState();
    }


    /**
    * resume sets the game state to the state the player left the game in.
    */
    public void resume() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("files/persistent.txt"));

            numFlagsLeft = Integer.parseInt(reader.readLine());
            gameOver = Boolean.parseBoolean(reader.readLine());
            gameWon = Boolean.parseBoolean(reader.readLine());

            userBoard = new int[10][10];
            trueBoard = new int[10][10];

            for (int a = 0; a < 10; a++) {

                // Read a line of text from the file
                String text = reader.readLine();

                // Extract the comma-separated values from the line and store them in an array
                String[] sections = text.split(",");
                for (int i = 0; i < 10; i++) {
                    userBoard[a][i] = Integer.parseInt(sections[i]);
                }
            }

            for (int a = 0; a < 10; a++) {
                // Read a line of text from the file
                String text = reader.readLine();

                // Extract the comma-separated values from the line and store them in an array
                String[] sections = text.split(",");
                for (int i = 0; i < 10; i++) {
                    trueBoard[a][i] = Integer.parseInt(sections[i]);
                }
            }
            reader.close();
        } catch (IOException e) {
        
        }

    }


    /**
    * saveState() saves the game state to the file persistent.txt 
    */
    private void saveState() {
        int outcome = gameOutcome();
        try {
            BufferedWriter writer = 
                new BufferedWriter(new FileWriter("files/persistent.txt", false));

            // save features of game state
            writer.write("" + numFlagsLeft);
            writer.newLine();
            writer.write("" + gameOver);
            writer.newLine();
            writer.write("" + gameWon);
            writer.newLine();

            // save current userBoard
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    writer.write("" + userBoard[i][j]);
                    if (j != 9) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }

            // save current trueBoard
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    writer.write("" + trueBoard[i][j]);
                    if (j != 9) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) { 
            
        }
    }


    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         userBoard.
     */
    public int getUserCell(int c, int r) {
        return userBoard[c][r];
    }

    /**
     * setUserBoard is a setter for the contents of the userBoard. This is a
     * method only intended for use in testing. The user should input a working
     * board or this method will not work properly.
     *
     * @param board the board userBoard is set to
     *
     */
    public void setUserBoard(int[][] board) {
        userBoard = board;
        saveState();
    }

    /**
     * setTrueBoard is a setter for the contents of the trueBoard. This is a
     * method only intended for use in testing. The user should input a working
     * board or this method will not work properly.
     *
     * @param board the board trueBoard is set to
     *
     */
    public void setTrueBoard(int[][] board) {
        trueBoard = board;
        saveState();
    }

    /**
     * getUserBoard is a getter for the contents of the userBoard. This is a
     * method only intended for use in testing. 
     *
     * @return board the int[][] representing userBoard
     *
     */
    public int[][] getUserBoard() {
        return userBoard;
    }

    /**
     * getNumFlagsLeft is a getter for the number of flags/mines left.
     *
     *
     * @return numFlagsLeft
     *
     */
    public int getNumFlagsLeft() {
        return numFlagsLeft;
    }


    /**
     * printGameState prints the current game state
     * for debugging by printing the userBoard
     */
    public void printGameState() {
        System.out.println("\n\nFlags Left: " + numFlagsLeft + "\n");
        for (int i = 0; i < userBoard.length; i++) {
            for (int j = 0; j < trueBoard[i].length; j++) {
                System.out.print(trueBoard[i][j] + "  ");
            }
            System.out.println();
        }

        if (gameOutcome() == 2) {
            System.out.println();
            System.out.println("You have lost :(");
        } else if (gameOutcome() == 1) {
            System.out.println();
            System.out.println("You have won :)");
        }
    }
}


