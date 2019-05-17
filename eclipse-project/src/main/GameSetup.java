package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameSetup {
  
  private static final int MIN_CREW = 2;
  private static final int MAX_CREW = 4;
  private static final int MIN_DAYS = 3;
  private static final int MAX_DAYS = 10;
  private static final int DEFAULT_PLANET_NUM = 8;
  private static final int NUM_STORE_ITEMS = 4;
  
  private static final double SHOP_SELL_MODIFER = 1.15;
  private static final double SHOP_BUY_MODIFER = 0.85;
  
  private  boolean cl = true;
  
  
  private List<CrewMember> crewMembers = new ArrayList<CrewMember>();
  private Ship ship = null;
  private JFrame frame;
  private GameEnvironment env;
  
  
  
  /**
   * GameSetup constructor.
   * @param env the GameEnvironment being used to store the game's state
   * @param cl flag for if the game should start in command line mode
   */
  public GameSetup(GameEnvironment env, boolean cl) {
    this.env = env;
    this.cl = cl;
    
    generatePlanets(DEFAULT_PLANET_NUM);
    env.setCurrentPlanet(env.getPlanets().get(0));
    buildDefaultEventManager();
    
    if (cl) {
      commandLine();
    } else {
      initializeWindow();
      frame.setVisible(true);
    }
  }
  
  /**
   * performs final setup steps (creating the crewState and applying bonuses).
   */
  public void finishedSetup() {
    setUpCrewState();
    env.finishSetup(this);
  }
  
  /**
   * closes the GameSetup Swing window.
   */
  public void closeWindow() {
    if (!cl) {
      frame.dispose();
    }
  }
  
  private void initializeWindow() {
    frame = new JFrame();
    frame.setBounds(100, 100, 636, 468);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    
    JLabel lblWelcome = new JLabel("Welcome to SpaceExplorer");
    lblWelcome.setBounds(6, 6, 181, 16);
    frame.getContentPane().add(lblWelcome);
  }
  
  private void buildDefaultEventManager() {
    // DAY_START
    WeightedArrayList<GameEvent> dayStartEvents = new WeightedArrayList<GameEvent>();
    dayStartEvents.addItem(new NullEvent(), 1000);
    dayStartEvents.addItem(new SpacePlagueEvent(), 500);
    dayStartEvents.addItem(new PiratesEvent(), 1000);
    
    // CREW_TRAVEL
    WeightedArrayList<GameEvent> travelEvents = new WeightedArrayList<GameEvent>();
    travelEvents.addItem(new NullEvent(), 1000);
    travelEvents.addItem(new SpacePlagueEvent(), 50);
    travelEvents.addItem(new PiratesEvent(), 200);
    travelEvents.addItem(new AsteroidEvent(), 1000);
    
    
    // CREW_EXPLORE
    WeightedArrayList<GameEvent> exploreEvents = new WeightedArrayList<GameEvent>();
    exploreEvents.addItem(new NullEvent(), 1000);
    
    // Create manager
    GameEventManager eventManager = new GameEventManager();
    eventManager.setActionEvents(GameAction.DAY_START, dayStartEvents);
    eventManager.setActionEvents(GameAction.CREW_TRAVEL, travelEvents);
    eventManager.setActionEvents(GameAction.CREW_EXPLORE, exploreEvents);
    
    env.setEventManager(eventManager);
  }
  
  
  /**
   * sets up the planets and outposts that players can visit in the game.
   * @param numPlanets The number of planets to be generated.
   */
  private void generatePlanets(int numPlanets) {
    
    // Loot Table
    WeightedArrayList<Item> lootTable = new WeightedArrayList<Item>();
    lootTable.addItem(new GenericRestorationItem("Medicaid", 60, 30, 0),                  100);
    lootTable.addItem(new GenericRestorationItem("Bandages", 25, 5, 0, 3),                100);
    lootTable.addItem(new GenericRestorationItem("StimuLife", 130, 65, 50),               100);
    lootTable.addItem(new GenericRestorationItem("Ham Sandwich", 20, 5, 25),              100);
    lootTable.addItem(new GenericRestorationItem("Space Candy", 20, 0, 1, 10),            400);
    lootTable.addItem(new ShipUpgradeItem("Shield Upgrade A", 30, 50),                    150);
    lootTable.addItem(new Item("Photon Cannons", 100),                                    100);
    lootTable.addItem(new Item("Pulsar Beam", 200),                                       100);
    lootTable.addItem(new GenericRestorationItem("Hamburger", 40, 10, 50),                100);
    lootTable.addItem(new GenericRestorationItem("Scrambled Eggs", 20, 0, 20),            100);
    lootTable.addItem(new Item("Golden Spork", 100),                                      100);
    lootTable.addItem(new Item("Golden Bars", 1000),                                       50);
    lootTable.addItem(new GenericRestorationItem("Antimatter Brownies", 40, 0, -30, 3),   100);
    lootTable.addItem(new GenericRestorationItem("Bountiful Feast", 300, 0, 50, 10),      100);
    lootTable.addItem(new PlagueCure("Plague-Away", 300),                                 150);

    
    // Shop Name Table
    WeightedArrayList<String> shopNames = new WeightedArrayList<String>();
    shopNames.addItem("Galactic Medications",   100);
    shopNames.addItem("Warmonger Supplies",     100);
    shopNames.addItem("Bart's Buffet",          100);
    shopNames.addItem("Crazy Carls",            100);
    
    // Planet Name Table
    WeightedArrayList<String> planetNames = new WeightedArrayList<String>();
    planetNames.addItem("Izanaki",              100);
    planetNames.addItem("New Quebec",           100);
    planetNames.addItem("Earth II",             100);
    planetNames.addItem("Keziah",               100);
    planetNames.addItem("Sorin Alpha",          100);
    planetNames.addItem("Nordlingen",           100);
    planetNames.addItem("Archimedra",           100);
    planetNames.addItem("Corolina",             100);
    planetNames.addItem("Alpha Centari Prime",  100);
    planetNames.addItem("Oregon",               100);
    
    
    // Generate Planets
    ArrayList<Planet> planets = new ArrayList<Planet>();
    
    for (int i = 0; i < numPlanets; i++) {
      
      // Create Shop Item List
      ArrayList<Item> shopItemList = new ArrayList<Item>(NUM_STORE_ITEMS);
      for (int j = 0; j < NUM_STORE_ITEMS; j++) {
        Item item = lootTable.getRandomItem(env.getRandomGenerator());
        shopItemList.add(item.copy());
      }
      
      // Create Shop
      String shopName = shopNames.getRandomItem(env.getRandomGenerator());
      Shop shop = new Shop(shopName, shopItemList, SHOP_BUY_MODIFER, SHOP_SELL_MODIFER);
      
      // Create Planet
      String planetName = planetNames.getRandomItem(env.getRandomGenerator());
      Planet planet = new Planet(planetName, shop, lootTable);
      
      // Add Planet
      planets.add(planet);
    }
    env.setPlanets(planets);
  }
  
  /**
   * sets the number of days the game should last.
   * Scatters correct number of parts between the game's planets.
   * @param days number of days for the game to last
   */
  private void setNumDays(int days) {
    if (days < MIN_DAYS || days > MAX_DAYS) {
      throw new RuntimeException("invalid number of days. Please enter a number between "
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
    Collections.shuffle(planets);
    for (int i = 0; i <= (days * 2) / 3 && i < planets.size(); i++) {
      env.getPlanets().get(i).setPart(new ShipPart());
    }
    Collections.shuffle(planets);
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
    if (crewMembers.size() == MAX_CREW) {
      throw new RuntimeException("max crew size has been reached");
    }
    crewMembers.add(CrewMemberFactory.createCrewMember(type, name));
  }
  
  /**
   * creates a Ship for the crew with a given name.
   * @param name name to give the Ship
   */
  private void createShip(String name) {
    ship = new Ship(name);
  }
  
  /**
   * sets the GameEnvironment's CrewState object.
   */
  private void setUpCrewState() {
    if (crewMembers.size() < MIN_CREW) {
      throw new RuntimeException("not enough crew members created, min " + MIN_CREW + " required");
    }
    if (crewMembers.size() > MAX_CREW) {
      throw new RuntimeException("too many crew members. please restart the game.");
    }
    if (ship == null) {
      throw new RuntimeException("ship has not been created, run createShip()");
    }
    CrewState crewState = new CrewState(crewMembers, ship);
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
                                    "Medic -- " + Medic.getClassDescription());
      
      crewName = cl.inputString("Enter crew member's name: ", 30);
      
      createCrewMember(crewTypeNum + 1, crewName);
      
      // Display current crew
      cl.print("\nCurrent Crew:\n");
      cl.print("==============================\n");
      for (CrewMember member : crewMembers) {
        cl.print("  + " + member.getFullTitle() + "\n");
      }
      cl.print("==============================\n\n");
      
      // Ask to continue if min or max hasn't been reached
      if (crewMembers.size() >= MIN_CREW) {
        if (crewMembers.size() < MAX_CREW) {
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
  
}
