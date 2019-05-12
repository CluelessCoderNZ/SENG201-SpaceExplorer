package main;

import java.util.ArrayList;
import java.util.List;

public class CrewState {
  
  private int funds = 0;
  private List<Item> inventory = new ArrayList<Item>();
  private List<CrewMember> crew;
  private Ship ship;
  
  
  /**
   * constructor creates a CrewState object with a given crew and ship and
   * an empty inventory with 0 funds.
   * @param crewMembers List of crew members
   * @param ship Ship for the crew to pilot
   */
  public CrewState(List<CrewMember> crewMembers, Ship ship) {
    this.crew = crewMembers;
    this.ship = ship;
  }
  
  
  /*
  ===============
     SET & GET   
  ===============
  */
  
  /**
   * returns the funds of the player's crew.
   * @return funds as an integer
   */
  public int getFunds() {
    return funds;
  }
  
  /**
   * sets the funds of the player's crew.
   * @param funds integer to set the crew's funds to
   */
  public void setFunds(int funds) {
    if (funds < 0) {
      throw new IllegalArgumentException("cannot set funds to a negative value");
    }
    this.funds = funds;
  }
  
  /**
   * adds funds to the crew's funds.
   * @param funds a non negative integer amount to add to the funds
   */
  public void addFunds(int funds) {
    if (funds < 0) {
      throw new IllegalArgumentException("cannot add negative funds, use removeFunds(int funds)");
    }
    this.funds += funds;
  }
  
  /**
   * removes funds from the crew's funds.
   * @param funds a non negative integer amount to remove from the funds
   */
  public void removeFunds(int funds) {
    if (funds < 0) {
      throw new IllegalArgumentException("cannot remove negative funds, use addFunds(int funds)");
    }
    this.funds = Math.max(0, this.funds - funds);
  }
  
  public List<Item> getInventory() {
    return inventory;
  }
  
  /**
   * adds an item to the crew's inventory.
   * @param item item not already in the crew's inventory to add
   */
  public void addItemToInventory(Item item) {
    if (inventory.contains(item)) {
      throw new IllegalArgumentException("item is already in the crewState's inventory");
    }
    inventory.add(item);
  }
  
  /**
   * removes an item from the crew's inventory.
   * @param item item in the crew's inventory to remove
   */
  public void removeItemFromInventory(Item item) {
    if (!inventory.contains(item)) {
      throw new IllegalArgumentException("item is not in the crewState's inventory");
    }
    inventory.remove(item);
  }
  
  /**
   * returns the current crew as a List.
   * @return List of the current CrewMembers
   */
  public List<CrewMember> getCrew() {
    return crew;
  }
  
  /**
   * returns the current Ship.
   * @return Ship object
   */
  public Ship getShip() {
    return ship;
  }
  
  @Override
  public String toString() {
    String crewStateString = "Ship name: " + ship.getName() + "\n";
    crewStateString += "Crew funds: " + funds + "\n";
    crewStateString += "Crew Members:\n";
    for (CrewMember crewMember : crew) {
      crewStateString += "    " + crewMember.toString() + "\n";
    }
    crewStateString += "Inventory:\n";
    if (inventory.size() == 0) {
      crewStateString += "    none\n";
    }
    for (Item item : inventory) {
      crewStateString += "    " + item.toString() + "\n";
    }
    return crewStateString;
  }
  

}
