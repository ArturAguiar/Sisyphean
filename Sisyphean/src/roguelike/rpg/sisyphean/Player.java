package roguelike.rpg.sisyphean;


import android.graphics.PointF;

/**
* The class for the character controlled by the player.
*
* @author Artur
* @version Nov 3, 2012
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

    private float moveSpeed = 0.5f;

    /** The action being taken in battle */
    public enum BattleAction { ATTACKING, MOVING, IDLE };
    private BattleAction battleAction = BattleAction.IDLE;

    private PointF moveBy = new PointF();

    private float battleFrame = 0.0f;

    private int tempFrame = 0;

    // TODO: this should probably go to the Character class.
    private boolean battleMode = true;

    /**
* Method to be called when the player levels up.
*/
    abstract public void levelUp();

    @Override
    synchronized public void update()
    {
        // TODO: How do we know if we are in battle mode or maze exploration mode?

        // Update the armor location to always be covering the character.
        this.getArmor().getMazeSprite().setPosition(getPosition().x, getPosition().y);

        /* The walking movement and animation frame-change in maze mode.
* On frame 1 the character is standing, on frame 0 and 2 he is walking.
* Animation goes 1 -> 2 -> 1 -> 0 -> repeat (columns)
* and the row is determined by the facing direction enum type.
*/
        if (walking && !battleMode)
        {
            // Actual walking.
            if (moveBy.y > 0.0f)
            {
                this.getMazeSprite().move(0.0f, moveSpeed);
                moveBy.set(moveBy.x, moveBy.y - moveSpeed);
                if (moveBy.y < 0.0f)
                {
                    moveBy.set(moveBy.x, 0.0f);
                }
            }
            else if (moveBy.y < 0.0f)
            {
                this.getMazeSprite().move(0.0f, -moveSpeed);
                moveBy.set(moveBy.x, moveBy.y + moveSpeed);
                if (moveBy.y > 0.0f)
                {
                    moveBy.set(moveBy.x, 0.0f);
                }
            }
            else if (moveBy.x > 0.0f)
            {
                this.getMazeSprite().move(moveSpeed, 0.0f);
                moveBy.set(moveBy.x - moveSpeed, moveBy.y);
                if (moveBy.x < 0.0f)
                {
                    moveBy.set(0.0f, moveBy.y);
                }
            }
            else if (moveBy.x < 0.0f)
            {
                this.getMazeSprite().move(-moveSpeed, 0.0f);
                moveBy.set(moveBy.x + moveSpeed, moveBy.y);
                if (moveBy.x > 0.0f)
                {
                    moveBy.set(0.0f, moveBy.y);
                }
            }
            else
            {
                // This makes the character animation stop one frame after it
                // should, but I don't believe it will be a problem.
                walking = false;
                walkFrame = 1.0f;
            }

            // Walking animation.
            tempFrame = (int)(walkFrame);

            if (tempFrame == 3)
            {
                tempFrame = 1;
            }

            this.getMazeSprite().setCol(tempFrame);
            this.getArmor().getMazeSprite().setCol(tempFrame);

            walkFrame += 0.1f;

            if (walkFrame >= 4.0f)
            {
                walkFrame = 0;
            }
        }

        /*
* The animations in battle mode.
*/
        else if (battleMode)
        {
            tempFrame = (int)(battleFrame);

            this.getBattleSprite().setCol(tempFrame);

            battleFrame += 0.2f;

            switch (battleAction)
            {
                case IDLE:
                    if (battleFrame >= 6.0f)
                    {
                        battleFrame = 0.0f;
                    }
                    break;

                default:
                    if (battleFrame >= 8.0f)
                    {
                        battleFrame = 0.0f;
                        this.setBattleAction(BattleAction.IDLE);
                    }
                    break;
            }
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

    /**
     * Returns the player type/class.
     * @return The class of the player character.
     */
    public PlayerType getType()
    {
        return type;
    }

    /**
     * Sets the player's character type/class.
     * @param type The new type of the player.
     */
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

    /**
     * Orders the movement of the character in one of the main
     * cardinal directions.
     * @param direction The direction to move to.
     */
    public void move(String direction)
    {
        //TODO: I need to know the cell size here in order to calculate the movement.
        if (direction == null)
        {
            return;
        }
        else if (direction.equals("down"))
        {
            moveBy.set(0.0f, 30.0f);
            facing = Facing.DOWN;
        }
        else if (direction.equals("up"))
        {
            moveBy.set(0.0f, -30.0f);
            facing = Facing.UP;
        }
        else if (direction.equals("right"))
        {
            moveBy.set(30.0f, 0.0f);
            facing = Facing.RIGHT;
        }
        else if (direction.equals("left"))
        {
            moveBy.set(-30.0f, 0.0f);
            facing = Facing.LEFT;
        }
        else
        {
            return;
        }
        walking = true;
        this.getMazeSprite().setRow(facing.ordinal());
        this.getArmor().getMazeSprite().setRow(facing.ordinal());
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

}
