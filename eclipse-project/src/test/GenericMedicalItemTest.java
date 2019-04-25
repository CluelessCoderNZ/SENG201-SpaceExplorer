package test;

import main.CrewMember;
import main.GenericFoodItem;
import main.GenericMedicalItem;
import main.Item;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenericMedicalItemTest {

  @Test
  void testApplyEffects() {
    CrewMember crew = new CrewMember("Cuthbert", "Clumsy", 100, 100, 100, 2);
    crew.setHealth(50);
    
    GenericMedicalItem stimpak = new GenericMedicalItem("Stimpak", 35, 30);
    stimpak.applyEffects(crew);
    stimpak.applyEffects(crew); // will not increase health
    assertEquals(80, crew.getHealth());
    assertEquals(0, stimpak.getRemainingUses());
    assertTrue(stimpak.isEmpty());
    
    
    GenericMedicalItem bandaid = new GenericMedicalItem("Bandaid", 10, 8, 4);
    bandaid.applyEffects(crew);
    bandaid.applyEffects(crew);
    bandaid.applyEffects(crew);
    assertEquals(100, crew.getHealth());
    assertEquals(1, bandaid.getRemainingUses());
    bandaid.applyEffects(crew);
    assertTrue(bandaid.isEmpty());
    
    
    crew.setHealth(50);
    
    GenericMedicalItem dissapear = new GenericMedicalItem("Dissapear", 0, 10, 0);
    dissapear.applyEffects(crew);
    assertEquals(50, crew.getHealth());
    
    
    GenericMedicalItem oofJuice = new GenericMedicalItem("Oof Juice", 1000, -100);
    oofJuice.applyEffects(crew);
    assertEquals(0, crew.getHealth());
    
    
    GenericMedicalItem holyWater = new GenericMedicalItem("Holy Water", 500, 100);
    assertThrows(Exception.class, () -> {
      holyWater.applyEffects(crew);
    });
    assertEquals(0, crew.getHealth());
  }
  
  @Test
  void testEquals() {
    GenericMedicalItem item1 = new GenericMedicalItem("Item", 10, 10, 3);
    GenericMedicalItem item2 = new GenericMedicalItem("Item", 10, 10, 3);
    assertTrue(item1.equals(item2));
    
    item1.applyEffects(new CrewMember("Jim", "Normal"));
    assertFalse(item1.equals(item2));
    
    item2.applyEffects(new CrewMember("Jim", "Normal"));
    assertTrue(item1.equals(item2));
    
    GenericMedicalItem item3 = new GenericMedicalItem("Item", 10, 10, 3);
    GenericMedicalItem item4 = new GenericMedicalItem("Item", 10, 5, 3);
    assertFalse(item3.equals(item4));
  }
  
  
  @Test
  void testToString() {
    Item item = new GenericMedicalItem("Crystal Meth", 50, 20, 3);
    assertEquals("'Crystal Meth': 50, 20, 3", item.toString());
  }

}
