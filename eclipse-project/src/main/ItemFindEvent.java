package main;

import java.util.Random;

public class ItemFindEvent extends GameEvent {
  
  private Item foundItem;
  
  @Override
  public GameEvent createEvent(Random randomGenerator, GameEnvironment env) {
    ItemFindEvent event = new ItemFindEvent();
    event.foundItem = env.getCurrentPlanet().getRandomItem(randomGenerator);
    
    return event;
  }

  @Override
  public void applyEvent(GameEnvironment env) {
    env.getCrewState().addItemToInventory(foundItem);
    env.addScore(100);
  }
  
  @Override
  public boolean canEventApply(GameEnvironment env) {
    return foundItem != null;
  }

  @Override
  public String getEventMessage() {
    return "You found a " + foundItem.getName();
  }

}
