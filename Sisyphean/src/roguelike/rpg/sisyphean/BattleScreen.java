package roguelike.rpg.sisyphean;

import sofia.app.ShapeScreen;
import android.widget.TextView;
import android.widget.Button;

/**
 *  The battle screen controls the representation of a battle and calls
 *  animations and effects based on the enemy and the player's actions.
 *
 *  @author Artur
 *  @version Nov 29, 2012
 */
public class BattleScreen extends ShapeScreen
{
    private GameWorld gameWorld;

    private Player player;

    private Enemy enemy;

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
    }

    @Override
    public void finish()
    {
        gameWorld.setBattling(false);
        super.finish();
    }

    /**
     * Called when the attack button is pressed.
     */
    public void attackClicked()
    {
        player.attack();
    }

    /**
     * Called when the escape button is pressed.
     */
    public void escapeClicked()
    {
        // To be implemented.
    }

}
