package main;

public class Planet {
  private ShipPart part = null;
  private Shop shop;
  private String name;
  
  
  public Planet(String name, Shop shop) {
    this.name = name;
    this.shop = shop;
  }
  
  /**
   * sets the ShipPart found on this Planet.
   * @param part the ShipPart found on this planet
   */
  public void setPart(ShipPart part) {
    if (hasShipPart()) {
      throw new IllegalArgumentException("Planet already has a ShipPart");
    }
    this.part = part;
  }
  
  public String getName() {
    return name;
  }
  
  public Shop getShop() {
    return shop;
  }
  
  
  public boolean hasShipPart() {
    return part != null;
  }
  
  /**
   * removes and returns the ShipPart stored on the Planet.
   * @return the planet's ship part, null if no part is on the planet
   */
  public ShipPart removeShipPart() {
    ShipPart returnPart = part;
    part = null;
    return returnPart;
  }
  
}
