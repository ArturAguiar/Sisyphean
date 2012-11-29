package roguelike.rpg.sisyphean;

import roguelike.rpg.sisyphean.Character.PlayerType;
import sofia.util.Random;
import junit.framework.TestCase;

// -------------------------------------------------------------------------
/**
 *  Tests the player character class.
 *
 *  @author whlund15
 *  @version 2012.11.13
 */
public class PlayerTest extends TestCase
{
    private Player sisyphean;
    private Enemy thatHillAndBoulderCombo;
    /**
     * Instantiates a new player object.
     */
    public void setUp()
    {
        GameWorld newGameWorld = new GameWorld();
        newGameWorld.getDisplayMetrics().setToDefaults();
        sisyphean = new Warrior("Brutus", 0, 0, newGameWorld);

      /*
       * TODO Ask artur if this will make the enemy a zombie and his level
       * to be level 1.
       */

        Random.setNextInts(0, 1);
        thatHillAndBoulderCombo = new Enemy(1);

    }

    //Methods.

    /**
     * Tests the update method.
     */
    public void testUpdate()
    {
      //TODO: Create method.
    }

    /**
     * Tests the was hit method.
     */
    public void testWasHit()
    {
        sisyphean.wasHit(thatHillAndBoulderCombo);
        //no damage done
        assertEquals(0.0F, sisyphean.wasHit(thatHillAndBoulderCombo));
        assertEquals(120.0F, sisyphean.getHealth());

        /*
         * now make zombie have higher strength than defense. I would
         * use the level up method, but right now it might be easier
         * to just set his strength to be greater than the defense of
         * the warrior.
         */
        thatHillAndBoulderCombo.setStrength(50);

        sisyphean.wasHit(thatHillAndBoulderCombo);

        assertEquals(25.0F, sisyphean.wasHit(thatHillAndBoulderCombo));
        assertEquals(95.0F, sisyphean.getHealth());

    }

    /**
     * Tests the player type setting and getting.
     */
    public void testPlayerType()
    {
        assertEquals(PlayerType.WARRIOR, sisyphean.getType());

        sisyphean.setType(PlayerType.ARCHER);
        assertEquals(PlayerType.ARCHER, sisyphean.getType());
    }

    /**
     * Tests the get and set experience points method.
     */
    public void testExperience()
    {
        assertEquals(0.0F, sisyphean.getExperience());

        sisyphean.setExperience(7);
        assertEquals(3.0F, sisyphean.getExpToNextLevel());
        assertEquals(7.0F, sisyphean.getExperience());
    }

    /**
     * Tests the experience to the next level amount of points and also the
     * setter.
     */
    public void testExpTonextLevel()
    {
        assertEquals(10.0F, sisyphean.getExpToNextLevel());

        sisyphean.setExpToNextLevel(5);
        assertEquals(5.0F, sisyphean.getExpToNextLevel());


    }

    /**
     * Tests the setter and getter armor change methods.
     */
    public void testPlayerArmor()
    {
        assertEquals(2.0F, sisyphean.getArmor());

        sisyphean.setDefense(5);
        assertEquals(5.0F, sisyphean.getArmor());
    }

    /**
     * Tests the weapon getter and setter methods.
     */
    public void testPlayerWeapon()
    {
      assertEquals("Rusty Daggar", sisyphean.getWeapon().getName());

      Weapon waterBlade = new Weapon("Waters Edge", "Hard to handle, " +
      		"but will definitely rain on their parade", 30);
      sisyphean.setWeapon(waterBlade);
      assertEquals("Waters Edge", sisyphean.getWeapon().getName());
    }

    /**
     * Tests the move by method.
     */
    public void testMoveBy()
    {
      //TODO: Create method.
    }

    /**
     * Tests the walk animation method.
     */
    public void testWalkAnimEnded()
    {
      //TODO: Create method.
    }

}
