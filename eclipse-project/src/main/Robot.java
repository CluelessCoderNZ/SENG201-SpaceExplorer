package main;

public class Robot extends CrewMember {

  public Robot(String name) {
    super(name, "Robot", 120, 0, 0, 1);
  }
  
  public static String getClassDescription() {
    return String.format("Doesn't need to eat or sleep but has 1 AP");
  }
  
  @Override
  public void addEffect(CrewMemberEffect effect) {
    if (effect != CrewMemberEffect.HUNGRY 
        && effect != CrewMemberEffect.TIRED
        && effect != CrewMemberEffect.PLAGUED) {
      super.addEffect(effect);
    }
  }
  
  @Override
  public String getStatus() {
    String output = getFullTitle() + " ("
        + String.format("HP: %d/%d, ", getHealth(), getMaxHealth())
        + String.format("AP: %d/%d)", getActionPoints(), getMaxActionPoints());
    
    for (CrewMemberEffect effect : activeEffects) {
      output += " " + effect.name();
    }
    
    return output;
  }

}
