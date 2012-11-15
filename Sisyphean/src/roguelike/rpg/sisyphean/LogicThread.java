package roguelike.rpg.sisyphean;

import android.util.Log;

/**
 *  This thread runs the game logic at a constant frame rate.
 *  It calls the update method on every character in order for them to react to
 *  changes in the game world accordingly.
 *
 *  @author Artur
 *  @version Nov 5, 2012
 */
public class LogicThread extends Thread
{
    private GameWorld gameWorld;

    private boolean run;

    private long startTime;
    private long currentTime;
    private int sleepTimeNanos;
    private int millis;

    /**
     * Thread constructor.
     * @param myGameWorld The reference back to the game world.
     */
    public LogicThread(GameWorld myGameWorld)
    {
        Log.v("LogicThread", "created!");
        gameWorld = myGameWorld;

        run = false;
    }

    /**
     * Change the value of run.
     * Make it false to stop the logic from executing.
     * @param newRun The new value for run.
     */
    public void setRunning(boolean newRun)
    {
        run = newRun;
    }

    @Override
    public void run()
    {
        while (run)
        {
            startTime = System.nanoTime();

            for (Character character : gameWorld.getAllCharacters())
            {
                character.update();
            }

            try
            {
                currentTime = System.nanoTime();
                sleepTimeNanos = (int)((1.0f/60.0f) * 1000000000) - (int)(currentTime - startTime);

                if (sleepTimeNanos > 0)
                {
                    millis = sleepTimeNanos / 1000000;
                    Thread.sleep( millis, sleepTimeNanos - millis*1000000 );
                }
                else
                {
                    //Log.i("Logic Thread", "Running behind!");
                }
            }
            catch (InterruptedException e)
            {
                Log.v("LogicThread Exception", e.getMessage());
            }
        }
    }
}