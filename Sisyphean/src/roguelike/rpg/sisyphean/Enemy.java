package roguelike.rpg.sisyphean;

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

    private float experienceGiven;

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
            // TODO: balance the orc.
            case ORC:
                this.setName("Orc");
                this.setDescription("A hulking brute of a monster.");
                this.experienceGiven = 5.0f + getLevel() * 4.5f;
                this.setMaxHealth(20.0f + getLevel() * 2.2f );
                //this.setMaxStamina(25.0f);
                this.setStrength(28.0f + getLevel() * 12.0f );
                this.setDexterity(8.0f + getLevel() * 5.0f );
                this.setDefense(13.0f + getLevel() * 2.0f );
                this.setMazeSprite(
                    new Sprite(R.drawable.orc_single, 32, 32, 1, 1,
                               gameWorld.getDisplayMetrics().density));
                this.setBattleSprite(
                    new Sprite(R.drawable.orc_sheet, 1040, 700, 8, 5,
                               gameWorld.getDisplayMetrics().density));
                this.getBattleSprite().setRow(2);
                Log.v("Enemy", "Orc level " + this.getLevel() + " created!");
                break;

            case ZOMBIE:
                this.setName("Zombie");
                this.setDescription("A flesh eating undead creature.");
                this.experienceGiven = 5.0f + getLevel() * 4.5f;
                this.setMaxHealth(28.0f + getLevel() * 2.2f );
                //this.setMaxStamina(25.0f);
                this.setStrength(24.0f + getLevel() * 12.0f );
                this.setDexterity(8.0f + getLevel() * 5.0f );
                this.setDefense(10.0f + getLevel() * 2.0f );
                this.setMazeSprite(
                    new Sprite(R.drawable.zombie_single, 32, 32, 1, 1,
                               gameWorld.getDisplayMetrics().density));
                this.setBattleSprite(
                    new Sprite(R.drawable.zombie_battle_sheet, 800, 500, 8, 5,
                               gameWorld.getDisplayMetrics().density));
                this.getBattleSprite().setRow(2);
                Log.v("Enemy", "Zombie level " + this.getLevel() + " created!");
                break;

            case SKELETON:
                this.setName("Skeleton");
                this.setDescription("A scary skinny white thingy.");
                this.experienceGiven = 5.0f + getLevel() * 4.5f;
                this.setMaxHealth(20.0f + getLevel() * 2.2f );
                //this.setMaxStamina(25.0f);
                this.setStrength(25.0f + getLevel() * 12.0f );
                this.setDexterity(8.0f + getLevel() * 5.0f );
                this.setDefense(7.0f + getLevel() * 2.0f );
                this.setMazeSprite(
                    new Sprite(R.drawable.skeleton_single, 32, 32, 1, 1,
                               gameWorld.getDisplayMetrics().density));
                this.setBattleSprite(
                    new Sprite(R.drawable.skeleton_sheet, 1040, 650, 8, 5,
                               gameWorld.getDisplayMetrics().density));
                this.getBattleSprite().setRow(2);
                Log.v("Enemy", "Skeleton level " + this.getLevel() + " created!");
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
                    if (!attackCalled && battleFrame >= 4.0f)
                    {
                        attackCalled = true;
                        // Make a callback to the BattleScreen to let it know that the attack was performed.
                        if (this.getBattleObserver() != null)
                            this.getBattleObserver().enemyAttackDone();
                    }
                    else if (battleFrame >= 8.0f)
                    {
                        battleFrame = 0.0f;
                        this.setBattleAction(BattleAction.IDLE);
                        this.getBattleSprite().setPosition(this.getInitialBattlePosition(),
                                                           this.getBattleSprite().getPosition().y);
                        attackCalled = false;
                    }
                    break;

                case HURT:
                    if (battleFrame >= 8.0f)
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

                default:
                    break;
            }
        }

    }


    @Override
    public void attack()
    {
        this.attackMove = gameWorld.getDisplayMetrics().widthPixels / 3.0f;
        this.setBattleAction(BattleAction.MOVING);
    }

    /**
     * The method called when this enemy gets hit by the player.
     * @param player The player hitting this enemy.
     * @return The total damage done.
     */
    public float wasHit(Player player)
    {
        float damageDone = 0.0f;

        if (player.getType() == PlayerType.ARCHER)
        {
            damageDone = player.getDexterity() + player.getWeapon().getDamage() - getDefense();
        }
        else
        {
            damageDone = player.getStrength() + player.getWeapon().getDamage() - getDefense();
        }
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
                this.setMazeSprite(new Sprite(
                    R.drawable.bones, 32, 32, 1, 1,
                    gameWorld.getDisplayMetrics().density));
            }

            return damageDone;
        }

        return 0.0f;
    }

    /**
     *  Called when this enemy is hit by a magic.
     *  @param player The player that casted the magic.
     *  @param magic The magic that was casted.
     *  @return The total damage done.
     */
    public float wasHit(Player player, Magic magic)
    {
        float damageDone = magic.getTotalEffect(player) - this.getDefense() * 0.4f;

        this.setBattleAction(BattleAction.HURT);

        if ( damageDone > 0 )
        {
            this.setHealth( this.getHealth() - damageDone );
            Log.v("Enemy", "Health left: " + this.getHealth());

            if (this.getHealth() <= 0.0f)
            {
                this.setBattleAction(BattleAction.DEAD);
                this.getBattleObserver().enemyDied();
                this.setMazeSprite(new Sprite(
                    R.drawable.bones, 32, 32, 1, 1,
                    gameWorld.getDisplayMetrics().density));
            }

            return damageDone;
        }

        return 0.0f;
    }

    /**
     * Returns the experience that this enemy gives to the player when it is
     * defeated.
     * @return The experience given.
     */
    public float getExperienceGiven()
    {
        return experienceGiven;
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
