package test;

import static org.junit.jupiter.api.Assertions.*;

import main.CrewMember;
import main.GenericFoodItem;
import main.GenericMedicalItem;
import main.Item;

import org.junit.jupiter.api.Test;

class GenericFoodItemTest {

  @Test
  void testEffects() {
    CrewMember crew = new CrewMember("barry", "Poor Sod", 100, 100, 100, 2);
    
    GenericFoodItem apple = new GenericFoodItem("Rotten Apple", 3, -40, 3);
    
    apple.applyEffects(crew);
    assertEquals(60, crew.getFullness());
    assertEquals(2, apple.getRemainingUses());
    assertFalse(apple.isEmpty());
    
    apple.applyEffects(crew);
    assertEquals(20, crew.getFullness());
    assertEquals(1, apple.getRemainingUses());
    assertFalse(apple.isEmpty());
    
    apple.applyEffects(crew);
    assertEquals(0, crew.getFullness());
    assertEquals(0, apple.getRemainingUses());
    assertTrue(apple.isEmpty());
    
    
    
    GenericFoodItem sandwich = new GenericFoodItem("Ham Sandwich", 50, 20, 3);
    
    sandwich.applyEffects(crew);
    sandwich.applyEffects(crew);
    sandwich.applyEffects(crew);
    assertEquals(60, crew.getFullness());
    
    sandwich.applyEffects(crew);
    assertEquals(60, crew.getFullness());
    
    
    
    GenericFoodItem candy = new GenericFoodItem("Candy", 2000, 5);
    
    candy.applyEffects(crew);
    assertEquals(0, apple.getRemainingUses());
    assertTrue(apple.isEmpty());
    
  }
  
  
  @Test
  void testEquals() {
    fail("not implemented yet");
  }
  
  @Test
  void testToString() {
    Item item = new GenericFoodItem("Ham Sandwich", 50, 20, 3);
    assertEquals("'Ham Sandwich': 50, 20, 3", item.toString());
  }

}
