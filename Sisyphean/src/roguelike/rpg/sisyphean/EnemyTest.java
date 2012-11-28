package roguelike.rpg.sisyphean;

import sofia.util.Random;
import roguelike.rpg.sisyphean.Character.EnemyType;
import junit.framework.TestCase;

/**
 * // -------------------------------------------------------------------------
/**
 *  Tests the enemy character class.
 *
 *  @author whlund15
 *  @version 2012.11.13
 */
public class EnemyTest extends TestCase
{
    private Enemy newEnemy;
    private Warrior sisyphean;
    private Weapon darkSword;

    /**
     * Instantiates the new enemy.
     */
    public void setUp()
    {
        //TODO Ask artur if this will make the enemy a zombie and his level
        //TODO to be level 1.
        Random.setNextInts(0, 1);
        newEnemy = new Enemy(1);
        GameWorld newGameWorld = new GameWorld();


        /*
         * Creates the player, sets him as a warrior, and makes him
         * level 1 for easier testing.
         */
        sisyphean = new Warrior("Brutus", 0, 0, newGameWorld);

        /*
         * Weapon for sisyphean to use during battle against enemy in this case
         */
        darkSword = new Weapon("Bumblebee", "Stings the opponent", 30);
        darkSword.addBonusDamage(EnemyType.ZOMBIE.toString(), 15.0F);
    }

    //Methods.

    /**
     * Tests the constructor.
     */
    public void testEnemyConstructor()
    {
        assertEquals("Zombie", newEnemy.getName());
        assertEquals(1, newEnemy.getLevel());

    }

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
    public void wasHit()
    {
        /*TODO
         * Ask artur if this will set him to go melee and not add
         * any weapon abilities
         */
        sisyphean.setWeapon(null);
        //Only since we haven't declared what the stats are for player classes
        sisyphean.setStrength(10);
        newEnemy.wasHit(sisyphean);
        //Not enough damage to do any harm
        assertEquals(0.0F, newEnemy.wasHit(sisyphean));
        assertEquals(100.0F, newEnemy.getHealth());

        sisyphean.setStrength(38);
        //enough to do damage now
        newEnemy.wasHit(sisyphean);
        assertEquals(20.0F, newEnemy.wasHit(sisyphean));
        assertEquals(80.0F, newEnemy.getHealth());


        sisyphean.setWeapon(darkSword);
        //enough to do damage and additional damage from weapon
        newEnemy.wasHit(sisyphean);
        assertEquals(83.0F, newEnemy.wasHit(sisyphean));
        assertEquals(17.0F, newEnemy.getHealth());



    }

    /**
     * Returns a description of the enemy and tests the setter method as well.
     */
    public void testDescription()
    {
        assertEquals("A flesh eating undead creature.",
            newEnemy.getDescription());
        newEnemy.setDescription("Crazy walking undead moron running blindly");

        assertEquals("Crazy walking undead moron running blindly",
            newEnemy.getDescription());
    }

}
