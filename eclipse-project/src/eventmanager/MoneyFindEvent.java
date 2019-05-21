package eventmanager;

import java.util.Random;

import main.GameEnvironment;

/**
 * MoneyFindEvent gives a random amount of money to the crew.
 */
public class MoneyFindEvent extends GameEvent {
  static final int MIN_MONEY_FOUND = 5;
  static final int MAX_MONEY_FOUND = 50;
  
  private int moneyFound;


  @Override
  public GameEvent createEvent(Random randomGenerator, GameEnvironment env) {
    MoneyFindEvent event = new MoneyFindEvent();
    
    event.moneyFound = MIN_MONEY_FOUND + randomGenerator.nextInt(MAX_MONEY_FOUND - MIN_MONEY_FOUND);
    
    return event;
  }

  @Override
  public void applyEvent(GameEnvironment env) {
    env.getCrewState().addFunds(moneyFound);
    env.addScore(50);
  }

  @Override
  public String getEventMessage() {
    return "You found $" + moneyFound;
  }

}
