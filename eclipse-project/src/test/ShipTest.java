package test;

import main.Ship;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    
    ship.takeDamage(150);
    assertEquals(0, ship.getShieldLevel());
    
    assertThrows(IllegalArgumentException.class, () -> {
      ship.takeDamage(-10);
    });
    
    assertThrows(IllegalArgumentException.class, () -> {
      ship.increaseShield(-10);
    });
  }

}
