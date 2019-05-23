package main;

import items.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Shop is a shop that players can buy and sell items to.
 * Has a name and a List of Items that can be bought, and a modifier
 * that sets the percentage of an Item's base price it is sold for.
 */
public class Shop {
  private String name;
  private List<Item> inventory = new ArrayList<Item>();
  private double buyModifier = 1.0;
  private double sellModifier = 1.0;
  
  
  /**
   * Initialises a Shop object that sells items in a given inventory.
   * @param name the name to give the Shop
   * @param galacticMedItems the inventory for the shop to sell items from
   */
  public Shop(String name, List<Item> galacticMedItems) {
    this.name = name;
    this.inventory = galacticMedItems;
  }
  
  /**
   * Initialises a Shop that sells items in a given inventory with buy and sell price modifiers.
   * @param name the name to give the Shop
   * @param inventory List of items for the shop to sell items from
   * @param buyModifier the fraction of an item's value the shop will buy it for
   * @param sellModifier the fraction of an item's value the shop will sell it for
   */
  public Shop(String name, List<Item> inventory, double buyModifier, double sellModifier) {
    this.name = name;
    this.inventory = inventory;
    this.buyModifier = buyModifier;
    this.sellModifier = sellModifier;
  }
  
  
  
  /* METHODS */
  
  /**
   * returns the Shop's inventory of Items.
   * @return List of Items that can be bought from the Shop
   */
  public List<Item> getInventory() {
    return inventory;
  }
  
  /**
   * returns the Shop's name.
   * @return Shop name as a String
   */
  public String getName() {
    return name;
  }
  
  /**
   * gets the price the Shop will sell an Item for.
   * @param item the item in the Shop's inventory to get the price for
   * @return the price the item would be if bought from the shop
   */
  public int getSellPrice(Item item) {
    if (!inventory.contains(item)) {
      throw new IllegalArgumentException("given item is not in the Shop's inventory");
    }
    return (int)(item.getValue() * sellModifier);
  }
  
  /**
   * gets the price the Shop will buy an Item for.
   * @param item the Item to get the price for
   * @return the price the shop will buy the item for
   */
  public int getBuyPrice(Item item) {
    if (inventory.contains(item)) {
      throw new IllegalArgumentException("a Shop cannot buy an Item off itself");
    }
    return (int)(item.getValue() * buyModifier);
  }
  
  /**
   * buys an Item and adds it to the shop's inventory.
   * @param item the item to be sold to the shop
   * @return the amount of money the shop buys the item for
   */
  public int buyItem(Item item) {
    int cost = getBuyPrice(item);
    inventory.add(item);
    return cost;
  }
  
  /**
   * sells an Item and removes it from the shop's inventory.
   * @param item the item to be bought from the shop
   * @return the amount of money the shop sells the item for
   */
  public int sellItem(Item item) {
    int price = getSellPrice(item);
    inventory.remove(item);
    return price;
  }
}
