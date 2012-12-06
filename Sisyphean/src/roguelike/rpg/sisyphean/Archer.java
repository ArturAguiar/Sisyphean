package roguelike.rpg.sisyphean;

// -------------------------------------------------------------------------
/**
 *  This class contains the class for a player to be an archer type character.
 *  The archer class has a name, health, mana, stamina, strength, defense,
 *  dexterity, and intelligence stats. This class contains one method which
 *  is levelup which levels up the player.
 *
 *  @author Tk
 *  @version Nov 27, 2012
 */
public class Archer
    extends Player
{

    // ----------------------------------------------------------
    /**
     * Creates a new Archer object.
     * @param name The name of the character.
     * @param x The row value of the player
     * @param y The column value of the player
     * @param gameWorld The game world where the player is playing in
     */
    public Archer(String name, float x, float y, GameWorld gameWorld)
    {
        this.gameWorld = gameWorld;

        this.setName(name);
        this.setType(PlayerType.ARCHER);

        this.setMaxHealth(120.0f);
        this.setMaxMana(100.0f);
        //this.setMaxStamina(120.0f);

        // I swapped the strength and intelligence skill number.
        // Change them if you need to!
        this.setStrength(20.0f);
        this.setDefense(25.0f);
        this.setDexterity(22.0f);
        this.setIntelligence(10.0f);

        this.setLevel(1);
        this.setExpToNextLevel(10.0f);

        // The sprite in maze mode.
        this.setMazeSprite(new Sprite(R.drawable.male_base, 96, 128, 3, 4, gameWorld.getDisplayMetrics().density));
        this.getMazeSprite().setCol(1);

        // The sprite in battle mode.
        this.setBattleSprite(new Sprite(R.drawable.crusader_sprite_sheet, 1440, 390, 8, 3, gameWorld.getDisplayMetrics().density));
        this.getBattleSprite().setRow(2);


        // Set the location of the sprite based on the given parameters.
        this.setPosition(x, y);


        // Initial equipment
        this.setArmor(new Armor(
            "Prisoner Garments",
            "Rusty handcuffs and dark pants. Hardly any protection.",
            2.0f, gameWorld));
        this.getArmor().getMazeIcon().setPosition(x, y);

        // Anyway to change to a staff or rod??
        // -tk
        this.setWeapon(new Weapon(
            "Rusty Dagger",
            "If tetanus killed quickly, this would actually be half-decent.",
            6.0f,
            gameWorld));
        this.getWeapon().addBonusDamage("ZOMBIE", 2.0f);
    }


    /**
     * This method levels up the character by changing its stats and setting
     * them to their new values. If the character has leveled up twice in one
     * battle, then a recursive call is made.
     */
    @Override
    public void levelUp()
    {
     // I changed the stats so that it would make sense for a wizard class.
        // Increase statuses.
        this.setMaxHealth(getMaxHealth() + 11.0f);
        //this.setMaxStamina(getMaxStamina() + 12.0f);
        this.setMaxMana(getMaxMana() + 13.0f);
        this.setStrength(getStrength() + 8.0f);
        this.setDefense(getDefense() + 12.0f);
        this.setDexterity(getDexterity() + 11.0f);
        this.setIntelligence(getIntelligence() + 10.0f);

        // Update the level and experience.
        this.setLevel(getLevel() + 1);
        this.setExpToNextLevel(getExpToNextLevel() * 2.5f);

        // Update skills.
        /*
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

        // Check if the player leveled up again.
        if ( getExperience() >= getExpToNextLevel() )
        {
            levelUp();
        }

    }


}
