package main;

public class Item {
  /*
  ===============
      MEMBERS
  ===============
  */
  private String name;
  private int value = 0;
  
  
  public Item(String name) {
    this.name = name;
  }
  
  public Item(String name, int value) {
    this.name = name;
    setValue(value);
  }
  
  /*
  ===============
     SET & GET   
  ===============
  */

  public String getName() {
    return this.name;
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
  public String toString() {
    return String.format("'%s': %d", name, value);
  }

  public boolean equals(Item item) {
    return this.getName() == item.getName() && this.getValue() == item.getValue();
  }
}
