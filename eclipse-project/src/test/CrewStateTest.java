package test;

import main.CrewMember;
import main.CrewState;
import main.Item;
import main.Ship;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrewStateTest {
  
  //private CrewState crewState = new CrewState();
  private static Ship ship = new Ship("Sputnik");
  private static ArrayList<CrewMember> crew = new ArrayList<CrewMember>(Arrays.asList(
      new CrewMember("John", "Normal"),
      new CrewMember("Billy", "Burly"),
      new CrewMember("Miles", "Meek")
  ));
  private static ArrayList<Item> inventory = new ArrayList<Item>(Arrays.asList(
      new Item("Egg"),
      new Item("Book"),
      new Item("Health Pack")
  ));

  @Test
  void testFunds() {
    CrewState crewState = new CrewState(crew, ship, 100);
    crewState.addFunds(50);
    assertEquals(150, crewState.getFunds());
    
    crewState.removeFunds(20);
    assertEquals(130, crewState.getFunds());
    
    crewState.removeFunds(200);
    assertEquals(0, crewState.getFunds());
    
    crewState.setFunds(100);
    assertEquals(100, crewState.getFunds());
    
    assertThrows(IllegalArgumentException.class, () -> {
      crewState.addFunds(-10);
    });
    
    assertThrows(IllegalArgumentException.class, () -> {
      crewState.removeFunds(-10);
    });
    
    assertThrows(IllegalArgumentException.class, () -> {
      crewState.setFunds(-10);
    });
    
    assertEquals(100, crewState.getFunds());
  }
  
  @Test
  void testInventory() {
    CrewState crewState = new CrewState(crew, ship, 100, inventory);
    assertEquals(inventory, crewState.getInventory());
    
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
  }
  
  @Test
  void testShip() {
    CrewState crewState = new CrewState(crew, ship);
    assertEquals(ship, crewState.getShip());
  }

}
