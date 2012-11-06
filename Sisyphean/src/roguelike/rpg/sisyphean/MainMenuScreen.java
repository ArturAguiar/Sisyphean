package roguelike.rpg.sisyphean;

import android.view.MotionEvent;
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
public class MainMenuScreen extends ShapeScreen
{
    // I found out why it's not loading the xml layout for this.
    // It needs to extend Screen and not ShapeScreen.
    // But until we have a maze screen, I need something to test the sprites on. =/

    // Also, for some reason the sprites are kinda broken when I test with the virtual device,
    // but work perfectly on my phone. =/
    // Will have to check this with Tony...

    // Everything you see here is probably just temporary, so feel free to change anything you need.

    private GameWorld gameWorld;

    private LogicThread logicThread;

    private Player player;

    public void initialize()
    {
        // This is just for testing, the world should not be created on this screen in the final version.
        gameWorld = new GameWorld();

        logicThread = new LogicThread(gameWorld);
        logicThread.setRunning(true);
        logicThread.start();

        // This is just a test of overlapping the character "naked" image with
        // the armor image. It works!
        player = new Warrior("Jack", 30.0f, 30.0f);
        this.add(player.getMazeSprite());
        this.add(player.getArmor().getMazeSprite());
        gameWorld.getAllCharacters().add(player);
    }

    /**
     * The method that gets called when the user touches the screen.
     * @param event The event containing the touch information.
     */
    public void onTouchDown(MotionEvent event)
    {
        player.moveBy(0.0f, 30.0f);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        logicThread.setRunning(false);
    }
}
