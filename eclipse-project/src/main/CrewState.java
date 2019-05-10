package main;

import java.util.ArrayList;
import java.util.List;

public class CrewState {
  
  private int funds = 0;
  private List<Item> inventory = new ArrayList<Item>();
  private List<CrewMember> crew;
  private Ship ship;
  
  
  public CrewState(List<CrewMember> crewMembers, Ship ship) {
    this.crew = crewMembers;
    this.ship = ship;
  }
  
  public CrewState(List<CrewMember> crew, Ship ship, int funds) {
    this.crew = crew;
    this.ship = ship;
    this.funds = funds;
  }
  
  public CrewState(List<CrewMember> crew, Ship ship, int funds, ArrayList<Item> inventory) {
    this.crew = crew;
    this.ship = ship;
    this.funds = funds;
    this.inventory = inventory;
  }
  
  
  public int getFunds() {
    return funds;
  }
  
  public void setFunds(int funds) {
    if (funds < 0) {
      throw new IllegalArgumentException("cannot set funds to a negative value");
    }
    this.funds = funds;
  }
  
  public void addFunds(int funds) {
    if (funds < 0) {
      throw new IllegalArgumentException("cannot add negative funds, use removeFunds(int funds)");
    }
    this.funds += funds;
  }
  
  public void removeFunds(int funds) {
    if (funds < 0) {
      throw new IllegalArgumentException("cannot remove negative funds, use addFunds(int funds)");
    }
    this.funds = Math.max(0, this.funds - funds);
  }
  
  public List<Item> getInventory() {
    return inventory;
  }
  
  public void addItemToInventory(Item item) {
    if (inventory.contains(item)) {
      throw new IllegalArgumentException("item is already in the crewState's inventory");
    }
    inventory.add(item);
  }
  
  public void removeItemFromInventory(Item item) {
    if (!inventory.contains(item)) {
      throw new IllegalArgumentException("item is not in the crewState's inventory");
    }
    inventory.remove(item);
  }
  
  public List<CrewMember> getCrew() {
    return crew;
  }
  
  public Ship getShip() {
    return ship;
  }
  

}
