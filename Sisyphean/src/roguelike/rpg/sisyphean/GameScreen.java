package roguelike.rpg.sisyphean;

import sofia.graphics.ShapeView;
import android.graphics.PointF;
import android.util.Log;
import android.graphics.RectF;
import sofia.graphics.Image;
import android.view.MotionEvent;
import sofia.graphics.ImageShape;
import sofia.app.ShapeScreen;

// -------------------------------------------------------------------------
/**
 *  This is the screen/activity for the game.
 *  It will create a maze at the beginning of the activity. The player must
 *  choose a hero and enemies will spawn. There are Health and Mana points
 *  which will be represented by rectangles at the bottom of the screen.
 *
 *  @author Tk, Artur, Petey
 *  @version Nov 9, 2012
 */
public class GameScreen extends ShapeScreen
{
    private ShapeView shapeView;

    private Maze maze;
    private GameWorld gameWorld;
    private int currentFloor;

    private LogicThread logicThread;

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param playerClass The class of the player's character.
     * @param floor The floor of the maze.
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

        Log.v("GameScreen", "initialized.");

        switch (playerClass)
        {
            case WARRIOR:
                gameWorld.setPlayer(new Warrior("John Doe", 250.0f, 250.0f, gameWorld));
                break;

            default:
                break;
        }

        maze = new Maze(gameWorld, floor);
        this.currentFloor = floor;

        float y = shapeView.getHeight();
        float x = shapeView.getWidth();
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

        shapeView.setAutoRepaint(false);

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

                shapeView.add(new ImageShape(groundImage, position));
                /*if (maze.roomCells().contains(maze.getCell(col, row)))
                {
                    shapeView.add(new ImageShape(new Image("ic_action_search"), position));
                }*/

                if (maze.getCell(col, row).getWalls()[0]) //top wall
                {
                    shapeView.add(new ImageShape(backImage, position));
                }
                if (maze.getCell(col, row).getWalls()[1]) //right wall
                {
                    shapeView.add(new ImageShape(rightImage, position));
                }
                else if (row > 0 && maze.getCell(col, row - 1).getWalls()[1])
                {
                    shapeView.add(new ImageShape(rightFrontImage, position));
                }
                if (maze.getCell(col, row).getWalls()[3]) //left wall
                {
                    shapeView.add(new ImageShape(leftImage, position));
                }
                else if (row > 0 && maze.getCell(col, row - 1).getWalls()[3])
                {
                    shapeView.add(new ImageShape(leftFrontImage, position));
                }

                if (maze.getCell(col, row).getEnemy() != null)
                {
                    maze.getCell(col, row).getEnemy().setPosition(left + cellSize * 0.1f, top + cellSize * 0.1f);
                    maze.getCell(col, row).getEnemy().getMazeSprite().setSize(cellSize * 0.8f);
                    shapeView.add(maze.getCell(col, row).getEnemy().getMazeSprite().getImageShape());
                }
            }

        }

        gameWorld.getPlayer().setPosition(
            cellSize * maze.startColumn() + cellSize * 0.1f,
            cellSize * maze.startRow() + cellSize * 0.1f);
        gameWorld.getPlayer().setCell(maze.startColumn(), maze.startRow());
        gameWorld.getPlayer().getMazeSprite().setSize(cellSize * 0.8f);
        shapeView.add(gameWorld.getPlayer().getMazeSprite().getImageShape());
        gameWorld.getPlayer().getMazeSprite().getImageShape().setZIndex(10);

        shapeView.add(new ImageShape("stairs",
            cellSize * maze.exitColumn(),
            cellSize * maze.exitRow(),
            cellSize * (maze.exitColumn() + 1),
            cellSize * (maze.exitRow() + 1)));

        shapeView.setAutoRepaint(true);
        shapeView.repaint();

        Log.v("GameScreen", "Maze fully created.");
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
        float cellSize = Math.min(shapeView.getHeight(), shapeView.getWidth()) / maze.floorSize();
        if (event.getX() > cellSize * maze.exitColumn() &&
            event.getX() < cellSize * (maze.exitColumn() + 1) &&
            event.getY() > cellSize * maze.exitRow() &&
            event.getY() < cellSize * (maze.exitRow() + 1))
        {
            this.clear();
            presentScreen(GameScreen.class, Character.PlayerType.WARRIOR, ++currentFloor);
            finish();
        }
        else
        {
            // Pops up a toast with information for testing purposes
            //Toast.makeText(this, maze.counter(), Toast.LENGTH_LONG).show();
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

        if (!maze.getCell(x, y).getWalls()[2] && y + 1 < maze.floorSize() && !checkForEnemyIn(x, y + 1))
        {
            gameWorld.getPlayer().move("down", Math.min(shapeView.getHeight(), shapeView.getWidth()) / maze.floorSize());
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

        if (!maze.getCell(x, y).getWalls()[0] && y - 1 >= 0 && !checkForEnemyIn(x, y - 1))
        {
            gameWorld.getPlayer().move("up", Math.min(shapeView.getHeight(), shapeView.getWidth()) / maze.floorSize());
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

        if (!maze.getCell(x, y).getWalls()[1] && x + 1 < maze.floorSize() && !checkForEnemyIn(x + 1, y))
        {
            gameWorld.getPlayer().move("right", Math.min(shapeView.getHeight(), shapeView.getWidth()) / maze.floorSize());
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

        if (!maze.getCell(x, y).getWalls()[3] && x - 1 >= 0 && !checkForEnemyIn(x - 1, y))
        {
            gameWorld.getPlayer().move("left", Math.min(shapeView.getHeight(), shapeView.getWidth()) / maze.floorSize());
        }
    }

    /**
     * Checks for the existence of an enemy in the given cell coordinate.
     * If there is an enemy, initiate a battle with it.
     * @param x The x coordinate of the cell to check.
     * @param y the y coordinate of the cell to check.
     * @return True if an enemy was found and a battle was started, false otherwise.
     */
    public boolean checkForEnemyIn(int x, int y)
    {
        Enemy myEnemy = maze.getCell(x, y).getEnemy();
        if (myEnemy != null &&
            myEnemy.isAlive())
        {
            presentScreen(BattleScreen.class, gameWorld, myEnemy);

            return true;
        }

        return false;
    }

    /**
     * Removes the enemy's maze sprite and adds a pile of bones instead to
     * indicate that it is dead.
     * @param enemy The enemy that was killed.
     */
    public void killEnemy(Enemy enemy)
    {
        PointF spritePos = enemy.getMazeSprite().getPosition();
        shapeView.remove(enemy.getMazeSprite().getImageShape());

        enemy.setMazeSprite(
            new Sprite(R.drawable.bones, 32, 32, 1, 1,
                       gameWorld.getDisplayMetrics().density));

        enemy.getMazeSprite().setSize(Math.min(shapeView.getHeight(), shapeView.getWidth()) / maze.floorSize() * 0.8f);
        shapeView.add(enemy.getMazeSprite().getImageShape());
        enemy.getMazeSprite().setPosition(spritePos.x, spritePos.y);
    }

    @Override
    public void onResume()
    {
        Log.v("GameScreen", "Resumed.");

        if (gameWorld != null)
        {
            if (gameWorld.isGameOver())
            {
                presentScreen(MainMenuScreen.class);
                finish();
            }
            else if (gameWorld.getEnemyKilled() != null)
            {
                this.killEnemy(gameWorld.getEnemyKilled());
            }
        }

        super.onResume();
    }


    @Override
    public void onDestroy()
    {
        Log.v("GameScreen", "Destroyed.");
        shapeView.clear();
        logicThread.setRunning(false);

        super.onDestroy();
    }


}
