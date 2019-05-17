package main;

public class ShipUpgradeItem extends Item {

  private int maxShieldIncrease = 0;
  
  public ShipUpgradeItem(String name, int value, int maxShieldIncrease) {
    super(name, value);
    this.maxShieldIncrease = maxShieldIncrease;
  }
  
  @Override
  public Item copy() {
    ShipUpgradeItem item = new ShipUpgradeItem(this.getName(), 
                                               this.getValue(), 
                                               this.maxShieldIncrease);
    return item;
  }
  
  public void applyEffects(Ship ship) {
    ship.increaseMaxShield(maxShieldIncrease);
  }

}
