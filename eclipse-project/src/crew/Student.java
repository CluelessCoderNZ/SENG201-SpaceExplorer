package crew;

/**
 * the CrewMember subclass Student has 3 max action points
 * and 50 max health, fullness, and restedness.
 */
public class Student extends CrewMember {

  /**
   * creates a Student with the given name.
   * @param name name for the Student to display.
   */
  public Student(String name) {
    super(name, "Student", 50, 50, 50, 3);
    addEffect(CrewMemberEffect.STRESSED);
  }
  
  /**
   * static method to return the description of the special attributes of the Student class.
   * @return description of the special attributes of the Student class.
   */
  public static String getClassDescription() {
    return String.format("Has 3 AP but has low stats");
  }

  @Override
  public void setHealth(int health) {
    super.setHealth(health);
    
    // morbid humour
    if (hasEffect(CrewMemberEffect.DEAD)) {
      removeEffect(CrewMemberEffect.STRESSED);
    }
  }
}
