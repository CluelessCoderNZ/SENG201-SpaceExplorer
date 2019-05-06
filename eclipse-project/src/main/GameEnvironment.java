package main;

import java.util.ArrayList;

public class GameEnvironment {
  
  private Planet currentPlanet;
  private ArrayList<Planet> planetList;
  //private GameEventManager eventManager;
  private CrewState crewState;
  private int daysPassed = 0;
  private int maxDays;
  
  /**
   * creates a new GameEnvironment for a game lasting days days, with a given crew and planet list.
   * @param days number of days for the game to last
   * @param crewState the CrewState object used to hold information about the crew
   * @param planetList the list of planets that players can travel to where the first entry is the starting planet
   */
  public GameEnvironment(int days, CrewState crewState,
      /*GameEventManager eventManager,*/ ArrayList<Planet> planetList) {
    maxDays = days;
    this.crewState = crewState;
    this.planetList = planetList;
    if (planetList.size() == 0) {
      throw new IllegalArgumentException("game needs at least one planet");
    }
    currentPlanet = planetList.get(0);
  }

  
  public void moveToPlanet(Planet planet, CrewMember pilot, CrewMember coPilot) {
    
  }
  
  
  
  public static void main(String[] args) {
    System.out.println("Hello World!");
  }

}