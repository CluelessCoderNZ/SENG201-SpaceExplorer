package items;

import crew.CrewMember;
import crew.CrewMemberEffect;

/**
 * ConsumableItem subclass that has the effect of removing the
 * PLAGUED effect from a CrewMember.
 */
public class PlagueCure extends ConsumableItem {
  boolean beenUsed = false;

  /**
   * constructor creates a new PlagueCure with a given name and value.
   * @param name the name to give the PlagueCure
   * @param value the value of the PlagueCure object in funds as an int
   */
  public PlagueCure(String name, int value) {
    super(name, value);
    setDescription("A coffin would have been cheaper.");
  }

  @Override
  public void applyEffects(CrewMember crew) {
    crew.removeEffect(CrewMemberEffect.PLAGUED);
    beenUsed = true;
  }
  
  @Override
  public Item copy() {
    PlagueCure item = new PlagueCure(this.getName(), this.getValue());
    item.beenUsed = this.beenUsed;
    
    return item;
  }

  @Override
  public int getRemainingUses() {
    if (beenUsed) {
      return 0;
    } else {
      return 1;
    }
  }
  
  @Override
  public String getEffectsString() {
    return "Cures SpacePlague";
  }

}
