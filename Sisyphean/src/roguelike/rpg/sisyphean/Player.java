package roguelike.rpg.sisyphean;

import android.util.Log;
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

    private float experience = 0.0f;
    private float expToNextLevel;

    private Weapon weapon;
    private Armor armor;

    private enum Facing { DOWN, LEFT, RIGHT, UP };
    private Facing facing = Facing.DOWN;

    private float walkFrame = 1.0f;
    private boolean walking = false;

    private PointF moveBy = new PointF();

    private boolean attackCalled = false;

    private int currentCellX = 0;
    private int currentCellY = 0;

    private int healthPotions = 0;
    private int manaPotions = 0;

    private Sprite projectile;
    private boolean shootingProjectile = false;

    /**
    * Method to be called when the player levels up.
    */
    abstract public void levelUp();

    @Override
    public void update()
    {
        int tempFrame = 0;

        /* The walking animation frame-change in maze mode.
        * On frame 1 the character is standing, on frame 0 and 2 he is walking.
        * Animation goes 1 -> 2 -> 1 -> 0 -> repeat (columns)
        * and the row is determined by the facing direction enum type.
        */
        if (walking && !gameWorld.getBattling())
        {
            tempFrame = (int)(walkFrame);

            if (tempFrame == 3)
            {
                tempFrame = 1;
            }

            this.getMazeSprite().setCol(tempFrame);

            walkFrame += 0.5f;

            if (walkFrame >= 4.0f)
            {
                walkFrame = 0;
            }
        }
        else
        {
            walkFrame = 1.0f;
            this.getMazeSprite().setCol(1);
        }

        /*
        * The animations in battle mode.
        */
        if (gameWorld.getBattling())
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

                case MOVING:
                    if (battleFrame >= 8.0f)
                    {
                        battleFrame = 0.0f;
                    }

                    if (attackMove > 0.0f)
                    {
                        this.getBattleSprite().setPosition(this.getBattleSprite().getPosition().x - 5.0f,
                                                           this.getBattleSprite().getPosition().y);
                        this.attackMove -= 5.0f;
                    }
                    else
                    {
                        this.attackMove = 0.0f;
                        this.battleFrame = 0.0f;
                        this.setBattleAction(BattleAction.ATTACKING);
                    }
                    break;

                case ATTACKING:
                    if (this.getType() != PlayerType.ARCHER && !attackCalled && battleFrame >= 4.0f)
                    {
                        attackCalled = true;

                        // Make a callback to the BattleScreen to let it know that the attack was performed.
                        if (this.getBattleObserver() != null)
                            this.getBattleObserver().playerAttackDone();
                    }
                    else if (battleFrame >= 8.0f)
                    {
                        battleFrame = 0.0f;
                        this.setBattleAction(BattleAction.IDLE);
                        this.getBattleSprite().setPosition(this.getInitialBattlePosition(),
                                                           this.getBattleSprite().getPosition().y);
                        attackCalled = false;

                        if (this.getType() == PlayerType.ARCHER)
                            projectile.setCol(1);
                            shootingProjectile = true;
                    }

                    break;

                case HURT:
                    if (battleFrame >= 6.0f)
                    {
                        battleFrame = 0.0f;
                        this.setBattleAction(BattleAction.IDLE);
                    }
                    break;

                case DEAD:
                    if (battleFrame >= 8.0f)
                    {
                        battleFrame = 7.0f;
                        this.setAlive(false);
                    }
                    break;

                case CASTING:
                    if (!attackCalled && battleFrame >= 4.0f)
                    {
                        attackCalled = true;
                        // Make a callback to the BattleScreen to let it know that the attack was performed.
                        if (this.getBattleObserver() != null)
                            this.getBattleObserver().playerCastingDone();
                    }
                    else if (battleFrame >= 8.0f)
                    {
                        battleFrame = 0.0f;
                        this.setBattleAction(BattleAction.IDLE);
                        attackCalled = false;
                    }
                    break;
            }

            // Projectile animation (archer)
            if (shootingProjectile && projectile != null)
            {
                projectile.move(-5.0f, 0.0f);

                if (projectile.getPosition().x <= gameWorld.getDisplayMetrics().widthPixels / 3.0f - 50.0f )
                {
                    shootingProjectile = false;
                    projectile.setCol(0);
                    projectile.setPosition(this.getInitialBattlePosition(), projectile.getPosition().y);

                    this.getBattleObserver().playerAttackDone();
                }
            }
        }

    }

    @Override
    public void attack()
    {
        if (battleAction == BattleAction.IDLE)
        {
            if (this.getType() == PlayerType.ARCHER)
            {
                this.setBattleAction(BattleAction.ATTACKING);
            }
            else
            {
                attackMove = gameWorld.getDisplayMetrics().widthPixels / 3.0f - 50.0f;
                this.setBattleAction(BattleAction.MOVING);
            }

        }
    }

    /**
     * Starts the casting animation.
     * This is meant to be called by the battle screen.
     */
    public void castMagic()
    {
        if (battleAction == BattleAction.IDLE)
        {
            this.setBattleAction(BattleAction.CASTING);
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
        float damageDone = enemy.getStrength() - getDefense() - this.getArmor().getDefense();

        Log.v("Player", "Damage received: " + damageDone);

        this.setBattleAction(BattleAction.HURT);

        if ( damageDone > 0 )
        {
            this.setHealth( this.getHealth() - damageDone );
            Log.v("Player", "Health left: " + this.getHealth());

            if (this.getHealth() <= 0.0f)
            {
                this.setBattleAction(BattleAction.DEAD);
                this.getBattleObserver().playerDied();
            }

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
    protected void setType(PlayerType type)
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
    * Increases the player's experience by the value given.
    * @param experienceToAdd The experience gained by the player.
    */
    public void addExperience(float experienceToAdd)
    {
        this.experience += experienceToAdd;

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
    protected void setExpToNextLevel(float expToNextLevel)
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
     * @param amount The amount of pixels to move (should be the cell size).
     */
    public void move(String direction, float amount)
    {
        if (!walking)
        {
            if (direction == null)
            {
                return;
            }
            else if (direction.equals("down"))
            {
                moveBy.set(0.0f, amount);
                facing = Facing.DOWN;
                currentCellY++;
            }
            else if (direction.equals("up"))
            {
                moveBy.set(0.0f, -amount);
                facing = Facing.UP;
                currentCellY--;
            }
            else if (direction.equals("right"))
            {
                moveBy.set(amount, 0.0f);
                facing = Facing.RIGHT;
                currentCellX++;
            }
            else if (direction.equals("left"))
            {
                moveBy.set(-amount, 0.0f);
                facing = Facing.LEFT;
                currentCellX--;
            }
            else
            {
                return;
            }
            walking = true;
            this.getMazeSprite().setRow(facing.ordinal());
        }
    }

    // ----------------------------------------------------------
    /**
     * Accessor for the x coordinate of the cell the player is in.
     * @return currentCellX The x coordinate
     */
    public int getCellX()
    {
        return currentCellX;
    }

    // ----------------------------------------------------------
    /**
     * Accessor for the y coordinate of the cell the player is in.
     * @return currentCell The y coordinate
     */
    public int getCellY()
    {
        return currentCellY;
    }

    /**
     * Mutator for the cell the player is in.
     * @param x The x coordinate of the cell the player is being moved to
     * @param y The y coordinate of the cell the player is being moved to
     */
    public void setCell(int x, int y)
    {
        currentCellX = x;
        currentCellY = y;
    }

    /**
     * Accessor for the walking boolean.
     * @return boolean Whether the player is walking.
     */
    public boolean isWalking()
    {
        return walking;
    }

    /**
     * Accessor for the number of health potions.
     * @return int The number of potions
     */
    public int getHealthPotions()
    {
        return healthPotions;
    }

    /**
     * Accessor for the number of mana potions.
     * @return int The number of potions
     */
    public int getManaPotions()
    {
        return manaPotions;
    }

    // ----------------------------------------------------------
    /**
     * Allows the player to pick up a potion.
     * @param potion The type of potion
     */
    public void pickUpPotion(PotionType potion)
    {
        switch (potion)
        {
            case HEALTH:
            {
                healthPotions++;
            }
                break;

            case MANA:
            {
                manaPotions++;
            }
                break;
        }
    }

    // ----------------------------------------------------------
    /**
     * Tells the player to consume a potion.
     * @param potion The type of potion
     */
    public void consumePotion(PotionType potion)
    {
        switch (potion)
        {
            case HEALTH:
                if (healthPotions > 0)
                {
                    if (Potion.fullRestore())
                    {
                        setHealth(getMaxHealth());
                    }
                    else
                    {
                        setHealth(getHealth() + getMaxHealth() * 0.25f);
                    }
                    healthPotions--;
                }
                break;

            case MANA:
                if (manaPotions > 0)
                {
                    if (Potion.fullRestore())
                    {
                        setMana(getMaxMana());
                    }
                    else
                    {
                        setMana(getMana() + getMaxMana() * 0.25f);
                    }
                    manaPotions--;
                }
                break;
        }
    }

    /**
     * Starts the moving animation facing the direction given.
     * @param direction The direction that the player is moving.
     */
    public void startMoving(String direction)
    {
        if (direction == null)
        {
            return;
        }
        else if (direction.equals("down"))
        {
            facing = Facing.DOWN;
            //currentCellY++;
        }
        else if (direction.equals("up"))
        {
            facing = Facing.UP;
            //currentCellY--;
        }
        else if (direction.equals("right"))
        {
            facing = Facing.RIGHT;
            //currentCellX++;
        }
        else if (direction.equals("left"))
        {
            facing = Facing.LEFT;
            //currentCellX--;
        }
        else
        {
            return;
        }

        walking = true;
        this.getMazeSprite().setRow(facing.ordinal());
    }

    /**
     * Stops moving animation and updates the current player cell.
     */
    public void stopMoving()
    {
        if (facing == Facing.UP)
        {
            currentCellY--;
        }
        else if (facing == Facing.DOWN)
        {
            currentCellY++;
        }
        else if (facing == Facing.LEFT)
        {
            currentCellX--;
        }
        else if (facing == Facing.RIGHT)
        {
            currentCellX++;
        }

        Log.v("Player", "Position = (" + this.getCellX() + ", " + this.getCellY() + ")");
        walking = false;
        walkFrame = 1.0f;
    }

    /**
     * Returns the projectile sprite. Used for the archer class.
     * @return The projectile fired when the player attacks (arrow).
     */
    public Sprite getProjectile()
    {
        return projectile;
    }

    /**
     * Sets the projectile sprite to a new value.
     * @param projectile The new sprite for the projectile (arrow).
     */
    public void setProjectile(Sprite projectile)
    {
        this.projectile = projectile;
    }
}
