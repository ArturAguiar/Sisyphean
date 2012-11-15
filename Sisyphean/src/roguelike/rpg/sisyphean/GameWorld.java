package roguelike.rpg.sisyphean;

import android.util.DisplayMetrics;
import java.util.HashSet;

/**
* The class that contains all the pertinent game information.
* Contains the set of all characters and the current maze.
*
* @author Artur
* @version Nov 5, 2012
*/
public class GameWorld
{
    private HashSet<Character> allCharacters;

    private Maze maze;

    private Player player;

    private DisplayMetrics displayMetrics;

    /**
     * Constructor of the game world.
     * There should only be one instance of this object in the whole game.
     */
    public GameWorld()
    {
        allCharacters = new HashSet<Character>();
        displayMetrics = new DisplayMetrics();
    }

    /**
     * Returns the set of all characters.
     * @return The set containing all characters.
     */
    public HashSet<Character> getAllCharacters()
    {
        return allCharacters;
    }

    /**
     * Returns the current maze being explored.
     * @return The current maze.
     */
    public Maze getMaze()
    {
        return maze;
    }

    /**
     * Changes the maze being explored.
     * @param maze The new maze.
     */
    public void setMaze(Maze maze)
    {
        this.maze = maze;
    }

    /**
     * Returns the main character (the player).
     * There should only be one instance of this in the whole game.
     * @return The player.
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * Sets the player reference in the game world to point to the player
     * object.
     * @param player The character controlled by the player.
     */
    public void setPlayer(Player player)
    {
        this.player = player;
        getAllCharacters().add(player);
    }

    /**
     * Returns the display metrics.
     * Mostly used to obtain the screen density.
     * @return The display metrics. Contains data about the screen.
     */
    public DisplayMetrics getDisplayMetrics()
    {
        return displayMetrics;
    }

}
