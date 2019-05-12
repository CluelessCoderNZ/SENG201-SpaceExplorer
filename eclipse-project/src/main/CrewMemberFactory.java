package main;

public class CrewMemberFactory {

  public static CrewMember createCrewMember(int newCrewMemberType, String name) {
    switch (newCrewMemberType) {
      case 1:
        return new Investor(name);
      default:
        throw new RuntimeException("invalid crew member type requested");
    }
  }
  
}
