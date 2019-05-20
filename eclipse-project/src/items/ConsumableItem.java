package items;

import crew.CrewMember;

public abstract class ConsumableItem extends Item {
  
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
  
  public abstract int getRemainingUses();
    
  public boolean isEmpty() {
    return getRemainingUses() == 0;
  }
  
  public boolean equals(ConsumableItem item) {
    return super.equals(item) && item.getRemainingUses() == getRemainingUses();
  }
}
