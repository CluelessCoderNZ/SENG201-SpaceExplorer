package main;

public class Ship {
  
  private String name;
  private int maxShieldLevel = 100;
  private int shieldLevel = maxShieldLevel;
  private ShipWeaponItem weapon = new ShipWeaponItem("Photon Cannon", 10, 0);

  
  /**
   * default constructor sets the ship's name.
   * @param name the ship's name
   */
  public Ship(String name) {
    this.name = name;
  }
  
  /**
   * extended constructor sets the ship's name and max shield level.
   * @param name the ship's name
   * @param maxShieldLevel the maximum shield level
   */
  public Ship(String name, int maxShieldLevel) {
    this.name = name;
    this.maxShieldLevel = maxShieldLevel;
    this.shieldLevel = maxShieldLevel;
  }
  
  /**
   * returns the name of the ship.
   * @return the ship's name as a String
   */
  public String getName() {
    return name;
  }
  
  /**
   * returns the shield level of the ship.
   * @return shield level as an int
   */
  public int getShieldLevel() {
    return shieldLevel;
  }
  
  public int getMaxShieldLevel() {
    return maxShieldLevel;
  }
  
  
  /* METHODS */
  
  /**
   * reduces the ship's shield level.
   * @param damage positive amount of damage to take
   */
  public void takeDamage(int damage) {
    if (damage >= 0) {
      shieldLevel = Math.max(0, shieldLevel - damage);
    } else {
      throw new IllegalArgumentException("damage cannot be negative. use repairShield(int).");
    }
  }
  
  /**
   * increases the ship's shield level.
   * @param amount positive amount by which to repair the shield
   */
  public void increaseShield(int amount) {
    if (amount >= 0) {
      shieldLevel = Math.min(maxShieldLevel, shieldLevel + amount);
    } else {
      throw new IllegalArgumentException("amount cannot be negative. use takeDamage(int).");
    }
  }
  
  /**
   * increases the ship's maximum shield level. increases current shield level by the same amount.
   * @param amount positive amount by which to increase the max shield
   */
  public void increaseMaxShield(int amount) {
    if (amount >= 0) {
      maxShieldLevel += amount;
      shieldLevel += amount;
    } else {
      throw new IllegalArgumentException("amount cannot be negative. use decreaseMaxSheild(int).");
    }
  }
  
  /**
   * decreases the ship's maximum shield level.
   * @param amount positive amount by which to decrease the max shield
   */
  public void decreaseMaxShield(int amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("amount cannot be negative. use increaseMaxShield(int).");
    } else {
      maxShieldLevel -= Math.min(amount, maxShieldLevel);
      shieldLevel = Math.min(shieldLevel, maxShieldLevel);
    }
  }
  
  /**
   * sets the ship's current weapon. Returns the old weapon.
   * @param weapon non null new weapon to set as the ship's weapon
   * @return the previous weapon assigned to the ship
   */
  public ShipWeaponItem setWeapon(ShipWeaponItem weapon) {
    if (weapon == null) {
      throw new IllegalArgumentException("weapon cannot be null");
    }
    ShipWeaponItem oldWeapon = this.weapon;
    this.weapon = weapon;
    return oldWeapon;
  }
  
  public ShipWeaponItem getWeapon() {
    return weapon;
  }

}