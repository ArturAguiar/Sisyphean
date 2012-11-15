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
     * The image displayed on the character when he is wearing this armor
     * outside of battle mode.
     */
    private Sprite mazeSprite;


    /**
     * The armor constructor. Initializes all immutable fields.
     * @param name The name of this armor.
     * @param description The description of this armor.
     * @param defense The defense bonus given by this armor.
     * @param mazeSprite The sprite displayed when not in battle mode.
     */
    public Armor(String name, String description, float defense, Sprite mazeSprite)
    {
        this.setName(name);
        this.setDescription(description);
        this.defense = defense;
        this.mazeSprite = mazeSprite;
        this.mazeSprite.setCol(1);
    }

    /**
     * The armor defense getter.
     * @return The armor defense.
     */
    public float getDefense()
    {
        return defense;
    }

    /**
     * The maze sprite getter.
     * @return The maze sprite of this armor.
     */
    public Sprite getMazeSprite()
    {
        return mazeSprite;
    }
}