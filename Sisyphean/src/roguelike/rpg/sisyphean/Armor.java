package roguelike.rpg.sisyphean;

import sofia.graphics.ImageShape;

/**
 *  Armor gives defense bonuses to the player.
 *  Only one can be equipped/carried at a time.
 *
 *  @author Artur
 *  @version Nov 3, 2012
 */
public class Armor extends Item
{
    private float defense;


    /**
     * The armor constructor. Initializes all immutable fields.
     * @param name The name of this armor.
     * @param description The description of this armor.
     * @param defense The defense bonus given by this armor.
     * @param mazeSprite The sprite displayed when not in battle mode.
     * @param gameWorld The GameWorld
     */
    public Armor(String name, String description, float defense, GameWorld gameWorld)
    {
        this.setName(name);
        this.setDescription(description);
        this.defense = defense;

        setMazeIcon(new Sprite(R.drawable.chestplate, 70, 70, 1, 1,
            gameWorld.getDisplayMetrics().density));
    }

    /**
     * The armor defense getter.
     * @return The armor defense.
     */
    public float getDefense()
    {
        return defense;
    }
}