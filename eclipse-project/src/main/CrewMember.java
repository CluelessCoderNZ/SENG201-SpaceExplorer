package main;

import java.util.EnumSet;


public class CrewMember {
  private static final int HUNGER_THRESHOLD = 30;
  private static final int TIRED_THRESHOLD = 30;
  
  /*
  ===============
      MEMBERS
  ===============
  */
  private String name;
  private String title = "Average Joe";
  
  private int health;
  private int maxHealth = 100;
  private int fullness;
  private int maxFullness = 100;
  private int restedness;
  private int maxRestedness = 100;
  
  private int actionPoints;
  private int maxActionPoints = 2;
  
  private EnumSet<CrewMemberEffect> activeEffects = EnumSet.noneOf(CrewMemberEffect.class);

  /*
  ===============
     SET & GET   
  ===============
  */
  
  /**
   * Default constructor sets crew member attributes to default values.
   * @param name crewmember's name
   * @param title describes the vanity title
   */
  public CrewMember(String name, String title) {
    this.name = name;
    this.title = title;
    refillAllStats();
  }
  
  /**
   * Extended constructor allows custom values for crew member attributes.
   * @param name crewmember's name
   * @param title describes the vanity title
   * @param maxHealth maximum health
   * @param maxFullness maximum fullness
   * @param maxRestedness maximum restedness
   * @param maxActionPoints maximum actions points
   */
  public CrewMember(String name, String title, int maxHealth, 
                    int maxFullness, int maxRestedness, int maxActionPoints) {
    this.name = name;
    this.title = title;
    this.maxHealth = Math.max(maxHealth, 0);
    this.maxFullness = Math.max(maxFullness, 0);
    this.maxRestedness = Math.max(maxRestedness, 0);
    this.maxActionPoints = Math.max(maxActionPoints, 0);
    refillAllStats();
  }
  
  public String getName() {
    return name;
  }
  
  public String getTitle() {
    return title;
  }
  
  public String getFullTitle() {
    return String.format("%s the %s", name, title);
  }
  
  public int getMaxHealth() {
    return maxHealth;
  }
  
  public int getHealth() {
    return health;
  }
  
  public void setHealth(int health) {
    this.health = Math.min(maxHealth, Math.max(0, health));
  }
  
  public int getMaxFullness() {
    return maxFullness;
  }
  
  public int getFullness() {
    return fullness;
  }
  
  /**
   * Sets fullness with range 0 to maxFullness. 
   * If fullness is then below HUNGER_THRESHOLD the effect HUNGRY is applied to crew member.
   * @param fullness The new fullness value.
   */
  public void setFullness(int fullness) {
    this.fullness = Math.min(maxFullness, Math.max(0, fullness));
    
    if (this.fullness < HUNGER_THRESHOLD) {
      addEffect(CrewMemberEffect.HUNGRY);
    } else {
      removeEffect(CrewMemberEffect.HUNGRY);
    }
  }
  
  public int getMaxRestedness() {
    return maxRestedness;
  }
  
  public int getRestedness() {
    return restedness;
  }
  
  /**
   * Sets restedness with range 0 to maxRestedness. 
   * If restedness is then below TIRED_THRESHOLD the effect TIRED is applied to crew member.
   * @param restedness The new restedness value.
   */
  public void setRestedness(int restedness) {
    this.restedness = Math.min(maxRestedness, Math.max(0, restedness));
    
    if (this.restedness < TIRED_THRESHOLD) {
      addEffect(CrewMemberEffect.TIRED);
    } else {
      removeEffect(CrewMemberEffect.TIRED);
    }
  }
  
  public int getActionPoints() {
    return actionPoints;
  }
  
  public int getMaxActionPoints() {
    return maxActionPoints;
  }

  /*
  ===============
     METHODS
  ===============
  */
  
  /**
   * Activates items effects on the crew member.
   * @param item activated item
   */
  public void useItem(ConsumableItem item) {
    item.applyEffects(this);
  }
  
  /**
   * Resets actions back to maximum value.
   */
  public void resetActions() {
    actionPoints = getMaxActionPoints();
  }
  
  /**
   * reduces action points by one.
   */
  public void useAction() {
    actionPoints = Math.max(actionPoints - 1, 0);
  }

  public boolean hasActionAvaliable() {
    return actionPoints > 0;
  }

  public boolean hasEffect(CrewMemberEffect effect) {
    return activeEffects.contains(effect);
  }
  
  public void addEffect(CrewMemberEffect effect) {
    activeEffects.add(effect);
  }
  
  public void removeEffect(CrewMemberEffect effect) {
    activeEffects.remove(effect);
  }
  
  private void refillAllStats() {
    setHealth(getMaxHealth());
    setFullness(getMaxFullness());
    setRestedness(getMaxRestedness());
    resetActions();
  }
  
  public void applyStartBonuses(CrewState crewState) { }
}
