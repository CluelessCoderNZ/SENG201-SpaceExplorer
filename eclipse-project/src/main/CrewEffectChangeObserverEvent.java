package main;

public class CrewEffectChangeObserverEvent {
  private CrewMemberEffect effect;
  private boolean isAdded;
  
  public CrewEffectChangeObserverEvent(boolean isAdded, CrewMemberEffect effect) {
    this.isAdded = isAdded;
    this.effect = effect; 
  }
  
  public boolean wasAdded() {
    return isAdded;
  }
  
  public boolean wasRemoved() {
    return !isAdded;
  }
  
  public CrewMemberEffect getEffect() {
    return effect;
  }
}
