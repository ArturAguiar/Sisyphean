package roguelike.rpg.sisyphean;

import sofia.graphics.Color;
import sofia.graphics.TextShape;
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
    private boolean playerDied = false;

    private boolean wait = false;

    private Enemy enemy;
    private boolean enemyDied = false;

    private Button attack, escape;
    private TextView healthPoints, manaPoints;

    /**
     * Called when the battle starts.
     *
     * @param gameWorld The game world reference. Can be used to get the player.
     * @param enemy The enemy that the player is battling.
     */
    public void initialize(GameWorld gameWorld, Enemy enemy)
    {
        this.gameWorld = gameWorld;
        this.gameWorld.setEnemyKilled(null);
        this.player = gameWorld.getPlayer();
        this.player.setBattleObserver(this);

        this.enemy = enemy;
        this.enemy.setBattleObserver(this);

        gameWorld.setBattling(true);

        this.add(enemy.getBattleSprite().getImageShape());
        this.enemy.getBattleSprite().setPosition(this.getWidth() / 3.0f - enemy.getBattleSprite().getImageShape().getWidth(),
                                            this.getHeight() / 2.0f - player.getBattleSprite().getImageShape().getHeight() / 2.0f);
        this.enemy.setInitialBattlePosition(this.getWidth() / 3.0f - enemy.getBattleSprite().getImageShape().getWidth());

        this.add(player.getBattleSprite().getImageShape());
        this.player.setInitialBattlePosition(this.getWidth() * 2.0f / 3.0f - player.getBattleSprite().getImageShape().getWidth() / 2.0f);
        this.player.getBattleSprite().setPosition(player.getInitialBattlePosition(),
                                             this.getHeight() / 2.0f - player.getBattleSprite().getImageShape().getHeight() / 2.0f);
    }

    @Override
    public void onDestroy()
    {
        gameWorld.setBattling(false);

        this.player.setBattleObserver(null);
        this.enemy.setBattleObserver(null);

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
        // To be implemented.
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
        Magic casted = null;
        // TODO: get this magic from the selection.
        for (Magic magic : player.getMagics())
        {
            casted = magic;
        }

        if (casted != null && casted.heals())
        {
            float healAmount = casted.getTotalEffect(player);
            player.setHealth(player.getHealth() + healAmount);

            this.createHealingText(healAmount, player.getBattleSprite().getPosition().x + 60.0f, player.getBattleSprite().getPosition().y - 10.0f);
        }
        else
        {
            // TODO: pass the magic to the wasHit method.
            this.createDamageText((int)enemy.wasHit(player), enemy.getBattleSprite().getPosition().x + 30.0f, enemy.getBattleSprite().getPosition().y - 10.0f);
        }

        if (!enemyDied)
        {
            enemy.getBattleSprite().getImageShape().animate(800).name("enemyActionDelay").play();
        }
    }

    public void enemyAttackDone()
    {
        this.createDamageText((int)player.wasHit(enemy), player.getBattleSprite().getPosition().x + 60.0f, player.getBattleSprite().getPosition().y - 10.0f);

        wait = false;
    }

    public void enemyDied()
    {
        enemyDied = true;
        ImageShape victoryText = new ImageShape(R.drawable.battle_victory, new RectF(0.0f, 0.0f, 279.0f, 59.0f));
        victoryText.setAlpha(0);
        this.add(victoryText);
        victoryText.setPosition(this.getWidth() / 2.0f - 279.0f / 2.0f,
                                this.getHeight() / 2.0f - 59.0f / 2.0f);

        victoryText.animate(800).delay(1000).alpha(255).play();
        player.getBattleSprite().getImageShape().animate(5800).name("victory").play();
        Log.v("BattleScreen", "Enemy died.");

        gameWorld.setEnemyKilled(enemy);
        player.addExperience(enemy.getExperienceGiven());
    }

    public void playerDied()
    {
        playerDied = true;
        ImageShape defeatText = new ImageShape(R.drawable.battle_defeat, new RectF(0.0f, 0.0f, 279.0f, 59.0f));
        defeatText.setAlpha(0);
        this.add(defeatText);
        defeatText.setPosition(this.getWidth() / 2.0f - 279.0f / 2.0f,
                                this.getHeight() / 2.0f - 59.0f / 2.0f);

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
        this.finish();
    }

    public void defeatAnimationEnded()
    {
        gameWorld.gameOver();

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

}
