package main;

import java.util.ArrayList;

public class CrewState {
  
  private int funds = 0;
  private ArrayList<Item> inventory = new ArrayList<Item>();
  private ArrayList<CrewMember> crew;
  private Ship ship = null;
  
  
  public CrewState(ArrayList<CrewMember> crew, Ship ship) {
    this.crew = crew;
    this.ship = ship;
  }
  
  public CrewState(ArrayList<CrewMember> crew, Ship ship, int funds) {
    this.crew = crew;
    this.ship = ship;
    this.funds = funds;
  }
  
  public CrewState(ArrayList<CrewMember> crew, Ship ship, int funds, ArrayList<Item> inventory) {
    this.crew = crew;
    this.ship = ship;
    this.funds = funds;
    this.inventory = inventory;
  }

}
