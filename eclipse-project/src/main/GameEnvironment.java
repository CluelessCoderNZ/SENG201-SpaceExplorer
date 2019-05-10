package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameEnvironment {
  
  private Planet currentPlanet;
  private List<Planet> planetList;
  private CrewState crewState;
  private int daysPassed = 0;
  private int maxDays;
  
  public void setCrewState(CrewState state) {
    crewState = state;
  }
  
  public CrewState getCrewState() {
    return crewState;
  }
  
  public void setNumDays(int days) {
    maxDays = days;
  }
  
  public int getCurrentDay() {
    return daysPassed;
  }
  
  public void setPlanets(List<Planet> planets) {
    planetList = planets;
  }
  
  public List<Planet> getPlanets() {
    return planetList;
  }
  
  public void setupGame() {
    GameSetup setup = new GameSetup(this);
  }
  
  public void finishSetup(GameSetup setup) {
    scatterParts();
  }
  
  /**
   * scatters the ship parts among the planets.
   */
  public void scatterParts() {
    Collections.shuffle(planetList);
    for (int i = 0; i <= maxDays * 2 / 3 && i < planetList.size(); i++) {
      planetList.get(i).setPart(new ShipPart());
    }
    Collections.shuffle(planetList);
  }

  
  public void moveToPlanet(Planet planet, CrewMember pilot, CrewMember coPilot) {
    
  }
  
  
  
  public static void main(String[] args) {
    GameEnvironment env = new GameEnvironment();
    env.setupGame();
  }

}