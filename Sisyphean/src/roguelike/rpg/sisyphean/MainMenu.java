package roguelike.rpg.sisyphean;

import android.graphics.PointF;
import sofia.app.ShapeScreen;

public class MainMenu extends ShapeScreen
{
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
