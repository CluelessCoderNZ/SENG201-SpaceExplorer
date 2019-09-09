package items;

/**
 * Item subclass that represents the ShipParts that players must locate to win the game.
 */
public class ShipPart extends Item {

  /**
   * creates a new ShipPart object.
   */
  public ShipPart() {
    super("Ship part", 10000);
  }
  
  @Override
  public Item copy() {
    ShipPart item = new ShipPart();
    return item;
  }
}
