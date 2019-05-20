package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import crew.Ship;

class ShipTest {

  Ship ship;
  
  @BeforeEach
  void setUp() throws Exception {
    ship = new Ship("Rust Bucket", 100);
  }

  @Test
  void testShield() {
    ship.takeDamage(40);
    assertEquals(60, ship.getShieldLevel());
    
    ship.increaseShield(30);
    assertEquals(90, ship.getShieldLevel());
    
    ship.increaseShield(50);
    assertEquals(100, ship.getShieldLevel());
    
    ship.increaseMaxShield(20);
    assertEquals(120, ship.getShieldLevel());
    
    ship.decreaseMaxShield(40);
    assertEquals(80, ship.getShieldLevel());
    ship.increaseShield(20);
    assertEquals(80, ship.getShieldLevel());
    
    ship.takeDamage(150);
    assertEquals(0, ship.getShieldLevel());
    
    assertThrows(IllegalArgumentException.class, () -> {
      ship.takeDamage(-10);
    });
    
    assertThrows(IllegalArgumentException.class, () -> {
      ship.increaseMaxShield(-10);
    });
    
    assertThrows(IllegalArgumentException.class, () -> {
      ship.decreaseMaxShield(-10);
    });
  }

}