package roguelike.rpg.sisyphean;

import android.graphics.PointF;
import java.util.HashSet;

/**
 *  The abstract class for every character in the game.
 *  This could be a player, an enemy and etc.
 *
 *  @author Artur
 *  @version Nov 3, 2012
 */
abstract public class Character
{
    /////FIELDS-----------------------------------------------------------------

    /** A reference to the game world. */
    protected GameWorld gameWorld;

    // Name
    private String name;

    // Level
    private int level;

    // Health
    private float health;

    // Mana
    private float mana;

    // Stamina
    //private float stamina;

    // Statuses
    private float maxHealth;
    private float maxMana;
    //private float maxStamina;
    private float strength;
    private float defense;
    private float dexterity;
    private float intelligence;

    // Types

    /** The different player types/classes available. */
    public enum PlayerType
    {
        /** Warrior class. */
        WARRIOR,
        /** Wizard class. */
        WIZARD,
        /** Archer class. */
        ARCHER
    };

    /** The different enemy types/classes available. */
    public enum EnemyType
    {
        /** Zombie enemy. */
        ZOMBIE,
        /** Skeleton enemy. */
        SKELETON,
        /** Orc enemy. */
        ORC
    };

    // Battle actions.

    /** The different actions in battle */
    public enum BattleAction
    {
        /** When the character is attacking. */
        ATTACKING,
        /** When the character is moving to attack */
        MOVING,
        /** When the character is idle in battle. */
        IDLE,
        /** When the character has been hit. */
        HURT,
        /** When the character is dying. */
        DEAD,
        /** When the character is casting magic. Only implemented for players. */
        CASTING
    };

    /** The action being taken in battle */
    protected BattleAction battleAction = BattleAction.IDLE;

    /** The current frame of the battle animation */
    protected float battleFrame = 0.0f;

    /** The amount movement to attack. */
    protected float attackMove = 0.0f;

    private float initialBattlePosition;

    private boolean alive = true;

    //Skills.
    //private HashSet<Skill> skills;

    //Magics.
    private HashSet<Magic> magics;


    // Sprites
    /** The sprite to use when not in battle */
    private Sprite mazeSprite;

    /** The sprite to use while in battle */
    private Sprite battleSprite;


    // Observers
    private BattleScreen battleObserver;



    /////METHODS----------------------------------------------------------------

    // Abstract

    /**
     * The method called to run any update logic on this character.
     */
    abstract public void update();

    /**
     *
     */
    abstract public void attack();


    // Getters and setters

    /**
     * Gets the name of the character.
     * @return String The name of the character
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the character.
     * @param name The new name of the character
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Retrieves the characters level.
     * @return int The integer value of the level that the character is
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * Sets the level of the character.
     * @param level The new level of the character
     */
    protected void setLevel(int level)
    {
        this.level = level;
    }

    /**
     * Retrieves the max health of the character.
     * @return float The value returned for your health
     */
    public float getMaxHealth()
    {
        return maxHealth;
    }

    /**
     * Sets the max health of the character.
     * @param maxHealth the new max health for the character
     */
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

    /**
     * Retrieves the current health of the character.
     * @return float The current health value
     */
    public float getHealth()
    {
        return health;
    }

    /**
     * Sets the current health of the character.
     * @param health The new current health
     */
    public void setHealth(float health)
    {
        if ( health > getMaxHealth() )
        {
            this.health = getMaxHealth();
        }
        else if (health < 0)
        {
            this.health = 0.0f;
        }
        else
        {
            this.health = health;
        }

    }

    /**
     * Retrieves the max mana.
     * @return float The max mana of the character
     */
    public float getMaxMana()
    {
        return maxMana;
    }

    /**
     * Sets the max mana of the character.
     * @param maxMana The new max mana
     */
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

    /**
     * The current mana of the character.
     * @return float The characters current mana
     */
    public float getMana()
    {
        return mana;
    }

    /**
     * Sets the current mana of the character.
     * @param mana The new current mana
     */
    public void setMana(float mana)
    {
        if ( mana > getMaxMana() )
        {
            this.mana = getMaxMana();
        }
        else if (mana < 0.0f)
        {
            this.mana = 0.0f;
        }
        else
        {
            this.mana = mana;
        }
    }

    /**
     * Retrieves the max stamina of the character.
     * @return float The stamina value of the character
     */
    /*
    public float getMaxStamina()
    {
        return maxStamina;
    }
    */

    /**
     * Sets the max stamina of the character.
     * @param maxStamina The new max stamina of the character
     */
    /*
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
    */

    /**
     * Retrieves the current stamina of the character.
     * @return float The current stamina of the character
     */
    /*
    public float getStamina()
    {
        return stamina;
    }
    */

    /**
     * Sets the current stamina of the character.
     * @param stamina The new current stamina
     */
    /*
    public void setStamina(float stamina)
    {
        if ( stamina > getMaxStamina() )
        {
            this.stamina = getMaxStamina();
        }
        else
        {
            this.stamina = stamina;
        }
    }
    */

    /**
     * Returns the current strength of the character.
     * @return float The current strength of the character
     */
    public float getStrength()
    {
        return strength;
    }

    /**
     * Sets the current strength of the character.
     * @param strength The new current strength of the character
     */
    public void setStrength(float strength)
    {
        this.strength = strength;
    }

    /**
     * Returns the current defense value of the character.
     * @return float The current defense value
     */
    public float getDefense()
    {
        return defense;
    }

    /**
     * Sets the current defense value of the character
     * @param defense The new current defense value for the character
     */
    public void setDefense(float defense)
    {
        this.defense = defense;
    }

    /**
     * Returns the current dexterity of the character.
     * @return float The current dexterity of the character
     */
    public float getDexterity()
    {
        return dexterity;
    }

    /**
     * Sets the current dexterity of the character.
     * @param dexterity The new current dexterity of the character
     */
    public void setDexterity(float dexterity)
    {
        this.dexterity = dexterity;
    }

    /**
     * Returns the intelligence value of the character.
     * @return float The current intelligence value
     */
    public float getIntelligence()
    {
        return intelligence;
    }

    /**
     * Sets the current intelligence value of the character.
     * @param intelligence The new current intelligence of the character
     */
    public void setIntelligence(float intelligence)
    {
        this.intelligence = intelligence;
    }

    /**
     * Returns the skills the character has.
     * @return All the skills of this character.
     */
    /*
    public HashSet<Skill> getSkills()
    {
        if (skills == null)
        {
            skills = new HashSet<Skill>();
        }

        return skills;
    }
    */

    /**
     * Returns the magics the character has.
     * @return All the magics of this character.
     */
    public HashSet<Magic> getMagics()
    {
        if (magics == null)
        {
            magics = new HashSet<Magic>();
        }

        return magics;
    }

    /**
     * Returns an image of the sprite.
     * @return ImageShape The image of the sprite
     */
    public Sprite getMazeSprite()
    {
        return mazeSprite;
    }

    /**
     * Sets the image of the sprite.
     * @param mazeSprite Sets the maze sprite image
     */
    public void setMazeSprite(Sprite mazeSprite)
    {
        this.mazeSprite = mazeSprite;

        //mazeFrameWidth = frameWidth;
        //mazeFrameHeight = frameHeight;
    }

    /**
     * Returns the battle sprite image.
     * @return ImageShape the battle sprite image
     */
    public Sprite getBattleSprite()
    {
        return battleSprite;
    }

    /**
     * Sets the battle sprite image.
     * @param battleSprite The battle sprite image
     */
    public void setBattleSprite(Sprite battleSprite)
    {
        this.battleSprite = battleSprite;
    }


    /**
     * Changes the action being taken during battle.
     * @param action The new action being taken.
     */
    public void setBattleAction(BattleAction action)
    {
        battleFrame = 0.0f;
        battleAction = action;
        this.getBattleSprite().setRow(battleAction.ordinal());
    }

    /**
     * Returns this character's position, which is the same as the position of
     * it's sprite.
     * @return The character's position.
     */
    public PointF getPosition()
    {
        return getMazeSprite().getPosition();
    }

    /**
     * Changes the characters position by changing its sprite position.
     * @param x The x coordinate of the new position.
     * @param y The y coordinate of the new position.
     */
    public void setPosition(float x, float y)
    {
        if (getMazeSprite() != null)
        {
            this.getMazeSprite().setPosition(x, y);
        }
    }

    /**
     * Returns the default battle position of this character.
     * @return The default battle position of this character.
     */
    public float getInitialBattlePosition()
    {
        return initialBattlePosition;
    }

    /**
     * Sets the initial/default position of this character in battle.
     * @param initialBattlePosition The initial/default position of this
     *        character in battle.
     */
    public void setInitialBattlePosition(float initialBattlePosition)
    {
        this.initialBattlePosition = initialBattlePosition;
    }

    /**
     * Returns the battle observer of this character. The screen to receive
     * callbacks when animations finish.
     * @return The battle observer of this character.
     */
    protected BattleScreen getBattleObserver()
    {
        return battleObserver;
    }

    /**
     * The battle observer setter.
     * Meant to be called by the battle screen passing itself when initialized.
     * @param battleObserver The reference to the battle screen.
     */
    public void setBattleObserver(BattleScreen battleObserver)
    {
        this.battleObserver = battleObserver;
    }

    /**
     * Returns if the character is alive or not.
     * @return True if the character is alive, false otherwise.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Determines if the character is alive or not.
     * Dead characters do not update animations and etc.
     * @param alive If the character is alive or not.
     */
    public void setAlive(boolean alive)
    {
        this.alive = alive;
    }
}
