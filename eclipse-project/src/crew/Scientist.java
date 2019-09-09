package crew;

/**
 * the CrewMember subclass Scientist causes the hasScientist() method
 * of any CrewState it is stored in to return true.
 */
public class Scientist extends CrewMember {

  /**
   * creates a Scientist with the given name.
   * @param name name for the Scientist to display.
   */
  public Scientist(String name) {
    super(name, "Scientist", 80, 100, 100, 2);
  }
  
  /**
   * static method to return the description of the special attributes of the Scientist class.
   * @return description of the special attributes of the Scientist class.
   */
  public static String getClassDescription() {
    return String.format("Marks which planets have a ship part");
  }

}
