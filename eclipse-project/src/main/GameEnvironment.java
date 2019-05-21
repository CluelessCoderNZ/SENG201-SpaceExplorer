package main;

import crew.CrewEffectChangeObserverEvent;
import crew.CrewMember;
import crew.CrewState;
import eventmanager.GameAction;
import eventmanager.GameEvent;
import eventmanager.GameEventManager;
import gui.EventPopupWindow;
import gui.MainWindow;
import gui.ShopWindow;

import java.util.List;
import java.util.Random;

/**
 * GameEnvironment is the central class to the game having all game related objects as its members.
 * The main entry point for the program is from GameEnvironment. It then calls GameSetup to 
 * instantiate some of it's members. Throughout much of the application GameEnvironment is used as
 * an argument to pass context to the game state, such as in GameEvent.
 */
public class GameEnvironment implements Observer {
  
  private GameEventManager eventManager;
  private Planet currentPlanet;
  private List<Planet> planetList;
  private CrewState crewState;
  private int daysPassed = 0;
  private int maxDays;
  
  // The central randomGenerator allows for seeds to deterministically produce games.
  private Random randomGenerator = new Random();
  
  private int currentScore = 0;
  
  /**
   * sets the game's GameEventManager.
   * @param eventManager GameEventManager to use for this GameEnvironment
   */
  public void setEventManager(GameEventManager eventManager) {
    this.eventManager = eventManager;
  }
  
  /**
   * sets the random generator seed to be used by the event system.
   * @param seed long seed to use for the Random object
   */
  public void setRandomSeed(long seed) {
    randomGenerator.setSeed(seed);
  }
  
  /**
   * returns the Random object used for random generation.
   * @return the random generator used by this GameEnvironment
   */
  public Random getRandomGenerator() {
    return randomGenerator;
  }
  
  /**
   * sets the GameEnvironment's CrewState.
   * @param state CrewState to track using this GameEnvironment
   */
  public void setCrewState(CrewState state) {
    crewState = state;
  }
  
  /**
   * return the CrewState.
   * @return CrewState object this GameEnvironment is tracking
   */
  public CrewState getCrewState() {
    return crewState;
  }
  
  /**
   * sets the maximum number of days the game will last.
   * @param days max days the game will last
   */
  public void setNumDays(int days) {
    maxDays = days;
  }
  
  /**
   * returns the number of days passed so far.
   * @return the days passed so far as an int
   */
  public int getCurrentDay() {
    return daysPassed;
  }
  
  /**
   * returns the maximum days the game will last.
   * @return max number of days as an int
   */
  public int getMaxDays() {
    return maxDays;
  }
  
  /**
   * returns the number of ship parts needed in total to complete the game.
   * @return total ship parts needed as an int
   */
  public int getShipPartsNeededCount() {
    return (maxDays * 2) / 3;
  }
  
  /**
   * sets the List of planets the player can travel to.
   * @param planets List of available Planets
   */
  public void setPlanets(List<Planet> planets) {
    planetList = planets;
  }
  
  /**
   * returns the List of Planets the player can travel to.
   * @return List of available Planets
   */
  public List<Planet> getPlanets() {
    return planetList;
  }
  
  /**
   * Creates a GameSetup object to initialise GameEnvironment.
   * @param cl whether to start game in command line mode
   */
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
  
  /**
   * Launches main gui window.
   */
  public void mainWindow() {
    new MainWindow(this);
  }
  
  /**
   * Called at the end of the game. Calculates final score and displays final game end message.
   * @param mainWindow reference to the main window to close.
   */
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
  
  /**
   * closes the main window and opens the shop window.
   * @param mainWindow the MainWindow to close
   */
  public void openShop(MainWindow mainWindow) {
    mainWindow.closeWindow();
    new ShopWindow(this);
  }
  
  /**
   * closes the shop window and opens the main window.
   * @param shop the ShopWindow to close
   */
  public void finishShop(ShopWindow shop) {
    shop.closeWindow();
    new MainWindow(this);
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
  
  /**
   * increase the player's score by an integer amount.
   * @param score int to add to the current score
   */
  public void addScore(int score) {
    currentScore += score;
  }
  
  /**
   * returns the player's current score.
   * @return player's current score as an int
   */
  public int getScore() {
    return currentScore;
  }
  
  /**
   * shows the success popup for the game with the number of parts collected and the player's score.
   */
  private void displayWinScreen() {
    new EventPopupWindow(String.format("You Won:\nYou managed to collect all %d parts.\n\n"
                                       + "Score: %d",
                                       getShipPartsNeededCount(),
                                       getScore()));
  }
  
  /**
   * shows the lose popup for the game with the number of parts collected and the player's score.
   */
  private void displayLoseScreen() {
    new EventPopupWindow(String.format("GAME OVER:\nYou failed to collect all %d parts.\n\n"
        + "Score: %d",
        getShipPartsNeededCount(),
        getScore()));
  }
  
  /**
   * Observes crew members and shows popups when their active effect set changes.
   */
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