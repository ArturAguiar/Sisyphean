package roguelike.rpg.sisyphean;

import junit.framework.TestCase;

public class CellTest extends TestCase
{
    Cell cell;

    public void setUp()
        throws Exception
    {
        cell = new Cell(0, 0);
    }


    public void testWallString()
    {
        assertEquals("TTTT", cell.wallString());
    }


    public void testRemoveWall()
    {
        assertEquals("TTTT", cell.wallString());
        cell.removeWall(0);
        assertEquals("FTTT", cell.wallString());
    }

}
