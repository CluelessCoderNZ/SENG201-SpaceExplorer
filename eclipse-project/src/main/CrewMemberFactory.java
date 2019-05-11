package main;

public class CrewMemberFactory {

  public static CrewMember createCrewMember(int newCrewMemberType, String name, String title) {
    switch (newCrewMemberType) {
      case 1:
        return new Investor(name, title);
      default:
        throw new RuntimeException("invalid crew member type requested");
    }
  }
  
}
