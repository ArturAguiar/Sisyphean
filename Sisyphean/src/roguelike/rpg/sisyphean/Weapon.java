package roguelike.rpg.sisyphean;

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
    }

    /**
     * The weapon damage getter.
     * @return The weapon damage.
     */
    public float getDamage()
    {
        return damage;
    }
}
