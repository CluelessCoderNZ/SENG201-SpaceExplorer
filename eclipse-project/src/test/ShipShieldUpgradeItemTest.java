package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import crew.Ship;
import items.ShipShieldUpgradeItem;

class ShipShieldUpgradeItemTest {
  
  @Test
  void testString() {
    ShipShieldUpgradeItem item = new ShipShieldUpgradeItem("shield", 10, 30);
    assertEquals("Max Shield +30", item.getEffectsString());
  }
  
  @Test
  void testApplyEffects() {
    Ship ship = new Ship("Test Ship", 100);
    ShipShieldUpgradeItem item = new ShipShieldUpgradeItem("shield", 10, 30);
    item.applyEffects(ship);
    
    assertEquals(130, ship.getMaxShieldLevel());
  }

}
