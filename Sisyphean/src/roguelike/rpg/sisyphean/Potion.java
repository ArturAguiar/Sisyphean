package roguelike.rpg.sisyphean;

import sofia.util.Random;

// -------------------------------------------------------------------------
/**
 *  Potions replenish either the player's health or mana stat, depending on the
 *  type of potion.
 *  The effectiveness of each potion is based on a percentage of the player's
 *  stat to be refilled. The percentage is always a flat rate based on the
 *  player's class, but a random chance of fully restoring the stat is
 *  calculated every time the player uses a potion.
 *
 *  @author Petey
 *  @version Dec 3, 2012
 */
public class Potion extends Item
{
    private PotionType type;

    // ----------------------------------------------------------
    /**
     * Create a new Potion object.
     * @param potionType The type of potion being created
     * @param gameWorld The GameWorld
     */
    public Potion(PotionType potionType, GameWorld gameWorld)
    {
        type = potionType;
        if (potionType == PotionType.HEALTH)
        {
            setMazeIcon(new Sprite(R.drawable.health_potion, 70, 70, 1, 1,
                gameWorld.getDisplayMetrics().density));
            setName("Health Potion");
            setDescription("A hot elixir that rejuvenates your body. It restores a fourth of your health");
        }
        else
        {
            setMazeIcon(new Sprite(R.drawable.mana_potion, 70, 70, 1, 1,
                gameWorld.getDisplayMetrics().density));
            setName("Mana Potion");
            setDescription("A chilled concoction makes you feel more powerful. It restores a fourth of your mana");
        }
    }

    /**
     * Accessor for type.
     * @return PotionType The type of the potion
     */
    public PotionType getType()
    {
        return type;
    }

    /**
     * Gives a random 1% chance of success, intended to be called by the player
     * class when a potion is used to determine whether to refill a percentage
     * of the player's stat, or the full stat.
     * @return boolean Whether the potion fully restores a player's stat
     */
    public static boolean fullRestore()
    {
        Random rand = new Random();
        return rand.nextInt(100) == 0;
    }
}