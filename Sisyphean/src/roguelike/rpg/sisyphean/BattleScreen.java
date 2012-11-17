package roguelike.rpg.sisyphean;

import android.util.Log;
import sofia.app.ShapeScreen;
import android.widget.TextView;
import android.widget.Button;

public class BattleScreen extends ShapeScreen
{
    private Button attack, escape;
    private TextView healthPoints, manaPoints;

    /**
     * Default initializer.
     */
    public void initialize()
    {
        Log.v("BattleScreen", "default initialized!");
    }

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

    }

    /**
     * Called when the escape button is pressed.
     */
    public void escapeClicked()
    {

    }

}
