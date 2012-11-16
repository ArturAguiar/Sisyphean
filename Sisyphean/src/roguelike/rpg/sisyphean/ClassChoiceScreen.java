package roguelike.rpg.sisyphean;

import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
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
    private CheckBox mage, warrior, ranger;
    private EditText name;
    private Button submit;
    private Warrior playerIsWarrior;
    private GameWorld newGame;

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void initialize()
    {
        newGame = new GameWorld();
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void submitClicked()
    {
        if (warrior.isChecked() && !(mage.isChecked() || ranger.isChecked()))
        {
            playerIsWarrior = new Warrior(name.toString(), 0, 0, newGame);
            presentScreen(GameScreen.class, playerIsWarrior, newGame);
        }
        else if (mage.isChecked() &&
            !(warrior.isChecked() || ranger.isChecked()))
        {
            // TODO
            // Make mage class and create a mage player.
        }
        else
        {
            // TODO
            // Make ranger class and create a ranger player.
        }
    }


}
