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


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }


}
