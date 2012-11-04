package roguelike.rpg.sisyphean;

import java.util.ArrayList;
import sofia.app.ShapeScreen;
import sofia.graphics.ImageShape;

/**
 *  The abstract class for every character in the game.
 *  This could be a player, an enemy and etc.
 *
 *  TODO: how to loop through an animation sprite using sofia?!
 *
 *  @author Artur
 *  @version Nov 3, 2012
 */
abstract public class Character
{
    /////FIELDS-----------------------------------------------------------------

    // Name
    private String name;

    // Level
    private int level;

    // Health
    private float health;

    // Mana
    private float mana;

    //Stamina
    private float stamina;

    // Statuses
    private float maxHealth;
    private float maxMana;
    private float maxStamina;
    private float strength;
    private float defense;
    private float dexterity;
    private float intelligence;

    //Skills.
    // TODO: is an ArrayList the best data structure for this?
    private ArrayList<Skill> skills;


    // Sprites

    /** The sprite to use when not in battle */
    private ImageShape mazeSprite;

    /** The sprite to use while in battle */
    private ImageShape battleSprite;


    /////METHODS----------------------------------------------------------------

    // Abstract

    /**
     * The method called to draw this character on the screen.
     * TODO: Do we need this if we're using sophia?
     *
     * @param The screen to draw on.
     */
    abstract public void drawMe(ShapeScreen screen);


    // Getters and setters

    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public int getLevel()
    {
        return level;
    }


    public void setLevel(int level)
    {
        this.level = level;
    }

    public float getMaxHealth()
    {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth)
    {
        float percentage;

        if (getMaxHealth() > 0)
        {
            percentage = getHealth() / getMaxHealth();
        }
        else
        {
            percentage = 1.0f;
        }

        this.maxHealth = maxHealth;

        // Update the health to continue as the same percentage of the max.
        setHealth( getMaxHealth() * percentage );
    }

    public float getHealth()
    {
        return health;
    }

    public void setHealth(float health)
    {
        this.health = health;
    }

    public float getMaxMana()
    {
        return maxMana;
    }

    public void setMaxMana(float maxMana)
    {
        float percentage;

        if (getMaxMana() > 0)
        {
            percentage = getMana() / getMaxMana();
        }
        else
        {
            percentage = 1.0f;
        }

        this.maxMana = maxMana;

        // Update the mana to continue as the same percentage of the max.
        setMana( getMaxMana() * percentage );
    }

    public float getMana()
    {
        return mana;
    }

    public void setMana(float mana)
    {
        this.mana = mana;
    }


    public float getMaxStamina()
    {
        return maxStamina;
    }


    public void setMaxStamina(float maxStamina)
    {
        float percentage;

        if (getMaxStamina() > 0)
        {
            percentage = getStamina() / getMaxStamina();
        }
        else
        {
            percentage = 1.0f;
        }

        this.maxStamina = maxStamina;

        // Update the stamina to continue as the same percentage of the max.
        setStamina( getMaxStamina() * percentage );
    }


    public float getStamina()
    {
        return stamina;
    }


    public void setStamina(float stamina)
    {
        this.stamina = stamina;
    }


    public float getStrength()
    {
        return strength;
    }

    public void setStrength(float strength)
    {
        this.strength = strength;
    }

    public float getDefense()
    {
        return defense;
    }

    public void setDefense(float defense)
    {
        this.defense = defense;
    }

    public float getDexterity()
    {
        return dexterity;
    }

    public void setDexterity(float dexterity)
    {
        this.dexterity = dexterity;
    }


    public float getIntelligence()
    {
        return intelligence;
    }


    public void setIntelligence(float intelligence)
    {
        this.intelligence = intelligence;
    }


    public ArrayList<Skill> getSkills()
    {
        return skills;
    }


    public ImageShape getMazeSprite()
    {
        return mazeSprite;
    }


    public void setMazeSprite(ImageShape mazeSprite)
    {
        this.mazeSprite = mazeSprite;
    }


    public ImageShape getBattleSprite()
    {
        return battleSprite;
    }


    public void setBattleSprite(ImageShape battleSprite)
    {
        this.battleSprite = battleSprite;
    }

}
