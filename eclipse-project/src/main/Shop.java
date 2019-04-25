package main;

import java.util.ArrayList;
import java.util.Collections;

public class Shop {
  private ArrayList<Item> inventory = new ArrayList<Item>();
  private double buyModifier = 1.0;
  private double sellModifier = 1.0;
  
  
  /**
   * initializes a Shop object that sells items in a given inventory.
   * @param inventory the inventory for the shop to sell items from
   */
  public Shop(ArrayList<Item> inventory) {
    this.inventory = inventory;
  }
  
  /**
   * initializes a Shop that sells items in a given inventory with buy and sell price modifiers.
   * @param inventory arraylist of items for the shop to sell items from
   * @param buyModifier the fraction of an item's value the shop will buy it for
   * @param sellModifier the fraction of an item's value the shop will sell it for
   */
  public Shop(ArrayList<Item> inventory, double buyModifier, double sellModifier) {
    this.inventory = inventory;
    this.buyModifier = buyModifier;
    this.sellModifier = sellModifier;
  }
  
  
  
  /* METHODS */
  
  public ArrayList<Item> getInventory() {
    return inventory;
  }
  
  
  public int getStock(Item item) {
    return Collections.frequency(inventory, item);
  }
  
//  public ArrayList<Double> getPrices() {
//    ArrayList<Double> prices = new ArrayList<Double>();
//    for (Item item : inventory) {
//      prices.add(item.getValue() * sellModifier);
//    }
//    return prices;
//  }
//  
//  
//  public ArrayList<Double> getStock() {
//    ArrayList<Double> prices = new ArrayList<Double>();
//    for (Item item : inventory) {
//      prices.add(item.getValue() * sellModifier);
//    }
//    return prices;
//  }
}
