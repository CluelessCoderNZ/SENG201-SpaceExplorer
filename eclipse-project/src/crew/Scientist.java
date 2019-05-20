package crew;

public class Scientist extends CrewMember {

  public Scientist(String name) {
    super(name, "Scientist", 80, 100, 100, 2);
  }
  
  public static String getClassDescription() {
    return String.format("Marks which planets have a ship part");
  }

}
