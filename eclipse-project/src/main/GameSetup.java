package main;

import commandline.CommandLineParser;
import commandline.CommandLineRunner;
import crew.CrewMember;
import crew.CrewMemberFactory;
import crew.CrewState;
import crew.Engineer;
import crew.Investor;
import crew.Medic;
import crew.Robot;
import crew.Scientist;
import crew.Ship;
import crew.Student;
import items.ShipPart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GameSetup handles both the gui and cl setup of the game.
 * It instantiates members within game environment so its ready for the main game play loop.
 * It makes use of GameWorldGenerator. After it's done it calls finishedSetup in GameEnvironment.
 */
public class GameSetup {
  
  public static final int MIN_CREW = 2;
  public static final int MAX_CREW = 4;
  public static final int MIN_DAYS = 3;
  public static final int MAX_DAYS = 10;
  
  private boolean useCl = false;
  
  private Ship ship = null;
  private List<CrewMember> crewList = new ArrayList<CrewMember>();
  private GameEnvironment env;
  
  
  
  /**
   * GameSetup constructor.
   * @param env the GameEnvironment being used to store the game's state
   * @param cl flag for if the game should start in command line mode
   */
  public GameSetup(GameEnvironment env, boolean cl) {
    this.env = env;
    this.useCl = cl;
    if (useCl) {
      commandLine();
    }
  }
  
  /**
   * performs final setup steps (creating the crewState and applying bonuses).
   */
  public void finishedSetup() {
    setUpCrewState();
  }

  /**
   * sets the number of days the game should last.
   * Scatters correct number of parts between the game's planets.
   * @param days number of days for the game to last
   */
  public void setNumDays(int days) {
    if (days < MIN_DAYS || days > MAX_DAYS) {
      throw new RuntimeException("invalid number of days. Please pass a number between "
          + MIN_DAYS + " and " + MAX_DAYS);
    }
    env.setNumDays(days);
    scatterParts(days);
  }
  
  /**
   * scatters n = days * 2 / 3 parts among the game's planets.
   * @param days number of days the game will last for
   */
  private void scatterParts(int days) {
    List<Planet> planets = env.getPlanets();
    for (int i = 0; i < (days * 2) / 3 && i < planets.size(); i++) {
      env.getPlanets().get(i).setPart(new ShipPart());
    }
    Collections.shuffle(planets, env.getRandomGenerator());
  }
  
  /**
   * applies the starting bonuses given by each crew member.
   * @param crewState the CrewState to get the crew of and apply the bonuses to
   */
  public void applyCrewMemberBonuses(CrewState crewState) {
    for (CrewMember crew : crewState.getCrew()) {
      crew.applyStartBonuses(crewState);
    }
  }
  
  /**
   * creates a crew member with given parameters and adds to the crew.
   * @param name the name the new CrewMember should have
   * @param title the title the new CrewMember should have
   * @throws RuntimeException throws exception if trying to add member to a full crew
   */
  private void createCrewMember(int type, String name) {
    if (crewList.size() == MAX_CREW) {
      throw new RuntimeException("max crew size has been reached");
    }
    crewList.add(CrewMemberFactory.createCrewMember(type, name));
  }
  
  /**
   * adds an already created CrewMember to the crew List.
   * @param crew CrewMember to add to the List of CrewMembers
   */
  public void addCrewMember(CrewMember crew) {
    if (crewList.size() == MAX_CREW) {
      throw new RuntimeException("max crew size has been reached");
    }
    crewList.add(crew);
  }
  
  /**
   * creates a Ship for the crew with a given name.
   * @param name name to give the Ship
   */
  public void createShip(String name) {
    ship = new Ship(name);
  }
  
  /**
   * sets the GameEnvironment's CrewState object.
   */
  private void setUpCrewState() {
    if (crewList.size() < MIN_CREW) {
      throw new RuntimeException("not enough crew members created, min " + MIN_CREW + " required");
    }
    if (crewList.size() > MAX_CREW) {
      throw new RuntimeException("too many crew members. please restart the game.");
    }
    if (ship == null) {
      throw new RuntimeException("ship has not been created, run createShip()");
    }
    
    CrewState crewState = new CrewState(crewList, ship);
    applyCrewMemberBonuses(crewState);
    env.setCrewState(crewState);
  }
  

  /**
   * command line version of SpaceExplorer game setup window.
   */
  private void commandLine() {
    
    CommandLineParser cl = new CommandLineParser(System.out, System.in);
    
    int numDays = cl.inputInt(String.format("Game Length (%d-%d): ",
                              MIN_DAYS, MAX_DAYS), MIN_DAYS, MAX_DAYS);
    setNumDays(numDays);
    
    int crewTypeNum = 0;
    String crewName = "";
    boolean continueMakingCrew = true;
    do {
      crewTypeNum = cl.inputOptions("Select crew member type: ",
                                    "Investor -- " + Investor.getClassDescription(),
                                    "Medic -- " + Medic.getClassDescription(),
                                    "Engineer -- " + Engineer.getClassDescription(),
                                    "Student -- " + Student.getClassDescription(),
                                    "Scientist -- " + Scientist.getClassDescription(),
                                    "Robot -- " + Robot.getClassDescription());
      
      crewName = cl.inputString("Enter crew member's name: ", 30);
      
      createCrewMember(crewTypeNum + 1, crewName);
      
      // Display current crew
      cl.print("\nCurrent Crew:\n");
      cl.print("==============================\n");
      for (int i = 0; i < crewList.size(); i++) {
        cl.print("  + " + crewList.get(i).getFullTitle() + "\n");
      }
      cl.print("==============================\n\n");
      
      // Ask to continue if min or max hasn't been reached
      if (crewList.size() >= MIN_CREW) {
        if (crewList.size() < MAX_CREW) {
          continueMakingCrew = cl.inputBoolean("Create another crew member? (Y/N): ");
        } else {
          cl.print("Max number of crew member reached.\n");
          continueMakingCrew = false;
        }
      }
      
    } while (continueMakingCrew);
    
    String shipName = cl.inputString("Enter crew's ship name: ", 30);
    createShip(shipName);

    CommandLineRunner runner = new CommandLineRunner(env, cl);
    
    finishedSetup();
    
    runner.startGame();
    
  }
  
  public boolean isCL() {
    return useCl;
  }
}
