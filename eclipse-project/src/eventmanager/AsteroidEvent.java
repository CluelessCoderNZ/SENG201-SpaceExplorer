package eventmanager;

import java.util.Random;

import main.GameEnvironment;

public class AsteroidEvent extends GameEvent {
  static final int MAXBASE_DAMAGE = 50;
  static final int SCALED_DAMAGE = 80;
  
  boolean isLethalDamage = false;
  int shieldDamage = 0;

  @Override
  public GameEvent createEvent(Random randomGenerator, GameEnvironment env) {
    AsteroidEvent event = new AsteroidEvent();
    
    double shieldPrecent = (double)env.getCrewState().getShip().getShieldLevel() 
                                 / env.getCrewState().getShip().getMaxShieldLevel();
    
    event.shieldDamage = randomGenerator.nextInt(MAXBASE_DAMAGE);
    
    event.shieldDamage += (1.0 - shieldPrecent) * SCALED_DAMAGE;
    
    event.isLethalDamage = event.shieldDamage > env.getCrewState().getShip().getShieldLevel();
    
    return event;
  }

  @Override
  public void applyEvent(GameEnvironment env) {
    env.getCrewState().getShip().takeDamage(shieldDamage);
  }

  @Override
  public String getEventMessage() {
    if (isLethalDamage) {
      return "This is it. The asteroid are tearing through the outer hull. "
          + "To think this could of all been avoided if someone had repaired the ship.";
    }
    
    return String.format("We got bombarded by asteroids, doing %d damage to the shields", 
                          shieldDamage);
  }

}
