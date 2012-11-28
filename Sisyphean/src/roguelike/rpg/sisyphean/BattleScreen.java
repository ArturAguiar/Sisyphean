package roguelike.rpg.sisyphean;

import android.util.Log;
import sofia.app.ShapeScreen;

/**
 *  This class handles the battle system.
 *  It is created when the player collides with an enemy and it is destroyed
 *  when the battle ends.
 *
 *  @author TK
 *  @author Artur
 *  @version Nov 17, 2012
 */
public class BattleScreen extends ShapeScreen
{
    //private Button attack, escape;
    //private TextView healthPoints, manaPoints;

    /**
     * Called when the battle starts.
     * @param player The player's character.
     */
    public void initalize(Player player)
    {
        Log.v("BattleScreen", "initialized!");
        this.add(player.getBattleSprite().getImageShape());
        player.getBattleSprite().setPosition(0.0f, 0.0f);
    }

    /**
     * Called when the attack button is pressed.
     */
    public void attackClicked()
    {
        // Nothing here yet.
    }

    /**
     * Called when the escape button is pressed.
     */
    public void escapeClicked()
    {
        // Nothing here yet.
    }

}
