package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import crew.Ship;
import items.ShipShieldUpgradeItem;

class ShipShieldUpgradeItemTest {

  @Test
  void testEquals() {
    ShipShieldUpgradeItem item1 = new ShipShieldUpgradeItem("shield", 10, 30);
    ShipShieldUpgradeItem item2 = new ShipShieldUpgradeItem("shield", 10, 30);
    
    assertEquals(item1, item2);
    
    ShipShieldUpgradeItem item3 = new ShipShieldUpgradeItem("shield", 10, 20);
    assertNotEquals(item1, item3);
    
    ShipShieldUpgradeItem item4 = new ShipShieldUpgradeItem("fake shield", 10, 30);
    assertNotEquals(item1, item4);
    
    ShipShieldUpgradeItem item4copy = (ShipShieldUpgradeItem)item4.copy();
    assertEquals(item4, item4copy);
  }
  
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
