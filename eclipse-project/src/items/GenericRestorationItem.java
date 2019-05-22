package items;

import crew.CrewMember;
import crew.InvalidCrewMemberException;

/**
 * generic ConsumableItem subclass that can increase a CrewMember's fullness or health (or both).
 */
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
   * @param healthAmount health restoration amount
   * @param fullnessAmount fullness restoration amount
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
  
  @Override
  public Item copy() {
    GenericRestorationItem item = new GenericRestorationItem(this.getName(), 
                                                             this.getValue(), 
                                                             this.healthRestoreAmount, 
                                                             this.fullnessRestoreAmount, 
                                                             this.usesRemaining);
    item.setDescription(getDescription());
    return item;
  }
  
  /**
   * returns the amount by which a CrewMember's health will be restored by this item.
   * @return the health restore amount as an int
   */
  public int getHealthRestoreAmount() {
    return healthRestoreAmount;
  }
  
  /**
   * returns the amount by which a CrewMember's fullness will be restored by this item.
   * @return the fullness restore amount as an int
   */
  public int getFullnessRestoreAmount() {
    return fullnessRestoreAmount;
  }
  
  @Override
  public int getRemainingUses() {
    return usesRemaining;
  }
  
  /*
  ===============
      METHODS
  ===============
  */
  
  @Override
  public void applyEffects(CrewMember crew) {
    if (crew.getHealth() == 0 || crew.getFullness() == 0) {
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
  
  @Override
  public String getEffectsString() {
    String result = "";
    
    if (getHealthRestoreAmount() > 0) {
      result += "HP: +" + getHealthRestoreAmount() + " ";
    } else if (getHealthRestoreAmount() < 0) {
      result += "HP: " + getHealthRestoreAmount() + " ";
    }
    
    if (getFullnessRestoreAmount() > 0) {
      result += "Food: +" + getFullnessRestoreAmount() + " ";
    } else if (getFullnessRestoreAmount() < 0) {
      result += "Food: " + getFullnessRestoreAmount() + " ";
    }
    
    result += "Uses: " + getRemainingUses();
    
    return result;
  }
}
