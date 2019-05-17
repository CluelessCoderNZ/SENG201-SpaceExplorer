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
    for (int i = 0; i < GameAction.COUNT; i++) {
      gameActionEventList.add(new WeightedArrayList<GameEvent>());
    }
  }
  
  
  
  public void setActionEvents(GameAction action, WeightedArrayList<GameEvent> events) {
    gameActionEventList.set(action.ordinal(), events);
  }
  
  /**
   * Method returns a random event from a action group of events.
   * If no action group events is empty it returns null.
   * @param action group
   * @param randomGenerator used to generate random members of events.
   * @param env environment to be affected by event.
   * @return
   */
  public GameEvent getRandomEvent(GameAction action, Random randomGenerator, GameEnvironment env) {
    GameEvent event = gameActionEventList.get(action.ordinal()).getRandomItem(randomGenerator);
    return event.createEvent(randomGenerator, env);
  }
}
