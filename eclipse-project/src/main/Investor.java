package main;

public class Investor extends CrewMember {
  
  public static final int FUNDS_BONUS = 100;
  public static final int SHIELD_BONUS = 30;
  
  public Investor(String name) {
    super(name, "Investor");
  }
  
  @Override
  public void applyStartBonuses(CrewState crewState) {
    crewState.addFunds(FUNDS_BONUS);
    crewState.getShip().increaseMaxShield(SHIELD_BONUS);
  }
  
  public static String getClassDescription() {
    return String.format("start the game with +%d funds and +%d max ship shield",
        FUNDS_BONUS, SHIELD_BONUS);
  }

}
