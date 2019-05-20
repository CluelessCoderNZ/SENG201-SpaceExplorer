package test;

import java.util.ArrayList;
import java.util.Arrays;

import main.Shop;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import items.Item;

class ShopTest {

  @Test
  void testShop() {
    ArrayList<Item> inventory = new ArrayList<Item>(Arrays.asList(
        new Item("Brick", 100),
        new Item("Wood", 50),
        new Item("Pizza", 10),
        new Item("Pizza", 10),
        new Item("Pizza", 10)
    ));
    double buyMod = 0.5;
    double sellMod = 2;
    Shop shop = new Shop("Test Shop", inventory, buyMod, sellMod);
    
    assertEquals(inventory, shop.getInventory());
    for (Item item : inventory) {
      assertEquals((int)(item.getValue() * sellMod), shop.getSellPrice(item));
    }
    
    Item sword = new Item("Sword", 50);
    assertEquals((int)(sword.getValue() * buyMod), shop.getBuyPrice(sword));
    assertThrows(IllegalArgumentException.class, () -> {
      shop.getSellPrice(sword);
    });
    
    assertEquals((int)(sword.getValue() * buyMod), shop.buyItem(sword));
    assertTrue(shop.getInventory().contains(sword));
    
    assertThrows(IllegalArgumentException.class, () -> {
      shop.getBuyPrice(sword);
    });
    
    assertEquals((int)(sword.getValue() * sellMod), shop.sellItem(sword));
    assertFalse(shop.getInventory().contains(sword));
  }

}
