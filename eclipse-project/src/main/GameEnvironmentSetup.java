package main;

import java.util.ArrayList;
import java.util.Scanner;

public class GameEnvironmentSetup {
  
  private static final int MIN_CREW = 2;
  private static final int MAX_CREW = 4;
  
  private int numDays = 0;
  private ArrayList<CrewMember> crewMembers = new ArrayList<CrewMember>();
  private ArrayList<Planet> planets = new ArrayList<Planet>();
  private Ship ship = null;
  private CrewState crewState = null;
  private static Scanner reader = new Scanner(System.in);
  
  
  
  public ArrayList<CrewMember> getCrewMembers() {
    return crewMembers;
  }
  
  
  public void setNumDays() {
    System.out.println("enter the number of days to run the game for: ");
    numDays = reader.nextInt();
  }
  
  /**
   * creates a crew member with given parameters and adds to the crew.
   * @param name the name the new CrewMember should have
   * @param title the title the new CrewMember should have
   * @throws RuntimeException throws exception if trying to add member to a full crew
   */
  public void createCrewMember(String name, String title) throws RuntimeException {
    if (crewMembers.size() == MAX_CREW) {
      throw new RuntimeException("max crew size has been reached");
    }
    crewMembers.add(new CrewMember(name, title));
  }
  
  
  public void createShip() {
    System.out.println("enter ship name: ");
    String name = reader.nextLine();
    ship = new Ship(name);
  }

  
  public void createCrewState() {
    if (crewMembers.size() < MIN_CREW) {
      throw new RuntimeException("not enough crew members created, min " + MIN_CREW + " required");
    }
    if (ship == null) {
      throw new RuntimeException("ship has not been created, run createShip()");
    }
    crewState = new CrewState(crewMembers, ship);
  }
  
  
  /**
   * main function. tests the class with command line input
   * @param args command line arguments. should be empty
   */
  public static void main(String[] args) {
    GameEnvironmentSetup setup = new GameEnvironmentSetup();
    ArrayList<CrewMember> crewMembers = setup.getCrewMembers();
    
    String name = "";
    do {
      System.out.println(String.format("current crew count: %d. min %d. max %d. ",
          crewMembers.size(), MIN_CREW, MAX_CREW));
      
      System.out.println("enter new crew member name (type x to finish): ");
      name = reader.nextLine();
      System.out.println("enter " + name + "'s title: ");
      String title = reader.nextLine();
      setup.createCrewMember(name, title);
    } while (!name.equals("x") || crewMembers.size() < MIN_CREW);
    
    setup.createShip();
    setup.createCrewState();
    
  }
  
}
