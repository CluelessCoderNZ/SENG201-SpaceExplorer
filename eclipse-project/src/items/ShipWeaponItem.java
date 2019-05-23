package items;

import crew.Ship;

/**
 * Item subclass that implements ShipUpgrade and acts as a weapon that can
 * be attached to a Ship object.
 */
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
  
  /**
   * returns the damage dealt by this weapon.
   * @return damage dealt by this weapon
   */
  public int getDamage() {
    return damage;
  }
  
  @Override
  public void applyEffects(Ship ship) {
    ship.setWeapon(this);
  }
  
  @Override
  public String getEffectsString() {
    return "Damage set to " + damage;
  }
  
  @Override
  public Item copy() {
    ShipWeaponItem item = new ShipWeaponItem(this.getName(), 
                                             this.getValue(),
                                             this.damage);
    return item;
  }

}
