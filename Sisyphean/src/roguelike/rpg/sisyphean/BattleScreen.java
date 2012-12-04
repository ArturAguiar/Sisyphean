package roguelike.rpg.sisyphean;

import sofia.graphics.Color;
import sofia.graphics.RectangleShape;
import sofia.graphics.ShapeView;
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

    private Enemy enemy;

    private Button attack, escape, magic;
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
        this.enemy = enemy;


        gameWorld.setBattling(true);

        this.add(player.getBattleSprite().getImageShape());
        player.setInitialBattlePosition(this.getWidth() * 2.0f / 3.0f - player.getBattleSprite().getImageShape().getWidth() / 2.0f);
        player.getBattleSprite().setPosition(player.getInitialBattlePosition(),
                                             this.getHeight() / 2.0f - player.getBattleSprite().getImageShape().getHeight() / 2.0f);

        this.add(enemy.getBattleSprite().getImageShape());
        enemy.getBattleSprite().setPosition(this.getWidth() / 3.0f - enemy.getBattleSprite().getImageShape().getWidth(),
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
    public void finish()
    {
        gameWorld.setBattling(false);
        super.finish();
    }

    /**
     * Called when the attack button is pressed and attacks the enemy.
     */
    public void attackClicked()
    {
        player.attack();
    }

    /**
     * Called when the escape button is pressed and takes player back to the
     * game screen.
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
    }

    // for (Magic magic : this.getMagics())
        // if magic.getName.equals("String of magic clicked")
            // clicked = magic;
            // break;
    // player.castMagic(magic);

}
