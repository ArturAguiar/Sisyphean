package roguelike.rpg.sisyphean;

import android.util.DisplayMetrics;
import android.graphics.RectF;
import sofia.graphics.ImageShape;
import sofia.graphics.ShapeView;
import sofia.app.ShapeScreen;
import android.widget.CheckBox;
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
public class ClassChoiceScreen extends ShapeScreen
{
    private ShapeView shapeView;

    private EditText playerName;
    private RadioButton wizard, warrior, archer;
    private EditText name;
    private Button submit;

    private ImageShape warriorImage;
    private ImageShape archerImage;
    private ImageShape wizardImage;

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void initialize()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        warriorImage = new ImageShape(R.drawable.crusader_single, new RectF(0.0f, 0.0f, 100.0f * metrics.density, 100.0f * metrics.density));
        archerImage = new ImageShape(R.drawable.archer_single, new RectF(0.0f, 0.0f, 100.0f * metrics.density, 100.0f * metrics.density));
        wizardImage = new ImageShape(R.drawable.wizard_single, new RectF(0.0f, 0.0f, 100.0f * metrics.density, 100.0f * metrics.density));
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void submitClicked()
    {
        String name = playerName.getText().toString();

        if (warrior.isChecked() && !(wizard.isChecked() || archer.isChecked()))
        {

            presentScreen(GameScreen.class, Character.PlayerType.WARRIOR, 1, name);
            finish();
        }
        else if (wizard.isChecked() &&
            !(warrior.isChecked() || archer.isChecked()))
        {

            presentScreen(GameScreen.class, Character.PlayerType.WIZARD, 1, name);
            finish();
        }
        else
        {

            presentScreen(GameScreen.class, Character.PlayerType.ARCHER, 1, name);
            finish();
        }
    }


    /**
     * Listens for the the warrior to be selected.
     */
    public void warriorClicked()
    {
        shapeView.remove(archerImage);
        shapeView.remove(wizardImage);
        shapeView.add(warriorImage);
    }

    /**
     * Listens for the the archer to be selected.
     */
    public void archerClicked()
    {
        shapeView.remove(warriorImage);
        shapeView.remove(wizardImage);
        shapeView.add(archerImage);
    }

    /**
     * Listens for the the wizard to be selected.
     */
    public void wizardClicked()
    {
        shapeView.remove(archerImage);
        shapeView.remove(warriorImage);
        shapeView.add(wizardImage);
    }


}
