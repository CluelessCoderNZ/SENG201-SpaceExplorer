package main;

public class CrewMemberFactory {

  /**
   * creates crew member of a given type with the given name.
   * @param newCrewMemberType integer to select new crew member subtype.
   *        1 for Investor, 2 for 
   * @param name name for the new crew member
   * @return the created CrewMember object
   */
  public static CrewMember createCrewMember(int newCrewMemberType, String name) {
    switch (newCrewMemberType) {
      case 1:
        return new Investor(name);
      case 2:
        return new Medic(name);
      default:
        throw new RuntimeException("invalid crew member type requested");
    }
  }
  
}
