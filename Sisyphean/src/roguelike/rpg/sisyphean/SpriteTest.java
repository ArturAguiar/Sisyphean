package roguelike.rpg.sisyphean;

import junit.framework.TestCase;

// -------------------------------------------------------------------------
/**
 *  Tests the sprite class.
 *
 *  @author whlund15
 *  @version 2012.11.29
 */
public class SpriteTest extends TestCase
{
    private Sprite sisyphean;

    /**
     * Instantiates the Sprite object.
     */
    public void setUp()
    {
        sisyphean = new Sprite(0, 1, 1, 1, 1, 1);
    }

    /**
     * Tests the step method.
     */
    public void testStep()
    {

        sisyphean.step();

        assertEquals(2, sisyphean.getColumn());
    }

    /**
     * Tests the set row method and getter method.
     */
    public void testRow()
    {
        assertEquals(1, sisyphean.getRow());

        sisyphean.setRow(3);
        assertEquals(0, sisyphean.getColumn());
        assertEquals(3, sisyphean.getRow());
    }

    /**
     * Tests the set column method and getter method.
     */
    public void testColumn()
    {
        assertEquals(1, sisyphean.getColumn());

        sisyphean.setCol(3);
        assertEquals(1, sisyphean.getRow());
        assertEquals(0, sisyphean.getColumn());
    }

    /**
     * Tests the set position method.
     */
    public void testSetPosition()
    {
        //TODO
    }

    /**
     * Tests the get position method.
     */
    public void testGetPosition()
    {
        //TODO
    }

    /**
     * Tests the move method.
     */
    public void testMove()
    {
        //TODO
    }

    /**
     * Tests the get image shape method.
     */
    public void testGetImageShape()
    {
        //TODO
    }

    /**
     * Tests the update source method.
     */
    public void testUpdateSource()
    {
        //TODO
    }

}
