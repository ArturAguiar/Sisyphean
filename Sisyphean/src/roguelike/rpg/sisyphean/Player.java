package roguelike.rpg.sisyphean;


import android.graphics.Rect;

/**
 *  The class for the character controlled by the player.
 *
 *  @author Artur
 *  @version Nov 3, 2012
 */
abstract public class Player extends Character
{
    private PlayerType type;

    private float experience;
    private float expToNextLevel;

    private Weapon weapon;
    private Armor armor;

    private enum Facing { DOWN, LEFT, RIGHT, UP };
    private Facing facing = Facing.DOWN;

    private float walkFrame = 1.0f;
    private boolean walking = false;

    /**
     * Method to be called when the player levels up.
     */
    abstract public void levelUp();

    @Override
    public void update()
    {
        // TODO: How do we know if we are in battle mode or maze exploration mode?

        // Update the armor location to always be covering the character.
        this.getArmor().getMazeSprite().setPosition(getPosition());

        /* The walking animation frame-change.
         * On frame 1 the character is standing, on frame 0 and 2 he is walking.
         * Animation goes 1 -> 2 -> 1 -> 0 -> repeat (columns)
         * and the row is determined by the facing direction enum type. */
        if (walking)
        {
            int temp = (int)(walkFrame);

            if (temp == 3)
            {
                temp = 1;
            }

            Rect source = new Rect(32 * temp, 32 * facing.ordinal(),
                                   32 * temp + 32, 32 * facing.ordinal() + 32);

            this.getMazeSprite().setSourceBounds(source);
            this.getArmor().getMazeSprite().setSourceBounds(source);

            walkFrame += 0.1f;

            if (walkFrame >= 4.0f)
            {
                walkFrame = 0;
            }
        }
        else
        {
            walkFrame = 1.0f;
        }
    }

    /**
     * The method called when the player gets hit by an enemy.
     * The damage returned by this method will be displayed over the enemy when
     * the attack is done.
     * @param enemy The enemy hitting the player.
     * @return The total damage done.
     */
    public float wasHit(Enemy enemy)
    {
        // TODO: This is probably not the best way to calculate things...
        float damageDone = enemy.getStrength() - getDefense();
        if ( damageDone > 0 )
        {
            this.setHealth( getHealth() - damageDone );
            return damageDone;
        }

        return 0.0f;
    }


    public PlayerType getType()
    {
        return type;
    }

    public void setType(PlayerType type)
    {
        this.type = type;
    }

    /**
     * The experience getter.
     * @return The experience of the player.
     */
    public float getExperience()
    {
        return experience;
    }

    /**
     * The experience setter.
     * @param experience The new experience of the player.
     */
    public void setExperience(float experience)
    {
        this.experience = experience;

        if (this.experience >= getExpToNextLevel())
        {
            this.levelUp();
        }
    }

    /**
     * The getter for the experience required to get to the next level.
     * @return The experience required to get to the next level.
     */
    public float getExpToNextLevel()
    {
        return expToNextLevel;
    }

    /**
     * The setter for the experience required to get to the next level.
     * @param expToNextLevel The new value for the experience required to reach
     * the next level.
     */
    public void setExpToNextLevel(float expToNextLevel)
    {
        this.expToNextLevel = expToNextLevel;
    }

    /**
     * The player's armor getter.
     * @return The player's current armor.
     */
    public Armor getArmor()
    {
        return armor;
    }

    /**
     * The player's armor setter.
     * @param armor The new armor for the player to equip.
     */
    public void setArmor(Armor armor)
    {
        this.armor = armor;
    }

    /**
     * The player's Weapon getter.
     * @return The player's current weapon.
     */
    public Weapon getWeapon()
    {
        return weapon;
    }

    /**
     * The player's weapon setter.
     * @param weapon The new weapon for the player to equip.
     */
    public void setWeapon(Weapon weapon)
    {
        this.weapon = weapon;
    }

    /* Don't think we'll need this.
    public Facing getFacing()
    {
        return facing;
    }

    public void setFacing(Facing facing)
    {
        this.facing = facing;
    }
    */

    public void moveBy(float x, float y)
    {
        // TODO: I think that this should take the maze cell coordinates instead of regular coordinates.
        walking = true;
        this.getMazeSprite().animate(1500).name("walk").moveBy(x, y).play();
    }

    public void walkAnimationEnded()
    {
        walking = false;
    }

}
