package crew;

/**
 * CrewEffectChangeObserverEvent acts as a container for information regarding
 * CrewMember active effect changes.
 * It holds onto this state information so it may be passed onto observers.
 */ 
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
