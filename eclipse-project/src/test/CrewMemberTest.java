package test;

import crew.CrewMember;
import crew.CrewMemberEffect;
import crew.Ship;
import items.GenericRestorationItem;

import static org.junit.jupiter.api.Assertions.*;
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
    
    CrewMember crew2 = new CrewMember("Mrs.Test", "Accountant", 120, 0, 0, 3);
    
    assertEquals(crew2.getHealth(), 120);
    assertEquals(crew2.getFullness(), 0);
    assertEquals(crew2.getRestedness(), 0);
    assertEquals(crew2.getActionPoints(), 3);
    
    assertThrows(IllegalArgumentException.class, () -> {
      new CrewMember("Invalid", "Outcast", -1, -1, -1, -1);
    });
  }

  @Test
  void testActionPoints() {
    CrewMember crew = new CrewMember("Donald", "Duck", 100, 100, 100, 2);
    
    assertEquals(2, crew.getMaxActionPoints());
    
    assertEquals(2, crew.getActionPoints());
    assertTrue(crew.canAct(2));
        
    crew.useAction();
    assertEquals(1, crew.getActionPoints());
    assertFalse(crew.canAct(2));
    assertTrue(crew.canAct());
    
    crew.useAction();
    assertEquals(0, crew.getActionPoints());
    assertFalse(crew.canAct());
    
    crew.useAction();
    assertEquals(0, crew.getActionPoints());
    assertFalse(crew.canAct());
    
    crew.resetActions();
    assertEquals(2, crew.getActionPoints());
    
    crew.setHealth(0);
    assertFalse(crew.canAct());
  }
  
  @Test
  void testActions() {
    CrewMember crew = new CrewMember("Sonic", "Hedgehog", 100, 100, 100, 3);
    GenericRestorationItem testItem = new GenericRestorationItem("food", 0, 10, 20, 2);
    
    // with AP remaining
    crew.setHealth(50);
    crew.setFullness(50);
    crew.useItem(testItem);
    assertEquals(60, crew.getHealth());
    assertEquals(70, crew.getFullness());
    
    Ship ship = new Ship("Salmon Express", 100);
    ship.takeDamage(60);
    crew.repairShip(ship);
    assertEquals(90, ship.getShieldLevel());
    
    crew.setRestedness(10);
    crew.sleep();
    assertEquals(60, crew.getRestedness());
    
    // with no AP remaining
    crew.useItem(testItem);
    assertEquals(60, crew.getHealth());
    assertEquals(70, crew.getFullness());
    
    crew.repairShip(ship);
    assertEquals(90, ship.getShieldLevel());
    
    crew.sleep();
    assertEquals(60, crew.getRestedness());
  }
  
  @Test
  void testStats() {
    CrewMember crew = new CrewMember("Mickey", "Mouse", 100, 100, 100, 2);
    
    // check max values
    assertEquals(100, crew.getMaxHealth());
    assertEquals(100, crew.getMaxFullness());
    assertEquals(100, crew.getMaxRestedness());
    
    // Check upper limit
    crew.setFullness(1000);
    crew.setHealth(1000);
    crew.setRestedness(1000);
    
    assertEquals(100, crew.getHealth(), crew.getMaxHealth());
    assertEquals(100, crew.getFullness());
    assertEquals(100, crew.getRestedness());
    
    // Check changes
    crew.changeFullness(-40);
    assertEquals(60, crew.getFullness());
    
    crew.setRestedness(50);
    crew.changeRestedness(30);
    assertEquals(80, crew.getRestedness());
    
    crew.changeHealth(-30);
    assertEquals(70, crew.getHealth());
    
    // check lower limit
    crew.setFullness(-1000);
    crew.setRestedness(-1000);
    crew.setHealth(-1000);
    
    assertEquals(0, crew.getFullness());
    assertEquals(0, crew.getRestedness());
    assertEquals(0, crew.getHealth());
    
    // check no change when dead
    crew.setFullness(100);
    crew.setRestedness(100);
    crew.setHealth(100);
    
    assertEquals(0, crew.getFullness());
    assertEquals(0, crew.getRestedness());
    assertEquals(0, crew.getHealth());
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
    
    crew.setHealth(0);
    assertTrue(crew.hasEffect(CrewMemberEffect.DEAD));
    assertTrue(crew.isDead());
  }
  
  @Test
  void testStrings() {
    CrewMember crew = new CrewMember("Bob", "TestCase", 50, 60, 70, 4);
    assertEquals("Bob", crew.getName());
    assertEquals("TestCase", crew.getTitle());
    assertEquals("Bob the TestCase", crew.getFullTitle());
    
    assertEquals("Bob the TestCase (HP: 50/50, Fullness: 60/60, Restedness: 70/70, AP: 4/4)",
        crew.toString());
    
    crew.addEffect(CrewMemberEffect.TIRED);
    assertEquals("Bob the TestCase (HP: 50/50, Fullness: 60/60, Restedness: 70/70, AP: 4/4) TIRED",
        crew.toString());
  }
  
  @Test
  void testDayStartEffects() {
    CrewMember crew = new CrewMember("John", "Bullet", 100, 100, 100, 2);
    
    // test daily stat changes
    crew.applyDayStartEffects();
    assertEquals(100, crew.getHealth());
    assertEquals(100 - 20, crew.getFullness());
    assertEquals(100 - 30, crew.getRestedness());
    assertEquals(2, crew.getActionPoints());
    
    crew.setFullness(100);
    crew.setRestedness(100);
    
    // test impact of active effects on stats
    crew.addEffect(CrewMemberEffect.HUNGRY);
    crew.addEffect(CrewMemberEffect.TIRED);
    crew.addEffect(CrewMemberEffect.PLAGUED);
    crew.applyDayStartEffects();
    assertEquals(100 - 30 - 30, crew.getHealth());
    assertEquals(100 - 40 - 20, crew.getFullness());
    assertEquals(100 - 30, crew.getRestedness());
    assertEquals(2 / 2, crew.getActionPoints());
    
    // test dead crew member
    crew.setHealth(0);
    crew.applyDayStartEffects();
    assertEquals(0, crew.getHealth());
    assertEquals(100 - 40 - 20, crew.getFullness());
    assertEquals(100 - 30, crew.getRestedness());
    assertEquals(2 / 2, crew.getActionPoints());
  }
  
}
