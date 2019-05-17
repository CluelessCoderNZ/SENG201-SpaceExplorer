package main;

import java.util.Random;

public abstract class GameEvent {
  
  /**
   * Creates clone event of the same type with random event members.
   * @return
   */
  public abstract GameEvent createEvent(Random randomGenerator, GameEnvironment env);
  
  /**
   * Applies custom event to GameEnvironment specified.
   * @param env GameEnviornment to be affected.
   */
  public abstract void applyEvent(GameEnvironment env);
  
  /**
   * Checks if event can be applied to the environment given its current state.
   * @param env The environment to check
   * @return If the event can apply
   */
  public boolean canEventApply(GameEnvironment env) {
    return true;
  }
  
  public abstract String getEventMessage();
}
