package roguelike.rpg.sisyphean;

import android.widget.Button;
import sofia.app.Screen;

/**
 * // -------------------------------------------------------------------------
/**
 *  The Main Menu screen for Sisyphean.
 *
 *  @author
 *  @version 2012.11.04
 */
public class MainMenuScreen extends Screen
{
    private Button startGame, help;

    /**
     * This method gets called only once when the screen is created.
     */
    public void initialize()
    {
        // Logic here.
    }

    public void startGameClicked()
    {
        presentScreen(GameScreen.class);
    }

    public void helpClicked()
    {
        presentScreen(HelpScreen.class);
    }
}
