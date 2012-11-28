package roguelike.rpg.sisyphean;

import junit.framework.TestCase;

/**
 * // -------------------------------------------------------------------------
/**
 *  Tests the weapon class.
 *
 *  @author whlund15
 *  @version 2012.11.13
 */
public class WeaponTest extends TestCase
{
    private Weapon silverSword;

    /**
     * Instantiates a new weapon object.
     */
    public void setUp()
    {
        silverSword = new Weapon("Wicked sword of Magror", "This sword " +
      		    "raises the chance of fearing the target for one attack turn",
      		    45);
    }

    //Methods.

    /**
     * Tests the get damage method.
     */
    public void testDamage()
    {
        assertEquals(45.0F, silverSword.getDamage());
    }

    /**
     * Tests the get and add bonus damage methods.
     */
    public void testBonusDamage()
    {
      //TODO: Create method.
    }
}
