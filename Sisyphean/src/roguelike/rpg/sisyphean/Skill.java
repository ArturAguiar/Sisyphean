package roguelike.rpg.sisyphean;

/**
 *  A skill deals physical damage to the enemy and consumes stamina.
 *
 *  @author Artur
 *  @version Nov 3, 2012
 */
public class Skill
{
    private String name;
    private String description;
    private float dmgMultiplier;
    private float consumption;

    /**
     * Skill constructor initializes all immutable fields.
     * @param name The name of this skill.
     * @param description The description of this skill.
     * @param dmgMultiplier The damage multiplier to be applied to the player's
     *        attack.
     * @param consumption The amount of stamina required to use this skill.
     */
    public Skill(String name, String description, float dmgMultiplier, float consumption)
    {
        this.name = name;
        this.description = description;
        this.dmgMultiplier = dmgMultiplier;
        this.consumption = consumption;
    }

    /**
     * The name getter.
     * @return The name of this skill.
     */
    public String getName()
    {
        return name;
    }

    /**
     * The damage multiplier getter.
     * @return The damage multiplier of this skill.
     */
    public float getDmgMultiplier()
    {
        return dmgMultiplier;
    }

    /**
     * The description getter.
     * @return The description of this skill.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * The consumption getter.
     * @return The stamina consumption of this skill.
     */
    public float getConsumption()
    {
        return consumption;
    }
}
