package roguelike.rpg.sisyphean;

import sofia.app.ShapeScreen;

/**
 *  The class for the character controlled by the player.
 *
 *  @author Artur
 *  @version Nov 3, 2012
 */
abstract public class Player extends Character
{
    private float experience;
    private float expToNextLevel;

    private Weapon weapon;
    private Armor armor;

    /**
     * Method to be called when the player levels up.
     */
    abstract public void levelUp();

    @Override
    public void drawMe(ShapeScreen screen)
    {
        // TODO: How do we know if we are in battle mode or maze exploration mode?

        //if ( in maze mode )
        //screen.add( getMazeSprite() );
        //screen.add( armor.getMazeSprite() );

        //else if ( in battle mode )
        //screen.add( getBattleSprite() );
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

}
