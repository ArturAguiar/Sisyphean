package roguelike.rpg.sisyphean;


import sofia.util.Random;
import junit.framework.TestCase;

// -------------------------------------------------------------------------
/**
 *  Tests the generation of a Maze to ensure it behaves as expected.
 *
 *  The maze being tested should appear as below:
 *   __ __ __ __
 *  |   __ __ __|
 *  |   __ __ __|
 *  |   __ __ __|
 *  |__ __ __ __|
 *
 *  @author Petey
 *  @version Nov 5, 2012
 */
public class MazeTest
    extends TestCase
{
    private Maze maze;
    private GameWorld gameWorld;

    protected void setUp()
        throws Exception
    {
        super.setUp();
        // Random is called for the x and y coordinates of the starting cell,
        // and each time it looks for an unexplored adjacent cell,
        // which are ordered N, E, S, W.
        Random.setNextInts(
            0, 3,               // Start cell coordinates
            0, 1, 2, 3,         // Looking at cells adjacent to (0, 3)
            0, 1, 2, 3,         // Looking at cells adjacent to (0, 2)
            0, 1, 2, 3,         // Looking at cells adjacent to (1, 3)
            0, 1, 2, 3,         // Looking at cells adjacent to (0, 1)
            0, 1, 2, 3,         // Looking at cells adjacent to (1, 2)
            0, 1, 2, 3,         // Looking at cells adjacent to (2, 3)
            0, 1, 2, 3,         // Looking at cells adjacent to (0, 0)
            0, 1, 2, 3,         // Looking at cells adjacent to (1, 1)
            0, 1, 2, 3,         // Looking at cells adjacent to (2, 2)
            0, 1, 2, 3,         // Looking at cells adjacent to (3, 3)
            0, 1, 2, 3,         // Looking at cells adjacent to (1, 0)
            0, 1, 2, 3,         // Looking at cells adjacent to (2, 1)
            0, 1, 2, 3,         // Looking at cells adjacent to (3, 2)
            0, 1, 2, 3,         // Looking at cells adjacent to (3, 3)
            0, 1, 2, 3,         // Looking at cells adjacent to (2, 0)
            0, 1, 2, 3,         // Looking at cells adjacent to (3, 1)
            0, 1, 2, 3);        // Looking at cells adjacent to (3, 0)


        maze = new Maze(gameWorld, 0);
    }

    // ----------------------------------------------------------
    /**
     * Tests the generate() method, specifically pertaining to what the
     * primsAlgorithm() method is intended to accomplish, by determining the
     * locations of the walls in each cell.
     */
    public void testPrimsAlgorithm()
    {
        assertEquals(2, maze.getCell(0, 3).numberOfWalls());
        assertEquals("FFTT", maze.getCell(0, 3).wallString());

        assertEquals("FFFT", maze.getCell(0, 2).wallString());

        assertEquals("TFTF", maze.getCell(1, 3).wallString());

        assertEquals("FFFT", maze.getCell(0, 1).wallString());

        assertEquals("TFTF", maze.getCell(1, 2).wallString());

        assertEquals("TFTF", maze.getCell(2, 3).wallString());

        assertEquals("TFFT", maze.getCell(0, 0).wallString());

        assertEquals("TFTF", maze.getCell(1, 1).wallString());

        assertEquals("TFTF", maze.getCell(2, 2).wallString());

        assertEquals("TTTF", maze.getCell(3, 3).wallString());

        assertEquals("TFTF", maze.getCell(1, 0).wallString());

        assertEquals("TFTF", maze.getCell(2, 1).wallString());

        assertEquals("TTTF", maze.getCell(3, 2).wallString());

        assertEquals("TFTF", maze.getCell(2, 0).wallString());

        assertEquals("TTTF", maze.getCell(3, 1).wallString());

        assertEquals("TTTF", maze.getCell(3, 0).wallString());
    }

}
