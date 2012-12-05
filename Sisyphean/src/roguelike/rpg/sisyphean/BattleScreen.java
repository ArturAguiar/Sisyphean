package roguelike.rpg.sisyphean;

import sofia.graphics.Color;
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
    private boolean playerDied = false;

    private boolean wait = false;

    private Enemy enemy;
    private boolean enemyDied = false;

    private Button attack, escape;
    private TextView healthPoints, manaPoints;
    private ShapeView shapeView1, shapeView2;
    private RectangleShape healthRect, manaRect;

    /**
     * Called when the battle starts.
     *
     * @param gameWorld The game world reference. Can be used to get the player.
     * @param enemy The enemy that the player is battling.
     */
    public void initialize(GameWorld gameWorld, Enemy enemy)
    {
        this.gameWorld = gameWorld;
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

        float health = player.getHealth();
        float mana = player.getMana();
        healthRect = new RectangleShape(0, 0, shapeView2.getWidth(),
            shapeView2.getHeight());
        healthRect.setColor(Color.green);
        shapeView2.add(healthRect);
        manaRect = new RectangleShape(0, shapeView2.getHeight() - 10,
            shapeView2.getWidth(), shapeView2.getHeight());
        manaRect.setColor(Color.blue);
        shapeView1.add(manaRect);
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
        // present(GameScreen.class);
    }

    // ----------------------------------------------------------
    /**
     * Called when the magic button is clicked and attacks the enemy with magic.
     */
    public void magicClicked()
    {
        // Make other buttons appear

        // for (Magic magic : this.getMagics())
            // if magic.getName.equals("String of magic clicked")
                // clicked = magic;
                // break;
        // player.castMagic(magic);
    }

    public void playerAttackDone()
    {
        enemy.wasHit(player);

        if (!enemyDied)
        {
            enemy.getBattleSprite().getImageShape().animate(800).name("enemyActionDelay").play();
        }
    }

    public void enemyAttackDone()
    {
        player.wasHit(enemy);

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

}
