package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameEnvironment {
  
  private GameEventManager eventManager;
  private Planet currentPlanet;
  private List<Planet> planetList;
  private CrewState crewState;
  private int daysPassed = 0;
  private int maxDays;
  private Random randomGenerator = new Random();
  
  public void setEventManager(GameEventManager eventManager) {
    this.eventManager = eventManager;
  }
  
  public void setRandomSeed(long seed) {
    randomGenerator.setSeed(seed);
  }
  
  public Random getRandomGenerator() {
   return randomGenerator;
  }
  
  public void setCrewState(CrewState state) {
    crewState = state;
  }
  
  public CrewState getCrewState() {
    return crewState;
  }
  
  public void setNumDays(int days) {
    maxDays = days;
  }
  
  public int getCurrentDay() {
    return daysPassed;
  }
  
  public int getMaxDays() {
    return maxDays;
  }
  
  public int getShipPartsNeededCount() {
    return (maxDays * 2) / 3;
  }
  
  public void setPlanets(List<Planet> planets) {
    planetList = planets;
  }
  
  public List<Planet> getPlanets() {
    return planetList;
  }
  
  public void setupGame(boolean cl) {
    GameSetup setup = new GameSetup(this, cl);
  }
  
  /**
   * closes the game setup window and starts the main game.
   * @param setup the setup window to close
   */
  public void finishSetup(GameSetup setup) {
    setup.closeWindow();
    System.out.println(crewState);
    MainWindow mainGame = new MainWindow(this);
    mainWindow();
  }
  
  public void mainWindow() {
    MainWindow mainGame = new MainWindow(this);
  }

  
  /**
   * Sets the current planet of the game.
   * @param planet planet to set.
   */
  public void setCurrentPlanet(Planet planet) {
    if (!getPlanets().contains(planet)) {
      throw new IllegalArgumentException("Planet is not in planet list.");
    }
    
    currentPlanet = planet;
  }
  
  public Planet getCurrentPlanet() {
    return currentPlanet;
  }
  
  /**
   * Increases daysPassed and activates DAY_START events.
   * Also alerts all crew of new day.
   * @return an event if an event applied, null otherwise.
   */
  public GameEvent moveForwardADay() {
    daysPassed += 1;
    
    // This order matters.
    crewState.applyDayStartEffects();
    
    GameEvent event = eventManager.getRandomEvent(GameAction.DAY_START, randomGenerator, this);
    
    if (event.canEventApply(this)) {
      event.applyEvent(this);
      
      // Return for interface to be aware of what happened.
      return event;
    }
    
    return null;
  }
  
  /**
   * Change current planets checking for events and reducing crew member's action points.
   * @param crewMemberA pilot
   * @param crewMemberB pilot
   * @param planet new current planet
   * @return
   */
  public GameEvent travelToPlanet(CrewMember crewMemberA, CrewMember crewMemberB, Planet planet) {
    // Error Checking
    if (!getCrewState().getCrew().contains(crewMemberA)
        || !getCrewState().getCrew().contains(crewMemberB)) {
      throw new IllegalArgumentException("Crew members are not in crew.");
    }
    
    if (!getPlanets().contains(planet)) {
      throw new IllegalArgumentException("Planet is not in planet list.");
    }
    
    // Travel To Planet
    if (crewMemberA.hasActionAvaliable() && crewMemberB.hasActionAvaliable()) {
      crewMemberA.useAction();
      crewMemberB.useAction();
      
      setCurrentPlanet(planet);
      
      // Event Handling
      GameEvent event = eventManager.getRandomEvent(GameAction.CREW_TRAVEL, randomGenerator, this);
      
      if (event.canEventApply(this)) {
        event.applyEvent(this);
        
        // Return for interface to be aware of what happened.
        return event;
      }
    }
    
    return null;
  }
  
  /**
   * Runs CREW_EXPLORE events and returns on applicable ones.
   * @param crewMember crew member who 'explores'
   * @return
   */
  public GameEvent explorePlanet(CrewMember crewMember) {
    // Error Checking
    if (!getCrewState().getCrew().contains(crewMember)) {
      throw new IllegalArgumentException("Crew member is not in crew.");
    }
    
    if (crewMember.hasActionAvaliable()) {
      crewMember.useAction();
      
      // Exploring Event.
      GameEvent event = eventManager.getRandomEvent(GameAction.CREW_EXPLORE, randomGenerator, this);
      
      if (event.canEventApply(this)) {
        event.applyEvent(this);
        
        // Return for interface to be aware of what happened.
        return event;
      }
    }
    
    return null;
  }
  
  /**
   * Check if Lose/Win Condition has been met resulting in the game halting.
   * @return
   */
  public boolean gameStillActive() {
    boolean active = true;
    
    // Check max days is not reached.
    active = active && getCurrentDay() < maxDays;
    
    // Check there is still living crew members
    active = active && crewState.getLivingCrewCount() > 0;
    
    active = active && crewState.getShip().getShieldLevel() > 0;
    
    return active;
  }
  
  
  
  public static void main(String[] args) {
    GameEnvironment env = new GameEnvironment();
    env.setupGame(false);
  }

}