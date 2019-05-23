package test;

import static org.junit.jupiter.api.Assertions.*;

import items.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class ItemTest {

  @Test
  void testValue() {
    Item item = new Item("Item");
    
    item.setValue(5);
    assertEquals(5, item.getValue());
    
    item.setValue(-5);
    assertEquals(-5, item.getValue());
    
    item.setValue(0);
    assertEquals(0, item.getValue());
  }
  
  @Test
  void testName() {
    Item item = new Item("Apple");
    
    assertEquals("Apple", item.getName());
  }
  
  @Test
  void testToString() {
    Item item = new Item("Ham Sandwich", 25);
    assertEquals("Ham Sandwich", item.toString());
  }
  
  @Test
  void testEquals() {
    Item item1 = new Item("Ham Sandwich", 25);
    Item item2 = new Item("Ham Sandwich", 25);
    assertEquals(item1, item2);
    
    Item item3 = new Item("Ham Sandwich", 30);
    assertNotEquals(item1, item3);
    
    Item item4 = new Item("Falafel Sandwich", 25);
    assertNotEquals(item1, item4);
  }
  
  @Test
  void testContains() {
    List<Item> list = new ArrayList<Item>(Arrays.asList(
        new Item("Brick", 100),
        new Item("Wood", 50),
        new Item("Pizza", 10),
        new Item("Pizza", 10),
        new Item("Pizza", 10)
    ));
    
    assertFalse(Item.containsExactReference(list, new Item("Pizza", 10)));
    assertTrue(Item.containsExactReference(list, list.get(0)));
    
    Item item = list.get(3);
    list.remove(3);
    assertFalse(Item.containsExactReference(list, item));
  }

}
