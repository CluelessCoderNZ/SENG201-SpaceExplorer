package crew;

/**
 * the CrewMember subclass Robot cannot have the CrewMemberEffects
 * HUNGRY, TIRED, or PLAGUED. Has a maximum of only 1 action point.
 */
public class Robot extends CrewMember {

  /**
   * creates a Robot with the given name.
   * @param name name for the Robot to display.
   */
  public Robot(String name) {
    super(name, "Robot", 120, 0, 0, 1);
  }
  
  /**
   * static method to return the description of the special attributes of the Robot class.
   * @return description of the special attributes of the Robot class.
   */
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
  public String toString() {
    String output = getFullTitle() + " ("
        + String.format("HP: %d/%d, ", getHealth(), getMaxHealth())
        + String.format("AP: %d/%d)", getActionPoints(), getMaxActionPoints());
    
    for (CrewMemberEffect effect : activeEffects) {
      output += " " + effect.name();
    }
    
    return output;
  }

}
