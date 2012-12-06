package roguelike.rpg.sisyphean;

import java.util.ArrayList;


/**
 * // -------------------------------------------------------------------------
/**
 *  Creates a class used as a database for our armor pieces and weapons.
 *
 *  @author whlund15
 *  @version 2012.12.04
 */
public class ItemCreator
{
    private GameWorld gameWorld;

    private ArrayList<Weapon> weapons;
    private ArrayList<Armor> armors;
    private sofia.util.Random rand = new sofia.util.Random();


    /**
     * Instantiates the map and populates it with the desired weapons and armor.
     * @param gameWorld The game world reference.
     */
    public ItemCreator(GameWorld gameWorld)
    {
        this.gameWorld = gameWorld;

        weapons =  new ArrayList<Weapon>();
        armors = new ArrayList<Armor>();


        /*
         * Calls the method that adds the armor and weapons. I chose
         * this to keep the constructor cleaner.
         */
        generateItems();
    }


    /**
     * Selects an item for the user to randomly pick up when found.
     * @return String The key for the item
     */
    public Item selectItem()
    {
        int itemType = rand.nextInt(6);

        if (itemType == 0)
        {
            return weapons.get(rand.nextInt(weapons.size()));
        }
        else if (itemType == 1)
        {
            return armors.get(rand.nextInt(armors.size()));
        }
        else
        {
            if (rand.nextInt(2) == 0)
            {
                //return health potion
                return null;
            }
            else
            {
                //return mana potion
                return null;
            }
        }


    }


    /**
     * Adds the items to the hash map with random stats.
     */
    private void generateItems()
    {


        //Adds the weapons to the database.
        weapons.add(new Weapon("Dull Sword",
            "This sword is a decent beginners weapon.", 4.0F, gameWorld));
        weapons.add(new Weapon("Agro-Bal Wand",
            "Beginner wand of ArchMage Agro-Bal. Powerful early game",
            7.0F, gameWorld));
        weapons.add(new Weapon("Conjuring Staff of Mal",
            "Powerful staff of evil mage Mal", 15.0F, gameWorld));
        weapons.add(new Weapon("Broadsword of Light",
            "Epic sword of Magmus Ponderosa", 18.0F, gameWorld));

        //Adds the armor to the database.
        armors.add(new Armor("Chestpiece of Brogma",
            "Chestpiece of demon Brogma.", 12.0F, gameWorld));
        armors.add(new Armor("Bracers of the Holy",
            "Armor for those of grand decent", 15.0F, gameWorld));
    }



}