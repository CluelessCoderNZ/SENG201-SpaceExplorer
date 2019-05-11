package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GameSetup {
  
  private static final int MIN_CREW = 2;
  private static final int MAX_CREW = 4;
  private static final int MIN_DAYS = 3;
  private static final int MAX_DAYS = 10;
  private static Scanner reader = new Scanner(System.in);
  
  
  private List<CrewMember> crewMembers = new ArrayList<CrewMember>();
  private Ship ship = null;
  private JFrame frame;
  private GameEnvironment env;
  
  
  
  /**
   * GameSetup constructor.
   * @param env the GameEnvironment being used to store the game's state
   */
  public GameSetup(GameEnvironment env) {
    this.env = env;
    buildSolarSystem();
    //commandLine();
    initializeWindow();
    frame.setVisible(true);
  }
  
  public void closeWindow() {
    frame.dispose();
  }
  
  private void initializeWindow() {
    frame = new JFrame();
    frame.setBounds(100, 100, 636, 468);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    
    JLabel lblWelcome = new JLabel("Welcome to SpaceExplorer");
    lblWelcome.setBounds(6, 6, 181, 16);
    frame.getContentPane().add(lblWelcome);
  }
  
  /**
   * sets up the planets and outposts that players can visit in the game.
   */
  public void buildSolarSystem() {
    List<Item> galacticMedItems = new ArrayList<Item>(Arrays.asList(
        new GenericRestorationItem("Medicaid", 60, 30, 0),
        new GenericRestorationItem("Bandages", 25, 5, 0, 3),
        new GenericRestorationItem("StimuLife", 130, 65, 50),
        new GenericRestorationItem("Ham Sandwich", 20, 5, 25),
        new GenericRestorationItem("Space Candy", 20, 0, 1, 10)
    ));
    Shop galacticMeds = new Shop("Galactic Medications", galacticMedItems);
    
    List<Item> warmongerSuppliesItems = new ArrayList<Item>(Arrays.asList(
        new Item("Shield Upgrade A", 30),
        new Item("Photon Cannons", 100),
        new Item("Pulsar Beam", 200)
    ));
    Shop warmongerSupplies = new Shop("Warmonger Supplies", warmongerSuppliesItems);
    
    List<Item> bartsBuffetItems = new ArrayList<Item>(Arrays.asList(
        new GenericRestorationItem("Hamburger", 40, 10, 50),
        new GenericRestorationItem("Scrambled Eggs", 20, 0, 20),
        new Item("Golden Spork", 100),
        new GenericRestorationItem("Antimatter Brownies", 40, 0, -30, 3),
        new GenericRestorationItem("All You Can Eat", 300, 0, 50, 10)
    ));
    Shop bartsBuffet = new Shop("Bart's Buffet", bartsBuffetItems);
    
    Planet izanaki = new Planet("Izanaki", galacticMeds);
    Planet newQuebec = new Planet("New Quebec", warmongerSupplies);
    Planet earth2 = new Planet("Earth II", bartsBuffet);
    Planet keziah = new Planet("Keziah", warmongerSupplies);
    Planet sorinAlpha = new Planet("Sorin Alpha", bartsBuffet);
    Planet nordlingen = new Planet("Nordlingen", warmongerSupplies);
    Planet archimedra = new Planet("Archimedra", galacticMeds);
    Planet corolina = new Planet("Corolina", galacticMeds);
    
    List<Planet> planets = new ArrayList<Planet>(Arrays.asList(izanaki, newQuebec, earth2, keziah,
        sorinAlpha, nordlingen, archimedra, corolina));
    env.setPlanets(planets);
  }
    
  public void createShip(String name) {
    ship = new Ship(name);
  }
  
  public void setPlanets(List<Planet> planets) {
    env.setPlanets(planets);
  }
  
  /**
   * sets the number of days the game should last.
   * Scatters correct number of parts between the game's planets.
   * @param days number of days for the game to last
   */
  public void setNumDays(int days) {
    if (days < MIN_DAYS || days > MAX_DAYS) {
      throw new RuntimeException("invalid number of days. Please enter a number between "
          + MIN_DAYS + " and " + MAX_DAYS);
    }
    env.setNumDays(days);
    scatterParts(days);
  }
  
  public void finishedSetup() {
    setCrewState();
    applyCrewMemberBonuses(env.getCrewState());
    env.finishSetup(this);
  }
  
  /**
   * scatters n = days * 2 / 3 parts among the game's planets.
   * @param days number of days the game will last for
   */
  public void scatterParts(int days) {
    List<Planet> planets = env.getPlanets();
    Collections.shuffle(planets);
    for (int i = 0; i <= (days * 2) / 3 && i < planets.size(); i++) {
      env.getPlanets().get(i).setPart(new ShipPart());
    }
    Collections.shuffle(planets);
  }
  
  public void applyCrewMemberBonuses(CrewState crewState) {
    for (CrewMember crew : crewState.getCrew()) {
      crew.applyStartBonuses(crewState);
    }
  }
  
  /**
   * creates a crew member with given parameters and adds to the crew.
   * @param name the name the new CrewMember should have
   * @param title the title the new CrewMember should have
   * @throws RuntimeException throws exception if trying to add member to a full crew
   */
  public void createCrewMember(int type, String name, String title) {
    if (crewMembers.size() == MAX_CREW) {
      throw new RuntimeException("max crew size has been reached");
    }
    crewMembers.add(CrewMemberFactory.createCrewMember(type, name, title));
  }
  
  
  /**
   * sets the GameEnvironment's CrewState object.
   */
  public void setCrewState() {
    if (crewMembers.size() < MIN_CREW) {
      throw new RuntimeException("not enough crew members created, min " + MIN_CREW + " required");
    }
    if (crewMembers.size() > MAX_CREW) {
      throw new RuntimeException("too many crew members. please restart the game.");
    }
    if (ship == null) {
      throw new RuntimeException("ship has not been created, run createShip()");
    }
    env.setCrewState(new CrewState(crewMembers, ship));
  }
  

  /**
   * command line version of SpaceExplorer game setup window.
   */
  public void commandLine() {
    
    System.out.println(String.format("Enter a number of days. min %d. max %d:",
        MIN_DAYS, MAX_DAYS));
    int numDays = reader.nextInt();
    reader.nextLine(); // consumes the newline character after the integer
    setNumDays(numDays);
    
    int crewTypeNum = 0;
    String crewName = "";
    String title = "";
    do {
      System.out.println(String.format("\ncurrent crew count: %d. min %d. max %d. ",
          crewMembers.size(), MIN_CREW, MAX_CREW));
      
      System.out.println("choose a crew member type: ");
      System.out.println("     1. Investor -- " + Investor.getClassDescription());
      crewTypeNum = reader.nextInt();
      reader.nextLine();
      
      System.out.println("enter new crew member name: ");
      crewName = reader.nextLine();
      
      System.out.println("enter " + crewName + "'s title: ");
      title = reader.nextLine();
      
      createCrewMember(crewTypeNum, crewName, title);
      
      System.out.println("create another crew member? (Y/n): ");
    } while (!reader.nextLine().equals("n"));
    
    System.out.println("\nenter a name for your crew's ship: ");
    String shipName = reader.nextLine();
    createShip(shipName);

    finishedSetup();
  }
  
}
