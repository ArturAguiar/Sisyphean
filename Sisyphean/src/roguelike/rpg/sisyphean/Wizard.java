package roguelike.rpg.sisyphean;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Tk
 *  @version Nov 27, 2012
 */
public class Wizard
    extends Player
{
    // ----------------------------------------------------------
    /**
     * Create a new Wizard object.
     * @param name
     * @param x
     * @param y
     * @param gameWorld
     */
    public Wizard(String name, float x, float y, GameWorld gameWorld)
    {
        this.gameWorld = gameWorld;

        this.setName(name);
        this.setType(PlayerType.WIZARD);

        this.setMaxHealth(120.0f);
        this.setMaxMana(100.0f);
        //this.setMaxStamina(120.0f);

        // I swapped the strength and intelligence skill number.
        // Change them if you need to!
        this.setStrength(10.0f);
        this.setDefense(25.0f);
        this.setDexterity(12.0f);
        this.setIntelligence(30.0f);

        this.setLevel(1);
        this.setExpToNextLevel(10.0f);

        // The sprite in maze mode.
        this.setMazeSprite(new Sprite(R.drawable.wizard, 96, 128, 3, 4, gameWorld.getDisplayMetrics().density));
        this.getMazeSprite().setCol(1);

        // The sprite in battle mode.
        this.setBattleSprite(new Sprite(R.drawable.wizard_sheet, 1040, 840, 8, 6, gameWorld.getDisplayMetrics().density));
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
            "Broken Stick",
            "From small beginnings.",
            6.0f,
            gameWorld));
    }

    @Override
    public void levelUp()
    {
        // I changed the stats so that it would make sense for a wizard class.
        // Increase statuses.
        this.setMaxHealth(getMaxHealth() + 13.0f);
        //this.setMaxStamina(getMaxStamina() + 12.0f);
        this.setMaxMana(getMaxMana() + 13.0f);
        this.setStrength(getStrength() + 8.0f);
        this.setDefense(getDefense() + 12.0f);
        this.setDexterity(getDexterity() + 6.0f);
        this.setIntelligence(getIntelligence() + 13.0f);

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
