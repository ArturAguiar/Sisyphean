package roguelike.rpg.sisyphean;

import sofia.graphics.ImageShape;
import android.graphics.PointF;

/**
 *  The Item abstract superclass.
 *  An item can be a: weapon, armor, potion...
 *
 *  @author Artur
 *  @version Nov 3, 2012
 */
abstract public class Item
{
    private String name;
    private String description;
    private ImageShape mazeIcon;


    /**
     * Returns the name of the item.
     * @return String The string name of the item
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the item.
     * @param name The name of the item
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the description of the item.
     * @return String The string description of the item
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the item.
     * @param description The description of the item
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Returns an image of the sprite.
     * @return ImageShape The image of the sprite
     */
    public ImageShape getMazeIcon()
    {
        return mazeIcon;
    }

    /**
     * Sets the image of the sprite.
     * @param mazeSprite Sets the maze sprite image
     */
    public void setMazeIcon(ImageShape mazeSprite)
    {
        this.mazeIcon = mazeSprite;
    }

    /**
     * Returns this character's position, which is the same as the position of
     * it's sprite.
     * @return The character's position.
     */
    public PointF getPosition()
    {
        return getMazeIcon().getPosition();
    }

    /**
     * Changes the characters position by changing its sprite position.
     * @param x The x coordinate of the new position.
     * @param y The y coordinate of the new position.
     */
    public void setPosition(float x, float y)
    {
        if (getMazeIcon() != null)
        {
            this.getMazeIcon().setPosition(x, y);
        }
    }
}
