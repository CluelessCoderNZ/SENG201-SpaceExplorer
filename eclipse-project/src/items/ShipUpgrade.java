package items;

import crew.Ship;

public interface ShipUpgrade {
  
  /**
   * applies the upgrade to the given Ship.
   * @param ship Ship object to apply the upgrade to
   */
  public void applyEffects(Ship ship);
  
  /**
   * returns a string that displays the effects of the ShipUpgrade.
   * @return string that displays the effects of the ShipUpgrade
   */
  public String getEffectsString();
  
}
