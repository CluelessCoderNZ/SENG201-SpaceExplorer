package crew;

/**
 * singleton class to create CrewMember subtypes.
 */
public class CrewMemberFactory {

  /**
   * creates crew member of a given type with the given name.
   * @param type CrewMemberType to create an instance of
   * @param name name for the new crew member
   * @return the created CrewMember object
   */
  public static CrewMember createCrewMember(CrewMemberType type, String name) {
    switch (type) {
      case INVESTOR:
        return new Investor(name);
      case MEDIC:
        return new Medic(name);
      case ENGINEER:
        return new Engineer(name);
      case STUDENT:
        return new Student(name);
      case SCIENTIST:
        return new Scientist(name);
      case ROBOT:
        return new Robot(name);
      default:
        throw new RuntimeException("invalid crew member type requested");
    }
  }
  
  /**
   * creates crew member of a given type with the given name.
   * @param newCrewMemberType integer to select new crew member subtype.
   *        1 for Investor
   *        2 for Medic
   *        3 for Engineer
   *        4 for Student
   *        5 for Scientist
   *        6 for Robot
   * @param name name for the new crew member
   * @return the created CrewMember object
   */
  public static CrewMember createCrewMember(int newCrewMemberType, String name) {
    switch (newCrewMemberType) {
      case 1:
        return new Investor(name);
      case 2:
        return new Medic(name);
      case 3:
        return new Engineer(name);
      case 4:
        return new Student(name);
      case 5:
        return new Scientist(name);
      case 6:
        return new Robot(name);
      default:
        throw new RuntimeException("invalid crew member type requested");
    }
  }
  
}
