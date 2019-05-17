package main;

public class PlagueCure extends ConsumableItem {
  boolean beenUsed = false;

  public PlagueCure(String name, int value) {
    super(name, value);
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
