package test;

import java.util.ArrayList;
import main.Shop;
import main.Planet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eventmanager.WeightedArrayList;
import items.Item;
import items.ShipPart;

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
    Planet planet = new Planet("Earth", shop, new WeightedArrayList<Item>());
    assertEquals(shop, planet.getShop());
  }
  
  
  @Test
  void testPart() {
    ShipPart part = new ShipPart();
    Planet planet = new Planet("Mars", shop, new WeightedArrayList<Item>());
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
