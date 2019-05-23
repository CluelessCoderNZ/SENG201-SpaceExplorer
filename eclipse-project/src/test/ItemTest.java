package test;

import static org.junit.jupiter.api.Assertions.*;

import items.Item;

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

}
