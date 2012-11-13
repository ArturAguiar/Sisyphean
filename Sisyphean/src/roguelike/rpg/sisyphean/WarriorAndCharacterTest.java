package roguelike.rpg.sisyphean;

import junit.framework.TestCase;


/**
 * // -------------------------------------------------------------------------
/**
 *  Tests the character class.
 *
 *  @author whlund15
 *  @version Nov 4, 2012
 */
public class WarriorAndCharacterTest extends TestCase
{
    private Warrior newChar;

    /**
     * Instantiates a warrior class character.
     */
    public void setUp()
    {
        newChar = new Warrior("Sisyphean", 10.0f, 10.0f);
    }

    //Test Methods-------------------------------------------------------------

    /**
     * Tests the name (getter and setter) methods.
     */
    public void testName()
    {
        assertEquals("Sisyphean", newChar.getName());
        newChar.setName("Ralph");
        assertEquals("Ralph", newChar.getName());
    }

    /**
     * Tests the level (getter and setter) methods.
     */
    public void testLevel()
    {
        assertEquals(1, newChar.getLevel());
        newChar.setLevel(2);
        assertEquals(2, newChar.getLevel());
    }

    /**
     * Tests the max health (getter and setter) methods.
     */
    public void testMaxHealth()
    {
        assertEquals(120.0f, newChar.getMaxHealth());

        newChar.setMaxHealth(220);

        assertEquals(220.0f, newChar.getMaxHealth());
        newChar.setHealth(110);
        newChar.setMaxHealth(100);

        assertEquals(50.0f, newChar.getHealth());
    }

    /**
     * Tests the current health (getter and setter) methods.
     */
    public void testCurrentHealth()
    {
        newChar.setMaxHealth(120);
        assertEquals(120.0f, newChar.getHealth());

        newChar.setHealth(150);
        assertEquals(120.0f, newChar.getHealth());
    }

    /**
     * Tests the max mana (getter and setter) methods.
     */
    public void testMaxMana()
    {
        assertEquals(100.0f, newChar.getMaxMana());

        newChar.setMaxMana(220);

        assertEquals(220.0f, newChar.getMaxMana());
        newChar.setMana(110);
        newChar.setMaxMana(100);

        assertEquals(50.0f, newChar.getMana());
    }

    /**
     * Tests the current mana (getter and setter) methods.
     */
    public void testCurrentMana()
    {
        newChar.setMaxMana(100);
        assertEquals(100.0f, newChar.getMana());

        newChar.setMana(150);
        assertEquals(100.0f, newChar.getMana());
    }

    /**
     * Tests the max stamina (getter and setter) methods.
     */
    public void testMaxStamina()
    {
        assertEquals(120.0f, newChar.getMaxStamina());

        newChar.setMaxStamina(220);

        assertEquals(220.0f, newChar.getMaxStamina());
        newChar.setStamina(110);
        newChar.setMaxStamina(100);

        assertEquals(50.0f, newChar.getStamina());
    }

    /**
     * Tests the current stamina (getter and setter) methods.
     */
    public void testCurrentStamina()
    {
        newChar.setMaxStamina(100);
        assertEquals(100.0f, newChar.getStamina());

        newChar.setStamina(150);
        assertEquals(100.0f, newChar.getStamina());
    }

    /**
     * Tests the strength (getter and setter) methods.
     */
    public void testStrength()
    {
        assertEquals(30.0f, newChar.getStrength());

        newChar.setStrength(45);
        assertEquals(45.0f, newChar.getStrength());
    }

    /**
     * Tests the defense (getter and setter) methods.
     */
    public void testDefense()
    {
        assertEquals(25.0f, newChar.getDefense());
        newChar.setDefense(40);
        assertEquals(40.0f, newChar.getDefense());
    }

    /**
     * Tests the dexterity (getter and setter) methods.
     */
    public void testDexterity()
    {
        assertEquals(12.0f, newChar.getDexterity());
        newChar.setDexterity(20);
        assertEquals(20.0f, newChar.getDexterity());
    }

    /**
     * Tests the intelligence (getter and setter) methods.
     */
    public void testIntelligence()
    {
        assertEquals(10.0f, newChar.getIntelligence());
        newChar.setIntelligence(20);
        assertEquals(20.0f, newChar.getIntelligence());
    }

    /**
     * Tests the skills set of the warrior.
     */
    public void testGetSkills()
    {
        //Finish after the skills issue is fixed.
        /*
        assertEquals(0, newChar.getSkills().size());

        newChar.levelUp();
        newChar.levelUp();

        assertEquals(1, newChar.getSkills().size());
        */

    }

    /**
     * Tests the mazeSprite of the warrior.
     */
    public void testGetMazeSprite()
    {
        assertNotNull(newChar.getMazeSprite());
    }
}

