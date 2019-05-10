package main;

import java.util.Random;

public class NullEvent extends GameEvent {

  public NullEvent(int chanceWeight) {
    this.chanceWeight = chanceWeight;
  }
  
  @Override
  public GameEvent createEvent(Random randomGenerator) {
    return new NullEvent(chanceWeight);
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
