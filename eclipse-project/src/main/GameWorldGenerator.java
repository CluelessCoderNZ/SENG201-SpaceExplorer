package main;

import eventmanager.AsteroidEvent;
import eventmanager.GameAction;
import eventmanager.GameEvent;
import eventmanager.GameEventManager;
import eventmanager.ItemFindEvent;
import eventmanager.MoneyFindEvent;
import eventmanager.NullEvent;
import eventmanager.PiratesEvent;
import eventmanager.ShipPartFindEvent;
import eventmanager.SpacePlagueEvent;
import eventmanager.WeightedArrayList;
import items.GenericRestorationItem;
import items.Item;
import items.PlagueCure;
import items.ShipShieldUpgradeItem;
import items.ShipWeaponItem;

import java.util.ArrayList;

/**
 * GameWorldGenerator contains many factory functions for building key game aspects. 
 * Such as the event builder, shops and planets. 
 * A lot of the game balance and configuration can be done out of this class.
 */
public class GameWorldGenerator {
  
  private static final int DEFAULT_PLANET_NUM = 8;
  private static final int NUM_STORE_ITEMS = 5;
  private static final double SHOP_SELL_MODIFER = 1.15;
  private static final double SHOP_BUY_MODIFER = 0.85;
  
  /**
   * generates planets and an event manager for the given GameEnvironment.
   * @param env the GameEnvironment to assign the generated assets to
   */
  public static void generateWorld(GameEnvironment env) {
    generatePlanets(env, DEFAULT_PLANET_NUM);
    env.setCurrentPlanet(env.getPlanets().get(0));
    buildDefaultEventManager(env);
  }

  /**
   * builds an event manager and assigns it to the given GameEnvironment.
   * @param env the GameEnvironment to assign the GameEventManager to
   */
  private static void buildDefaultEventManager(GameEnvironment env) {
    // DAY_START
    WeightedArrayList<GameEvent> dayStartEvents = new WeightedArrayList<GameEvent>();
    dayStartEvents.addItem(new NullEvent(), 500);
    dayStartEvents.addItem(new SpacePlagueEvent(), 1000);
    dayStartEvents.addItem(new PiratesEvent(), 500);
    
    // CREW_TRAVEL
    WeightedArrayList<GameEvent> travelEvents = new WeightedArrayList<GameEvent>();
    travelEvents.addItem(new NullEvent(), 500);
    travelEvents.addItem(new PiratesEvent(), 200);
    travelEvents.addItem(new AsteroidEvent(), 2000);
    
    
    // CREW_EXPLORE
    WeightedArrayList<GameEvent> exploreEvents = new WeightedArrayList<GameEvent>();
    exploreEvents.addItem(new NullEvent(), 500);
    exploreEvents.addItem(new MoneyFindEvent(), 800);
    exploreEvents.addItem(new ItemFindEvent(), 500);
    exploreEvents.addItem(new ShipPartFindEvent(), 500);
    
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
  private static void generatePlanets(GameEnvironment env, int numPlanets) {
    
    // Loot Table
    WeightedArrayList<Item> lootTable = new WeightedArrayList<Item>();
    lootTable.addItem(new GenericRestorationItem("Medicaid", 60, 30, 0),                  100);
    lootTable.addItem(new GenericRestorationItem("Bandages", 25, 5, 0, 3),                100);
    lootTable.addItem(new GenericRestorationItem("StimuLife", 130, 65, 50),               100);
    lootTable.addItem(new GenericRestorationItem("Ham Sandwich", 20, 5, 25),              100);
    lootTable.addItem(new GenericRestorationItem("Space Candy", 20, 0, 1, 10),            400);
    lootTable.addItem(new ShipShieldUpgradeItem("Shield Upgrade A", 30, 50),              150);
    lootTable.addItem(new ShipWeaponItem("Photon Cannons", 100, 0),                       100);
    lootTable.addItem(new ShipWeaponItem("Pulsar Beam", 200, 40),                         100);
    lootTable.addItem(new GenericRestorationItem("Hamburger", 40, 10, 50),                100);
    lootTable.addItem(new GenericRestorationItem("Scrambled Eggs", 20, 0, 20),            100);
    lootTable.addItem(new Item("Golden Spork", 100, "Shiny. Not good for eating with."),  100);
    lootTable.addItem(new Item("Golden Bars", 1000, "We're rich!"),                        50);
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
    planetNames.addItem("Mars",                 100);
    planetNames.addItem("Titan",                100);
    planetNames.addItem("Magrathea",            100);
    
    
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
      planetNames.removeItem(planetName);
      
      Planet planet = new Planet(planetName, shop, lootTable);
      
      // Add Planet
      planets.add(planet);
    }
    env.setPlanets(planets);
  }
  
}
