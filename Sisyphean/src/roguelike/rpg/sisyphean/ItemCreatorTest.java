package roguelike.rpg.sisyphean;


import sofia.util.Random;
import junit.framework.TestCase;

/**
 * // -------------------------------------------------------------------------
/**
 *  Tests the Item creator class
 *
 *  @author whlund15
 *  @version 2012.12.04
 */
public class ItemCreatorTest extends TestCase
{
    private ItemCreator newItem;
    /**
     * Instantiates the new Item creator object.
     */
    public void setUp()
    {
        newItem = new ItemCreator(new GameWorld());
    }

    /**
     * Tests the instantiation of the two array lists of items.
     */
    public void testSelectItem()
    {
        Random.setNextInts(0, 0);
        assertEquals("Dull Sword", newItem.selectItem().getName());
        Random.setNextInts(1, 0);
        assertEquals("Chestpiece of Brogma", newItem.selectItem().getName());

        //Tests them before I had the actual health/mana objects.
        //TODO Change after class is fixed.
        Random.setNextInts(2, 0);
        assertNull(newItem.selectItem());
        Random.setNextInts(2, 1);
        assertNull(newItem.selectItem());
    }
}
