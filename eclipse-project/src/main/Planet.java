package main;

public class Planet {
  private ShipPart part = null;
  private Shop shop = null;
  private String name = null;
  
  
  public Planet(String name) {
    this.name = name;
  }
  
  /**
   * sets the Shop visited when going to the Planet.
   * @param shop the Shop object on this Planet
   */
  public void setShop(Shop shop) {
    if (this.shop != null) {
      throw new IllegalArgumentException("Planet already has a Shop");
    }
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
   * @return the planet's ship part
   */
  public ShipPart removeShipPart() {
    ShipPart returnPart = part;
    part = null;
    return returnPart;
  }
  
}
