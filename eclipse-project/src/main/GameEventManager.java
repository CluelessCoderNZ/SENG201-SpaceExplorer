package main;

import java.util.Random;


public class GameEventManager {
  /*
  ===============
      MEMBERS
  ===============
  */
  private GameEvent[][] gameActionEventList;
  private Random randomGenerator;

  /*
  ===============
     SET & GET   
  ===============
  */
  
  
  public GameEventManager() {
    randomGenerator = new Random();
    gameActionEventList = new GameEvent[GameAction.COUNT][];
  }
  
  
  public GameEventManager(long randomSeed) {
    randomGenerator = new Random(randomSeed);
    gameActionEventList = new GameEvent[GameAction.COUNT][];
  }
  
  public void setActionEvents(GameAction action, GameEvent[] events) {
    gameActionEventList[action.ordinal()] = events;
  }
  
  public GameEvent getRandomEvent(GameAction action) {
    return selectRandomEvent(gameActionEventList[action.ordinal()]);
  }
  
  /*
  ===============
     METHODS
  ===============
  */
  
  
  private GameEvent selectRandomEvent(GameEvent[] events) {
    GameEvent randomEvent = null;
    
    int randomWeight = randomGenerator.nextInt(getTotalChanceWeight(events));
    
    // Will always select event for positive chanceWeights
    int weightSum = 0;
    for (GameEvent event : events) {
      weightSum += event.getChanceWeight();
      
      if (randomWeight <= weightSum) {
        randomEvent = event.createEvent(randomGenerator);
        break;
      }
    }
    
    return randomEvent;
  }
  
  private int getTotalChanceWeight(GameEvent[] events) {
    int sum = 0;
    for (GameEvent event : events) {
      sum += event.getChanceWeight();
    }
    
    return sum;
  }
}
