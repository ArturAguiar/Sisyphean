package roguelike.rpg.sisyphean;

// -------------------------------------------------------------------------
/**
 *  This class contains the class for a player to be an archer type character.
 *  The archer class has a name, health, mana, stamina, strength, defense,
 *  dexterity, and intelligence stats. This class contains one method which
 *  is levelup which levels up the player.
 *
 *  @author Tk
 *  @version Nov 27, 2012
 */
public class Archer
    extends Player
{

    // ----------------------------------------------------------
    /**
     * Creates a new Archer object.
     * @param name The name of the character.
     * @param x The row value of the player
     * @param y The column value of the player
     * @param gameWorld The game world where the player is playing in
     */
    public Archer(String name, float x, float y, GameWorld gameWorld)
    {
        this.gameWorld = gameWorld;

        this.setName(name);
        this.setType(PlayerType.ARCHER);

        this.setMaxHealth(110.0f);
        this.setMaxMana(100.0f);
        //this.setMaxStamina(120.0f);

        // I swapped the strength and intelligence skill number.
        // Change them if you need to!
        this.setStrength(18.0f);
        this.setDefense(30.0f);
        this.setDexterity(22.0f);
        this.setIntelligence(10.0f);

        this.setLevel(1);
        this.setExpToNextLevel(10.0f);

        // The sprite in maze mode.
        this.setMazeSprite(new Sprite(R.drawable.archer, 96, 128, 3, 4, gameWorld.getDisplayMetrics().density));
        this.getMazeSprite().setCol(1);

        // The sprite in battle mode.
        this.setBattleSprite(new Sprite(R.drawable.archer_sprite_sheet, 1040, 780, 8, 6, gameWorld.getDisplayMetrics().density));
        this.getBattleSprite().setRow(2);

        // The arrow sprite for use in battle.
        this.setProjectile(new Sprite(R.drawable.arrow, 62, 7, 2, 1,
            gameWorld.getDisplayMetrics().density));


        // Set the location of the sprite based on the given parameters.
        this.setPosition(x, y);


        // Initial equipment
        this.setArmor(new Armor(
            "Leather Cuirass",
            "Leather chestpiece for archers.",
            3.0f, gameWorld));
        this.getArmor().getMazeIcon().setPosition(x, y);


        this.setWeapon(new Weapon(
            "Bone Bow",
            "Not. Very. Fancy.",
            3.0f,
            gameWorld));
        this.getWeapon().addBonusDamage("SKELETON", -2.0f);
    }


   /**
    * This method levels up the character by changing its stats and setting
    * them to their new values. If the character has leveled up twice in one
    * battle, then a recursive call is made.
    */
   @Override
   public void levelUp()
   {
    // I changed the stats so that it would make sense for a wizard class.
       // Increase statuses.
       this.setMaxHealth(getMaxHealth() + getMaxHealth() * 0.15F);
       //this.setMaxStamina(getMaxStamina() + 12.0f);
       this.setMaxMana(getMaxMana() + getMaxMana() * .02F);
       this.setStrength(getStrength() + getStrength() * 0.07F);
       this.setDefense(getDefense() + getDefense() * 0.11F);
       this.setDexterity(getDexterity() + getDexterity() * 0.12F);
       this.setIntelligence(getIntelligence() + getIntelligence() * 0.05F);

       // Update the level and experience.
       this.setLevel(getLevel() + 1);
       this.setExpToNextLevel(getExpToNextLevel() * 2.5f);

       //Update magic for the archer.
       switch (getLevel())
       {
           case 3:
               getMagics().add(new Magic(
                   "Minor Heal",
                   "Heals the warrior for 20 health.",
                   20.0f, 15.0f, true));
               break;

           case 5:
               getMagics().add(new Magic(
                   "Ice Dagger",
                   "Deals a small bit of damage.",
                   30.0f, 25.0f, false));
               break;

           case 9:
               getMagics().add(new Magic(
                   "Enlightenment",
                   "hHeals a lot of health.",
                   60.0f, 30.0f, true));
               break;

           case 15:
               getMagics().add(new Magic(
                   "High Heal",
                   "Heals a lot of health.",
                   100.0f, 90.0f, true));
               break;

           case 20:
               getMagics().add(new Magic(
                   "Vice Grip",
                   "Hurts the enemy with all of the archer's arcane might.",
                   150.0f, 100.0f, false));
               break;

       }

       // Check if the player leveled up again.
       if ( getExperience() >= getExpToNextLevel() )
       {
           levelUp();
       }

   }


}
