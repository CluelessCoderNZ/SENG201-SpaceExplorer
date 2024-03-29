package crew;

import items.ConsumableItem;

import java.util.EnumSet;

import main.Observable;

/**
 * class containing information about and methods to affect the state of a crew member.
 */
public class CrewMember extends Observable {
  protected static final int HUNGER_THRESHOLD = 30;
  protected static final int TIRED_THRESHOLD = 30;
  
  protected static final int HUNGER_DAMAGE = 30;
  protected static final int TIRED_FULL_REDUCTION = 40;
  protected static final int PLAGUED_DAMAGE = 30;
  
  protected static final int DAILY_REST_REDUCTION = 30;
  protected static final int DAILY_FULL_REDUCTION = 20;
  
  protected static final int DEFAULT_SHIP_REPAIR = 50;
  protected static final int DEFAULT_SLEEP_RESTORE = 50;
  
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
  
  protected EnumSet<CrewMemberEffect> activeEffects = EnumSet.noneOf(CrewMemberEffect.class);

  
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
   * @param maxHealth non negative maximum health value
   * @param maxFullness maximum fullness
   * @param maxRestedness maximum restedness
   * @param maxActionPoints maximum actions points
   */
  public CrewMember(String name, String title, int maxHealth, 
                    int maxFullness, int maxRestedness, int maxActionPoints) {
    if (maxHealth < 0 || maxFullness < 0 || maxRestedness < 0 || maxActionPoints < 0) {
      throw new IllegalArgumentException("max stat values cannot be negative.");
    }
    this.name = name;
    this.title = title;
    this.maxHealth = Math.max(maxHealth, 0);
    this.maxFullness = Math.max(maxFullness, 0);
    this.maxRestedness = Math.max(maxRestedness, 0);
    this.maxActionPoints = Math.max(maxActionPoints, 0);
    refillAllStats();
  }
  
  
  /*
  ===============
     SET & GET   
  ===============
  */
  
  /**
   * returns the name of the CrewMember.
   * @return the crew member's name as a string
   */
  public String getName() {
    return name;
  }
  
  /**
   * returns the title of the CrewMember.
   * @return the crew member's title as a string
   */
  public String getTitle() {
    return title;
  }
  
  /**
   * returns the name and title of the CrewMember.
   * @return the crew member's title as a string form {name} the {title}
   */
  public String getFullTitle() {
    return getName() + " the " + getTitle();
  }
  
  /**
   * returns the CrewMember's current health level.
   * @return the crew member's current health level as an integer
   */
  public int getHealth() {
    return health;
  }
  
  /**
   * returns the CrewMember's max health level.
   * @return the crew member's max health level as an integer
   */
  public int getMaxHealth() {
    return maxHealth;
  }
  
  /**
   * Sets the crew member's health with range 0 to maxFullness.
   * @param health the new health value
   */
  public void setHealth(int health) {
    if (!isDead()) {
      this.health = Math.min(maxHealth, Math.max(0, health));
      
      if (this.health == 0) {
        addEffect(CrewMemberEffect.DEAD);
      }
    }
  }
  
  public void changeHealth(int amount) {
    setHealth(getHealth() + amount);
  }
  
  /**
   * returns the CrewMember's current fullness.
   * @return the crew member's current fullness as an integer
   */
  public int getFullness() {
    return fullness;
  }
  
  /**
   * returns the CrewMember's max fullness.
   * @return the crew member's max fullness as an integer
   */
  public int getMaxFullness() {
    return maxFullness;
  }
  
  /**
   * Sets fullness with range 0 to maxFullness. 
   * If fullness is then below HUNGER_THRESHOLD the effect HUNGRY is applied to crew member.
   * @param fullness The new fullness value
   */
  public void setFullness(int fullness) {
    if (!isDead()) {
      this.fullness = Math.min(maxFullness, Math.max(0, fullness));
      
      if (this.fullness < HUNGER_THRESHOLD) {
        addEffect(CrewMemberEffect.HUNGRY);
      } else {
        removeEffect(CrewMemberEffect.HUNGRY);
      }
    }
  }
  
  public void changeFullness(int amount) {
    setFullness(getFullness() + amount);
  }
  
  /**
   * returns the CrewMember's current restedness.
   * @return the crew member's current restedness as an integer
   */
  public int getRestedness() {
    return restedness;
  }
  
  /**
   * returns the CrewMember's max restedness.
   * @return the crew member's max restedness as an integer
   */
  public int getMaxRestedness() {
    return maxRestedness;
  }
  
  /**
   * Sets restedness with range 0 to maxRestedness. 
   * If restedness is then below TIRED_THRESHOLD the effect TIRED is applied to crew member.
   * @param restedness The new restedness value.
   */
  public void setRestedness(int restedness) {
    if (!isDead()) {
      this.restedness = Math.min(maxRestedness, Math.max(0, restedness));
      
      if (this.restedness < TIRED_THRESHOLD) {
        addEffect(CrewMemberEffect.TIRED);
      } else {
        removeEffect(CrewMemberEffect.TIRED);
      }
    }
  }
  
  /**
   * change the CrewMember's restedness by a given amount.
   * @param amount the amount to change the CrewMember's restedness by
   */
  public void changeRestedness(int amount) {
    setRestedness(getRestedness() + amount);
  }
  
  /**
   * returns the CrewMember's current action points.
   * @return the crew member's current action points as an integer
   */
  public int getActionPoints() {
    return actionPoints;
  }
  
  /**
   * returns the CrewMember's max action points.
   * @return the crew member's max action points as an integer
   */
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
    if (canAct()) {
      item.applyEffects(this);
      useAction();
    }
  }
  
  /**
   * Restores DEFAULT_SHIP_REPAIR to ships shield.
   * @param ship to be affected.
   */
  public void repairShip(Ship ship) {
    if (canAct()) {
      ship.increaseShield(DEFAULT_SHIP_REPAIR);
      useAction();
    }
  }
  
  /**
   * Restores DEFAULT_SLEEP_RESTORE if has actions available.
   */
  public void sleep() {
    if (canAct()) {
      changeRestedness(DEFAULT_SLEEP_RESTORE);
      useAction();
    }
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

  /**
   * checks if a crew member is currently being affected by a given effect.
   * @param effect the effect to check the crew member for
   * @return true if the crew member has the given effect, false otherwise
   */
  public boolean hasEffect(CrewMemberEffect effect) {
    return activeEffects.contains(effect);
  }
  
  /**
   * adds a CrewMemberEffect to the crew member.
   * @param effect the effect to add
   */
  public void addEffect(CrewMemberEffect effect) {
    if (!hasEffect(effect)) {
      activeEffects.add(effect);
      setChanged();
      notifyObservers(new CrewEffectChangeObserverEvent(true, effect));
    }
  }
  
  /**
   * removes an active CrewMemberEffect from the crew member.
   * @param effect the active effect to remove
   */
  public void removeEffect(CrewMemberEffect effect) {
    if (hasEffect(effect)) {
      activeEffects.remove(effect);
      setChanged();
      notifyObservers(new CrewEffectChangeObserverEvent(false, effect));
    }
  }
  
  /**
   * returns True if the CrewMember is dead.
   * @return wether the CrewMember is dead
   */
  public boolean isDead() {
    return hasEffect(CrewMemberEffect.DEAD);
  }
  
  
  /**
   * sets all of the CrewMembers stats to their max values.
   */
  private void refillAllStats() {
    setHealth(getMaxHealth());
    setFullness(getMaxFullness());
    setRestedness(getMaxRestedness());
    resetActions();
  }
  
  /**
   * returns whether the CrewMember has an action point remaining.
   * @return True if the CrewMember has at least one action point remaining.
   */
  public boolean canAct() {
    return canAct(1);
  }
  
  /**
   * returns whether the CrewMember has the given number of action points remaining.
   * @param actionPoints the number of action points to check for.
   * @return True if the CrewMember has at least actionPoints action points remaining.
   */
  public boolean canAct(int actionPoints) {
    return getActionPoints() >= actionPoints && !isDead();
  }
  
  /**
   * Returns formatted text representation of crew member stats.
   * @return formatted text representation of crew member stats.
   */
  public String getFormattedStatusString() {
    String output = getFullTitle() + ":\n";
    if (isDead()) {
      output += "  He's dead Jim!";
    } else {
      output += String.format("  HP: %d/%d \n", health, maxHealth)
          + String.format("  Fullness: %d/%d \n", fullness, maxFullness)
          + String.format("  Restedness: %d/%d \n", restedness, maxRestedness)
          + String.format("  AP: %d/%d \n", actionPoints, maxActionPoints);
      
      output += "  Status effects:";
      if (activeEffects.size() == 0) {
        output += " None"; 
      }
      for (CrewMemberEffect effect : activeEffects) {
        output += " " + effect.name();
      }
    }
    
    return output;
  }
  
  /**
   * returns a string representation of the CrewMember.
   * @return String representation
   */
  @Override
  public String toString() {
    String output = getFullTitle() + " ("
        + String.format("HP: %d/%d, ", health, maxHealth)
        + String.format("Fullness: %d/%d, ", fullness, maxFullness)
        + String.format("Restedness: %d/%d, ", restedness, maxRestedness)
        + String.format("AP: %d/%d)", actionPoints, maxActionPoints);
    
    for (CrewMemberEffect effect : activeEffects) {
      output += " " + effect.name();
    }
    
    return output;
  }
  
  /**
   * applies the starting bonuses given by a crew member.
   * @param crewState the CrewState object to apply the effects to
   */
  public void applyStartBonuses(CrewState crewState) { }
  
  /**
   * Applies new day start effects. By default this is stat changes by different effects.
   * However this can be overloaded to include custom character effects.
   */
  public void applyDayStartEffects() {
    if (!isDead()) {
      if (hasEffect(CrewMemberEffect.HUNGRY)) {
        changeHealth(-HUNGER_DAMAGE);
      }
      
      if (hasEffect(CrewMemberEffect.TIRED)) {
        changeFullness(-TIRED_FULL_REDUCTION);
      }
      
      if (hasEffect(CrewMemberEffect.HUNGRY) || hasEffect(CrewMemberEffect.TIRED)) {
        actionPoints = maxActionPoints / 2;
      } else {
        resetActions();
      }
      
      if (hasEffect(CrewMemberEffect.PLAGUED)) {
        changeHealth(-PLAGUED_DAMAGE);
      }
      
      changeRestedness(-DAILY_REST_REDUCTION);
      changeFullness(-DAILY_FULL_REDUCTION);
    }
  }
}
