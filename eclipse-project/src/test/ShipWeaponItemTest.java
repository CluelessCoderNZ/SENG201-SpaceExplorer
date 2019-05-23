package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import crew.Ship;
import items.ShipWeaponItem;

class ShipWeaponItemTest {
  
  @Test
  void testString() {
    ShipWeaponItem item = new ShipWeaponItem("shield", 10, 30);
    assertEquals("Damage set to 30", item.getEffectsString());
  }
  
  @Test
  void testApplyEffects() {
    Ship ship = new Ship("Test Ship", 100);
    ShipWeaponItem item = new ShipWeaponItem("shield", 10, 30);
    item.applyEffects(ship);
    
    assertEquals(item, ship.getWeapon());
  }

}
