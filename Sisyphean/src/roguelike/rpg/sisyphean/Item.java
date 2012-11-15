package roguelike.rpg.sisyphean;

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


}
