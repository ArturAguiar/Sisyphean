package roguelike.rpg.sisyphean;

import sofia.graphics.TextShape;
import sofia.graphics.Color;
import sofia.graphics.RectangleShape;
import sofia.graphics.Shape;
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

    private GameWorld gameWorld;


    // Maze images
    private Image groundImage;
    private Image backImage;
    private Image rightImage;
    private Image rightFrontImage;
    private Image leftImage;
    private Image leftFrontImage;

    private RectangleShape boundaryRect;

    //Cell size
    private float cellSize;

    // To control the movement
    private PointF moveBy = new PointF();
    private final float moveSpeed = 5.0f;


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
        gameWorld.setLogicThread(new LogicThread(gameWorld));
        gameWorld.getLogicThread().setGameScreen(this);

        gameWorld.getLogicThread().setRunning(true);
        gameWorld.getLogicThread().start();


        Log.v("GameScreen", "initialized.");

        switch (playerClass)
        {
            case WARRIOR:
                gameWorld.setPlayer(new Warrior("John Doe", 250.0f, 250.0f, gameWorld));
                break;
            case WIZARD:
                gameWorld.setPlayer(new Wizard("John Doe", 250.0f, 250.0f, gameWorld));
                break;
            case ARCHER:
                gameWorld.setPlayer(new Archer("John Doe", 250.0f, 250.0f, gameWorld));
            default:
                break;
        }

        gameWorld.setMaze(new Maze(gameWorld, floor));

        this.cellSize = Math.min(shapeView.getHeight(), shapeView.getWidth()) / 5;

        // Set the player's size, cell, and position.
        gameWorld.getPlayer().getMazeSprite().setSize(cellSize * 0.8f);
        gameWorld.getPlayer().setPosition(2.1f * cellSize, 2.1f * cellSize);
        gameWorld.getPlayer().setCell(gameWorld.getMaze().startColumn(), gameWorld.getMaze().startRow());

        this.drawMazeSection(gameWorld.getMaze(), gameWorld.getPlayer());
    }

    /**
     * This initialize method gets called when the maze was already created
     * once before.
     * It does not instantiate the game world, the player, and etc.
     * @param myGameWorld The game world reference.
     */
    public void initialize(GameWorld myGameWorld)
    {
        this.gameWorld = myGameWorld;
        gameWorld.getLogicThread().setGameScreen(this);

        this.drawMazeSection(gameWorld.getMaze(), gameWorld.getPlayer());
    }

    private void drawMazeSection(Maze maze, Player player)
    {
        groundImage = new Image("ground");
        backImage = new Image("back");
        rightImage = new Image("right");
        rightFrontImage = new Image("right_front");
        leftImage = new Image("left");
        leftFrontImage = new Image("left_front");

        float height = shapeView.getHeight();
        float width = shapeView.getWidth();
        float size = Math.min(height, width);
        this.cellSize = size / 5;

        shapeView.setAutoRepaint(false);

        for (int row = 0; row < 5; row++)
        {
            int cellRow = player.getCellY() - 2 + row;

            this.drawMazeRow(cellRow);
        }

        if (height > width)
        {
            boundaryRect = new RectangleShape(0.0f, 5 * cellSize,
                                              5 * cellSize, 5 * cellSize + height - width + 1);
        }
        else if (width > height)
        {
            boundaryRect = new RectangleShape(5 * cellSize, 0.0f,
                                              5 * cellSize + width - height + 1, 5 * cellSize);
        }

        boundaryRect.setFilled(true);
        boundaryRect.setColor(Color.black);
        boundaryRect.setFillColor(Color.black);
        shapeView.add(boundaryRect);
        boundaryRect.setZIndex(2);

        // Add the player.
        shapeView.add(gameWorld.getPlayer().getMazeSprite().getImageShape());
        gameWorld.getPlayer().getMazeSprite().getImageShape().setZIndex(10);

        // Re-enable repaint and repaint the view.
        shapeView.setAutoRepaint(true);
        shapeView.repaint();


    }

    private void drawMazeRow(int row)
    {
        int cellCol;

        Cell cell;
        Cell topCell;

        for (int col = 0; col < 5; col++)
        {
            cellCol = gameWorld.getPlayer().getCellX() - 2 + col;

            cell = gameWorld.getMaze().getCell(cellCol, row);
            topCell = gameWorld.getMaze().getCell(cellCol, row - 1);

            this.drawCell(cell, topCell, (row - gameWorld.getPlayer().getCellY() + 2) * cellSize, col * cellSize);
        }
    }

    private void removeMazeRow(int row)
    {
        shapeView.setAutoRepaint(false);

        float top = (row - gameWorld.getPlayer().getCellY() + 2) * cellSize;
        float left = 0;

        float bottom = top + cellSize;
        float right = 5 * cellSize;

        RectangleShape hitRect = new RectangleShape(left, top, right, bottom);
        hitRect.setFilled(true);
        hitRect.setFillColor(Color.black);
        shapeView.add(hitRect);

        for (Shape toRemove : shapeView.getIntersectingShapes(hitRect, ImageShape.class))
        {
            shapeView.remove(toRemove);
        }

        shapeView.remove(hitRect);

        shapeView.setAutoRepaint(true);
        shapeView.repaint();
    }

    private void drawMazeCol(int col)
    {
        int cellRow;

        Cell cell;
        Cell topCell;

        for (int row = 0; row < 5; row++)
        {
            cellRow = gameWorld.getPlayer().getCellY() - 2 + row;

            cell = gameWorld.getMaze().getCell(col, cellRow);
            topCell = gameWorld.getMaze().getCell(col, cellRow - 1);

            this.drawCell(cell, topCell, row * cellSize, (col - gameWorld.getPlayer().getCellX() + 2) * cellSize);
        }
    }

    private void removeMazeCol(int col)
    {
        shapeView.setAutoRepaint(false);

        float top = 0.0f;
        float left = (col - gameWorld.getPlayer().getCellX() + 2) * cellSize;

        float bottom = 5 * cellSize;
        float right = left + cellSize;

        RectangleShape hitRect = new RectangleShape(left, top, right, bottom);
        hitRect.setFilled(true);
        shapeView.add(hitRect);

        for (Shape toRemove : shapeView.getIntersectingShapes(hitRect, ImageShape.class))
        {
            shapeView.remove(toRemove);
        }

        shapeView.remove(hitRect);

        shapeView.setAutoRepaint(true);
        shapeView.repaint();
    }

    private void drawCell(Cell cell, Cell topCell, float top, float left)
    {
        if (cell == null)
            return;

        RectF position = new RectF(left, top, left + cellSize, top + cellSize);

        shapeView.add(new ImageShape(groundImage, position));

        if (cell.getWalls()[0]) //top wall
        {
            shapeView.add(new ImageShape(backImage, position));
        }
        if (cell.getWalls()[1]) //right wall
        {
            shapeView.add(new ImageShape(rightImage, position));
        }
        else if (topCell != null && topCell.getWalls()[1])
        {
            shapeView.add(new ImageShape(rightFrontImage, position));
        }
        if (cell.getWalls()[3]) //left wall
        {
            shapeView.add(new ImageShape(leftImage, position));
        }
        else if (topCell != null && topCell.getWalls()[3])
        {
            shapeView.add(new ImageShape(leftFrontImage, position));
        }

        if (cell.getEnemy() != null)
        {
            cell.getEnemy().setPosition(left + cellSize * 0.1f, top + cellSize * 0.1f);
            cell.getEnemy().getMazeSprite().setSize(cellSize * 0.8f);
            shapeView.add(cell.getEnemy().getMazeSprite().getImageShape());

            TextShape levelText = new TextShape("" + cell.getEnemy().getLevel(), 0, 0);

            levelText.setColor(Color.red);
            levelText.setTypefaceAndSize("*-bold-8");
            shapeView.add(levelText);
            levelText.setPosition(left + cellSize - levelText.getWidth() - 5.0f,
                                  top + cellSize - levelText.getHeight() - 5.0f);
        }

        if (cell.getItem() != null)
        {
            cell.getItem().setPosition(left + cellSize * 0.2f, top + cellSize * 0.3f);
            cell.getItem().getMazeIcon().setSize(cellSize * 0.8f);
            shapeView.add(cell.getItem().getMazeIcon().getImageShape());
        }
    }

    /*
    public void drawMaze(Maze maze)
    {
        float y = shapeView.getHeight();
        float x = shapeView.getWidth();
        float size = Math.min(y, x);
        float cellSize = size / maze.floorSize();
        float bottom = cellSize;
        float right = cellSize;
        float top = 0;
        float left = 0;

        // Load all images only once instead of inside the loop.
        Image groundImage = new Image("ground");
        Image backImage = new Image("back");
        Image rightImage = new Image("right");
        Image rightFrontImage = new Image("right_front");
        Image leftImage = new Image("left");
        Image leftFrontImage = new Image("left_front");

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

                if (maze.getCell(col, row).getItem() != null)
                {
                    maze.getCell(col, row).getItem().setPosition(left + cellSize * 0.2f, top + cellSize * 0.3f);
                    maze.getCell(col, row).getItem().getMazeIcon().setSize(cellSize * 0.8f);
                    shapeView.add(maze.getCell(col, row).getItem().getMazeIcon().getImageShape());
                }
            }

        }

        // Draw stairs
        shapeView.add(new ImageShape("stairs",
            cellSize * maze.exitColumn(),
            cellSize * maze.exitRow(),
            cellSize * (maze.exitColumn() + 1),
            cellSize * (maze.exitRow() + 1)));

        // Add the player.
        gameWorld.getPlayer().setPosition(
            cellSize * gameWorld.getMaze().startColumn() + cellSize * 0.1f,
            cellSize * gameWorld.getMaze().startRow() + cellSize * 0.1f);
        gameWorld.getPlayer().setCell(gameWorld.getMaze().startColumn(), gameWorld.getMaze().startRow());
        gameWorld.getPlayer().getMazeSprite().setSize(cellSize * 0.8f);
        shapeView.add(gameWorld.getPlayer().getMazeSprite().getImageShape());
        gameWorld.getPlayer().getMazeSprite().getImageShape().setZIndex(2);

        // Re-enable repaint and repaint the view.
        shapeView.setAutoRepaint(true);
        shapeView.repaint();
    }
    */


    /**
     * Method called when the user touches the screen.
     * Used for testing at the moment.
     * @param event The information about the touch.
     */
    public void onTouchDown(MotionEvent event)
    {
        // If you click on the exit cell, you move to the next floor
        if (event.getX() > cellSize * gameWorld.getMaze().exitColumn() &&
            event.getX() < cellSize * (gameWorld.getMaze().exitColumn() + 1) &&
            event.getY() > cellSize * gameWorld.getMaze().exitRow() &&
            event.getY() < cellSize * (gameWorld.getMaze().exitRow() + 1))
        {
            this.clear();
            presentScreen(GameScreen.class, Character.PlayerType.WARRIOR, gameWorld.getMaze().getFloor() + 1);
            finish();
        }
        else
        {
            // Pops up a toast with information for testing purposes
            //Toast.makeText(this, gameWorld.getMaze().counter(), Toast.LENGTH_LONG).show();
        }
    }

    // ----------------------------------------------------------
    /**
     * Move the player down.
     */
    public void downClicked()
    {
        if (gameWorld.getPlayer().isWalking())
            return;

        int x = gameWorld.getPlayer().getCellX();
        int y = gameWorld.getPlayer().getCellY();

        if (!gameWorld.getMaze().getCell(x, y).getWalls()[2] && y + 1 < gameWorld.getMaze().floorSize() && !checkForEnemyIn(x, y + 1))
        {
            //gameWorld.getPlayer().move("down", Math.min(shapeView.getHeight(), shapeView.getWidth()) / gameWorld.getMaze().floorSize());
            shapeView.setAutoRepaint(false);
            this.drawMazeRow(y + 3);
            shapeView.setAutoRepaint(true);
            shapeView.repaint();
            this.move("down");
        }

    }

    // ----------------------------------------------------------
    /**
     * Move the player up.
     */
    public void upClicked()
    {
        if (gameWorld.getPlayer().isWalking())
            return;

        int x = gameWorld.getPlayer().getCellX();
        int y = gameWorld.getPlayer().getCellY();

        if (!gameWorld.getMaze().getCell(x, y).getWalls()[0] && y - 1 >= 0 && !checkForEnemyIn(x, y - 1))
        {
            //gameWorld.getPlayer().move("up", Math.min(shapeView.getHeight(), shapeView.getWidth()) / gameWorld.getMaze().floorSize());
            shapeView.setAutoRepaint(false);
            this.drawMazeRow(y - 3);
            shapeView.setAutoRepaint(true);
            shapeView.repaint();
            this.move("up");
        }
    }

    // ----------------------------------------------------------
    /**
     * Move the player right.
     */
    public void rightClicked()
    {
        if (gameWorld.getPlayer().isWalking())
            return;

        int x = gameWorld.getPlayer().getCellX();
        int y = gameWorld.getPlayer().getCellY();

        if (!gameWorld.getMaze().getCell(x, y).getWalls()[1] && x + 1 < gameWorld.getMaze().floorSize() && !checkForEnemyIn(x + 1, y))
        {
            //gameWorld.getPlayer().move("right", Math.min(shapeView.getHeight(), shapeView.getWidth()) / gameWorld.getMaze().floorSize());
            shapeView.setAutoRepaint(false);
            this.drawMazeCol(x + 3);
            shapeView.setAutoRepaint(true);
            shapeView.repaint();
            this.move("right");
        }


    }

    // ----------------------------------------------------------
    /**
     * Move the player left.
     */
    public void leftClicked()
    {
        if (gameWorld.getPlayer().isWalking())
            return;

        int x = gameWorld.getPlayer().getCellX();
        int y = gameWorld.getPlayer().getCellY();

        if (!gameWorld.getMaze().getCell(x, y).getWalls()[3] && x - 1 >= 0 && !checkForEnemyIn(x - 1, y))
        {
            //gameWorld.getPlayer().move("left", Math.min(shapeView.getHeight(), shapeView.getWidth()) / gameWorld.getMaze().floorSize());
            shapeView.setAutoRepaint(false);
            this.drawMazeCol(x - 3);
            shapeView.setAutoRepaint(true);
            shapeView.repaint();
            this.move("left");
        }


    }

    /*
    public void playerMovementAnimationEnded()
    {
        gameWorld.getPlayer().stopMoving();
    }
    */

    /**
     * Checks for the existence of an enemy in the given cell coordinate.
     * If there is an enemy, initiate a battle with it.
     * @param x The x coordinate of the cell to check.
     * @param y the y coordinate of the cell to check.
     * @return True if an enemy was found and a battle was started, false otherwise.
     */
    public boolean checkForEnemyIn(int x, int y)
    {
        if (!gameWorld.getPlayer().isWalking())
        {
            Enemy myEnemy = gameWorld.getMaze().getCell(x, y).getEnemy();
            if (myEnemy != null &&
                myEnemy.isAlive())
            {
                presentScreen(BattleScreen.class, gameWorld, myEnemy);
                finish();
                return true;
            }
        }

        return false;
    }


    @Override
    public void onDestroy()
    {
        Log.v("GameScreen", "Destroyed.");
        shapeView.clear();
        gameWorld.getLogicThread().setGameScreen(null);

        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
        // Stop the logic thread.
        gameWorld.getLogicThread().setRunning(false);

        super.onBackPressed();
    }

    /**
     * Called by the logic thread to update this view.
     */
    public void update()
    {

        if (moveBy.y > 0.0f)
        {
            this.translateViewBy(0.0f, moveSpeed, true);
            moveBy.set(moveBy.x, moveBy.y - moveSpeed);
            if (moveBy.y <= 0.0f)
            {
                this.translateViewBy(0.0f, moveBy.y, true);
                moveBy.set(moveBy.x, 0.0f);

                this.removeMazeRow(gameWorld.getPlayer().getCellY() + 3);
            }
        }
        else if (moveBy.y < 0.0f)
        {
            this.translateViewBy(0.0f, -moveSpeed, true);
            moveBy.set(moveBy.x, moveBy.y + moveSpeed);
            if (moveBy.y >= 0.0f)
            {
                this.translateViewBy(0.0f, moveBy.y, true);
                moveBy.set(moveBy.x, 0.0f);

                this.removeMazeRow(gameWorld.getPlayer().getCellY() - 3); //should be 4
            }
        }
        else if (moveBy.x > 0.0f)
        {
            this.translateViewBy(moveSpeed, 0.0f, true);
            moveBy.set(moveBy.x - moveSpeed, moveBy.y);
            if (moveBy.x <= 0.0f)
            {
                this.translateViewBy(moveBy.x, 0.0f, true);
                moveBy.set(0.0f, moveBy.y);

                this.removeMazeCol(gameWorld.getPlayer().getCellX() + 3);
            }
        }
        else if (moveBy.x < 0.0f)
        {
            this.translateViewBy(-moveSpeed, 0.0f, true);
            moveBy.set(moveBy.x + moveSpeed, moveBy.y);
            if (moveBy.x >= 0.0f)
            {
                this.translateViewBy(moveBy.x, 0.0f, true);
                moveBy.set(0.0f, moveBy.y);

                this.removeMazeCol(gameWorld.getPlayer().getCellX() - 3);
            }
        }
        else if (gameWorld.getPlayer() != null && gameWorld.getPlayer().isWalking())
        {
            gameWorld.getPlayer().stopMoving();
        }
    }

    private void move(String direction)
    {
        if(gameWorld.getPlayer().isWalking())
            return;


        if (direction == "left")
        {
            moveBy.set(cellSize, 0.0f);
        }
        else if (direction == "right")
        {
            moveBy.set(-cellSize, 0.0f);
        }
        else if (direction == "up")
        {
            moveBy.set(0.0f, cellSize);
        }
        else if (direction == "down")
        {
            moveBy.set(0.0f, -cellSize);
        }

        gameWorld.getPlayer().startMoving(direction);
    }

    private void translateViewBy(float x, float y, boolean leavePlayer)
    {
        ImageShape playerImage = gameWorld.getPlayer().getMazeSprite().getImageShape();

        shapeView.setAutoRepaint(false);

        Log.v("GameScreen", "Translated view." );

        synchronized (shapeView)
        {
            for ( Shape shape : shapeView.getShapes())
            {
                if (shape != boundaryRect && (!leavePlayer || shape != playerImage))
                    shape.move(x, y);
            }
        }
        shapeView.setAutoRepaint(true);
        shapeView.repaint();
    }


}
