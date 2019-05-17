package main;

public class Item {
  /*
  ===============
      MEMBERS
  ===============
  */
  private String name;
  private String description;
  private int value = 0;
  
  
  public Item(String name) {
    this.name = name;
  }
  
  public Item(String name, int value) {
    this.name = name;
    setValue(value);
  }
  
  public Item(String name, String description) {
    this.name = name;
    this.description = description;
  }
  
  public Item(String name, int value, String description) {
    this.name = name;
    setValue(value);
    this.description = description;
  }
  
  public Item copy() {
    Item item = new Item(this.name, this.value, this.description);
    return item;
  }
  
  
  /*
  ===============
     SET & GET   
  ===============
  */

  public String getName() {
    return this.name;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public void setDescription(String desc) {
    this.description = desc;
  }

  public void setValue(int value) {
    this.value = value;
  }

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
   * @return
   */
  public String getEffectsString() {
    return "";
  }
  
  public String toString() {
    return String.format("'%s': %d", name, value);
  }

  public boolean equals(Item item) {
    return this.getName() == item.getName() && this.getValue() == item.getValue();
  }
}
