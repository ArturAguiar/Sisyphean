package roguelike.rpg.sisyphean;


import android.text.Editable;
import android.widget.EditText;


import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import android.widget.TextView;
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
    private ShapeView shapeView2;
    private RelativeLayout popup;
    private LinearLayout mainscreen;

    private GameWorld gameWorld;

    private TextView health, mana, level, strength, defense, dexterity, armor,
        weapon, intelligence, healthp, manap, currentitem, currentname,
        currentdesc, currentbuff, newitem, newname, newdesc, newbuff, playerName;
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

    private boolean choiceMenu;
    private ImageShape popUp;


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param playerClass The class of the player's character.
     * @param floor The floor of the maze.
     */
    public void initialize(Character.PlayerType playerClass, int floor, String name)
    {
        // Create the game world and set the display metrics.
        this.gameWorld = new GameWorld();
        this.getWindowManager().getDefaultDisplay().getMetrics(gameWorld.getDisplayMetrics());

        // Start the logic thread.
        gameWorld.setLogicThread(new LogicThread(gameWorld));
        gameWorld.getLogicThread().setGameScreen(this);

        gameWorld.getLogicThread().setRunning(true);
        gameWorld.getLogicThread().start();

        String playerNameString = name;
        if (playerNameString == null || playerNameString.trim().equals(""))
        {
            playerNameString = "Sisyphus";
            Log.v("Name", "name set to sisyphus");
        }

        playerName.setText(playerNameString);

        Log.v("GameScreen", "initialized.");

        switch (playerClass)
        {
            case WARRIOR:
                gameWorld.setPlayer(new Warrior(playerNameString, 250.0f, 250.0f, gameWorld));
                break;
            case WIZARD:
                gameWorld.setPlayer(new Wizard(playerNameString, 250.0f, 250.0f, gameWorld));
                break;
            case ARCHER:
                gameWorld.setPlayer(new Archer(playerNameString, 250.0f, 250.0f, gameWorld));
            default:
                break;
        }

        gameWorld.setMaze(new Maze(gameWorld, floor));

        this.cellSize = Math.min(shapeView.getHeight(), shapeView.getWidth()) / 5;

        // Set the player's size, cell, and position.
        gameWorld.getPlayer().getMazeSprite().setSize(cellSize * 0.8f);
        gameWorld.getPlayer().setPosition(2.1f * cellSize, 2.1f * cellSize);
        gameWorld.getPlayer().setCell(gameWorld.getMaze().startColumn(), gameWorld.getMaze().startRow());
        Log.v("Starting Position", "(" + gameWorld.getMaze().startColumn() + ", " + gameWorld.getMaze().startRow() + ")");

        this.updateFields();
        this.drawMazeSection(gameWorld.getMaze(), gameWorld.getPlayer());

        gameWorld.getLogicThread().setGameScreen(this);

        choiceMenu = false;
        popUp = new ImageShape("popup",
            getWidth() / 2 - cellSize * 2, getHeight() / 2 - cellSize * 2,
            getWidth() / 2 + cellSize * 2, getHeight() / 2 + cellSize * 2);
        popUp.setZIndex(11);
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

        this.updateFields();
        this.drawMazeSection(gameWorld.getMaze(), gameWorld.getPlayer());

        gameWorld.getLogicThread().setGameScreen(this);

        popUp = new ImageShape("popup",
            getWidth() / 2 - cellSize * 2, getHeight() / 2 - cellSize * 2,
            getWidth() / 2 + cellSize * 2, getHeight() / 2 + cellSize * 2);
        popUp.setZIndex(11);
    }

    /**
     * This initialize method gets called when the maze has to be recreated
     * because the player reached the floor's stairs.
     * It does not instantiate the game world, but it makes a new maze
     * bigger than the previous.
     * @param myGameWorld The game world reference.
     * @param floor The floor number of the new maze.
     */
    public void initialize(GameWorld myGameWorld, int floor)
    {
        gameWorld = myGameWorld;

        gameWorld.getAllCharacters().clear();
        gameWorld.getAllCharacters().add(gameWorld.getPlayer());

        gameWorld.setMaze(new Maze(gameWorld, floor));

        this.cellSize = Math.min(shapeView.getHeight(), shapeView.getWidth()) / 5;

        // Set the player's size, cell, and position.
        gameWorld.getPlayer().getMazeSprite().setSize(cellSize * 0.8f);
        gameWorld.getPlayer().setPosition(2.1f * cellSize, 2.1f * cellSize);
        gameWorld.getPlayer().setCell(gameWorld.getMaze().startColumn(), gameWorld.getMaze().startRow());

        this.updateFields();
        this.drawMazeSection(gameWorld.getMaze(), gameWorld.getPlayer());

        gameWorld.getLogicThread().setGameScreen(this);

        popUp = new ImageShape("popup",
            getWidth() / 2 - cellSize * 2, getHeight() / 2 - cellSize,
            getWidth() / 2 + cellSize * 2, getHeight() / 2 + cellSize);
        popUp.setZIndex(11);
    }

    private void updateFields()
    {
        Player thePlayer = gameWorld.getPlayer();
        String name = thePlayer.getName();
        playerName.setText(name);
        String weaponString = thePlayer.getWeapon().getName();
        weapon.setText(weaponString);
        String armorString = thePlayer.getArmor().getName();
        armor.setText(armorString);
        String currentLevel = thePlayer.getLevel() + "";
        String currentStr = (int) (thePlayer.getStrength()) + "";
        String currentDef = (int)(thePlayer.getDefense()) + "";
        String currentDex = (int)(thePlayer.getDexterity()) + "";
        String currentIntel = (int)(thePlayer.getIntelligence()) + "";
        health.setText("Health: " + (int)thePlayer.getHealth() + " / " + (int)thePlayer.getMaxHealth());
        mana.setText("Mana: " + (int)thePlayer.getMana() + " / " + (int)thePlayer.getMaxMana());
        level.setText("Level: " + currentLevel);
        strength.setText("Str: " + currentStr);
        defense.setText("Def: " + currentDef);
        dexterity.setText("Dex: " + currentDex);
        intelligence.setText("Intel: " + currentIntel);
        healthp.setText("Health Potions: " + thePlayer.getHealthPotions());
        manap.setText("Mana Potions: " + thePlayer.getManaPotions());
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
        Log.v("GameScreen", "CellSize = " + this.cellSize);

        shapeView.setAutoRepaint(false);

        for (int row = 0; row < 5; row++)
        {
            int cellRow = player.getCellY() - 2 + row;

            this.drawMazeRow(cellRow);
        }

        if (height > width)
        {
            boundaryRect = new RectangleShape(0.0f, 5 * cellSize,
                                              5 * cellSize, 5 * cellSize + height - width + 20.0f);
        }
        else if (width > height)
        {
            boundaryRect = new RectangleShape(5 * cellSize, 0.0f,
                                              5 * cellSize + width - height + 20.0f, 5 * cellSize);
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

    private void removeMazeRow(int row)
    {
        shapeView.setAutoRepaint(false);

        float top = (row - gameWorld.getPlayer().getCellY() + 2) * cellSize * 1.1f;
        float left = 0;

        float bottom = top + cellSize * 0.9f;
        float right = 5 * cellSize;

        RectangleShape hitRect = new RectangleShape(left, top, right, bottom);
        hitRect.setFillColor(Color.red);
        shapeView.add(hitRect);

        for (Shape toRemove : shapeView.getIntersectingShapes(hitRect, Shape.class))
        {
            if (toRemove != boundaryRect)
                shapeView.remove(toRemove);
        }

        //shapeView.remove(hitRect);

        shapeView.setAutoRepaint(true);
        shapeView.repaint();
    }

    private void removeMazeCol(int col)
    {
        shapeView.setAutoRepaint(false);

        float top = 0.0f;
        float left = (col - gameWorld.getPlayer().getCellX() + 2) * cellSize * 1.1f;

        float bottom = 5 * cellSize;
        float right = left + cellSize * 0.9f;

        RectangleShape hitRect = new RectangleShape(left, top, right, bottom);
        hitRect.setFilled(true);
        shapeView.add(hitRect);

        for (Shape toRemove : shapeView.getIntersectingShapes(hitRect, Shape.class))
        {
            if (toRemove != boundaryRect)
                shapeView.remove(toRemove);
        }

        //shapeView.remove(hitRect);

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

            TextShape levelText = new TextShape("" + cell.getEnemy().getLevel(),
                left + cellSize * 0.1f, top + cellSize * 0.1f);

            levelText.setColor(Color.red);
            levelText.setTypefaceAndSize("*-bold-8");
            shapeView.add(levelText);
            levelText.setPosition(left + cellSize * 0.9f - levelText.getWidth(),
                                  top + cellSize * 0.9f - levelText.getHeight());
        }
        else if (cell.getItem() != null)
        {
            cell.getItem().getMazeIcon().setBounds(new RectF(
                0.0f,
                0.0f,
                cellSize * 0.6f,
                cellSize * 0.6f));
            cell.getItem().setPosition(left + cellSize * 0.2f, top + cellSize * 0.2f);

            shapeView.add(cell.getItem().getMazeIcon());
        }
        else if (cell.isExit())
        {
            ImageShape exit = new ImageShape(R.drawable.stairs, new RectF(0.0f, 0.0f, cellSize, cellSize));
            exit.setPosition(left, top);
            shapeView.add(exit);
        }
    }


    /**
     * Method called when the user touches the screen.
     * Used for testing at the moment.
     * @param event The information about the touch.
     */
    public void onTouchDown(MotionEvent event)
    {
        if (choiceMenu && shapeView2.getBounds().contains(event.getX(), event.getY()))
        {
            if (chooseItem(event))
            {
                shapeView.remove(gameWorld.getMaze().getCell(gameWorld.getPlayer().getCellX(), gameWorld.getPlayer().getCellY()).getItem().getMazeIcon());
                gameWorld.getMaze().getCell(gameWorld.getPlayer().getCellX(), gameWorld.getPlayer().getCellY()).setItem(null);
            }
                runOnUiThread(new Runnable() {

                public void run()
                {
                    choiceMenu = false;
                    shapeView.remove(popUp);
                    currentitem.setText("");
                    currentname.setText("");
                    currentdesc.setText("");
                    currentbuff.setText("");
                    newitem.setText("");
                    newname.setText("");
                    newdesc.setText("");
                    newbuff.setText("");
                    mainscreen.bringToFront();
                    weapon.setText(gameWorld.getPlayer().getWeapon().getName());
                    armor.setText(gameWorld.getPlayer().getArmor().getName());
                }

                });
        }
    }

    /**
     * Helper method for onTouchDown to determine whether the player chooses to
     * keep their current item or pick up the new one found.
     * @param e Where the player touches
     * @return boolean Whether the player chose the new item or not
     */
    private boolean chooseItem(MotionEvent e)
    {
        //If the player chooses to keep their weapon by touching the right side,
        // do nothing.
        if (e.getX() < shapeView2.getWidth() / 2)
        {
            return false;
        }
        //otherwise swap items
        else
        {
            if (gameWorld.getMaze().getCell(
                gameWorld.getPlayer().getCellX(), gameWorld.getPlayer().getCellY()).getItem()
                instanceof Weapon)
            {
                gameWorld.getPlayer().setWeapon((Weapon)gameWorld.getMaze().getCell(
                    gameWorld.getPlayer().getCellX(), gameWorld.getPlayer().getCellY()).getItem());
            }
            else
            {
                gameWorld.getPlayer().setArmor((Armor)gameWorld.getMaze().getCell(
                    gameWorld.getPlayer().getCellX(), gameWorld.getPlayer().getCellY()).getItem());
            }
            return true;
        }
    }

    // ----------------------------------------------------------
    /**
     * Move the player down.
     */
    public void downClicked()
    {
        if (gameWorld.getPlayer().isWalking() || choiceMenu)
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
        if (gameWorld.getPlayer().isWalking() || choiceMenu)
            return;

        int x = gameWorld.getPlayer().getCellX();
        int y = gameWorld.getPlayer().getCellY();

        if (!gameWorld.getMaze().getCell(x, y).getWalls()[0] && y - 1 >= 0 && !checkForEnemyIn(x, y - 1))
        {
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
        if (gameWorld.getPlayer().isWalking() || choiceMenu)
            return;

        int x = gameWorld.getPlayer().getCellX();
        int y = gameWorld.getPlayer().getCellY();

        if (!gameWorld.getMaze().getCell(x, y).getWalls()[1] && x + 1 < gameWorld.getMaze().floorSize() && !checkForEnemyIn(x + 1, y))
        {
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
        if (gameWorld.getPlayer().isWalking() || choiceMenu)
            return;

        int x = gameWorld.getPlayer().getCellX();
        int y = gameWorld.getPlayer().getCellY();

        if (!gameWorld.getMaze().getCell(x, y).getWalls()[3] && x - 1 >= 0 && !checkForEnemyIn(x - 1, y))
        {
            shapeView.setAutoRepaint(false);
            this.drawMazeCol(x - 3);
            shapeView.setAutoRepaint(true);
            shapeView.repaint();
            this.move("left");
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

    /**
     * Checks for the existence of an item in the given cell coordinate.
     * If there is a potion, pick it up.
     * Make a choice pop-up for weapons and armor.
     * @param x The x coordinate of the cell to check.
     * @param y the y coordinate of the cell to check.
     */
    private void checkForItem(int x, int y)
    {
        if (gameWorld.getMaze().getCell(x, y).getItem() != null)
        {
            Item item = gameWorld.getMaze().getCell(x, y).getItem();
            if (item instanceof Potion)
            {
                gameWorld.getPlayer().pickUpPotion(((Potion)item).getType());
                gameWorld.getMaze().getCell(x, y).setItem(null);

                runOnUiThread(new Runnable() {
                    public void run()
                    {
                        healthp.setText("Health Potions: " + gameWorld.getPlayer().getHealthPotions());
                        manap.setText("Mana Potions: " + gameWorld.getPlayer().getManaPotions());
                    }
                });

                shapeView.remove(item.getMazeIcon());
            }
            else if (item instanceof Weapon)
                {
                    runOnUiThread(new Runnable() {
                        public void run()
                        {
                            currentitem.setText("Keep Current Item?");
                            currentname.setText(gameWorld.getPlayer().getWeapon().getName());
                            currentdesc.setText(gameWorld.getPlayer().getWeapon().getDescription());
                            currentbuff.setText("Damage: " + (int)gameWorld.getPlayer().getWeapon().getDamage());
                            newitem.setText("Take New Item?");
                            newname.setText(gameWorld.getMaze().getCell(gameWorld.getPlayer().getCellX(), gameWorld.getPlayer().getCellY()).getItem().getName());
                            newdesc.setText(gameWorld.getMaze().getCell(gameWorld.getPlayer().getCellX(), gameWorld.getPlayer().getCellY()).getItem().getDescription());
                            newbuff.setText("Damage :" + (int)((Weapon)gameWorld.getMaze().getCell(gameWorld.getPlayer().getCellX(), gameWorld.getPlayer().getCellY()).getItem()).getDamage());
                            shapeView.add(popUp);
                            popup.bringToFront();
                            choiceMenu = true;
                        }

                    });
                }
                else
                {
                    runOnUiThread(new Runnable() {
                        public void run()
                        {
                            currentitem.setText("Keep Current Item?");
                            currentname.setText(gameWorld.getPlayer().getArmor().getName());
                            currentdesc.setText(gameWorld.getPlayer().getArmor().getDescription());
                            currentbuff.setText("Defense: " + (int)gameWorld.getPlayer().getArmor().getDefense());
                            newitem.setText("Take New Item?");
                            newname.setText(gameWorld.getMaze().getCell(gameWorld.getPlayer().getCellX(), gameWorld.getPlayer().getCellY()).getItem().getName());
                            newdesc.setText(gameWorld.getMaze().getCell(gameWorld.getPlayer().getCellX(), gameWorld.getPlayer().getCellY()).getItem().getDescription());
                            newbuff.setText("Defense: " + (int)((Armor)gameWorld.getMaze().getCell(gameWorld.getPlayer().getCellX(), gameWorld.getPlayer().getCellY()).getItem()).getDefense());
                            shapeView.add(popUp);
                            popup.bringToFront();
                            choiceMenu = true;
                        }

                    });
                }
            }

    }

    private void checkForExit(int x, int y)
    {
        // If you move into the the exit cell, you move to the next floor
        if (gameWorld.getMaze().getCell(x, y).isExit())
        {
            this.clear();
            presentScreen(GameScreen.class, gameWorld, gameWorld.getMaze().getFloor() + 1);
            finish();
        }
    }

    /**
     * Consume a health potion.
     */
    public void healthbuttonClicked()
    {
        if (gameWorld.getPlayer().getHealth() < gameWorld.getPlayer().getMaxHealth())
        {
            gameWorld.getPlayer().consumePotion(PotionType.HEALTH);
            healthp.setText("Health Potions: " + gameWorld.getPlayer().getHealthPotions());
            health.setText("Health: " + (int)gameWorld.getPlayer().getHealth() +
                " / " + (int)gameWorld.getPlayer().getMaxHealth());
        }

        Log.v("Health Potions", gameWorld.getPlayer().getHealthPotions() + " potions");
    }

    /**
     * Consume a mana potion.
     */
    public void manabuttonClicked()
    {
        if (gameWorld.getPlayer().getMana() < gameWorld.getPlayer().getMaxMana())
        {
            gameWorld.getPlayer().consumePotion(PotionType.MANA);
            manap.setText("Mana Potions: " + gameWorld.getPlayer().getManaPotions());
            mana.setText("Mana: " + (int)gameWorld.getPlayer().getMana() +
                " / " + (int)gameWorld.getPlayer().getMaxMana());
        }

        Log.v("Mana Potions", gameWorld.getPlayer().getManaPotions() + " potions");
    }


    @Override
    public void onDestroy()
    {
        Log.v("GameScreen", "Destroyed.");
        shapeView.clear();
        //gameWorld.getLogicThread().setGameScreen(null);

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

            if (moveBy.y >= moveSpeed)
            {
                this.translateViewBy(0.0f, moveSpeed, true);
                moveBy.set(moveBy.x, moveBy.y - moveSpeed);
            }
            else
            {
                this.translateViewBy(0.0f, moveBy.y, true);
                moveBy.set(moveBy.x, 0.0f);
            }


            if (moveBy.y == 0.0f)
            {
                this.removeMazeRow(gameWorld.getPlayer().getCellY() + 3);
            }
        }
        else if (moveBy.y < 0.0f)
        {
            if (moveBy.y <= -moveSpeed )
            {
                this.translateViewBy(0.0f, -moveSpeed, true);
                moveBy.set(moveBy.x, moveBy.y + moveSpeed);
            }
            else
            {
                this.translateViewBy(0.0f, moveBy.y, true);
                moveBy.set(moveBy.x, 0.0f);
            }

            if (moveBy.y == 0.0f)
            {
                this.removeMazeRow(gameWorld.getPlayer().getCellY() - 3);
            }
        }
        else if (moveBy.x > 0.0f)
        {
            if (moveBy.x >= moveSpeed)
            {
                this.translateViewBy(moveSpeed, 0.0f, true);
                moveBy.set(moveBy.x - moveSpeed, moveBy.y);
            }
            else
            {
                this.translateViewBy(moveBy.x, 0.0f, true);
                moveBy.set(0.0f, moveBy.y);
            }

            if (moveBy.x == 0.0f)
            {
                this.removeMazeCol(gameWorld.getPlayer().getCellX() + 3);
            }
        }
        else if (moveBy.x < 0.0f)
        {
            if (moveBy.x <= -moveSpeed)
            {
                this.translateViewBy(-moveSpeed, 0.0f, true);
                moveBy.set(moveBy.x + moveSpeed, moveBy.y);
            }
            else
            {
                this.translateViewBy(moveBy.x, 0.0f, true);
                moveBy.set(0.0f, moveBy.y);
            }

            if (moveBy.x == 0.0f)
            {
                this.removeMazeCol(gameWorld.getPlayer().getCellX() - 3);
            }
        }
        else if (gameWorld.getPlayer() != null && gameWorld.getPlayer().isWalking())
        {
            gameWorld.getPlayer().stopMoving();
            checkForItem(gameWorld.getPlayer().getCellX(), gameWorld.getPlayer().getCellY());
            checkForExit(gameWorld.getPlayer().getCellX(), gameWorld.getPlayer().getCellY());
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
