package roguelike.rpg.sisyphean;

import java.util.HashMap;

/**
 *  Weapon gives attack bonuses to the player.
 *  Only one can be equipped/carried at a time.
 *
 *  @author Artur
 *  @version Nov 3, 2012
 */
public class Weapon extends Item
{
    private float damage;

    private HashMap<String, Float> bonusDamage;


    /**
     * The weapon constructor. Initializes all immutable fields.
     * @param name The name of this weapon
     * @param description The description of this weapon.
     * @param damage The damage bonus given by this weapon.
     */
    public Weapon(String name, String description, float damage)
    {
        this.setName(name);
        this.setDescription(description);
        this.damage = damage;
        this.bonusDamage = new HashMap<String, Float>();
    }

    /**
     * The weapon damage getter.
     * @return The weapon damage.
     */
    public float getDamage()
    {
        return damage;
    }

    /**
     * The bonus damage getter.
     * @return The bonus damage HashMap.
     */
    public HashMap<String, Float> getBonusDamageHash()
    {
        return bonusDamage;
    }

    /**
     * Adds a bonus damage to an enemy type given as a string.
     * @param enemyType The type of the enemy that this sword is strong against.
     * @param damageToAdd The damage to be added when attacking such enemy.
     */
    public void addBonusDamage(String enemyType, Float damageToAdd)
    {
        this.getBonusDamageHash().put(enemyType, damageToAdd);
    }

}
