package org.cis1200.minesweeper;

/*
 * CIS 120 HW09 - Minesweeper 
 * Atharv Awasthi
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class instantiates a Minesweeper object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. 
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameLayout extends JPanel {

    private Minesweeper ms; // model for the game
    private JLabel status; // current status text
    private boolean showInstructions; // should the instructions be shown

    // Game constants
    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_HEIGHT = 500;

    /**
     * Initializes the game board.
     */
    public GameLayout(JLabel statusInit) {
        // creates border around the board area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        ms = new Minesweeper(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        showInstructions = true; // initializes the boolean showInstructions

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();
                int button = e.getButton();

                /* update the model given the coordinates
                ** and type of the mouseclick
                */
                if (button == MouseEvent.BUTTON1) { // left click
                    ms.revealSquare(p.y / 50, p.x / 50);
                    showInstructions = false;
                } else if (button == MouseEvent.BUTTON3) { // right click
                    if (ms.getUserCell(p.y / 50, p.x / 50) == -2) { // currently flagged square
                        ms.unflagSquare(p.y / 50, p.x / 50);
                    } else { // currently unflagged square
                        ms.flagSquare(p.y / 50, p.x / 50);
                    }
                    showInstructions = false;
                }
                
                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });

        /*
         * Listens for key presses. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                // if the space bar is pressed, show instructions
                if (e.getKeyCode() == KeyEvent.VK_SPACE) { // space bar
                    flipShowInstructions();
                }
                
                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     *
     * @param resumeGame whether or not to resume the previous game
     */
    public void reset(boolean resumeGame) {
        ms.reset(resumeGame);
        status.setText("Make your move! You have " +
                        ms.getNumFlagsLeft() + " flags left to place...");
        showInstructions = true;
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {

        int winStatus = ms.gameOutcome();
        if (winStatus == 0) {
            status.setText("Make your move! You have " +
                            ms.getNumFlagsLeft() + " flags left to place...");
        } else if (winStatus == 1) {
            status.setText("You have won!!!");
        } else if (winStatus == 2) {
            status.setText("You have lost :(");
        }
    }

    /**
     * Flip the value of showInstructions.
     */
    public void flipShowInstructions() {
        showInstructions = !showInstructions;
    }


    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        for (int i = 1; i <= 9; i++) {
            g.drawLine(50 * i, 0, 50 * i, 500);
            g.drawLine(0, 50 * i, 500, 50 * i);
        }

        // Draws unrevealed, numbered, empty, flagged, and mine-rigged cells
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int state = ms.getUserCell(j, i);
                if (state == -3) { // Unrevealed Cells
                    g.setColor(Color.BLACK);
                    g.fillRect(5 + 50 * i, 5 + 50 * j, 40, 40);
                } else if (state == -1) { // Mine-Rigged Cells
                    g.setColor(new Color(128, 128, 128)); // grey
                    g.fillOval(10 + 50 * i, 15 + 50 * j, 30, 30);
                    g.fillRect(23 + 50 * i, 4 + 50 * j, 4, 20);
                    g.setColor(Color.WHITE);
                    g.fillOval(27 + 50 * i, 27 + 50 * j, 8, 8);
                    g.setColor(Color.ORANGE);
                    g.fillOval(20 + 50 * i, 2 + 50 * j, 10, 10);
                    g.setColor(Color.RED);
                    g.fillOval(22 + 50 * i, 4 + 50 * j, 6, 6);
                } else if (state == -2) { // Flagged cells
                    g.setColor(Color.RED);
                    int[] xPoints = {5 + 50 * i, 45 + 50 * i, 5 + 50 * i};
                    int[] yPoints = {5 + 50 * j, 15 + 50 * j, 25 + 50 * j};
                    g.fillPolygon(xPoints, yPoints, 3);
                    g.fillRect(5 + 50 * i, 8 + 50 * j, 4, 40);
                } else if (state == 0) { // Empty cells
                    g.setColor(Color.BLACK); // do nothing
                } else { // Numbered Cells
                    g.setColor(Color.BLUE);
                    g.setFont(new Font("Arial", Font.BOLD, 48));
                    g.drawString("" + state, 10 + 50 * i, 40 + 50 * j);
                }
            }
        }

        // Conditionally show instructions
        if (showInstructions) {
            g.setColor(Color.YELLOW);
            g.fillRect(25, 50, 450, 400);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("MINESWEEPER INSTRUCTIONS:", 70, 80);
            g.drawString("You can left-click on any black square", 40, 105);
            g.drawString("to reveal its contents.", 130, 130);
            g.drawString("The number on a square indicates the", 40, 155);
            g.drawString("number of mines in", 140, 180);
            g.drawString("its one-square radius.", 130, 205);
            g.drawString("You can then right-click to flag", 75, 230);
            g.drawString("(and unflag) the squares", 120, 255);
            g.drawString("you believe contain mines.", 112, 280);
            g.drawString("The goal is to correctly flag all mines", 40, 305);
            g.drawString("and reveal the safe squares.", 100, 330);
            g.drawString("Press SPACE to hide/show this guide.", 45, 355);
            g.drawString("Click anywhere to begin,", 110, 380);
            g.drawString("and don't worry –", 160, 405);
            g.drawString("you can never lose on the first click :)", 40, 430);
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
