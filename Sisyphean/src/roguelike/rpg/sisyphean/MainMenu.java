package roguelike.rpg.sisyphean;

import android.graphics.PointF;
import sofia.app.ShapeScreen;

/**
 * // -------------------------------------------------------------------------
/**
 *  The Main Menu screen for Sisyphean.
 *
 *  @author
 *  @version 2012.11.04
 */
public class MainMenu extends ShapeScreen
{
    /**
     * Instantiates the image of the character.
     */
    public void initialize()
    {
        // This is just a test of overlapping the character "naked" image with
        // the armor image. It works!
        Player player = new Warrior("Jack");
        player.getMazeSprite().setPosition(new PointF(30.0f, 30.0f));
        add(player.getMazeSprite());
        player.getArmor().getMazeSprite().setPosition(new PointF(30.0f, 30.0f));
        add(player.getArmor().getMazeSprite());
    }
}
