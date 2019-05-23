package crew;

import items.ConsumableItem;
import items.Item;
import items.ShipPart;
import items.ShipUpgrade;

import java.util.ArrayList;
import java.util.List;

/**
 * class to handle the state of the game's crew, including their funds, current ship, and inventory.
 */
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
   * @param funds non negative integer to set the crew's funds to
   */
  public void setFunds(int funds) {
    this.funds = Math.max(0, funds);
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
    setFunds(getFunds() - funds);
  }
  
  public List<Item> getInventory() {
    return inventory;
  }
  
  /**
   * returns the current number of found ShipParts.
   * @return integer count of ShipParts in the inventory
   */
  public int getShipPartsFoundCount() {
    int count = 0;
    for (Item item : inventory) {
      if (item instanceof ShipPart) {
        count += 1;
      }
    }
    
    return count;
  }
  
  /**
   * returns whether the current crew contains a scientist.
   * @return True if the current crew contains a scientist
   */
  public boolean hasScientist() {
    for (CrewMember cm : crew) {
      if (cm instanceof Scientist) {
        return true;
      }
    }
    
    return false;
  }
  
  /**
   * adds an item to the crew's inventory.
   * @param item item not already in the crew's inventory to add
   */
  public void addItemToInventory(Item item) {
    if (Item.containsExactReference(inventory, item)) {
      throw new IllegalArgumentException("item is already in the crewState's inventory");
    }
    inventory.add(item);
  }
  
  /**
   * removes an item from the crew's inventory.
   * @param item item in the crew's inventory to remove
   */
  public void removeItemFromInventory(Item item) {
    if (!Item.containsExactReference(inventory, item)) {
      throw new IllegalArgumentException("item is not in the crewState's inventory");
    }
    inventory.remove(item);
  }
  
  
  /**
   * Apply item to crew member reducing uses.
   * @param item Item to apply to crew member
   * @param crewMember the CrewMember to apply the item's effects to
   */
  public void useItem(ConsumableItem item, CrewMember crewMember) {
    
    // Error checking
    if (!Item.containsExactReference(inventory, item)) {
      throw new IllegalArgumentException("Item is not in the inventory");
    }
    
    if (!crew.contains(crewMember)) {
      throw new IllegalArgumentException("Crew member is not in crew");
    }
    
    // Use Item
    if (!item.isEmpty()) {
      crewMember.useItem(item);
    }
    
    // Remove item
    if (item.isEmpty()) {
      removeItemFromInventory(item);
    }
  }
  
  /**
   * Uses ShipUpgradeItem on a ship, then removes it.
   * @param item to be used on a ship.
   */
  public void useItem(ShipUpgrade item) {
    // Error checking
    if (!(item instanceof Item)) {
      throw new IllegalArgumentException("ShipUpgrade is not an item.");
    }
    
    if (!Item.containsExactReference(inventory, (Item)item)) {
      throw new IllegalArgumentException("Item is not in the inventory");
    }
    
    item.applyEffects(getShip());
    
    removeItemFromInventory((Item)item);
  }
  
  /**
   * Repairs ship using crew member.
   * @param crewMember crew member to repair ship
   */
  public void repairShip(CrewMember crewMember) {
    if (!crew.contains(crewMember)) {
      throw new IllegalArgumentException("Crew member is not in crew");
    }

    crewMember.repairShip(getShip());
  }
  
  /**
   * Calls all crew with applyDayStartEffects.
   */
  public void applyDayStartEffects() {
    for (CrewMember crewmember : crew) {
      crewmember.applyDayStartEffects();
    }
  }
  
  /**
   * returns the current crew as a List.
   * @return List of the current CrewMembers
   */
  public List<CrewMember> getCrew() {
    return crew;
  }
  
  /**
   * Returns a list of crew members with action points greater or equal to minAP.
   * @param minAP minimum action points needed
   * @return List of CrewMember objects
   */
  public List<CrewMember> getCrewWithActionPoints(int minAP) {
    ArrayList<CrewMember> result = new ArrayList<CrewMember>();
    
    for (CrewMember crewmember : getCrew()) {
      if (!crewmember.isDead() && crewmember.getActionPoints() >= minAP) {
        result.add(crewmember);
      }
    }
    
    return result;
  }
  
  /**
   * Get the number of crew who are still alive.
   * @return living crew count.
   */
  public int getLivingCrewCount() {
    int count = 0;
    for (CrewMember crewMember : crew) {
      if (!crewMember.isDead()) {
        count += 1;
      }
    }
    
    return count;
  }
  
  /**
   * returns the current Ship.
   * @return Ship object
   */
  public Ship getShip() {
    return ship;
  }
  
  /**
   * returns a string representation of the CrewState.
   * @return representation of the CrewState as a formatted string
   */
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
