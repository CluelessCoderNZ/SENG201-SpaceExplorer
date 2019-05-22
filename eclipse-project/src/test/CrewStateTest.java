package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import crew.CrewMember;
import crew.CrewState;
import crew.Ship;
import items.Item;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrewStateTest {
  
  private static Ship ship = new Ship("Sputnik");
  private static List<CrewMember> crew;
  
  @BeforeEach
  void beforeEach() {
    crew = new ArrayList<CrewMember>(Arrays.asList(
        new CrewMember("John", "Normal", 100, 100, 100, 2),
        new CrewMember("Billy", "Burly", 100, 100, 100, 3),
        new CrewMember("Miles", "Meek", 100, 100, 100, 1)
    ));
  }
  

  @Test
  void testFunds() {
    CrewState crewState = new CrewState(crew, ship);
    crewState.setFunds(0);
    crewState.addFunds(50);
    assertEquals(50, crewState.getFunds());
    
    crewState.removeFunds(20);
    assertEquals(30, crewState.getFunds());
    
    crewState.removeFunds(100);
    assertEquals(0, crewState.getFunds());
    
    crewState.setFunds(100);
    assertEquals(100, crewState.getFunds());
    
    assertThrows(IllegalArgumentException.class, () -> {
      crewState.addFunds(-10);
    });
    
    assertThrows(IllegalArgumentException.class, () -> {
      crewState.removeFunds(-10);
    });
    
    crewState.setFunds(-10);
    assertEquals(0, crewState.getFunds());
  }
  
  @Test
  void testInventory() {
    CrewState crewState = new CrewState(crew, ship);
    
    Item testItem = new Item("Test Item");
    crewState.addItemToInventory(testItem);
    assertTrue(crewState.getInventory().contains(testItem));
    assertThrows(IllegalArgumentException.class, () -> {
      crewState.addItemToInventory(testItem);
    });
    
    crewState.removeItemFromInventory(testItem);
    assertFalse(crewState.getInventory().contains(testItem));
    assertThrows(IllegalArgumentException.class, () -> {
      crewState.removeItemFromInventory(testItem);
    });
  }
  
  @Test
  void testCrewMembers() {
    CrewState crewState = new CrewState(crew, ship);
    assertEquals(crew, crewState.getCrew());
    
    assertEquals(crew, crewState.getCrewWithActionPoints(1));
    
    List<CrewMember> crewWithTwoAP = crewState.getCrewWithActionPoints(2);
    assertTrue(crewWithTwoAP.contains(crew.get(0)));
    assertTrue(crewWithTwoAP.contains(crew.get(1)));
    assertFalse(crewWithTwoAP.contains(crew.get(2)));
    
    crew.get(0).setHealth(0);
    assertEquals(2, crewState.getLivingCrewCount());
  }
  
  @Test
  void testShip() {
    CrewState crewState = new CrewState(crew, ship);
    assertEquals(ship, crewState.getShip());
  }

}
