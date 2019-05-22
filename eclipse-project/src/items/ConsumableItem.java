package items;

import crew.CrewMember;

/**
 * Item abstract subclass that can be used on a CrewMember a limited number of times.
 */
public abstract class ConsumableItem extends Item {
  
  /**
   * default constructor mirrors the Item superclass constructor.
   * @param name the name to give the item
   * @param value the funds value of the item
   */
  public ConsumableItem(String name, int value) {
    super(name, value);
  }

  /*
  ===============
      METHODS
  ===============
  */
  
  /**
   * Applies item effects to a crew member if not out of uses.
   * @param crew the crew member to apply the effects to
   */
  public abstract void applyEffects(CrewMember crew);
  
  /**
   * returns the remaining number of times the item can be used.
   * @return number of remaining uses as an int
   */
  public abstract int getRemainingUses();
    
  /**
   * returns whether the item has no remaining uses.
   * @return True if remaining uses is 0
   */
  public boolean isEmpty() {
    return getRemainingUses() == 0;
  }
  
  @Override
  public boolean equals(Object o) {
    if (o instanceof ConsumableItem) {
      ConsumableItem item = (ConsumableItem)o;
      return super.equals(item) && item.getRemainingUses() == getRemainingUses();
    } else {
      return false;
    }
  }
  
  @Override
  public String toString() {
    return String.format("%s (%d use%s)",
        getName(), getRemainingUses(), getRemainingUses() == 1 ? "" : "s");
  }
}
