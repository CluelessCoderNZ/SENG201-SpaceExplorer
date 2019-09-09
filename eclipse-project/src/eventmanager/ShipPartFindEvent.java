package eventmanager;

import items.ShipPart;

import java.util.Random;

import main.GameEnvironment;

/**
 * ShipPartFindEvent adds the Planet ShipPart to the Crew's Inventory.
 * If the planet has no ship part the player gets a message about the planet not having a ShipPart.
 */
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
