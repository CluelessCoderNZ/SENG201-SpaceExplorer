package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import crew.CrewMember;
import crew.InvalidCrewMemberException;
import items.GenericRestorationItem;
import items.Item;

class GenericRestorationItemTest {

  @Test
  void testApplyEffects() {
    CrewMember crew = new CrewMember("Cuthbert", "Clumsy", 100, 100, 100, 2);
    crew.setHealth(50);
    crew.setFullness(50);
    
    GenericRestorationItem sandwich = new GenericRestorationItem("Perfect Sandwich", 35, 10, 30);
    sandwich.applyEffects(crew);
    sandwich.applyEffects(crew); // should not be usable again
    assertEquals(60, crew.getHealth());
    assertEquals(80, crew.getFullness());
    assertEquals(0, sandwich.getRemainingUses());
    assertTrue(sandwich.isEmpty());
    
    
    crew.setHealth(80);
    GenericRestorationItem bandaid = new GenericRestorationItem("Bandaid", 10, 8, 0, 4);
    bandaid.applyEffects(crew);
    bandaid.applyEffects(crew);
    bandaid.applyEffects(crew);
    assertEquals(100, crew.getHealth());
    assertEquals(1, bandaid.getRemainingUses());
    bandaid.applyEffects(crew);
    assertTrue(bandaid.isEmpty());
    
    
    crew.setFullness(50);
    GenericRestorationItem dissapear = new GenericRestorationItem("Thin Air", 0, 0, 10, 0);
    dissapear.applyEffects(crew);
    assertTrue(dissapear.isEmpty());
    assertEquals(50, crew.getFullness());
    
    GenericRestorationItem oofJuice = new GenericRestorationItem("Oof Juice", 1000, -100, -100);
    oofJuice.applyEffects(crew);
    assertEquals(0, crew.getHealth());
    assertEquals(0, crew.getFullness());
    
    
    GenericRestorationItem buffet = new GenericRestorationItem("All You Can Eat", 0, 500, 100);
    assertThrows(InvalidCrewMemberException.class, () -> {
      buffet.applyEffects(crew);
    });
    assertEquals(0, crew.getHealth());
  }
  
  
  @Test
  void testToString() {
    Item item = new GenericRestorationItem("Crystal Meth", 50, 20, -20, 4);
    assertEquals("Crystal Meth (4 uses)", item.toString());
    assertEquals("HP: +20 Food: -20 Uses: 4", item.getEffectsString());
  }

}
