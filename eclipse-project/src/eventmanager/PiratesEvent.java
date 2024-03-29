package eventmanager;

import items.Item;
import items.ShipPart;

import java.util.Random;

import main.GameEnvironment;
import main.Planet;

/**
 * PiratesEvent takes a targetItem from the Crew's Inventory if their inventory
 * is empty the event does nothing.
 * Special Case: If the targetItem is a ShipPart it is given back to the first planet without one
 * to prevent a permanent lose state.
 */
public class PiratesEvent extends GameEvent {

  private Item targetItem = null;

  @Override
  public GameEvent createEvent(Random randomGenerator, GameEnvironment env) {
    PiratesEvent event = new PiratesEvent();
    
    if (env.getCrewState().getInventory().size() > 0) {
      int itemIndex = randomGenerator.nextInt(env.getCrewState().getInventory().size());
      event.targetItem = env.getCrewState().getInventory().get(itemIndex);
    }
        
    return event;
  }

  @Override
  public void applyEvent(GameEnvironment env) {
    env.getCrewState().removeItemFromInventory(targetItem);
    
    // If they steal a ship part redistribute to another planet.
    if (targetItem instanceof ShipPart) {
      for (Planet planet : env.getPlanets()) {
        if (!planet.hasShipPart()) {
          planet.setPart((ShipPart)targetItem);
          break;
        }
      }
    }
  }
  
  @Override
  public boolean canEventApply(GameEnvironment env) {
    return targetItem != null;
  }

  @Override
  public String getEventMessage() {
    if (targetItem != null) {
      return String.format("Damn pirates boarded the ship and stole a %s from the cargo.", 
                           targetItem.getName());
    }
    return "";
  }

}
