package main;

import java.util.ArrayList;
import java.util.Random;

/**
 * Very simple wrapper for ArrayList that gives each item a 
 * chance weight which is used for random selection.
 * Current implementation is very simple for it's current needed use cases.
 * @param <T> data type
 */
public class WeightedArrayList<T> {
  
  private class WeightedItem<I> {
    private I   item;
    private int weight;
    
    public WeightedItem(I item, int weight) {
      this.item = item;
      this.weight = weight;
    }
    
    public I getItem() {
      return item;
    }
    
    public int getWeight() {
      return weight;
    }
  }
  
  /*
  ===============
      MEMBERS
  ===============
  */
  private ArrayList<WeightedItem<T>> itemList;
  private int weightSum = 0;
  
  /**
   * Adds an item and weight to the ArrayList.
   * @param item item to add
   * @param weight weight to add
   */
  public void addItem(T item, int weight) {
    if (weight < 0) {
      throw new IllegalArgumentException("Weight must be non-negative");
    }
    
    itemList.add(new WeightedItem<T>(item, weight));
    weightSum += weight;
  }
  
  
  /**
   * Returns a random item using item weights.
   * @param randomGenerator random number generator to be used
   * @return random item
   */
  public T getRandomItem(Random randomGenerator) {
    T randomItem = null;
    
    if (itemList.size() > 0) {
      int randomWeight = randomGenerator.nextInt(weightSum);
      
      int weightSum = 0;
      for (WeightedItem<T> item : itemList) {
        weightSum += item.getWeight();
        
        if (randomWeight <= weightSum) {
          randomItem = item.getItem();
          break;
        }
      }
    }
    
    return randomItem;
  }
}
