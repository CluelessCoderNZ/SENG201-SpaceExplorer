package test;

import java.util.ArrayList;
import java.util.Random;

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
  
  @Test
  void testStrings() {
    Planet planet = new Planet("Mars", shop, new WeightedArrayList<Item>());
    
    assertEquals("Mars", planet.getName());
    
    assertEquals("Mars", planet.toString());
    
    assertEquals("Mars", planet.getNameShowPart());
    
    planet.setPart(new ShipPart());
    assertEquals("Mars *", planet.getNameShowPart());
  }
  
  @Test
  void testRandomLoot() {
    WeightedArrayList<Item> loot = new WeightedArrayList<Item>();
    Item trumpet = new Item("Pink Trumpet");
    Item swanEgg = new Item("Swan's Egg");
    loot.addItem(trumpet, 5);
    loot.addItem(swanEgg, 3);
    Planet planet = new Planet("Mars", shop, loot);
    
    Random generator = new Random(2);
    assertEquals("Pink Trumpet", planet.getRandomItem(generator).getName());
    assertEquals("Pink Trumpet", planet.getRandomItem(generator).getName());
    assertEquals("Swan's Egg", planet.getRandomItem(generator).getName());
  }

}
