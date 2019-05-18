package main;

import java.util.Random;

public class ShipPartFindEvent extends GameEvent {
  ShipPart partFound = null;
  
  @Override
  public GameEvent createEvent(Random randomGenerator, GameEnvironment env) {
    ShipPartFindEvent event = new ShipPartFindEvent();
    
    if (env.getCurrentPlanet().hasShipPart()) {
      event.partFound = env.getCurrentPlanet().removeShipPart();
    }
    
    return event;
  }

  @Override
  public void applyEvent(GameEnvironment env) {
    if (partFound != null) {
      env.getCrewState().addItemToInventory(partFound);
      env.addScore(500);
    }
  }

  @Override
  public String getEventMessage() {
    if (partFound == null) {
      return "You found the remains of an old ship already scrapped for parts.";
    } else {
      return String.format("You found a functioning %s, we're one step closer to fixing the ship!",
                            partFound.getName()); 
    }
  }

}
