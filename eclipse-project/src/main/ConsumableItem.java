package main;

public abstract class ConsumableItem extends Item {
  
  public ConsumableItem(String name, int value) {
    super(name, value);
  }

  /*
  ===============
      METHODS
  ===============
  */
  public abstract void applyEffects(CrewMember crew);
  
  public abstract int getRemainingUses();
    
  public boolean isEmpty() {
    return getRemainingUses() == 0;
  }
  
  public boolean equals(ConsumableItem item) {
    return super.equals(item) && item.getRemainingUses() == getRemainingUses();
  }
}
