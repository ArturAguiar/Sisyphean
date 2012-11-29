package roguelike.rpg.sisyphean;

import android.graphics.PointF;
import android.graphics.RectF;
import sofia.graphics.ImageShape;

/**
 * // -------------------------------------------------------------------------
/**
 *  Creates a class to make the sprite.
 *
 *  @author Artur
 *  @version Nov 15, 2012
 */
public class Sprite
{
    private ImageShape image;
    private int cols;
    private int frameWidth;
    private int frameHeight;
    private int currentCol;
    private int currentRow;

    /**
     * Instantiates the sprite size and image.
     * @param bitmapId
     * @param width
     * @param height
     * @param columns
     * @param rows
     * @param density
     */
    public Sprite(int bitmapId, int width, int height, int columns, int rows, float density)
    {
        this.cols = columns;
        frameWidth = (int)((width * density) / columns);
        frameHeight = (int)((height * density) / rows);
        image = new ImageShape(bitmapId, new RectF(0.0f, 0.0f, frameWidth, frameHeight));
        this.updateSource();
    }

    /**
     * Step for the sprite.
     */
    public void step()
    {
        this.setCol(currentCol + 1);

        this.updateSource();
    }

    /**
     * Sets the row number.
     * @param newRow The new row number
     */
    public void setRow(int newRow)
    {
        currentRow = newRow;
        this.setCol(0);
    }

    /**
     * Set column number.
     * @param col The new column number
     */
    public void setCol(int col)
    {
        currentCol = col;
        if (currentCol >= cols || currentCol < 0)
        {
            currentCol = 0;
        }

        this.updateSource();
    }

    /**
     * Sets the position of the image.
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public void setPosition(float x, float y)
    {
        image.setPosition(x, y);
    }

    /**
     * Returns the position of the image.
     * @return pointF Returns the position
     */
    public PointF getPosition()
    {
        return image.getPosition();
    }

    // TODO: have to account for the density...
    /**
     * Moves the image.
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public void move(float x, float y)
    {
        image.setPosition(image.getPosition().x + x, image.getPosition().y + y);
    }

    /**
     * Returns the image shape.
     * @return ImageShape The image to be returned
     */
    public ImageShape getImageShape()
    {
        return image;
    }

    /**
     * Updates the image.
     */
    private void updateSource()
    {
        image.setSourceBounds(currentCol * frameWidth,
                              currentRow * frameHeight,
                              (currentCol + 1) * frameWidth,
                              (currentRow + 1) * frameHeight);
    }

    /**
     * Returns the current column for testing purposes.
     * @return currentCol The current column
     */
    public int getColumn()
    {
        return currentCol;
    }

    /**
     * Returns the current row for testing purposes.
     * @return currentRow The current row
     */
    public int getRow()
    {
        return currentRow;
    }

}
