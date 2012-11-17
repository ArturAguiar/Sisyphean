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

    /**
     * Notes if the start game button is clicked or not.
     */
    public void startGameClicked()
    {
        presentScreen(ClassChoiceScreen.class);
        finish();
    }

    /**
     * Notes if the help button is clicked or not.
     */
    public void helpClicked()
    {
        presentScreen(HelpScreen.class);
    }
}
