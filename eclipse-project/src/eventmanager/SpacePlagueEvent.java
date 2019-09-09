package eventmanager;

import crew.CrewMember;
import crew.CrewMemberEffect;
import crew.Robot;

import java.util.Random;

import main.GameEnvironment;

/**
 * SpacePlagueEvent gives a random crew member the PLAGUED effect and a random amount of damage.
 * A crew member must be eligible(not plagued, not dead, not robot) for space plague
 * for the event to occur otherwise it does nothing.
 */
public class SpacePlagueEvent extends GameEvent {
  static final int MAX_DAMAGE = 20;
  static final int MIN_DAMAGE = 10;
  
  private CrewMember target = null;
  private int damage = 0; 

  @Override
  public GameEvent createEvent(Random randomGenerator, GameEnvironment env) {
    SpacePlagueEvent event = new SpacePlagueEvent();
    
    event.damage = randomGenerator.nextInt(MAX_DAMAGE - MIN_DAMAGE) + MIN_DAMAGE;
    
    // Select random target without PLAGUED effect.
    int offset = randomGenerator.nextInt(env.getCrewState().getCrew().size());
    for (int i = 0; i < env.getCrewState().getCrew().size(); i++) {
      int index = (offset + i) % env.getCrewState().getCrew().size();
      CrewMember crewmember = env.getCrewState().getCrew().get(index);
      
      if (!crewmember.hasEffect(CrewMemberEffect.PLAGUED) 
          && !crewmember.isDead() 
          && !(crewmember instanceof Robot)) {
        event.target = crewmember;
        break;
      }
    }
    
    return event;
  }

  @Override
  public void applyEvent(GameEnvironment env) {
    if (target != null) {
      target.addEffect(CrewMemberEffect.PLAGUED);
      target.changeHealth(damage);
    }
  }
  
  public boolean canEventApply(GameEnvironment env) {
    return target != null;
  }

  @Override
  public String getEventMessage() {
    return String.format("%s contracted SpacePlague. "
                       + "You should start looking for a cure or a coffin.", 
                         target.getName());
  }

}
