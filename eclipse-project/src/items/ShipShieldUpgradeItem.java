package items;

import crew.Ship;

/**
 * Item subclass that implements the ShipUpgrade interface.
 * increases a Ship object's max shield level when used.
 */
public class ShipShieldUpgradeItem extends Item implements ShipUpgrade {

  private int maxShieldIncrease = 0;
  
  /**
   * constructs item that when used upgrades the ship's max shield.
   * @param name name of the upgrade item
   * @param value funds value of the item
   * @param maxShieldIncrease integer amount to increase the ship's max shield
   */
  public ShipShieldUpgradeItem(String name, int value, int maxShieldIncrease) {
    super(name, value);
    this.maxShieldIncrease = maxShieldIncrease;
  }
  
  /**
   * returns the max shield increase applied by this ShipShieldUpgradeItem.
   * @return max shield increase given on application to a Ship as an int
   */
  public int getMaxShieldIncrease() {
    return maxShieldIncrease;
  }
  
  @Override
  public String getEffectsString() {
    return "Max Shield +" + maxShieldIncrease;
  }
  
  @Override
  public Item copy() {
    ShipShieldUpgradeItem item = new ShipShieldUpgradeItem(this.getName(), 
                                                           this.getValue(),
                                                           this.maxShieldIncrease);
    
    return item;
  }
  
  @Override
  public void applyEffects(Ship ship) {
    ship.increaseMaxShield(maxShieldIncrease);
    ship.increaseShield(maxShieldIncrease);
  }

}
