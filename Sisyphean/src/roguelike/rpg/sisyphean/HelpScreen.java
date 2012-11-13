package roguelike.rpg.sisyphean;

import android.widget.Button;
import sofia.app.Screen;

// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Tk
 *  @version Nov 9, 2012
 */
public class HelpScreen extends Screen
{

    private Button game;

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void initalize()
    {

    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void gameClicked()
    {
        presentScreen(GameScreen.class);
        finish();
    }

}
