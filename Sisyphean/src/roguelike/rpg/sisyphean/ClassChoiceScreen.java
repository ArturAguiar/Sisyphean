package roguelike.rpg.sisyphean;

import android.widget.RadioButton;
import android.widget.Button;
import android.widget.EditText;
import sofia.app.Screen;

// -------------------------------------------------------------------------
/**
 *  This class is the screen where the user picks which player class they want
 *  to be.
 *  There are checkboxes that represent the choice of class and a submit button.
 *  When the submit button is clicked, it takes the user to the gameScreen
 *  where the maze class is represented. It is in this class that a gameWorld
 *  and the player is created. These objects are sent over to the gameScreen.
 *
 *  @author Tk
 *  @version Nov 16, 2012
 */
public class ClassChoiceScreen extends Screen
{
    private RadioButton wizard, warrior, archer;
    private EditText name;
    private Button submit;

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void initialize()
    {
        // Empty
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void submitClicked()
    {
        if (warrior.isChecked() && !(wizard.isChecked() || archer.isChecked()))
        {

            presentScreen(GameScreen.class, Character.PlayerType.WARRIOR, 1);
            finish();
        }
        else if (wizard.isChecked() &&
            !(warrior.isChecked() || archer.isChecked()))
        {

            presentScreen(GameScreen.class, Character.PlayerType.WIZARD, 1);
            finish();
        }
        else
        {

            presentScreen(GameScreen.class, Character.PlayerType.ARCHER, 1);
            finish();
        }
    }


}
