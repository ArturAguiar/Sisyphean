package roguelike.rpg.sisyphean;

import sofia.graphics.Color;
import sofia.graphics.RectangleShape;
import sofia.app.ShapeScreen;

// -------------------------------------------------------------------------
/**
 *  This is the screen/activity for the game.
 *  It will create a maze at the beginning of the activity. The player must
 *  choose a hero and enemies will spawn. There are Health and Mana points
 *  which will be represented by rectangles at the bottom of the screen.
 *
 *  @author Tk
 *  @version Nov 9, 2012
 */
public class GameScreen extends ShapeScreen
{
    private Maze maze;
    private RectangleShape[][] visualMaze;

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void initialize()
    {
        // Stuff here.
        maze = new Maze(1);
        visualMaze = new RectangleShape[10][10];
        float y = getHeight();
        float x = getWidth();
        float size = Math.min(y, x);
        float cellSize = size / 10;
        float bottom = cellSize;
        float right = cellSize;
        float top = 0;
        float left = 0;
        for ( int row = 0; row < 10; row++)
        {
            left = 0;
            right = cellSize;
            top = cellSize * row;
            bottom = cellSize * (row + 1);
            for ( int col = 0; col < 10; col++)
            {
                left = cellSize * col;
                right = cellSize * (col + 1);
                visualMaze[row][col] =
                    new RectangleShape(left, top, right, bottom);
                visualMaze[row][col].setColor(Color.black);
                visualMaze[row][col].setFilled(true);
                visualMaze[row][col].setFillColor(Color.white);
                add(visualMaze[row][col]);
            }

        }
    }

}
