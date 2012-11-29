package roguelike.rpg.sisyphean;

import junit.framework.TestCase;

// -------------------------------------------------------------------------
/**
 *  Tests the skills class.
 *
 *  @author whlund15
 *  @version 2012.11.13
 */
public class SkillTest extends TestCase
{
    private Skill warriorSkill;

    /**
     * Instantiates a new skill object.
     */
    public void setUp()
    {
        warriorSkill = new Skill("Rage", "This skill grants the user extra" +
      		"damage for the next three turns.", 1.25F, 10);
    }

    //Methods.

    /**
     * Tests the return name of the skill.
     */
    public void testName()
    {
        assertEquals("Rage", warriorSkill.getName());
    }

    /**
     * Tests the damage multiplier of the skill.
     */
    public void testDmgMultiplier()
    {
        assertEquals(1.25F, warriorSkill.getDmgMultiplier());
    }

    /**
     * Returns the description of the skill.
     */
    public void testSkillDescription()
    {
        assertEquals("This skill grants the user extra" +
            "damage for the next three turns.", warriorSkill.getDescription());
    }

    /**
     * Tests the consumption of the skill from other skills.
     */
    public void testConsumption()
    {
        assertEquals(10.0F, warriorSkill.getConsumption());
    }

}
