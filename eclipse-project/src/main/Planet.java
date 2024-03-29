package main;

import eventmanager.WeightedArrayList;
import items.Item;
import items.ShipPart;

import java.util.Random;

/**
 * Planet class holds information about the planets in the game. has a name,
 * a Shop, and a loot table of items that can be found on the planet.
 * optionally has a ShipPart that can be found on the Planet.
 */
public class Planet {
  private ShipPart part = null;
  private Shop shop;
  private WeightedArrayList<Item> lootTable;
  private String name;
  
  
  /**
   * Default constructor for Planet.
   * @param name planet name
   * @param shop the shop on the planet
   * @param lootTable used as the list of items which can be found on the planet.
   */
  public Planet(String name, Shop shop, WeightedArrayList<Item> lootTable) {
    this.name = name;
    this.shop = shop;
    this.lootTable = lootTable;
  }
  
  /**
   * sets the ShipPart found on this Planet.
   * @param part the ShipPart found on this planet
   */
  public void setPart(ShipPart part) {
    if (hasShipPart()) {
      throw new IllegalArgumentException("Planet already has a ShipPart");
    }
    this.part = part;
  }
  
  public String getName() {
    return name;
  }
  
  /**
   * Returns the name of the planet marked if it has a ship part.
   * @return name of the planet, with an optional asterisk if a ship part is on it
   */
  public String getNameShowPart() {
    if (hasShipPart()) {
      return getName() + " *";
    } else {
      return getName();
    }
    
  }
  
  /**
   * returns the Shop attached to this Planet.
   * @return the Planet's Shop
   */
  public Shop getShop() {
    return shop;
  }
  
  /**
   * returns a random Item from the Planet's loot table.
   * @param generator the Random object to use to choose the Item.
   * @return the Item chosen by the random generator.
   */
  public Item getRandomItem(Random generator) {
    return lootTable.getRandomItem(generator).copy();
  }
  
  /**
   * returns whether the planet has a ShipPart on it.
   * @return True if the ShipPart attached to the Planet is not null 
   */
  public boolean hasShipPart() {
    return part != null;
  }
  
  /**
   * removes and returns the ShipPart stored on the Planet.
   * @return the planet's ship part, null if no part is on the planet
   */
  public ShipPart removeShipPart() {
    ShipPart returnPart = part;
    part = null;
    return returnPart;
  }
  
  @Override
  public String toString() {
    return getName();
  }
  
}
