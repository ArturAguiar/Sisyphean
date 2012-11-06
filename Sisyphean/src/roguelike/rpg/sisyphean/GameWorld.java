package roguelike.rpg.sisyphean;

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

    public GameWorld()
    {
        allCharacters = new HashSet<Character>();



        // TODO: The player needs to be set after the user chooses the player class!
        // How do I get this data in order to create the player?
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
    }

}
