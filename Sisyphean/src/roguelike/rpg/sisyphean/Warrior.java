package roguelike.rpg.sisyphean;

import android.graphics.RectF;
import sofia.graphics.ImageShape;

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
     */
    public Warrior(String name, float x, float y)
    {
        this.setName(name);
        this.setType(PlayerType.WARRIOR);

        this.setMaxHealth(120.0f);
        this.setMaxMana(100.0f);
        this.setMaxStamina(120.0f);

        this.setStrength(30.0f);
        this.setDefense(25.0f);
        this.setDexterity(12.0f);
        this.setIntelligence(10.0f);

        this.setLevel(1);
        this.setExpToNextLevel(10.0f);
        this.setExperience(0.0f);

        // Set the base sprite for the player
        this.setMazeSprite(new ImageShape(R.drawable.male_base,
            new RectF(0.0f, 0.0f, 32.0f, 32.0f)) );
        this.getMazeSprite().setSourceBounds(32, 0, 64, 32);

        // Set the location of the sprite based on the given parameters.
        this.setPosition(x, y);


        // Initial equipment
        this.setArmor(new Armor(
            "Prisoner Garments",
            "Rusty handcuffs and dark pants.%nHardly any protection.",
            2.0f,
            new ImageShape(R.drawable.prisoner_garments,
                           new RectF(0.0f, 0.0f, 32.0f, 32.0f)) ));

        this.setWeapon(new Weapon(
            "Rusty Dagger",
            "If tetanus killed quickly, this would actually be half-decent.",
            6.0f));
        this.getWeapon().addBonusDamage("ZOMBIE", 2.0f);
    }

    @Override
    public void levelUp()
    {
        // Increase statuses.
        this.setMaxHealth(getMaxHealth() + 13.0f);
        this.setMaxStamina(getMaxStamina() + 12.0f);
        this.setMaxMana(getMaxMana() + 8.0f);
        this.setStrength(getStrength() + 13.0f);
        this.setDefense(getDefense() + 12.0f);
        this.setDexterity(getDexterity() + 6.0f);
        this.setIntelligence(getIntelligence() + 5.0f);

        // Update the level and experience.
        this.setLevel(getLevel() + 1);
        this.setExperience(getExperience() - getExpToNextLevel());
        this.setExpToNextLevel(getExpToNextLevel() * 2.0f);

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

        // Check if the player leveled up again.
        if ( getExperience() >= getExpToNextLevel() )
        {
            levelUp();
        }
    }

}
