package main;

public class Ship {
  
  private String name;
  private int maxShieldLevel = 100;
  private int shieldLevel = maxShieldLevel;

  
  /**
   * default constructor sets the ship's name.
   * @param name the ship's name
   */
  public Ship(String name) {
    this.name = name;
  }
  
  /**
   * extended constructor sets the ship's name and max shield level.
   * @param name the ship's namee
   * @param maxShieldLevel the maximum shield level
   */
  public Ship(String name, int maxShieldLevel) {
    this.name = name;
    this.maxShieldLevel = maxShieldLevel;
    this.shieldLevel = maxShieldLevel;
  }
  

  
  public String getName() {
    return name;
  }
  
  public int getShieldLevel() {
    return shieldLevel;
  }
  
  
  /* METHODS */
  
  /**
   * reduces the ship's shield.
   * @param damage positive amount of damage to take
   */
  public void takeDamage(int damage) {
    if (damage >= 0) {
      shieldLevel = Math.max(0, shieldLevel - damage);
    } else {
      throw new IllegalArgumentException("damage cannot be negative. use repairShield(amount).");
    }
  }
  
  /**
   * increases the ship's shield.
   * @param amount positive amount by which to repair the shield
   */
  public void increaseShield(int amount) {
    if (amount >= 0) {
      shieldLevel = Math.min(maxShieldLevel, shieldLevel + amount);
    } else {
      throw new IllegalArgumentException("amount cannot be negative. use takeDamage(damage).");
    }
  }

}