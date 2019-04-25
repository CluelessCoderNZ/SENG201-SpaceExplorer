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

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
  }

  @BeforeEach
  void setUp() throws Exception {
  }

  @Test
  void testShop() {
    Shop shop = new Shop(new ArrayList<Item>());
    Planet planet = new Planet("Earth");
    assertEquals(null, planet.getShop());
    
    planet.setShop(shop);
    assertEquals(shop, planet.getShop());
    
    assertThrows(IllegalArgumentException.class, () -> {
      planet.setShop(shop);
    });
    assertEquals(shop, planet.getShop());
  }
  
  
  @Test
  void testPart() {
    ShipPart part = new ShipPart();
    Planet planet = new Planet("Mars");
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
