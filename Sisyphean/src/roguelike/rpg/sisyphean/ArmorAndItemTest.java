package roguelike.rpg.sisyphean;

import junit.framework.TestCase;

// -------------------------------------------------------------------------
/**
 *  Tests the armor class.
 *
 *  @author whlund15
 *  @version 2012.11.13
 */
public class ArmorAndItemTest extends TestCase
{
    private Armor leatherBoots;

    /**
     * Instantiates the piece of armor and it's visuals.
     */
    public void setUp()
    {

       leatherBoots = new Armor("Boots of blinding speed", "Boots grant" +
       		"more stamina for the character and a decrease in deppreciation" +
       		"of stamina.", 15.0F, null);
    }

    //Methods

    /**
     * Tests the name of the armor and setting it.
     */
    public void testName()
    {

        assertEquals("Boots of blinding speed", leatherBoots.getName());

        leatherBoots.setName("Boots of the slow");

        assertEquals("Boots of the slow", leatherBoots.getName());

    }

    /**
     * Tests the description of the armor and sets it.
     */
    public void testDescription()
    {
        assertEquals("Boots grant" +
            "more stamina for the character and a decrease in deppreciation" +
            "of stamina.", leatherBoots.getDescription());

        leatherBoots.setDescription("Boots of Agrul-Nobal the Slow. Stamina" +
        		"is depreciated faster over time as these boots are quite" +
        		" heavy");

        assertEquals("Boots of Agrul-Nobal the Slow. Stamina" +
                "is depreciated faster over time as these boots are quite" +
                " heavy", leatherBoots.getDescription());
    }

    /**
     * Tests the defense method.
     */
    public void testGetDefense()
    {
        assertEquals("15.0F", leatherBoots.getDefense());
    }

    /**
     * Tests obtaining the image of the piece of armor.
     */
    public void testMazeSpriteAccessor()
    {
      assertEquals("null", leatherBoots.getMazeSprite());
    }



}
