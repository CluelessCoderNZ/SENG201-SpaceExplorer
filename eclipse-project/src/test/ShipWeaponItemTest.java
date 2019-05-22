package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import crew.Ship;
import items.ShipWeaponItem;

class ShipWeaponItemTest {

  @Test
  void testEquals() {
    ShipWeaponItem item1 = new ShipWeaponItem("weapon", 10, 30);
    ShipWeaponItem item2 = new ShipWeaponItem("weapon", 10, 30);
    
    assertEquals(item1, item2);
    
    ShipWeaponItem item3 = new ShipWeaponItem("weapon", 10, 20);
    assertNotEquals(item1, item3);
    
    ShipWeaponItem item4 = new ShipWeaponItem("fake weapon", 10, 30);
    assertNotEquals(item1, item4);
    
    ShipWeaponItem item4copy = (ShipWeaponItem)item4.copy();
    assertEquals(item4, item4copy);
  }
  
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
