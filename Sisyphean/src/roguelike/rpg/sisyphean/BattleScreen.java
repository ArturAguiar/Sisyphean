package roguelike.rpg.sisyphean;

import android.view.View;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.Toast;
import sofia.util.Random;
import roguelike.rpg.sisyphean.Character.PlayerType;
import sofia.graphics.Color;
import sofia.graphics.TextShape;
import sofia.graphics.RectangleShape;
import sofia.graphics.ShapeView;
import android.graphics.RectF;
import sofia.graphics.ImageShape;
import android.util.Log;
import sofia.app.ShapeScreen;
import android.widget.TextView;
import android.widget.Button;

/**
 *  The battle screen controls the representation of a battle and calls
 *  animations and effects based on the enemy and the player's actions.
 *
 *  @author Artur, Tk
 *  @version Nov 29, 2012
 */
public class BattleScreen extends ShapeScreen
{
    private GameWorld gameWorld;
    private Player player;

    private boolean wait = false;

    private Enemy enemy;
    private boolean enemyDied = false;

    private Button attack, escape, magic;
    private ArrayList<Button> buttonArray;
    private LinearLayout buttonLayout;

    private TextShape healthPoints, manaPoints;
    private ShapeView shapeView, statsView;
    private RectangleShape healthRect, manaRect;

    // Keeps the magic that is being casted, until the damage/healing should be done.
    private Magic magicBeingCasted = null;

    private boolean selectingMagic = false;

    /**
     * Called when the battle starts.
     *
     * @param myGameWorld The game world reference. Can be used to get the player.
     * @param myEnemy The enemy that the player is battling.
     */
    public void initialize(GameWorld myGameWorld, Enemy myEnemy)
    {
        buttonArray = new ArrayList<Button>();
        this.gameWorld = myGameWorld;
        this.player = gameWorld.getPlayer();
        this.player.setBattleObserver(this);

        this.enemy = myEnemy;
        this.enemy.setBattleObserver(this);

        gameWorld.setBattling(true);

        shapeView.setAutoRepaint(false);
        statsView.setAutoRepaint(false);

        shapeView.add(enemy.getBattleSprite().getImageShape());
        this.enemy.getBattleSprite().setPosition(getWidth() / 3.0f - enemy.getBattleSprite().getImageShape().getWidth(),
                                            getHeight() / 2.0f - player.getBattleSprite().getImageShape().getHeight() / 2.0f);
        this.enemy.setInitialBattlePosition(getWidth() / 3.0f - enemy.getBattleSprite().getImageShape().getWidth());

        shapeView.add(player.getBattleSprite().getImageShape());
        this.player.setInitialBattlePosition(getWidth() * 2.0f / 3.0f - player.getBattleSprite().getImageShape().getWidth() / 2.0f);
        this.player.getBattleSprite().setPosition(player.getInitialBattlePosition(),
                                             getHeight() / 2.0f - player.getBattleSprite().getImageShape().getHeight() / 2.0f);

        // Health points.
        healthPoints = new TextShape((int)this.player.getHealth() + "/" + (int)this.player.getMaxHealth(), 0.0f, 0.0f);
        statsView.add(healthPoints);

        // Health bar.
        float currentHealthRatio = player.getHealth() / player.getMaxHealth();
        healthRect = new RectangleShape(
            0.0f, healthPoints.getHeight() * 1.5f,
            currentHealthRatio * statsView.getWidth(), healthPoints.getHeight() + statsView.getHeight()/3);

        healthRect.setColor(Color.green);
        healthRect.setFilled(true);
        healthRect.setFillColor(Color.green);
        statsView.add(healthRect);


        // Mana bar.
        float currentManaRatio = player.getMana() / player.getMaxMana();
        float top = (statsView.getHeight() / 3) * 2;
        manaRect = new RectangleShape(
            0.0f, top,
            currentManaRatio * statsView.getWidth(), statsView.getHeight());

        manaRect.setColor(Color.blue);
        manaRect.setFilled(true);
        manaRect.setFillColor(Color.blue);
        statsView.add(manaRect);

        // Mana points.
        manaPoints = new TextShape((int)this.player.getMana() + "/" + (int)this.player.getMaxMana(),
            0.0f, manaRect.getY() - healthPoints.getHeight() * 1.5f);
        statsView.add(manaPoints);

        // Add projectile.
        if (player.getType() == PlayerType.ARCHER)
        {
            player.getProjectile().setPosition(player.getInitialBattlePosition(),
                                               enemy.getBattleSprite().getPosition().y +
                                               enemy.getBattleSprite().getImageShape().getHeight() / 2.0f);
            shapeView.add(player.getProjectile().getImageShape());
        }


        // Add the magic list.
        for (Magic playerMagic : player.getMagics())
        {
            Button button = new Button(this);
            button.setText(playerMagic.getName() + " (" + playerMagic.getConsumption() + ")");
            button.setOnClickListener(new MagicListButton(playerMagic));
            buttonArray.add(button);
            buttonLayout.addView(button);
        }

        buttonLayout.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        buttonLayout.setVisibility(View.INVISIBLE);

        shapeView.setAutoRepaint(true);
        statsView.setAutoRepaint(true);
        shapeView.repaint();
        statsView.repaint();
    }

    @Override
    public void onDestroy()
    {
        gameWorld.setBattling(false);

        this.player.setBattleObserver(null);
        this.enemy.setBattleObserver(null);

        this.clear();

        super.onDestroy();
    }

    /**
     * Called when the attack button is pressed.
     */
    public void attackClicked()
    {
        if (!wait)
        {
            player.attack();
            wait = true;
        }
    }

    /**
     * Called when the escape button is pressed.
     */
    public void escapeClicked()
    {
        if (!wait)
        {
            Random rand = new sofia.util.Random();
            Log.v("BattleScreen", "Trying to escape: " + (player.getDexterity() - enemy.getDexterity() + 50));
            if (player.getDexterity() - enemy.getDexterity() + 100 > 0 &&
                rand.nextInt(1, (int)(player.getDexterity() - enemy.getDexterity() + 100)) > 50)
            {
                // Escaped successfully.
                presentScreen(GameScreen.class, gameWorld);
                finish();
            }
            else
            {
                Toast.makeText(this, "Attempt to escape failed!", Toast.LENGTH_LONG).show();
                enemy.getBattleSprite().getImageShape().animate(800).name("enemyActionDelay").play();
            }

            wait = true;
        }
    }

    // ----------------------------------------------------------
    /**
     * Called when the magic button is clicked and attacks the enemy with magic.
     */
    public void magicClicked()
    {
        if (wait || player.getMagics().isEmpty())
            return;

        if (!selectingMagic)
        {
            selectingMagic = true;

            buttonLayout.setVisibility(View.VISIBLE);

            attack.setTextColor(android.graphics.Color.parseColor("#666666"));
            attack.setEnabled(false);
            escape.setTextColor(android.graphics.Color.parseColor("#666666"));
            escape.setEnabled(false);
        }
        else
        {
            selectingMagic = false;

            buttonLayout.setVisibility(View.INVISIBLE);

            attack.setTextColor(android.graphics.Color.WHITE);
            attack.setEnabled(true);
            escape.setTextColor(android.graphics.Color.WHITE);
            escape.setEnabled(true);
        }
    }



    public void playerAttackDone()
    {
        this.createDamageText((int)enemy.wasHit(player), enemy.getBattleSprite().getPosition().x + 30.0f, enemy.getBattleSprite().getPosition().y - 10.0f);

        if (!enemyDied)
        {
            enemy.getBattleSprite().getImageShape().animate(800).name("enemyActionDelay").play();
        }
    }

    public void playerCastingDone()
    {
        if (magicBeingCasted == null)
            return;

        if (magicBeingCasted.heals())
        {
            float healAmount = magicBeingCasted.getTotalEffect(player);
            player.setHealth(player.getHealth() + healAmount);

            this.createHealingText(healAmount, player.getBattleSprite().getPosition().x + 60.0f, player.getBattleSprite().getPosition().y - 10.0f);
        }
        else
        {
            this.createDamageText((int)enemy.wasHit(player, magicBeingCasted), enemy.getBattleSprite().getPosition().x + 30.0f, enemy.getBattleSprite().getPosition().y - 10.0f);
        }

        if (!enemyDied)
        {
            enemy.getBattleSprite().getImageShape().animate(800).name("enemyActionDelay").play();
        }

        // Set the magic being casted back to null just to be safe
        magicBeingCasted = null;

        this.updateHP();
        this.updateMP();
    }

    public void enemyAttackDone()
    {
        this.createDamageText((int)player.wasHit(enemy), player.getBattleSprite().getPosition().x + 60.0f, player.getBattleSprite().getPosition().y - 10.0f);
        wait = false;

        this.updateHP();
    }

    public void enemyDied()
    {
        enemyDied = true;
        ImageShape victoryText = new ImageShape(R.drawable.battle_victory, new RectF(0.0f, 0.0f, 279.0f, 59.0f));
        victoryText.setAlpha(0);
        add(victoryText);
        victoryText.setPosition(getWidth() / 2.0f - 279.0f / 2.0f,
                               getHeight() / 2.0f - 59.0f / 2.0f);

        victoryText.animate(800).delay(1000).alpha(255).play();
        player.getBattleSprite().getImageShape().animate(5800).name("victory").play();
        Log.v("BattleScreen", "Enemy died.");

        player.addExperience(enemy.getExperienceGiven());
    }

    public void playerDied()
    {
        ImageShape defeatText = new ImageShape(R.drawable.battle_defeat, new RectF(0.0f, 0.0f, 279.0f, 59.0f));
        defeatText.setAlpha(0);
        add(defeatText);
        defeatText.setPosition(getWidth() / 2.0f - 279.0f / 2.0f,
                                getHeight() / 2.0f - 59.0f / 2.0f);

        defeatText.animate(800).delay(1000).alpha(255).play();
        player.getBattleSprite().getImageShape().animate(5800).name("defeat").play();
        Log.v("BattleScreen", "player died.");
    }

    public void enemyActionDelayAnimationEnded()
    {
        if (!enemyDied)
        {
            enemy.attack();
        }
    }

    public void victoryAnimationEnded()
    {
        presentScreen(GameScreen.class, gameWorld);
        this.finish();
    }

    public void defeatAnimationEnded()
    {
        gameWorld.gameOver();

        // Stop the logic thread.
        gameWorld.getLogicThread().setRunning(false);

        presentScreen(MainMenuScreen.class);
        this.finish();
    }

    @Override
    public void onBackPressed()
    {
        // This is overridden so that the player can't back out of the battle
        // by pressing the back button.
    }

    private void createDamageText(int damage, float x, float y)
    {
        TextShape damageText = new TextShape("" + damage, x, y);

        damageText.setColor(Color.red);
        damageText.setTypefaceAndSize("*-bold-10");

        this.add(damageText);

        damageText.animate(800).moveBy(0.0f, -30.0f).alpha(0).removeWhenComplete().play();
    }

    private void createHealingText(float healAmount, float x, float y)
    {
        TextShape damageText = new TextShape("" + healAmount, x, y);

        damageText.setColor(Color.green);
        damageText.setTypefaceAndSize("*-bold-10");

        this.add(damageText);

        damageText.animate(800).moveBy(0.0f, -30.0f).alpha(0).removeWhenComplete().play();
    }

    private void resetStateOfButtons()
    {
        selectingMagic = false;

        buttonLayout.setVisibility(View.INVISIBLE);

        attack.setTextColor(android.graphics.Color.parseColor("#ffffff"));
        attack.setEnabled(true);

        escape.setTextColor(android.graphics.Color.parseColor("#ffffff"));
        escape.setEnabled(true);
    }

    /**
     * This method changes the health text view and bar to let the user know
     * what their current health points are.
     */
    private void updateHP()
    {
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                int currentHealth = (int) (player.getHealth());
                int maxHealth = (int) (player.getMaxHealth());

                statsView.remove(healthPoints);
                healthPoints = new TextShape(currentHealth + "/" + maxHealth, 0.0f, 0.0f);
                statsView.add(healthPoints);

                float currentHealthRatio = player.getHealth() / player.getMaxHealth();
                float newRight = currentHealthRatio * statsView.getWidth();
                if ( newRight < 0.0f )
                {
                    newRight = 0.0f;
                }
                if (currentHealth == 0.0f)
                {
                    healthRect.setColor(Color.black);
                    healthRect.setFillColor(Color.black);
                }
                else
                {
                    RectF newBounds = new RectF(healthRect.getX(), healthRect.getY(),
                        healthRect.getX() + newRight, healthRect.getY() + healthRect.getHeight());
                    healthRect.setBounds(newBounds);
                }
           }
       });
    }

    /**
     * This method changes the mana text view and bar to let the user know
     * what their current mana points are.
     */
    private void updateMP()
    {
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                int currentMana = (int) (player.getMana());
                int maxMana = (int) (player.getMaxMana());

                statsView.remove(manaPoints);
                manaPoints = new TextShape(currentMana + "/" + maxMana,
                    0.0f, manaRect.getY() - healthPoints.getHeight() * 1.5f);
                statsView.add(manaPoints);

                float currentManaRatio = player.getMana() / player.getMaxMana();
                float newRight = currentManaRatio * statsView.getWidth();
                if ( newRight < 0.0f )
                {
                    newRight = 0.0f;
                }
                if (currentMana == 0.0f)
                {
                    manaRect.setColor(Color.black);
                    manaRect.setFillColor(Color.black);

                }
                else
                {
                    RectF newBounds = new RectF(manaRect.getX(), manaRect.getY(),
                        manaRect.getX() + newRight, manaRect.getY() + manaRect.getHeight());
                    manaRect.setBounds(newBounds);
                }
            }
        });
    }


    private class MagicListButton implements View.OnClickListener
    {
        private Magic magicToCast;

        public MagicListButton(Magic magic)
        {
            this.magicToCast = magic;
        }

        public void onClick(View v)
        {
            if (player.getMana() < magicToCast.getConsumption())
                return;

            player.castMagic(magicToCast);
            magicBeingCasted = magicToCast;
            wait = true;
            resetStateOfButtons();
        }
    }

}
