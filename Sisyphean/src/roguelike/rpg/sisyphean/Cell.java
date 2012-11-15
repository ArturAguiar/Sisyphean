package roguelike.rpg.sisyphean;

// -------------------------------------------------------------------------
/**
 *  This class contains the information that must be stored in each cell.
 *  This specifically includes which borders contain walls, whether there is an
 *  item, enemy, or exit there. If there is a wall in a certain direction, that
 *  direction will be stored as true, indicating that there is a wall in that
 *  direction from the center of the cell. The walls are contained in an array,
 *  listed according to clockwise rotation starting at the North wall. This is
 *  for easier access to the walls.
 *
 *  @author Petey
 *  @version Nov 4, 2012
 */
public class Cell
{
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean[] walls;
    private int numberOfWalls;

    private int x;
    private int y;

    private Item item;
    private Character enemy;

    private boolean exit;

    // ----------------------------------------------------------
    /**
     * Create a new Cell object.
     */
    public Cell()
    {
        left = true;
        right = true;
        up = true;
        down = true;
        walls = new boolean[4];
        walls[0] = up;
        walls[1] = right;
        walls[2] = down;
        walls[3] = left;
        exit = false;

        numberOfWalls = 4;
    }

    // ----------------------------------------------------------
    /**
     * Create a new Cell object with the specified location.
     * @param x The x coordinate of the cell
     * @param y The y coordinate of the cell
     */
    public Cell(int x, int y)
    {
        this();
        this.x = x;
        this.y = y;
    }
/*
    // ----------------------------------------------------------
    *//**
     * Create a new Cell object.
     * @param item
     *//*
    public Cell(Item item)
    {
        this();
        this.item = item;
    }

    // ----------------------------------------------------------
    *//**
     * Create a new Cell object.
     * @param enemy
     *//*
    public Cell(Character enemy)
    {
        this();
        this.enemy = enemy;
    }

*/

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param wall
     */
    public void removeWall(int wall)
    {
        if (wall >= 0 && wall < walls.length)
        {
            if (walls[wall] && numberOfWalls > 0) //If there is a wall there, decrease the counter
            {
                numberOfWalls--;
            }
            walls[wall] = false;
        }
        else
        {
            throw new IndexOutOfBoundsException(
                "Not a valid number. Must be between 0 and 3 inclusive.");
        }
    }

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param wall
     */
    public void placeWall(int wall)
    {
        if (wall >= 0 && wall < walls.length)
        {
            if (!walls[wall] && numberOfWalls < 4) //If there wasn't already a wall there, increase the counter
            {
                numberOfWalls++;
            }
            walls[wall] = true;
        }
        else
        {
            throw new IndexOutOfBoundsException(
                "Not a valid wall. Must be between 0 and 3 inclusive.");
        }
    }

    // ----------------------------------------------------------
    /**
     * Accessor for the list of booleans indicating the presence of walls,
     * ordered top, right, bottom, left.
     * @return boolean[] The list of booleans
     */
    public boolean[] getWalls()
    {
        return walls;
    }

    public int x()
    {
        return x;
    }
/*
    public void setX(int x)
    {
        this.x = x;
    }
*/
    public int y()
    {
        return y;
    }
/*
    public void setY(int y)
    {
        this.y = y;
    }
*/
    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }

    public Character getEnemy()
    {
        return enemy;
    }

    public void setEnemy()
    {
        this.enemy = enemy;
    }

    public boolean isExit()
    {
        return exit;
    }

    // ----------------------------------------------------------
    /**
     * Restricts the placement of an exit cell to a dead end (a cell with three
     * walls).
     * @return boolean Whether the cell was set to an exit cell
     */
    public boolean setExit()
    {
        /*int numberOfWalls = 0;
        for (boolean wall : walls)
        {
            if (wall) //Check if there's a wall, if so increase the counter
            {
                numberOfWalls++;
            }
        }
*/
        if (numberOfWalls == 3)
        {
            exit = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    public int numberOfWalls()
    {
        return numberOfWalls;
    }

    public String wallString()
    {
        String string = "";
        for (boolean wall : walls)
        {
            if (wall)
            {
                string += "T";
            }
            else
            {
                string += "F";
            }
        }
        return string;
    }

}
