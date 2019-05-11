package main;

public class ShipUpgradeItem extends Item {

  private int maxShieldIncrease = 0;
  
  public ShipUpgradeItem(String name, int value, int maxShieldIncrease) {
    super(name, value);
    this.maxShieldIncrease = maxShieldIncrease;
  }
  
  public void applyEffects(Ship ship) {
    ship.increaseMaxShield(maxShieldIncrease);
  }

}
