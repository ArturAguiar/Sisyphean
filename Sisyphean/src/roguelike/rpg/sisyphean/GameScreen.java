package roguelike.rpg.sisyphean;

import android.util.DisplayMetrics;
import android.view.MotionEvent;
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
    private GameWorld gameWorld;

    private Maze maze;
    private RectangleShape[][] visualMaze;
    private float cellSize;

    private LogicThread logicThread;


    //for testing
    private int action = 0;

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void initialize()
    {
        gameWorld = new GameWorld();
        this.getWindowManager().getDefaultDisplay().getMetrics(gameWorld.getDisplayMetrics());

        gameWorld.setPlayer(new Warrior("Daedalus", this.getWidth()/2.0f,this.getHeight()/2.0f, gameWorld));

        // Start the logic thread.
        logicThread = new LogicThread(gameWorld);
        logicThread.setRunning(true);
        logicThread.start();


        maze = new Maze(1);
        visualMaze = new RectangleShape[10][10];
        float y = getHeight();
        float x = getWidth();
        float size = Math.min(y, x);
        cellSize = size / 10;
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

        // Add the player's sprites.
        // TODO: Is there a way to do this in the player class?
        this.add(gameWorld.getPlayer().getMazeSprite().getImageShape());
        this.add(gameWorld.getPlayer().getArmor().getMazeSprite().getImageShape());
        this.add(gameWorld.getPlayer().getBattleSprite().getImageShape());
    }

    public void downClicked()
    {
        gameWorld.getPlayer().move("down");
    }

    public void upClicked()
    {
        gameWorld.getPlayer().move("up");
    }

    public void rightClicked()
    {
        gameWorld.getPlayer().move("right");
    }

    public void leftClicked()
    {
        gameWorld.getPlayer().move("left");
    }

    public void onTouchDown(MotionEvent event)
    {
        // This cycles through the animation types when the screen is touched.
        // For testing.
        switch(action)
        {
            case 0:
                gameWorld.getPlayer().setBattleAction(Player.BattleAction.ATTACKING);
                action++;
                break;

            case 1:
                gameWorld.getPlayer().setBattleAction(Player.BattleAction.MOVING);
                action++;
                break;

            case 2:
                gameWorld.getPlayer().setBattleAction(Player.BattleAction.IDLE);
                action = 0;
                break;

            default:
                break;
        }

    }

}
