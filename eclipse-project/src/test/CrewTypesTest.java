package test;

import static org.junit.jupiter.api.Assertions.*;

import crew.CrewMember;
import crew.CrewMemberEffect;
import crew.CrewState;
import crew.Engineer;
import crew.Investor;
import crew.Medic;
import crew.Robot;
import crew.Scientist;
import crew.Ship;
import crew.Student;
import items.PlagueCure;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CrewTypesTest {

  @Test
  void investorTest() {
    Ship ship = new Ship("Test", 100);
    CrewState crewState = new CrewState(null, ship);
    Investor investor = new Investor("Barry");
    investor.applyStartBonuses(crewState);
    
    assertEquals(Investor.FUNDS_BONUS, crewState.getFunds());
    assertEquals(130, ship.getMaxShieldLevel());
  }
  
  @Test
  void medicTest() {
    CrewState crewState = new CrewState(null, null);
    Medic medic = new Medic("Steve");
    medic.applyStartBonuses(crewState);
    
    assertTrue(crewState.getInventory().get(0) instanceof PlagueCure);
  }
  
  @Test
  void engineerTest() {
    Ship ship = new Ship("Test", 100);
    Engineer engineer = new Engineer("Barry");
    ship.takeDamage(90);
    
    engineer.repairShip(ship);
    assertEquals(10 + (50 * 1.5), ship.getShieldLevel());
  }
  
  @Test
  void studentTest() {
    Student student = new Student("Jack");
    assertTrue(student.hasEffect(CrewMemberEffect.STRESSED));
    
    student.setHealth(0);
    assertFalse(student.hasEffect(CrewMemberEffect.STRESSED));
  }
  
  @Test
  void scientistTest() {
    List<CrewMember> crew = new ArrayList<CrewMember>();
    crew.add(new Scientist("Marie Curie"));
    CrewState crewState = new CrewState(crew, null);
    
    assertTrue(crewState.hasScientist());
  }
  
  @Test
  void robotTest() {
    Robot robot = new Robot("Number 5");
    robot.addEffect(CrewMemberEffect.HUNGRY);
    robot.addEffect(CrewMemberEffect.TIRED);
    robot.addEffect(CrewMemberEffect.PLAGUED);
    
    assertFalse(robot.hasEffect(CrewMemberEffect.HUNGRY));
    assertFalse(robot.hasEffect(CrewMemberEffect.TIRED));
    assertFalse(robot.hasEffect(CrewMemberEffect.PLAGUED));
    
    assertEquals("Number 5 the Robot (HP: 120/120, AP: 1/1)", robot.toString());
  }

}
