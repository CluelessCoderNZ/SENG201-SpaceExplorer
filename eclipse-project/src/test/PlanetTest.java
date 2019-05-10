package test;

import java.util.ArrayList;
import main.Shop;
import main.ShipPart;
import main.Item;
import main.Planet;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlanetTest {
  
  private Shop shop;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
  }

  @BeforeEach
  void setUp() throws Exception {
    shop = new Shop("Test Shop", new ArrayList<Item>());
  }

  @Test
  void testShop() {
    Planet planet = new Planet("Earth", shop);
    assertEquals(shop, planet.getShop());
  }
  
  
  @Test
  void testPart() {
    ShipPart part = new ShipPart();
    Planet planet = new Planet("Mars", shop);
    assertFalse(planet.hasShipPart());
    
    assertEquals(null, planet.removeShipPart());
    
    planet.setPart(part);
    assertTrue(planet.hasShipPart());
    
    assertThrows(IllegalArgumentException.class, () -> {
      planet.setPart(part);
    });
    assertTrue(planet.hasShipPart());
    
    assertEquals(part, planet.removeShipPart());
    assertFalse(planet.hasShipPart());
  }

}
