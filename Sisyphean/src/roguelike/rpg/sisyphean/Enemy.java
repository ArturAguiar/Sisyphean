package roguelike.rpg.sisyphean;

import android.graphics.RectF;
import sofia.graphics.ImageShape;
import android.util.FloatMath;
import sofia.app.ShapeScreen;

public class Enemy extends Character
{
    private EnemyType type;

    private String description;

    /**
     * No parameter constructor that randomizes all statuses.
     *
     * @param floor The current maze floor that the player is in.
     */
    public Enemy(int floor)
    {
        sofia.util.Random rand = new sofia.util.Random();

        // Randomizes an enemy type.
        type = EnemyType.values()[ rand.nextInt(EnemyType.values().length) ];

        this.setLevel(rand.nextInt(floor * 10 - 9, floor * 10 + 1));

        switch (type)
        {
            case ZOMBIE:
                this.setName("Zombie");
                this.setDescription("A flesh eating undead creature.");
                this.setMaxHealth(100.0f + getLevel() * 10.0f );
                this.setMaxStamina(25.0f);
                this.setStrength(20.0f + getLevel() * 12.0f );
                this.setDexterity(8.0f + getLevel() * 5.0f );
                this.setDefense(18.0f + getLevel() * 10.0f );
                this.setMazeSprite(
                    new ImageShape(R.drawable.zombie_single,
                                   new RectF(0.0f, 0.0f, 32.0f, 32.0f)));
                break;

            case HARPY:
                break;

            case RAT:
                break;
        }
    }

    @Override
    public void drawMe(ShapeScreen screen)
    {
        // TODO Auto-generated method stub

    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

}
