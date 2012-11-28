package roguelike.rpg.sisyphean;

import junit.framework.TestCase;

/**
 * // -------------------------------------------------------------------------
/**
 *  Tests the cell class for creating random mazes.
 *
 *  @author Petey
 *  @version 2012.11.15
 */
public class CellTest extends TestCase
{
    private Cell cell;

    public void setUp()
        throws Exception
    {
        cell = new Cell(0, 0);
    }

    /**
     * Tests the wall string method.
     */
    public void testWallString()
    {
        assertEquals("TTTT", cell.wallString());
    }

    /**
     * Tests the wall remove method.
     */
    public void testRemoveWall()
    {
        assertEquals("TTTT", cell.wallString());
        cell.removeWall(0);
        assertEquals("FTTT", cell.wallString());
    }

}
