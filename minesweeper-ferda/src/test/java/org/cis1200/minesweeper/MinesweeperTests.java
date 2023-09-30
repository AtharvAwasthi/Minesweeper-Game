package org.cis1200.minesweeper;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class MinesweeperTests {

    private Minesweeper ms = new Minesweeper();

    @BeforeEach
    public void setUpClass() {

        ms.reset(false);

        int[][] board1 = {
            {0,   0,   0,  0,  1,  -1,  2,  1,  3,  -1},
            {1,   2,   2,  2,  3,  2,  3,  -1,  3,  -1  },
            {-1,  3,  -1, -1,  3,  -1,  2,  2,  3,  2},
            {-1,  3,  3,  -1, 4,  3,  3,  2,  -1,  2 },
            {1,  1,   2,  2,  3,  -1,  -1,  2,  2, -1  },
            {0,  0,   1, -1,  2,  2,  3,  2,  2,   1  },
            {0,  1,   3,  3,  2,  1,  2,  -1,  3,   2  },
            {0,  1, -1,  -1, 2,  2,  -1,  3,  -1,  -1  },
            {0,  2,   3,  3,  2,  -1,  4,   4,  3,  2  },
            {0,  1,  -1,  1,  1,  2,  -1,  -1,  1,  0 },
        };

        ms.setTrueBoard(board1);

        // In these cases, we do not want our first revealSquare call
        // to randomly set the board, hence the 1 at [1][0]
        int[][] board2 = {
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {1,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  }
        };

        ms.setUserBoard(board2);
    }

    @Test
    public void testEntireBoardUnrevealed() {
        boolean b = true;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (!(x == 1 && y == 0) && ms.getUserCell(x, y) != -3) {
                    b = false;
                }
            }
        }
        assertTrue(b);
        
    }

    @Test
    public void testRevealNumberedSquare() {
        ms.revealSquare(1, 2);

        int[][] expected = {
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {1,   -3,   2,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  }
        };

        assertArrayEquals(expected, ms.getUserBoard());

    }

    @Test
    public void testRevealEmptySquare() {
        ms.revealSquare(0, 2);

        int[][] expected = {
            {0,   0,   0,  0,  1,  -3,  -3,  -3,  -3,  -3  },
            {1,   2,   2,  2,  3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  }
        };

        assertArrayEquals(expected, ms.getUserBoard());

    }

    @Test
    public void testRevealMine() {
        ms.revealSquare(0, 9);

        int[][] expected = {
            {-3,   -3,   -3,  -3,  -3,  -1,  -3,  -3,  -3,  -1  },
            {1,   -3,   -3,  -3,  -3,  -3,  -3,  -1,  -3,  -1  },
            {-1,   -3,   -1,  -1,  -3,  -1,  -3,  -3,  -3,  -3  },
            {-1,   -3,   -3,  -1,  -3,  -3,  -3,  -3,  -1,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -1,  -1,  -3,  -3,  -1  },
            {-3,   -3,   -3,  -1,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -1,  -3,  -3  },
            {-3,   -3,   -1,  -1,  -3,  -3,  -1,  -3,  -1,  -1  },
            {-3,   -3,   -3,  -3,  -3,  -1,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -1,  -3,  -3,  -3,  -1,  -1,  -3,  -3  }
        };

        assertArrayEquals(expected, ms.getUserBoard());
        assertEquals(2, ms.gameOutcome());

    }

    @Test
    public void testFlagUnflaggedSquare() {
        ms.flagSquare(0, 3);

        int[][] expected = {
            {-3,   -3,   -3,  -2,  -3,  -3,  -3,  -3,  -3,  -3  },
            {1,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  }
        };

        assertArrayEquals(expected, ms.getUserBoard());

    }

    @Test
    public void testFlagRevealedSquare() {
        ms.flagSquare(1, 0);

        int[][] expected = {
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {1,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  }
        };

        assertArrayEquals(expected, ms.getUserBoard());

    }

    @Test
    public void testUnflagRevealedAndUnrevealedSquare() {
        ms.unflagSquare(1, 0);
        ms.unflagSquare(0, 0);

        int[][] expected = {
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {1,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  }
        };

        assertArrayEquals(expected, ms.getUserBoard());

    }

    @Test
    public void testUnflagFlaggedSquare() {
        
        ms.flagSquare(3, 0);
        int[][] expected1 = {
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {1,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-2,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  }
        };

        assertArrayEquals(expected1, ms.getUserBoard());

        ms.unflagSquare(3, 0);
        int[][] expected2 = {
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {1,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  },
            {-3,   -3,   -3,  -3,  -3,  -3,  -3,  -3,  -3,  -3  }
        };

        assertArrayEquals(expected2, ms.getUserBoard());
        assertEquals(0, ms.gameOutcome());
    }

    @Test
    public void testGameWon() {
        int[][] board = {
            {0,   0,   0,  0,  1,  -3,  2,  1,  3,  -3},
            {1,   2,   2,  2,  3,  2,  3,  -3,  3,  -3  },
            {-3,  3,  -3, -3,  3,  -3,  2,  2,  3,  2},
            {-3,  3,  3,  -3, 4,  3,  3,  2,  -3,  2 },
            {1,  1,   2,  2,  3,  -3,  -3,  2,  2, -3  },
            {0,  0,   1, -3,  2,  2,  3,  2,  2,   1  },
            {0,  1,   3,  3,  2,  1,  2,  -3,  3,   2  },
            {0,  1, -3,  -3, 2,  2,  -3,  3,  -3,  -3  },
            {0,  2,   3,  3,  2,  -3,  4,   4,  3,  2  },
            {0,  1,  -3,  1,  1,  2,  -3,  -3,  1,  0 },
        };
        ms.setUserBoard(board);

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (board[x][y] == -3) {
                    ms.flagSquare(x, y);
                }
            }
        }
        assertEquals(0, ms.getNumFlagsLeft());
        assertEquals(1, ms.gameOutcome());
        
    }

    @Test
    public void testFirstRevealProcedure() {
        ms.reset(false);
        ms.flagSquare(2, 5);
        ms.flagSquare(2, 4);
        ms.flagSquare(8, 8);

        assertEquals(22, ms.getNumFlagsLeft());

        ms.revealSquare(3, 5);

        for (int x = 2; x <= 4; x++) {
            for (int y = 4; x <= 6; x++) {
                assertTrue(ms.getUserCell(x, y) != -1);
            }
        }
       
        assertEquals(24, ms.getNumFlagsLeft());
    }

    @AfterEach
    public void finishTesting() {
        ms.reset(false); // ensures that testing does not impact the game
    }
    
    
}