package main;

public class GenericRestorationItem extends ConsumableItem {
  
  /*
  ===============
      MEMBERS
  ===============
  */
  private int usesRemaining = 1;
  private int healthRestoreAmount = 0;
  private int fullnessRestoreAmount = 0;
 
  /**
   * Generic Food Item is a basic restoration class for crew hunger.
   * You can specify the amount restored and the uses.
   * @param name food name
   * @param value food item value
   * @param healthRestore health restoration amount
   * @param fullnessRestore fullness restoration amount
   */
  public GenericRestorationItem(String name, int value, int healthAmount, int fullnessAmount) {
    super(name, value);
    healthRestoreAmount = healthAmount;
    fullnessRestoreAmount = fullnessAmount;
  }
  
  /**
   * Generic Food Item is a basic restoration class for crew hunger.
   * You can specify the amount restored and the uses.
   * @param name food name
   * @param value food item value
   * @param healthRestore health restoration amount
   * @param fullnessRestore fullness restoration amount
   * @param uses restoration uses
   */
  public GenericRestorationItem(String name, int value, int healthRestore,
      int fullnessRestore, int uses) {
    super(name, value);
    healthRestoreAmount = healthRestore;
    fullnessRestoreAmount = fullnessRestore;
    usesRemaining = uses;
  }
  
  
  public int getHealthRestoreAmount() {
    return healthRestoreAmount;
  }
  
  public int getFullnessRestoreAmount() {
    return fullnessRestoreAmount;
  }
  
  public int getRemainingUses() {
    return usesRemaining;
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
  public void applyEffects(CrewMember crew) {
    if (crew.getHealth() == 0 && crew.getFullness() == 0) {
      throw new InvalidCrewMemberException("crew member health or fullness is 0");
    }
    if (!isEmpty()) {
      crew.setHealth(crew.getHealth() + getHealthRestoreAmount());
      crew.setFullness(crew.getFullness() + getFullnessRestoreAmount());
      usesRemaining -= 1;
    }
  }
  
  /**
   * Indicates whether some other object is "equal to" this one.
   * @param item the reference GenericRestorationItem with which to compare
   * @return true if this item is the same as the argument item, false otherwise
   */
  public boolean equals(GenericRestorationItem item) {
    return super.equals(item)
        && item.getHealthRestoreAmount() == getHealthRestoreAmount()
        && item.getFullnessRestoreAmount() == getFullnessRestoreAmount();
  }
  
  
  public String toString() {
    return String.format("'%s': %d, %d, %d, %d", getName(), getValue(), healthRestoreAmount,
        fullnessRestoreAmount, usesRemaining);
  }
}