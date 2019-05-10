package main;

import java.util.ArrayList;
import java.util.Random;


public class GameEventManager {
  /*
  ===============
      MEMBERS
  ===============
  */
  private ArrayList<WeightedArrayList<GameEvent>> gameActionEventList;
  
  
  public GameEventManager() {
    gameActionEventList = new ArrayList<WeightedArrayList<GameEvent>>(GameAction.COUNT);
  }
  
  
  
  public void setActionEvents(GameAction action, WeightedArrayList<GameEvent> events) {
    gameActionEventList.set(action.ordinal(), events);
  }
  
  public GameEvent getRandomEvent(GameAction action, Random randomGenerator) {
    return gameActionEventList.get(action.ordinal()).getRandomItem(randomGenerator);
  }
}
