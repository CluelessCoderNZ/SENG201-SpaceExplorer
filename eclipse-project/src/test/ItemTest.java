package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import items.Item;

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

}
