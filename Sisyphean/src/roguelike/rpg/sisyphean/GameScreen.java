package roguelike.rpg.sisyphean;

import android.graphics.RectF;
import sofia.graphics.Image;
import android.view.MotionEvent;
import android.widget.Toast;
import sofia.graphics.ImageShape;
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
    private int floor;

    private LogicThread logicThread;

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param playerClass The class of the player's character.
     * @param floor
     */
    public void initialize(Character.PlayerType playerClass, int floor)
    {
        // Create the game world and set the display metrics.
        this.gameWorld = new GameWorld();
        this.getWindowManager().getDefaultDisplay().getMetrics(gameWorld.getDisplayMetrics());

        // Start the logic thread.
        logicThread = new LogicThread(gameWorld);
        logicThread.setRunning(true);
        logicThread.start();

        switch (playerClass)
        {
            case WARRIOR:
                gameWorld.setPlayer(new Warrior("John Doe", 250.0f, 250.0f, gameWorld));
                break;
            default:
                break;
        }

        maze = new Maze(gameWorld, floor);
        this.floor = floor;

        float y = getHeight();
        float x = getWidth();
        float size = Math.min(y, x);
        float cellSize = size / maze.floorSize();
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

        for ( int row = 0; row < maze.floorSize(); row++)
        {
            left = 0;
            right = cellSize;
            top = cellSize * row;
            bottom = cellSize * (row + 1);
            RectF position = new RectF();
            for ( int col = 0; col < maze.floorSize(); col++)
            {
                left = cellSize * col;
                right = cellSize * (col + 1);

                position.set(left, top, right, bottom);

                add(new ImageShape(groundImage, position));
                /*if (maze.roomCells().contains(maze.getCell(col, row)))
                {
                    add(new ImageShape(new Image("ic_action_search"), position));
                }*/

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

                if (maze.getCell(col, row).getEnemy() != null)
                {
                    maze.getCell(col, row).getEnemy().setPosition(left + cellSize * 0.1f, top + cellSize * 0.1f);
                    maze.getCell(col, row).getEnemy().getMazeSprite().setSize(cellSize * 0.8f);
                    add(maze.getCell(col, row).getEnemy().getMazeSprite().getImageShape());
                }
            }

        }

        gameWorld.getPlayer().setPosition(
            cellSize * maze.startColumn() + cellSize * 0.1f,
            cellSize * maze.startRow() + cellSize * 0.1f);
        gameWorld.getPlayer().setCell(maze.startColumn(), maze.startRow());
        gameWorld.getPlayer().getMazeSprite().setSize(cellSize * 0.8f);
        add(gameWorld.getPlayer().getMazeSprite().getImageShape());

        if (maze.getCell(maze.exitColumn(), maze.exitRow()).getWalls()[3]
            && !maze.getCell(maze.exitColumn(), maze.exitRow()).getWalls()[1])
        {
            // Flip the image somehow...
            add(new ImageShape("stairs",
                cellSize * (maze.exitColumn() + 1),
                cellSize * maze.exitRow(),
                cellSize * maze.exitColumn(),
                cellSize * (maze.exitRow() + 1)));
        }
        add(new ImageShape("stairs",
            cellSize * maze.exitColumn(),
            cellSize * maze.exitRow(),
            cellSize * (maze.exitColumn() + 1),
            cellSize * (maze.exitRow() + 1)));

    }

    /**
     * Method called when the user touches the screen.
     * Used for testing at the moment.
     * @param event The information about the touch.
     */
    public void onTouchDown(MotionEvent event)
    {
        //presentScreen(BattleScreen.class, gameWorld, new Enemy(1, gameWorld));

        // If you click on the exit cell, you move to the next floor
        float cellSize = Math.min(getHeight(), getWidth()) / maze.floorSize();
        if (event.getX() > cellSize * maze.exitColumn() &&
            event.getX() < cellSize * (maze.exitColumn() + 1) &&
            event.getY() > cellSize * maze.exitRow() &&
            event.getY() < cellSize * (maze.exitRow() + 1))
        {
            presentScreen(GameScreen.class, Character.PlayerType.WARRIOR, ++floor);
            finish();
        }
        else
        {
            // Pops up a toast with information for testing purposes
            //Toast.makeText(this, "" + maze.fs() + ", " + maze.counter() + ", " + maze.generations + ", " + maze.generated, Toast.LENGTH_LONG).show();
        }
    }

    // ----------------------------------------------------------
    /**
     * Move the player down.
     */
    public void downClicked()
    {
        int x = gameWorld.getPlayer().getCellX();
        int y = gameWorld.getPlayer().getCellY();
        if (!maze.getCell(x, y).getWalls()[2])
        {
            if (y + 1 < maze.floorSize() &&
                maze.getCell(x, y + 1).getEnemy() != null)
            {
                presentScreen(BattleScreen.class, gameWorld, maze.getCell(x, y + 1).getEnemy());
            }
            else
            {
                gameWorld.getPlayer().move("down", Math.min(getHeight(), getWidth()) / maze.floorSize());
            }
        }

    }

    // ----------------------------------------------------------
    /**
     * Move the player up.
     */
    public void upClicked()
    {
        int x = gameWorld.getPlayer().getCellX();
        int y = gameWorld.getPlayer().getCellY();
        if (!maze.getCell(x, y).getWalls()[0])
        {
            if (y - 1 >= 0 &&
                maze.getCell(x, y - 1).getEnemy() != null)
            {
                presentScreen(BattleScreen.class, gameWorld, maze.getCell(x, y - 1).getEnemy());
            }
            else
            {
                gameWorld.getPlayer().move("up", Math.min(getHeight(), getWidth()) / maze.floorSize());
            }
        }
    }

    // ----------------------------------------------------------
    /**
     * Move the player right.
     */
    public void rightClicked()
    {
        int x = gameWorld.getPlayer().getCellX();
        int y = gameWorld.getPlayer().getCellY();
        if (!maze.getCell(x, y).getWalls()[1])
        {
            if (x + 1 < maze.floorSize() &&
                maze.getCell(x + 1, y).getEnemy() != null)
            {
                presentScreen(BattleScreen.class, gameWorld, maze.getCell(x + 1, y).getEnemy());
            }
            else
            {
                gameWorld.getPlayer().move("right", Math.min(getHeight(), getWidth()) / maze.floorSize());
            }
        }

    }

    // ----------------------------------------------------------
    /**
     * Move the player left.
     */
    public void leftClicked()
    {
        int x = gameWorld.getPlayer().getCellX();
        int y = gameWorld.getPlayer().getCellY();
        if (!maze.getCell(x, y).getWalls()[3])
        {
            if (x - 1 >= 0 &&
                maze.getCell(x - 1, y).getEnemy() != null)
            {
                presentScreen(BattleScreen.class, gameWorld, maze.getCell(x - 1, y).getEnemy());
            }
            else
            {
                gameWorld.getPlayer().move("left", Math.min(getHeight(), getWidth()) / maze.floorSize());
            }
        }
    }

    @Override
    public void finish()
    {
        logicThread.setRunning(false);

        super.finish();
    }


}
