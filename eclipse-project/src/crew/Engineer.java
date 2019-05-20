package crew;

public class Engineer extends CrewMember {

  public Engineer(String name) {
    super(name, "Engineer", 110, 100, 100, 2);
  }
  
  public static String getClassDescription() {
    return String.format("1.5x ship repair");
  }
  
  @Override
  public void repairShip(Ship ship) {
    if (hasActionAvaliable()) {
      ship.increaseShield((int)(CrewMember.DEFAULT_SHIP_REPAIR * 1.5));
      useAction();
    }
  }
}
