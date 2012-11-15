package roguelike.rpg.sisyphean;

import android.util.DisplayMetrics;
import java.util.HashSet;

/**
 *  The class that contains all the pertinent game information.
 *  Contains the set of all characters and the current maze.
 *
 *  @author Artur
 *  @version Nov 5, 2012
 */
public class GameWorld
{
    private HashSet<Character> allCharacters;

    //private Maze maze;

    private Player player;

    private DisplayMetrics displayMetrics;

    public GameWorld()
    {
        allCharacters = new HashSet<Character>();
        displayMetrics = new DisplayMetrics();
    }

    public HashSet<Character> getAllCharacters()
    {
        return allCharacters;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;

        // Do I need to do this check if the set can't support duplicates?
        if (!getAllCharacters().contains(player))
        {
            getAllCharacters().add(player);
        }
    }

    public DisplayMetrics getDisplayMetrics()
    {
        return displayMetrics;
    }

}
