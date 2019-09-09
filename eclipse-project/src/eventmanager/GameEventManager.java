package eventmanager;

import java.util.ArrayList;
import java.util.Random;

import main.GameEnvironment;

/**
 * GameEventManager handles the possible GameEvents for every GameAction.
 * GameEventManager is to be initialised at the start of the game with setActionEvents.
 * During runtime  getRandomEvent can be called to receive a possible event for a GameAction
 */
public class GameEventManager {
  /*
  ===============
      MEMBERS
  ===============
  */
  private ArrayList<WeightedArrayList<GameEvent>> gameActionEventList;
  
  
  /**
   * Default Constructor initialises internal arrays.
   */
  public GameEventManager() {
    gameActionEventList = new ArrayList<WeightedArrayList<GameEvent>>(GameAction.COUNT);
    for (int i = 0; i < GameAction.COUNT; i++) {
      gameActionEventList.add(new WeightedArrayList<GameEvent>());
    }
  }
  
  
  /**
   * Should be run on startup sets the events associated with a GameAction.
   * @param action action associated
   * @param events possible game events with weights
   */
  public void setActionEvents(GameAction action, WeightedArrayList<GameEvent> events) {
    gameActionEventList.set(action.ordinal(), events);
  }
  
  /**
   * Method returns a random event from a action group of events.
   * If no action group events is empty it returns null.
   * @param action group
   * @param randomGenerator used to generate random members of events.
   * @param env environment to be affected by event.
   * @return random event
   */
  public GameEvent getRandomEvent(GameAction action, Random randomGenerator, GameEnvironment env) {
    GameEvent event = gameActionEventList.get(action.ordinal()).getRandomItem(randomGenerator);
    return event.createEvent(randomGenerator, env);
  }
}
