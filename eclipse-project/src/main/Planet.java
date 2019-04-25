package main;

public class Planet {
  private ShipPart part = null;
  private Shop shop = null;
  private String name = null;
  
  
  public Planet(String name) {
    this.name = name;
  }
  
  public void setShop(Shop shop) {
    this.shop = shop;
  }
  
  public void setPart(ShipPart part) {
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
  
  public ShipPart removeShipPart() {
    ShipPart returnPart = part;
    part = null;
    return returnPart;
  }
  
}
