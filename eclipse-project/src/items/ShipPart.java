package items;

public class ShipPart extends Item {

  public ShipPart() {
    super("Ship part", 10000);
  }
  
  public Item copy() {
    ShipPart item = new ShipPart();
    return item;
  }
}
