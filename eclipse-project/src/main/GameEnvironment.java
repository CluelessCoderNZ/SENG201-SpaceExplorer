package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import crew.CrewEffectChangeObserverEvent;
import crew.CrewMember;
import crew.CrewState;
import eventmanager.GameAction;
import eventmanager.GameEvent;
import eventmanager.GameEventManager;
import gui.EventPopupWindow;
import gui.MainWindow;
import gui.ShopWindow;

public class GameEnvironment implements Observer {
  
  private GameEventManager eventManager;
  private Planet currentPlanet;
  private List<Planet> planetList;
  private CrewState crewState;
  private int daysPassed = 0;
  private int maxDays;
  private Random randomGenerator = new Random();
  
  private int currentScore = 0;
  
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
    currentScore = 0;
    GameWorldGenerator.generateWorld(this);
    new GameSetup(this, cl);
  }
  
  /**
   * closes the game setup window and starts the main game.
   * @param setup the setup window to close
   */
  public void finishSetup(GameSetup setup) {
    setup.closeWindow();
    System.out.println(crewState);
    
    // Subscribe GameEnvironment to crew events.
    for (CrewMember cm : crewState.getCrew()) {
      cm.addObserver(this);
    }
    
    if (!setup.isCL()) {
      mainWindow();
    }
  }
  
  public void mainWindow() {
    MainWindow mainGame = new MainWindow(this);
  }
  
  public void finishMainGame(MainWindow mainWindow) {
    mainWindow.closeWindow();
    
    if (crewState.getShipPartsFoundCount() >= getShipPartsNeededCount()) {
      addScore(crewState.getLivingCrewCount() * 500);
      addScore((maxDays - daysPassed) * 1000);
      
      displayWinScreen();
    } else {
      displayLoseScreen();
    }
  }
  
  public void openShop(MainWindow mainWindow) {
    mainWindow.closeWindow();
    ShopWindow shop = new ShopWindow(this);
  }
  
  public void finishShop(ShopWindow shop) {
    shop.closeWindow();
    MainWindow mainWindow = new MainWindow(this);
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
    
    // Check ship is not destroyed
    active = active && crewState.getShip().getShieldLevel() > 0;
    
    // Check ship parts have not been found.
    active = active && crewState.getShipPartsFoundCount() < getShipPartsNeededCount();
    
    return active;
  }
  
  public void addScore(int score) {
    currentScore += score;
  }
  
  public int getScore() {
    return currentScore;
  }
  
  private void displayWinScreen() {
    new EventPopupWindow(String.format("You Won:\nYou managed to collect all %d parts.\n\n"
                                       + "Score: %d",
                                       getShipPartsNeededCount(),
                                       getScore()));
  }
  
  private void displayLoseScreen() {
    new EventPopupWindow(String.format("GAME OVER:\nYou failed to collect all %d parts.\n\n"
        + "Score: %d",
        getShipPartsNeededCount(),
        getScore()));
  }
  
  // Observes crew updates
  @Override
  public void update(Observable o, Object arg) {
    if (arg instanceof CrewEffectChangeObserverEvent) {
      CrewEffectChangeObserverEvent event = (CrewEffectChangeObserverEvent)arg;
      CrewMember crewMember = (CrewMember)o;
      
      if (event.wasAdded()) {
        switch (event.getEffect()) {
          case PLAGUED:
            break;
          case DEAD:
            new EventPopupWindow(String.format("%s is now %s", 
                                               crewMember.getName(),
                                               event.getEffect().name()), 
                                               "This is unfortunate.");
            break;
          default:
            new EventPopupWindow(String.format("%s is now %s", 
                                               crewMember.getName(), 
                                               event.getEffect().name()));
        }
        
      }
      
      if (event.wasRemoved()) {
        new EventPopupWindow(String.format("%s is no longer %s", 
                                           crewMember.getName(), 
                                           event.getEffect().name()));
      }
    }
  }
  
  /**
   * Entry point for the game.
   * @param args program arguments
   */
  public static void main(String[] args) {
    GameEnvironment env = new GameEnvironment();
    
    // Parse args
    boolean clMode = false;
    if (args.length > 0) {
      if (args[0].equals("cl")) {
        clMode = true;
      }
    }
    
    if (args.length > 1) {
      try {
        int seed = Integer.parseInt(args[1]);
        env.getRandomGenerator().setSeed(seed);
      } catch (NumberFormatException e) {
        System.out.println("Error: Seed must be int");
      }
    }
    
    env.setupGame(clMode);
  }

}