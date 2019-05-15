package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JFormattedTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class GameSetup {
  
  private static final int MIN_CREW = 2;
  private static final int MAX_CREW = 4;
  private static final int MIN_DAYS = 3;
  private static final int MAX_DAYS = 10;
  private static final int MAX_TEXT_SIZE = 20;
  private static final boolean useCl = false;
  
  
  private List<CrewMember> crewMembers = new ArrayList<CrewMember>();
  private Ship ship = null;
  private JList<CrewMember> crewMembersList;
  private JFrame frame;
  private GameEnvironment env;
  private JTextField shipNameField;
  private JTextField txtName;
  private JLabel lblAddError;
  
  
  
  /**
   * GameSetup constructor.
   * @param env the GameEnvironment being used to store the game's state
   */
  public GameSetup(GameEnvironment env) {
    this.env = env;
    buildSolarSystem();
    if (useCl) {
      commandLine();
    } else {
      initializeWindow();
      frame.setVisible(true);
    }
  }
  
  /**
   * performs final setup steps (creating the crewState and applying bonuses).
   */
  public void finishedSetup() {
    setUpCrewState();
    env.finishSetup(this);
  }
  
  /**
   * closes the GameSetup Swing window.
   */
  public void closeWindow() {
    if (!useCl) {
      frame.dispose();
    }
  }
  
  /**
   * initialises the javadoc window.
   * @wbp.parser.entryPoint
   */
  private void initializeWindow() {
    frame = new JFrame();
    frame.setBounds(100, 100, 735, 592);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    
    JLabel lblWelcome = new JLabel("Welcome to SpaceExplorer");
    lblWelcome.setBounds(233, 6, 181, 16);
    frame.getContentPane().add(lblWelcome);
    
    JLabel lblGameLength = new JLabel("Game Length (Days)");
    lblGameLength.setBounds(137, 44, 130, 56);
    frame.getContentPane().add(lblGameLength);
    
    JLabel lblShipName = new JLabel("Ship Name");
    lblShipName.setBounds(137, 112, 130, 16);
    frame.getContentPane().add(lblShipName);
    
    JLabel lblCrewMembers = new JLabel("Crew Members");
    lblCrewMembers.setBounds(137, 161, 130, 16);
    frame.getContentPane().add(lblCrewMembers);
    
    JSlider slider = new JSlider();
    slider.setSnapToTicks(true);
    slider.setPaintLabels(true);
    slider.setPaintTicks(true);
    slider.setMinimum(MIN_DAYS);
    slider.setMaximum(MAX_DAYS);
    slider.setBounds(300, 44, 190, 56);
    frame.getContentPane().add(slider);
    
    Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
    for (int i = MIN_DAYS; i <= MAX_DAYS; i++) {
      position.put(i, new JLabel(Integer.toString(i)));
    }
    slider.setLabelTable(position);
    
    shipNameField = new JTextField();
    shipNameField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isAlphabetic(c) || shipNameField.getText().length() > MAX_TEXT_SIZE) {
          e.consume();
        }
      }
    });
    shipNameField.setBounds(310, 107, 180, 26);
    frame.getContentPane().add(shipNameField);
    shipNameField.setColumns(10);
    
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(177, 300, 331, 86);
    frame.getContentPane().add(scrollPane);
    
    DefaultListModel<CrewMember> listModel = new DefaultListModel<CrewMember>();
    
    JButton btnDeleteSelected = new JButton("Delete selected");
    btnDeleteSelected.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        listModel.removeElement(crewMembersList.getSelectedValue());
      }
    });
    btnDeleteSelected.setBounds(177, 392, 140, 29);
    frame.getContentPane().add(btnDeleteSelected);
    btnDeleteSelected.setEnabled(false);
     
    crewMembersList = new JList<CrewMember>(listModel);
    crewMembersList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if (crewMembersList.getSelectedIndex() == -1) {
          btnDeleteSelected.setEnabled(false);
        } else {
          btnDeleteSelected.setEnabled(true);
        }
      }
    });
    crewMembersList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    crewMembersList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    scrollPane.setViewportView(crewMembersList);
    
    JLabel lblName = new JLabel("Name:");
    lblName.setBounds(137, 182, 61, 16);
    frame.getContentPane().add(lblName);
    
    txtName = new JTextField();
    txtName.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isAlphabetic(c) || txtName.getText().length() > MAX_TEXT_SIZE) {
          e.consume();
        }
      }
    });
    txtName.setBounds(221, 177, 130, 26);
    frame.getContentPane().add(txtName);
    txtName.setColumns(10);
    
    JLabel lblThe = new JLabel("the");
    lblThe.setBounds(363, 182, 26, 16);
    frame.getContentPane().add(lblThe);
    
    JTextPane txtpnClassDescription = new JTextPane();
    txtpnClassDescription.setText("Class description");
    txtpnClassDescription.setBounds(173, 210, 335, 56);
    frame.getContentPane().add(txtpnClassDescription);
    
    JComboBox<String> comboBox = new JComboBox<String>();
    comboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        updateCrewTypeDescription(txtpnClassDescription, comboBox.getSelectedItem().toString());
      }
    });
    comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Investor"}));
    comboBox.setBounds(390, 178, 118, 27);
    frame.getContentPane().add(comboBox);
    updateCrewTypeDescription(txtpnClassDescription, comboBox.getSelectedItem().toString());
    
    lblAddError = new JLabel("");
    lblAddError.setBounds(523, 210, 150, 16);
    frame.getContentPane().add(lblAddError);
    
    JButton btnAddCrew = new JButton("Add");
    btnAddCrew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tryAddCrew(txtName.getText(), comboBox.getSelectedItem().toString(), listModel, lblAddError);
      }
    });
    btnAddCrew.setBounds(513, 177, 89, 29);
    frame.getContentPane().add(btnAddCrew);
    
    JLabel lblStartError = new JLabel("");
    lblStartError.setBounds(462, 490, 201, 16);
    frame.getContentPane().add(lblStartError);
    
    JButton btnStartGame = new JButton("Start Game");
    btnStartGame.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tryStartGame(shipNameField.getText(), slider.getValue(), listModel, lblStartError);
      }
    });
    btnStartGame.setBounds(504, 422, 181, 56);
    frame.getContentPane().add(btnStartGame);

  }
  
  
  private void updateCrewTypeDescription(JTextPane description, String typeString) {
    if (typeString.equals("Investor")) {
      description.setText(typeString + ": " + Investor.getClassDescription());
    } else {
      description.setText("This class has no description!");
    }
  }
  
  
  private void tryAddCrew(String name, String typeString, DefaultListModel<CrewMember> crewMembers, JLabel error) {
    error.setText("");
    if (name.equals("")) {
      error.setText("please enter a name");
    } else {
      CrewMember crewMember = CrewMemberFactory.createCrewMember(typeString, name);
      crewMembers.addElement(crewMember);
    }
  }
  
  
  private void tryStartGame(String shipName, int numDays, DefaultListModel<CrewMember> crewList, JLabel error) {
    if (shipName.equals("")) {
      error.setText("please enter a ship name");
    } else if (crewList.size() < MIN_CREW || crewList.size() > MAX_CREW) {
      error.setText("please add " + (MIN_CREW - crewList.size()) + " crew");
    } else {
      setNumDays(numDays);
      createShip(shipName);
      for (int i = 0; i < crewList.size(); i++) {
        crewMembers.add(crewList.get(i));
      }
      finishedSetup();
    }
  }
  
  /**
   * sets up the planets and outposts that players can visit in the game.
   */
  private void buildSolarSystem() {
    List<Item> galacticMedItems = new ArrayList<Item>(Arrays.asList(
        new GenericRestorationItem("Medicaid", 60, 30, 0),
        new GenericRestorationItem("Bandages", 25, 5, 0, 3),
        new GenericRestorationItem("StimuLife", 130, 65, 50),
        new GenericRestorationItem("Ham Sandwich", 20, 5, 25),
        new GenericRestorationItem("Space Candy", 20, 0, 1, 10)
    ));
    Shop galacticMeds = new Shop("Galactic Medications", galacticMedItems);
    
    List<Item> warmongerSuppliesItems = new ArrayList<Item>(Arrays.asList(
        new ShipShieldUpgradeItem("Shield Upgrade A", 30, 20),
        new ShipWeaponItem("Pea Shooter", 40, 20),
        new ShipWeaponItem("Pulsar Beam", 200, 60)
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
  
  /**
   * sets the number of days the game should last.
   * Scatters correct number of parts between the game's planets.
   * @param days number of days for the game to last
   */
  private void setNumDays(int days) {
    if (days < MIN_DAYS || days > MAX_DAYS) {
      throw new RuntimeException("invalid number of days. Please enter a number between "
          + MIN_DAYS + " and " + MAX_DAYS);
    }
    env.setNumDays(days);
    scatterParts(days);
  }
  
  /**
   * scatters n = days * 2 / 3 parts among the game's planets.
   * @param days number of days the game will last for
   */
  private void scatterParts(int days) {
    List<Planet> planets = env.getPlanets();
    for (int i = 0; i <= (days * 2) / 3 && i < planets.size(); i++) {
      env.getPlanets().get(i).setPart(new ShipPart());
    }
    Collections.shuffle(planets);
  }
  
  /**
   * applies the starting bonuses given by each crew member.
   * @param crewState the CrewState to get the crew of and apply the bonuses to
   */
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
  private void createCrewMember(int type, String name) {
    if (crewMembers.size() == MAX_CREW) {
      throw new RuntimeException("max crew size has been reached");
    }
    crewMembers.add(CrewMemberFactory.createCrewMember(type, name));
  }
  
  /**
   * creates a Ship for the crew with a given name.
   * @param name name to give the Ship
   */
  private void createShip(String name) {
    ship = new Ship(name);
  }
  
  /**
   * sets the GameEnvironment's CrewState object.
   */
  private void setUpCrewState() {
    if (crewMembers.size() < MIN_CREW) {
      throw new RuntimeException("not enough crew members created, min " + MIN_CREW + " required");
    }
    if (crewMembers.size() > MAX_CREW) {
      throw new RuntimeException("too many crew members. please restart the game.");
    }
    if (ship == null) {
      throw new RuntimeException("ship has not been created, run createShip()");
    }
    CrewState crewState = new CrewState(crewMembers, ship);
    applyCrewMemberBonuses(crewState);
    env.setCrewState(crewState);
  }
  

  /**
   * command line version of SpaceExplorer game setup window.
   */
  private void commandLine() {
    
    CommandLineParser cl = new CommandLineParser(System.out, System.in);
    
    int numDays = cl.inputInt(String.format("Game Length (%d-%d): ",
                              MIN_DAYS, MAX_DAYS), MIN_DAYS, MAX_DAYS);
    setNumDays(numDays);
    
    int crewTypeNum = 0;
    String crewName = "";
    boolean continueMakingCrew = true;
    do {
      crewTypeNum = cl.inputOptions("Select crew member type: ",
                                    "Investor -- " + Investor.getClassDescription());
      
      crewName = cl.inputString("Enter crew member's name: ", 30);
      
      createCrewMember(crewTypeNum + 1, crewName);
      
      // Display current crew
      cl.print("\nCurrent Crew:\n");
      cl.print("==============================\n");
      for (CrewMember member : crewMembers) {
        cl.print("  + " + member.getFullTitle() + "\n");
      }
      cl.print("==============================\n\n");
      
      // Ask to continue if min or max hasn't been reached
      if (crewMembers.size() >= MIN_CREW) {
        if (crewMembers.size() < MAX_CREW) {
          continueMakingCrew = cl.inputBoolean("Create another crew member? (Y/N): ");
        } else {
          cl.print("Max number of crew member reached.\n");
          continueMakingCrew = false;
        }
      }
      
    } while (continueMakingCrew);
    
    String shipName = cl.inputString("Enter crew's ship name: ", 30);
    createShip(shipName);

    finishedSetup();
  }
}
