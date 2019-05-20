package items;

public class Item {
  /*
  ===============
      MEMBERS
  ===============
  */
  private String name;
  private String description = "";
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
  
  /**
   * Constructor of Item.
   * @param name item name
   * @param value item value
   * @param description item description
   */
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
   * Used in short displays of what the item does.
   * @return
   */
  public String getEffectsString() {
    return "";
  }
  
  public String toString() {
    return getName();
  }

  public boolean equals(Item item) {
    return this.getName() == item.getName() && this.getValue() == item.getValue();
  }
}
