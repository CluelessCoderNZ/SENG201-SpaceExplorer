package eventmanager;

import java.util.Random;

import main.GameEnvironment;

/**
 * An Asteroid event damages the ship in a GameEnvironment by a random scaled amount to it's shield.
 * The damage is mitigated by the ship's weapon damage.
 */
public class AsteroidEvent extends GameEvent {
  static final int MAXBASE_DAMAGE = 50;
  static final int SCALED_DAMAGE = 80;
  
  private boolean isLethalDamage = false;
  private int shieldDamage = 0;
  private int reducedDamage = 0;
  private int overallDamage = 0;

  @Override
  public GameEvent createEvent(Random randomGenerator, GameEnvironment env) {
    AsteroidEvent event = new AsteroidEvent();
    
    double shieldPrecent = (double)env.getCrewState().getShip().getShieldLevel() 
                                 / env.getCrewState().getShip().getMaxShieldLevel();
    
    event.shieldDamage = randomGenerator.nextInt(MAXBASE_DAMAGE);
    
    event.shieldDamage += (1.0 - shieldPrecent) * SCALED_DAMAGE;
    
    event.reducedDamage = env.getCrewState().getShip().getWeapon().getDamage();
        
    event.overallDamage = Math.max(0, event.shieldDamage - event.reducedDamage);
    
    event.isLethalDamage =  event.overallDamage > env.getCrewState().getShip().getShieldLevel();
    
    return event;
  }

  @Override
  public void applyEvent(GameEnvironment env) {
    env.getCrewState().getShip().takeDamage(overallDamage);
  }

  @Override
  public String getEventMessage() {
    if (isLethalDamage) {
      return "This is it. The asteroid are tearing through the outer hull. "
          + "To think this could of all been avoided if someone had repaired the ship.";
    }
    
    return String.format("We got bombarded by asteroids, doing %d damage to the shields. "
                         + "Our ship's weapon shot a few and protected us from %d damage", 
                          overallDamage, reducedDamage);
  }

}
