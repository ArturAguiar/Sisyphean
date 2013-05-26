package roguelike.rpg.sisyphean;

/**
 * // -------------------------------------------------------------------------
/**
 *  The warrior specializes in melee combat.
 *  His health, strength, and stamina increase faster as he levels up.
 *
 *  @author Artur
 *  @version Nov 3, 2012
 */
public class Warrior extends Player
{
    /**
     * Warrior constructor.
     * Initializes all the statuses.
     * Adds initial equipment.
     *
     * @param name The name of the warrior (player).
     * @param x The x coordinate of the warrior (player).
     * @param y The y coordinate of the warrior (player).
     * @param gameWorld The reference to the game world.
     */
    public Warrior(String name, float x, float y, GameWorld gameWorld)
    {
        this.gameWorld = gameWorld;

        this.setName(name);
        this.setType(PlayerType.WARRIOR);

        this.setMaxHealth(120.0f);
        this.setMaxMana(100.0f);
        //this.setMaxStamina(120.0f);

        this.setStrength(30.0f);
        this.setDefense(25.0f);
        this.setDexterity(12.0f);
        this.setIntelligence(5.0f);

        this.setLevel(1);
        this.setExpToNextLevel(10.0f);

        // The sprite in maze mode.
        this.setMazeSprite(new Sprite(R.drawable.warrior, 96, 128, 3, 4, gameWorld.getDisplayMetrics().density));
        this.getMazeSprite().setCol(1);

        // The sprite in battle mode.
        this.setBattleSprite(new Sprite(R.drawable.crusader_sprite_sheet, 1440, 780, 8, 6, gameWorld.getDisplayMetrics().density));
        this.getBattleSprite().setRow(2);


        // Set the location of the sprite based on the given parameters.
        this.setPosition(x, y);


        // Initial equipment.
        this.setArmor(new Armor(
            "Iron Chest",
            "Iron chestpiece.",
            2.0f, gameWorld));
        this.getArmor().getMazeIcon().setPosition(x, y);

        this.setWeapon(new Weapon(
            "Longsword",
            "Moderately made, but size makes it proficient against Zombies.",
            7.0f,
            gameWorld));
        this.getWeapon().addBonusDamage("ZOMBIE", 4.0f);

        // Initial magics.
        getMagics().add(new Magic(
            "Constrict",
            "Constricts the enemy causing at least 30 damage.",
            30.0f, 25.0f, false));
    }

    @Override
    public void levelUp()
    {
        // Increase statuses.
        this.setMaxHealth(getMaxHealth() + getMaxHealth() * 0.2F);
        //this.setMaxStamina(getMaxStamina() + 12.0f);
        this.setMaxMana(getMaxMana() + getMaxMana() * 0.08F);
        this.setStrength(getStrength() + getStrength() * 0.2F);
        this.setDefense(getDefense() + getDefense() * 0.2F);
        this.setDexterity(getDexterity() + getDexterity() * 0.08F);
        this.setIntelligence(getIntelligence() + getIntelligence() * 0.05F);

        // Update the level and experience.
        this.setLevel(getLevel() + 1);
        this.setExpToNextLevel(getExpToNextLevel() * 2.5f);

        //Update magic for the warrior.
        switch (getLevel())
        {
            case 3:
                getMagics().add(new Magic(
                    "Minor Heal",
                    "Heals the warrior at least 20 health.",
                    20.0f, 15.0f, true));
                break;

            case 9:
                getMagics().add(new Magic(
                    "Ice Pick",
                    "Deals moderate damage to the enemy",
                    30.0f, 25.0f, false));
                break;

            case 15:
                getMagics().add(new Magic(
                    "High Heal",
                    "Heals at lot of health for a lot of mana.",
                    180.0f, 80.0f, true));
                break;

            case 20:
                getMagics().add(new Magic(
                    "Vice Grip",
                    "Hurts the enemy with all of the warrior's arcane might.",
                    150.0f, 100.0f, false));
                break;
        }

        // Check if the player leveled up again.
        if ( getExperience() >= getExpToNextLevel() )
        {
            levelUp();
        }
    }

}