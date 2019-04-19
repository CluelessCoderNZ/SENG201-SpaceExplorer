package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.Item;

class ItemTest {

  @Test
  void testValue() {
    Item item = new Item();
    
    item.setValue(5);
    assertEquals(5, item.getValue());
    
    item.setValue(-5);
    assertEquals(-5, item.getValue());
    
    item.setValue(0);
    assertEquals(0, item.getValue());
  }
  
  @Test
  void testName() {
    Item item = new Item();
    
    item.setName("Apple");
    assertEquals("Apple", item.getName());
    
    item.setName("");
    assertEquals("", item.getName());
  }
  
  @Test
  void testToString() {
    Item item = new Item("Ham Sandwich", 25);
    assertEquals("'Ham Sandwich': 25", item.toString());
  }

}
