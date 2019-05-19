package test;

import static org.junit.jupiter.api.Assertions.*;

import main.CrewMember;
import main.CrewMemberEffect;

import org.junit.jupiter.api.Test;



class CrewMemberTest {

  @Test
  void testConstructor() {
    CrewMember crew1 = new CrewMember("Mr.Test", "Q&A Engineer");
    
    assertEquals(crew1.getName(), "Mr.Test");
    assertEquals(crew1.getTitle(), "Q&A Engineer");
    
    assertEquals(crew1.getHealth(), crew1.getMaxHealth());
    assertEquals(crew1.getFullness(), crew1.getMaxFullness());
    assertEquals(crew1.getRestedness(), crew1.getMaxRestedness());
    assertEquals(crew1.getActionPoints(), crew1.getMaxActionPoints());
    
    CrewMember crew2 = new CrewMember("Mrs.Test", "Accountant", 120, 00, 0, 3);
    
    assertEquals(crew2.getHealth(), 120);
    assertEquals(crew2.getFullness(), 0);
    assertEquals(crew2.getRestedness(), 0);
    assertEquals(crew2.getActionPoints(), 3);
  }

  @Test
  void testActionPoints() {
    CrewMember crew = new CrewMember("Donald", "Duck");
    
    assertEquals(crew.getActionPoints(), 2);
    assertTrue(crew.hasActionAvaliable());
    
    crew.useAction();
    
    assertEquals(crew.getActionPoints(), 1);
    assertTrue(crew.hasActionAvaliable());
    
    crew.useAction();
    
    assertEquals(crew.getActionPoints(), 0);
    assertFalse(crew.hasActionAvaliable());
    
    crew.useAction();
    
    assertEquals(crew.getActionPoints(), 0);
    assertFalse(crew.hasActionAvaliable());
  }
  
  @Test
  void testStats() {
    CrewMember crew = new CrewMember("Mickey", "Mouse");
    
    // Check Limits
    crew.setFullness(1000);
    crew.setHealth(1000);
    crew.setRestedness(1000);
    
    assertEquals(crew.getHealth(), crew.getMaxHealth());
    assertEquals(crew.getFullness(), crew.getMaxFullness());
    assertEquals(crew.getRestedness(), crew.getMaxRestedness());    
  }
  
  @Test
  void testEffects() {
    CrewMember crew = new CrewMember("Tyrion", "Lanistar");
    
    // Check that crew has no effects on creation
    for (CrewMemberEffect effect : CrewMemberEffect.values()) {
      assertFalse(crew.hasEffect(effect));
    }
    
    // Check that crew can have combined effects
    crew.addEffect(CrewMemberEffect.PLAGUED);
    assertTrue(crew.hasEffect(CrewMemberEffect.PLAGUED));
    
    crew.addEffect(CrewMemberEffect.TIRED);
    assertTrue(crew.hasEffect(CrewMemberEffect.PLAGUED));
    assertTrue(crew.hasEffect(CrewMemberEffect.TIRED));
    
    crew.removeEffect(CrewMemberEffect.PLAGUED);
    assertFalse(crew.hasEffect(CrewMemberEffect.PLAGUED));
    assertTrue(crew.hasEffect(CrewMemberEffect.TIRED));
    
    crew.removeEffect(CrewMemberEffect.TIRED);
    assertFalse(crew.hasEffect(CrewMemberEffect.TIRED));
    
    // Check that effects are automatically applied
    crew.setRestedness(0);
    assertTrue(crew.hasEffect(CrewMemberEffect.TIRED));
    
    crew.setFullness(0);
    assertTrue(crew.hasEffect(CrewMemberEffect.HUNGRY));
    
  }
  
}
