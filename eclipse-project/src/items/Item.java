package items;

/**
 * base class for items in the game. Have a name, description and value.
 */
public class Item {
  /*
  ===============
      MEMBERS
  ===============
  */
  private String name;
  private String description = "";
  private int value = 0;
  
  /**
   * default constructor creates an Item with the given name
   * with a value of 0 and no description.
   * @param name the name to give the Item
   */
  public Item(String name) {
    this.name = name;
  }
  
  /**
   * extended constructor creates an Item with the given name and value
   * with no description.
   * @param name the name to give the Item
   * @param value the value of the item in funds as an int
   */
  public Item(String name, int value) {
    this.name = name;
    setValue(value);
  }
  
  /**
   * extended constructor creates an Item with the given name and description
   * with a value of 0.
   * @param name the name to give the Item
   * @param description the description to give the Item
   */
  public Item(String name, String description) {
    this.name = name;
    this.description = description;
  }
  
  /**
   * extended constructor creates an Item with the given name, value and description.
   * @param name the name to give the Item
   * @param value the value of the item in funds as an int
   * @param description the description to give the Item
   */
  public Item(String name, int value, String description) {
    this.name = name;
    setValue(value);
    this.description = description;
  }
  
  /**
   * creates a new copy of this Item.
   * @return a new Item with the exact same values as this one.
   */
  public Item copy() {
    Item item = new Item(this.name, this.value, this.description);
    return item;
  }
  
  
  /*
  ===============
     SET & GET   
  ===============
  */

  /**
   * returns the name of the Item.
   * @return name of Item as a String
   */
  public String getName() {
    return this.name;
  }
  
  /**
   * returns the description of an item.
   * @return the item's description
   */
  public String getDescription() {
    return this.description;
  }
  
  /**
   * sets the description of an Item.
   * @param desc the description to give the Item as a string
   */
  public void setDescription(String desc) {
    this.description = desc;
  }

  /**
   * sets the value in funds of the Item.
   * @param value value of the item in funds
   */
  public void setValue(int value) {
    this.value = value;
  }

  /**
   * returns the value in funds of the Item.
   * @return item value in funds
   */
  public int getValue() {
    return this.value;
  }

  /*
  ===============
     METHODS
  ===============
  */
  
  /**
   * Returns a short string of the effects of the item.
   * Used in short displays of what the item does.
   * @return effects string as a String
   */
  public String getEffectsString() {
    return "";
  }
  
  /**
   * returns the name of the Item.
   * @return the name of the Item as a String
   */
  @Override
  public String toString() {
    return getName();
  }

  /**
   * checks if an Object is the same as this Item.
   * @param o the Object to check this Item for equality with
   * @return whether the two Items are the same
   */
  public boolean equals(Object o) {
    if (o instanceof Item) {
      Item item = (Item)o;
      return this.getName() == item.getName() && this.getValue() == item.getValue();
    } else {
      return false;
    }
  }
}
