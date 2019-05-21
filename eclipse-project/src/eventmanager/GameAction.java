package eventmanager;

/**
 * This enum represents the set of possible actions that can occur a random game event.
 */
public enum GameAction {
  CREW_TRAVEL,
  CREW_EXPLORE,
  DAY_START;
  
  public static int COUNT = GameAction.values().length;
}
