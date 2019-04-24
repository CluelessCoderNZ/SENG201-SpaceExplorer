package main;

public class GenericMedicalItem extends ConsumableItem {

  private int usesRemaining = 1;
  private int restoreAmount = 0;



  /**
   * Generic Medical Item is a basic restoration class for crew health.
   * You can specify the amount restored and the uses.
   * @param name item name
   * @param value health item value
   * @param amount health restoration amount
   */
  public GenericMedicalItem(String name, int value, int amount) {
    super(name, value);
    restoreAmount = amount;
    usesRemaining = 1;
  }
  
  
  /**
   * Generic Medical Item is a basic restoration class for crew health.
   * You can specify the amount restored and the uses.
   * @param name item name
   * @param value health item value
   * @param amount health restoration amount
   * @param uses restoration uses
   */
  public GenericMedicalItem(String name, int value, int amount, int uses) {
    super(name, value);
    restoreAmount = amount;
    usesRemaining = uses;
  }
  
  
  /* METHODS */

  /**
   * Applies item effects to a crew member if not empty.
   * @param crew the crew member to heal
   */
  public void applyEffects(CrewMember crew) { // throws DeadCrewMemberException {
    if (!isEmpty()) {
      int currentHealth = crew.getHealth();
      if (currentHealth > 0) {
        crew.setHealth(currentHealth + restoreAmount);
        usesRemaining -= 1;
      } else {
        //throw new main.DeadCrewMemberException("Crew member is/should be dead");
      }
    }
    /* TODO: check for emptiness here? */
  }
  

  public int getRemainingUses() {
    return usesRemaining;
  }
}
