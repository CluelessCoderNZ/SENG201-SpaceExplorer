package main;

public class Medic extends CrewMember {

  public Medic(String name) {
    super(name, "Medic", 100, 110, 90, 2);
  }
  
  @Override
  public void applyStartBonuses(CrewState crewState) {
    crewState.addItemToInventory(new PlagueCure("Plague-Away", 300));
  }
  
  public static String getClassDescription() {
    return String.format("Start the game with a Plague-Away");
  }
}
