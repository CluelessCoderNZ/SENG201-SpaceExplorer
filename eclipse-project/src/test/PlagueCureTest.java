package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import crew.CrewMember;
import crew.CrewMemberEffect;
import items.PlagueCure;

class PlagueCureTest {

  @Test
  void testCure() {
    CrewMember crew = new CrewMember("John", "Unwell");
    crew.addEffect(CrewMemberEffect.PLAGUED);
    PlagueCure cure = new PlagueCure("Laughter", 100);
    
    cure.applyEffects(crew);
    assertFalse(crew.hasEffect(CrewMemberEffect.PLAGUED));
  }
  
  @Test
  void testCopy() {
    PlagueCure cure = new PlagueCure("Control Z", 100);
    
    PlagueCure cureCopy = (PlagueCure)cure.copy();
    assertEquals(cure.getName(), cureCopy.getName());
    assertEquals(cure.getRemainingUses(), cure.getRemainingUses());
    assertNotEquals(cure, cureCopy);
    
    cure.applyEffects(new CrewMember("Barry", "Guinea Pig"));
    cureCopy = (PlagueCure)cure.copy();
    assertEquals(cure.getName(), cureCopy.getName());
    assertEquals(cure.getRemainingUses(), cure.getRemainingUses());
    assertNotEquals(cure, cureCopy);
  }

}
