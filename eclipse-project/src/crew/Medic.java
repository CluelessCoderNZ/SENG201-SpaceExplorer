package crew;

import items.PlagueCure;

/**
 * the CrewMember subclass Medic adds a PlagueCure item to a CrewState's
 * inventory at the start of the game.
 */
public class Medic extends CrewMember {

  /**
   * creates a Medic with the given name.
   * @param name name for the Medic to display.
   */
  public Medic(String name) {
    super(name, "Medic", 100, 110, 90, 2);
  }
  
  @Override
  public void applyStartBonuses(CrewState crewState) {
    crewState.addItemToInventory(new PlagueCure("Plague-Away", 300));
  }
  
  /**
   * static method to return the description of the special attributes of the Medic class.
   * @return description of the special attributes of the Medic class.
   */
  public static String getClassDescription() {
    return String.format("Start the game with a Plague-Away");
  }
}
