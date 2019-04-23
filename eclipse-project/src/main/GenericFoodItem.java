package main;

public class GenericFoodItem extends ConsumableItem {
  
  /*
  ===============
      MEMBERS
  ===============
  */
  private int usesRemaining = 1;
  private int restoreAmount = 0;
 
  /**
   * Generic Food Item is a basic restoration class for crew hunger.
   * You can specify the amount restored and the uses.
   * @param name food name
   * @param value food item value
   * @param amount restoration amount
   */
  public GenericFoodItem(String name, int value, int amount) {
    super(name, value);
    restoreAmount = amount;
  }
  
  /**
   * Generic Food Item is a basic restoration class for crew hunger.
   * You can specify the amount restored and the uses.
   * @param name food name
   * @param value food item value
   * @param amount restoration amount
   * @param uses restoration uses
   */
  public GenericFoodItem(String name, int value, int amount, int uses) {
    super(name, value);
    restoreAmount = amount;
    usesRemaining = uses;
  }
  /*
  ===============
      METHODS
  ===============
  */
  
  /**
   * Applies item effects to a crew member if not empty.
   */
  public void applyEffects(CrewMember crew) {
    if (!isEmpty()) {
      crew.setFullness(crew.getFullness() + restoreAmount);
      usesRemaining -= 1;
    }
  }

  public int getRemainingUses() {
    return usesRemaining;
  }
}
