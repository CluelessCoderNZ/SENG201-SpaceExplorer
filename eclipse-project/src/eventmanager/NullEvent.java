package eventmanager;

import java.util.Random;

import main.GameEnvironment;

/**
 * NullEvent represents the GameEvent of nothing happening. As such it can never be applied.
 * This event can be used to increase the odds of nothing happening for a GameAction.
 */
public class NullEvent extends GameEvent {

  
  @Override
  public GameEvent createEvent(Random randomGenerator, GameEnvironment env) {
    return new NullEvent();
  }

  @Override
  public void applyEvent(GameEnvironment env) { /* Do Nothing */ }

  @Override
  public String getEventMessage() { 
    return "Nothing Happened";
  }
  
  @Override
  public boolean canEventApply(GameEnvironment env) {
    return false; // Can never apply
  }

}
