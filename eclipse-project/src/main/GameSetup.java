package main;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameSetup {
  
  private static final int MIN_CREW = 2;
  private static final int MAX_CREW = 4;
  private static final int MIN_DAYS = 3;
  private static final int MAX_DAYS = 10;
  private List<Planet> planets = new ArrayList<Planet>();

  
  private List<CrewMember> crewMembers = new ArrayList<CrewMember>();
  private Ship ship = null;
  //private CrewState crewState = null;
  private static Scanner reader = new Scanner(System.in);
  private GameEnvironment env;
  
  
  
  
  public GameSetup(GameEnvironment env) {
    this.env = env;
    commandLine();
  }
  
  public void setupPlanets() {
    ArrayList<Item> galacticMedItems = new ArrayList<Item>(Arrays.asList(
        new GenericRestorationItem("Medicaid", 40, 20, 0),
        new GenericRestorationItem("Bandages", 8, 5, 0)
    ));
    Shop galacticMed = new Shop("Galactic Medications", galacticMedItems);
    planets.add(new Planet("Earth"));
  }
    
  public void createShip(String name) {
    ship = new Ship(name);
  }
  
  public void setPlanets(List<Planet> planets) {
    env.setPlanets(planets);
  }
  
  public void setNumDays(int days) {
    if (days < MIN_DAYS || days > MAX_DAYS) {
      throw new RuntimeException("invalid number of days. Please enter a number between "
          + MIN_DAYS + " and " + MAX_DAYS);
    }
    env.setNumDays(days);
  }
  
  public void finishedSetup() {
    setCrewState();
    env.finishSetup(this);
  }
  
  /**
   * creates a crew member with given parameters and adds to the crew.
   * @param name the name the new CrewMember should have
   * @param title the title the new CrewMember should have
   * @throws RuntimeException throws exception if trying to add member to a full crew
   */
  public void createInvestor(String name, String title) {
    if (crewMembers.size() == MAX_CREW) {
      throw new RuntimeException("max crew size has been reached");
    }
    crewMembers.add(new Investor(name, title));
  }
  
  
  /**
   * sets the GameEnvironment's CrewState object.
   */
  public void setCrewState() {
    if (crewMembers.size() < MIN_CREW) {
      throw new RuntimeException("not enough crew members created, min " + MIN_CREW + " required");
    }
    if (crewMembers.size() > MAX_CREW) {
      throw new RuntimeException("too many crew members. please restart the game.");
    }
    if (ship == null) {
      throw new RuntimeException("ship has not been created, run createShip()");
    }
    env.setCrewState(new CrewState(crewMembers, ship));
  }
  

  /**
   * command line version of SpaceExplorer game setup window.
   */
  public void commandLine() {
    
    System.out.println(String.format("Enter a number of days. min %d. max %d:",
        MIN_DAYS, MAX_DAYS));
    int numDays = reader.nextInt();
    reader.nextLine(); // consumes the newline character after the integer
    setNumDays(numDays);
    
    System.out.println(String.format("\ncurrent crew count: %d. min %d. max %d. ",
        crewMembers.size(), MIN_CREW, MAX_CREW));
    
    System.out.println("choose a crew member type: ");
    System.out.println("     1. Investor (start with +100 funds and +30 ship max shield)");
    int crewTypeNum = reader.nextInt();
    reader.nextLine();
    
    String crewName = "";
    do {
      System.out.println("enter new crew member name: ");
      crewName = reader.nextLine();
      
      System.out.println("enter " + crewName + "'s title: ");
      String title = reader.nextLine();
      switch (crewTypeNum) {
        case 1:
          createInvestor(crewName, title);
          break;
        default:
          throw new RuntimeException("invalid crew type number entered.");
      }
      
      System.out.println(String.format("\ncurrent crew count: %d. min %d. max %d. ",
          crewMembers.size(), MIN_CREW, MAX_CREW));
      
      System.out.println("choose a crew member type (type 0 to finish): ");
      System.out.println("     1. Investor (start with +100 funds and +30 ship max shield)");
      crewTypeNum = reader.nextInt();
      reader.nextLine();
    } while (crewTypeNum != 0);
    
    System.out.println("\nenter a name for your crew's ship: ");
    String shipName = reader.nextLine();
    createShip(shipName);

    finishedSetup();
  }
  
}
