package crew;

/**
 * the CrewMember subclass Engineer can repair Ship objects more effectively
 * based on a static modifier.
 */
public class Engineer extends CrewMember {
  
  private static final double REPAIR_MODIFIER = 1.5;

  /**
   * creates an Engineer with the given name.
   * @param name name for the Engineer to display.
   */
  public Engineer(String name) {
    super(name, "Engineer", 110, 100, 100, 2);
  }
  
  /**
   * static method to return the description of the special attributes of the Engineer class.
   * @return description of the special attributes of the Engineer class.
   */
  public static String getClassDescription() {
    return String.format("%.1fx ship repair", REPAIR_MODIFIER);
  }
  
  @Override
  public void repairShip(Ship ship) {
    if (canAct()) {
      ship.increaseShield((int)(CrewMember.DEFAULT_SHIP_REPAIR * REPAIR_MODIFIER));
      useAction();
    }
  }
}
