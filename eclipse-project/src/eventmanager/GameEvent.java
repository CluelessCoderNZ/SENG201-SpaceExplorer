package eventmanager;

import java.util.Random;

import main.GameEnvironment;

/**
 * GameEvent is the abstract base class for all random events in the game.
 * It requires the implementation of three functions.
 *  + createEvent : A function which clones the event and instantiates any random members.
 *  + applyEvent : A function which applies the event's actions to the GameEnvironment.
 *  + getEventMessage : A function which return's a flavoured message about the event for display.
 */
public abstract class GameEvent {
  
  /**
   * Creates clone event of the same type with random event members.
   * @param randomGenerator the Random generator to use
   * @param env the GameEnvironment to decide the result of the event based on
   * @return GameEvent the created GameEvent
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
  
  /**
   * Returns a message for display about the event.
   * May include numbers about the details of the event.
   * @return message
   */
  public abstract String getEventMessage();
}
