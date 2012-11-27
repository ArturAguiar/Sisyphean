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
    private CheckBox wizard, warrior, archer;
    private EditText name;
    private Button submit;
    private Warrior playerIsWarrior;
    private Archer playerIsArcher;
    private Wizard playerIsWizard;
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
        if (warrior.isChecked() && !(wizard.isChecked() || archer.isChecked()))
        {
            playerIsWarrior = new Warrior(name.toString(), 0, 0, newGame);
            presentScreen(GameScreen.class, playerIsWarrior, newGame);
        }
        else if (wizard.isChecked() &&
            !(warrior.isChecked() || archer.isChecked()))
        {
            playerIsWizard = new Wizard(name.toString(), 0, 0, newGame);
            presentScreen(GameScreen.class, playerIsWizard, newGame);
        }
        else
        {
            playerIsArcher = new Archer(name.toString(), 0, 0, newGame);
            presentScreen(GameScreen.class, playerIsArcher, newGame);
        }
    }


}
