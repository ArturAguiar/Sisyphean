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

        this.setMaxHealth(100.0f);
        this.setMaxMana(120.0f);
        //this.setMaxStamina(120.0f);

        // I swapped the strength and intelligence skill number.
        // Change them if you need to!
        this.setStrength(10.0f);
        this.setDefense(20.0f);
        this.setDexterity(15.0f);
        this.setIntelligence(27.0f);

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
            "Wizards Robe",
            "Wizards robing. Cloth material.",
            2.0f, gameWorld));
        this.getArmor().getMazeIcon().setPosition(x, y);

        this.setWeapon(new Weapon(
            "Broken Stick",
            "From small beginnings.",
            6.0f,
            gameWorld));

        // Initial Magic.
        getMagics().add(new Magic(
            "Petty Damage",
            "Deals at least 20 damage to the enemy unit.",
            20.0f, 10.0f, false));
    }

    @Override
    public void levelUp()
    {
        // Increase statuses.
        this.setMaxHealth(getMaxHealth() + getMaxHealth() * 0.2F);
        //this.setMaxStamina(getMaxStamina() + 12.0f);
        this.setMaxMana(getMaxMana() + getMaxMana() * 0.25F);
        this.setStrength(getStrength() + getStrength() * 0.15F);
        this.setDefense(getDefense() + getDefense() * 0.17F);
        this.setDexterity(getDexterity() + getDexterity() * 0.12F);
        this.setIntelligence(getIntelligence() + getIntelligence() * 0.3F);

        // Update the level and experience.
        this.setLevel(getLevel() + 1);
        this.setExpToNextLevel(getExpToNextLevel() * 2.5f);

        switch (getLevel())
        {
            case 3:
                getMagics().add(new Magic(
                    "Minor Heal",
                    "Heals the warrior for 20 health.",
                    20.0f, 10.0f, true));
                break;

            case 5:
                getMagics().add(new Magic(
                    "Fire Ball",
                    "Deals moderate fire damage to the enemy",
                    30.0f, 15.0f, false));
                break;

            case 9:
                getMagics().add(new Magic(
                    "SKull Splitter",
                    "Deals major damage to the enemy's skull.",
                    75.0f, 50.0f, false));
                break;

            case 13:
                getMagics().add(new Magic(
                    "Ice Pick",
                    "Deals moderate ice damage to the enemy",
                    45.0f, 20.0f, false));
                break;

            case 15:
                getMagics().add(new Magic(
                    "High Heal",
                    "Heals a lot of health.",
                    100.0f, 90.0f, true));
                break;

            case 20:
                getMagics().add(new Magic(
                    "Backstab",
                    "Hurts the enemy for critical damage.",
                    66.0f, 60.0f, false));
                break;

            case 25:
                getMagics().add(new Magic(
                    "Potrellos Anguish",
                    "It will wish it was already dead.",
                    80.0f, 70.0f, false));
                break;
        }

        // Check if the player leveled up again.
        if ( getExperience() >= getExpToNextLevel() )
        {
            levelUp();
        }

    }


}
