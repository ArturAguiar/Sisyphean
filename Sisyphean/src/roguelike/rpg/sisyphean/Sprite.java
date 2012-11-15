package roguelike.rpg.sisyphean;

import android.graphics.PointF;
import android.graphics.RectF;
import sofia.graphics.ImageShape;

public class Sprite
{
    private ImageShape image;
    private int cols;
    private int frameWidth;
    private int frameHeight;
    private int currentCol;
    private int currentRow;

    public Sprite(int bitmapId, int width, int height, int columns, int rows, float density)
    {
        this.cols = columns;
        frameWidth = (int)((width * density) / columns);
        frameHeight = (int)((height * density) / rows);
        image = new ImageShape(bitmapId, new RectF(0.0f, 0.0f, frameWidth, frameHeight));
        this.updateSource();
    }

    public void step()
    {
        this.setCol(currentCol + 1);

        this.updateSource();
    }

    public void setRow(int newRow)
    {
        currentRow = newRow;
        this.setCol(0);
    }

    public void setCol(int col)
    {
        currentCol = col;
        if (currentCol >= cols || currentCol < 0)
        {
            currentCol = 0;
        }

        this.updateSource();
    }

    public void setPosition(float x, float y)
    {
        image.setPosition(x, y);
    }

    public PointF getPosition()
    {
        return image.getPosition();
    }

    // TODO: have to account for the density...
    public void move(float x, float y)
    {
        image.setPosition(image.getPosition().x + x, image.getPosition().y + y);
    }

    public ImageShape getImageShape()
    {
        return image;
    }

    private void updateSource()
    {
        image.setSourceBounds(currentCol * frameWidth,
                              currentRow * frameHeight,
                              (currentCol + 1) * frameWidth,
                              (currentRow + 1) * frameHeight);
    }

}
