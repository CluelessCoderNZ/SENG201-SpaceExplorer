package crew;

/**
 * the CrewMember subclass Investor provides a starting bonus to a CrewState's funds
 * and the CrewState's Ship's max shield level.
 */
public class Investor extends CrewMember {
  
  public static final int FUNDS_BONUS = 100;
  public static final int SHIELD_BONUS = 30;
  
  /**
   * creates an Investor with the given name.
   * @param name name for the Investor to display.
   */
  public Investor(String name) {
    super(name, "Investor", 80, 100, 110, 2);
  }
  
  /**
   * static method to return the description of the special attributes of the Investor class.
   * @return description of the special attributes of the Investor class.
   */
  public static String getClassDescription() {
    return String.format("Start the game with +%d funds and +%d max ship shield",
        FUNDS_BONUS, SHIELD_BONUS);
  }
  
  @Override
  public void applyStartBonuses(CrewState crewState) {
    crewState.addFunds(FUNDS_BONUS);
    crewState.getShip().increaseMaxShield(SHIELD_BONUS);
  }

}
