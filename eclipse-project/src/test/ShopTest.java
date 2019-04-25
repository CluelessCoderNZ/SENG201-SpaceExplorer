package test;

import java.util.ArrayList;
import java.util.Arrays;
import main.Item;
import main.Shop;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopTest {
//
//  public Item brick = new Item("Brick");
//  public Item wood = new Item("Wood");
//  public Item pizza = new Item("Pizza");
//  public Item cat = new Item("Cat");
//  
//  @BeforeAll
//  static void setUpBeforeClass() throws Exception {
//    
//  }
//
//  @BeforeEach
//  void setUp() throws Exception {
//  }

  @Test
  void testShop() {
    ArrayList<Item> inventory = new ArrayList<Item>(Arrays.asList(
        new Item("Brick"),
        new Item("Wood"),
        new Item("Pizza"),
        new Item("Pizza"),
        new Item("Pizza")
    ));
    Shop shop = new Shop(inventory);
    
    assertEquals(inventory, shop.getInventory());
  }

}
