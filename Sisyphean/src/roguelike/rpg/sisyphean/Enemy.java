package roguelike.rpg.sisyphean;

import roguelike.rpg.sisyphean.Character.BattleAction;
import android.util.Log;


// -------------------------------------------------------------------------
/**
 *  Creates a class to make enemies and set their abilities and a brief
 *  description of what they are strong/weak against.
 *
 *  @author Artur
 *  @version 2012.11.13
 */
public class Enemy extends Character
{
    private EnemyType type;

    private String description;

    private boolean attackCalled = false;

    /**
     * This should randomize the enemy's statuses based on which floor it is
     * being created in.
     *
     * @param floor The current maze floor that the player is in.
     * @param gameWorld The game world reference.
     */
    public Enemy(int floor, GameWorld gameWorld)
    {
        this.gameWorld = gameWorld;

        sofia.util.Random rand = new sofia.util.Random();

        // Randomizes an enemy type.
        type = EnemyType.values()[ rand.nextInt(EnemyType.values().length) ];

        this.setLevel(rand.nextInt(floor * 10 - 9, floor * 10 + 1));

        switch (type)
        {
            case ZOMBIE:
            case HARPY:
            case RAT:
                this.setName("Zombie");
                this.setDescription("A flesh eating undead creature.");
                this.setMaxHealth(20.0f + getLevel() * 2.2f );
                this.setMaxStamina(25.0f);
                this.setStrength(20.0f + getLevel() * 12.0f );
                this.setDexterity(8.0f + getLevel() * 5.0f );
                this.setDefense(10.0f + getLevel() * 2.0f );
                this.setMazeSprite(
                    new Sprite(R.drawable.zombie_single, 32, 32, 1, 1,
                               gameWorld.getDisplayMetrics().density));
                this.setBattleSprite(
                    new Sprite(R.drawable.zombie_battle_sheet, 800, 400, 8, 4,
                               gameWorld.getDisplayMetrics().density));
                this.getBattleSprite().setRow(2);
                Log.v("Enemy", "Zombie level " + this.getLevel() + " created!");
                break;
        }

        gameWorld.getAllCharacters().add(this);
    }

    @Override
    public void update()
    {
        if (!this.isAlive())
        {
            return;
        }

        int tempFrame = 0;

        if (gameWorld.getBattling())
        {
            tempFrame = (int)(battleFrame);

            this.getBattleSprite().setCol(tempFrame);

            battleFrame += 0.2f;

            switch (battleAction)
            {
                case IDLE:
                    if (battleFrame >= 8.0f)
                    {
                        battleFrame = 0.0f;
                    }
                    break;

                case MOVING:
                    if (battleFrame >= 8.0f)
                    {
                        battleFrame = 0.0f;
                    }

                    if (attackMove > 0.0f)
                    {
                        this.getBattleSprite().setPosition(this.getBattleSprite().getPosition().x + 5.0f,
                                                           this.getBattleSprite().getPosition().y);
                        this.attackMove -= 5.0f;
                    }
                    else
                    {
                        this.attackMove = 0.0f;
                        this.battleFrame = 0.0f;
                        this.setBattleAction(BattleAction.ATTACKING);
                    }
                    break;

                case ATTACKING:
                    if (!attackCalled  && battleFrame >= 3.0f)
                    {
                        attackCalled = true;
                        // Make a callback to the BattleScreen to let it know that the attack was performed.
                        if (this.getBattleObserver() != null)
                            this.getBattleObserver().enemyAttackDone();
                    }
                    else if (battleFrame >= 6.0f)
                    {
                        battleFrame = 0.0f;
                        this.setBattleAction(BattleAction.IDLE);
                        this.getBattleSprite().setPosition(this.getInitialBattlePosition(),
                                                           this.getBattleSprite().getPosition().y);
                        attackCalled = false;
                    }
                    break;

                case HURT:
                    if (battleFrame >= 6.0f)
                    {
                        battleFrame = 0.0f;
                        this.setBattleAction(BattleAction.IDLE);
                    }
                    break;

                case DEAD:
                    if (battleFrame >= 8.0f)
                    {
                        battleFrame = 7.0f;
                        this.setAlive(false);
                    }
                    break;
            }
        }

    }


    @Override
    public void attack()
    {
        this.setBattleAction(BattleAction.MOVING);
        this.attackMove = gameWorld.getDisplayMetrics().widthPixels / 3.0f;
    }

    /**
     * The method called when this enemy gets hit by the player.
     * @param player The player hitting this enemy.
     * @return The total damage done.
     */
    public float wasHit(Player player)
    {
        // TODO: This is probably not the best way to calculate things...
        float damageDone = player.getStrength() + player.getWeapon().getDamage() - getDefense();
        Float bonusDamage = player.getWeapon().getBonusDamageHash().get(this.type.toString());

        if (bonusDamage != null)
        {
            damageDone += bonusDamage.floatValue();
        }

        Log.v("Enemy", "Damage received: " + damageDone);

        this.setBattleAction(BattleAction.HURT);

        if ( damageDone > 0 )
        {
            this.setHealth( this.getHealth() - damageDone );
            Log.v("Enemy", "Health left: " + this.getHealth());

            if (this.getHealth() <= 0.0f)
            {
                this.setBattleAction(BattleAction.DEAD);
                this.getBattleObserver().enemyDied();
            }

            return damageDone;
        }

        return 0.0f;
    }

    // ----------------------------------------------------------
    /**
     * Returns the description of the enemy.
     * @return description The notes on the enemy.
     */
    public String getDescription()
    {
        return description;
    }

    // ----------------------------------------------------------
    /**
     * Sets the description of the enemy.
     * @param description The description to be set to the enemy
     */
    public void setDescription(String description)
    {
        this.description = description;
    }



}
