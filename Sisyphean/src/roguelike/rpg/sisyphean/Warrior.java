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
        this.setIntelligence(10.0f);

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
            "Prisoner Garments",
            "Rusty handcuffs and dark pants. Hardly any protection.",
            2.0f, gameWorld));
        this.getArmor().getMazeIcon().setPosition(x, y);

        this.setWeapon(new Weapon(
            "Rusty Dagger",
            "If tetanus killed quickly, this would actually be half-decent.",
            6.0f,
            gameWorld));
        this.getWeapon().addBonusDamage("ZOMBIE", 2.0f);

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
        this.setMaxHealth(getMaxHealth() + 13.0f);
        //this.setMaxStamina(getMaxStamina() + 12.0f);
        this.setMaxMana(getMaxMana() + 8.0f);
        this.setStrength(getStrength() + 13.0f);
        this.setDefense(getDefense() + 12.0f);
        this.setDexterity(getDexterity() + 6.0f);
        this.setIntelligence(getIntelligence() + 5.0f);

        // Update the level and experience.
        this.setLevel(getLevel() + 1);
        this.setExpToNextLevel(getExpToNextLevel() * 2.5f);

        /*
        // Update skills.
        switch (getLevel())
        {

            case 3:
                getSkills().add(new Skill(
                    "Charged Slash",
                    "Concentrate your powers before dealing the blow.",
                    1.5f,
                    20.0f));
                break;

            case 5:
                getSkills().add(new Skill(
                    "Double Slash",
                    "Strike your foe twice in quick succession.",
                    2.0f,
                    28.0f));
                break;
        }
         */

        //Update magic.
        switch (getLevel())
        {
            case 3:
                getMagics().add(new Magic(
                    "Minor Heal",
                    "Heals the warrior for at least 60HP.",
                    60.0f, 20.0f, true));
        }

        // Check if the player leveled up again.
        if ( getExperience() >= getExpToNextLevel() )
        {
            levelUp();
        }
    }

}