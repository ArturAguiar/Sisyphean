package roguelike.rpg.sisyphean;

import android.graphics.RectF;
import sofia.graphics.Image;
import android.view.MotionEvent;
import android.widget.Toast;
import sofia.graphics.ImageShape;
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
    private GameWorld gameWorld;

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param playerClass The class of the player's character.
     */
    public void initialize(Character.PlayerType playerClass)
    {
        this.gameWorld = new GameWorld();
        this.getWindowManager().getDefaultDisplay().getMetrics(gameWorld.getDisplayMetrics());

        switch (playerClass)
        {
            case WARRIOR:
                gameWorld.setPlayer(new Warrior("John Doe", 250.0f, 250.0f, gameWorld));
                break;
            default:
                break;
        }

        maze = new Maze(gameWorld, 1);

        float y = getHeight();
        float x = getWidth();
        float size = Math.min(y, x);
        float cellSize = size / maze.size();
        float bottom = cellSize;
        float right = cellSize;
        float top = 0;
        float left = 0;

        // Load all images only once instead of inside the loop.
        Image groundImage = new Image(R.drawable.ground);
        Image backImage = new Image(R.drawable.back);
        Image rightImage = new Image(R.drawable.right);
        Image rightFrontImage = new Image(R.drawable.right_front);
        Image leftImage = new Image(R.drawable.left);
        Image leftFrontImage = new Image(R.drawable.left_front);

        for ( int row = 0; row < maze.size(); row++)
        {
            left = 0;
            right = cellSize;
            top = cellSize * row;
            bottom = cellSize * (row + 1);
            RectF position = new RectF();
            for ( int col = 0; col < maze.size(); col++)
            {
                left = cellSize * col;
                right = cellSize * (col + 1);

                position.set(left, top, right, bottom);

                add(new ImageShape(groundImage, position));

                if (maze.getCell(col, row).getWalls()[0]) //top wall
                {
                    add(new ImageShape(backImage, position));
                }
                if (maze.getCell(col, row).getWalls()[1]) //right wall
                {
                    add(new ImageShape(rightImage, position));
                }
                else if (row > 0 && maze.getCell(col, row - 1).getWalls()[1])
                {
                    add(new ImageShape(rightFrontImage, position));
                }
                if (maze.getCell(col, row).getWalls()[3]) //left wall
                {
                    add(new ImageShape(leftImage, position));
                }
                else if (row > 0 && maze.getCell(col, row - 1).getWalls()[3])
                {
                    add(new ImageShape(leftFrontImage, position));
                }
            }

        }

        //check();
    }

    public void check()
    {
        for (int col = 0; col < maze.size(); col++)
        {
            for (int row = 0; row < maze.size(); row++)
            {
                String s = "(" + maze.getCell(col, row).x() + ", " + maze.getCell(col, row).y() + ") " + maze.getCell(col, row).wallString();
                Toast.makeText(this, s, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onTouchDown(MotionEvent event)
    {
        presentScreen(BattleScreen.class, gameWorld.getPlayer());
    }
}
