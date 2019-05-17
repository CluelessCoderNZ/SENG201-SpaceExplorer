package main;

public class ShipWeaponItem extends Item implements ShipUpgrade {

  private int damage = 0;
  
  /**
   * constructs item that when used upgrades the ship's max shield.
   * @param name name of the upgrade item
   * @param value funds value of the item
   * @param weaponDamage integer amount of damage the weapon should do
   */
  public ShipWeaponItem(String name, int value, int weaponDamage) {
    super(name, value);
    this.damage = weaponDamage;
  }
  
  @Override
  public void applyEffects(Ship ship) {
    ship.setWeapon(this);
  }
  
  /**
   * returns the damage dealt by this weapon.
   * @return damage dealt by this weapon
   */
  public int getDamage() {
    return damage;
  }

}
