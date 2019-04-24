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
  public abstract void applyEffects(CrewMember crew);// throws DeadCrewMemberException;
  
  public abstract int getRemainingUses();
  
  public boolean isEmpty() {
    return getRemainingUses() == 0;
  }
}
