package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.ListSelectionListener;

import commandline.CommandLineParser;
import commandline.CommandLineRunner;
import crew.CrewMember;
import crew.CrewMemberFactory;
import crew.CrewState;
import crew.Engineer;
import crew.Investor;
import crew.Medic;
import crew.Robot;
import crew.Scientist;
import crew.Ship;
import crew.Student;
import items.ShipPart;

import javax.swing.event.ListSelectionEvent;

public class GameSetup {
  
  private static final int MIN_CREW = 2;
  private static final int MAX_CREW = 4;
  private static final int MIN_DAYS = 3;
  private static final int MAX_DAYS = 10;
  private static final int MAX_TEXT_SIZE = 20;
  
  private boolean useCl = false;
  
  //private List<CrewMember> crewMembers = new ArrayList<CrewMember>();
  private DefaultListModel<CrewMember> crewGuiList = new DefaultListModel<CrewMember>();
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
   * @param cl flag for if the game should start in command line mode
   */
  public GameSetup(GameEnvironment env, boolean cl) {
    this.env = env;
    this.useCl = cl;
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
  private void finishedSetup() {
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
    frame.setLocationRelativeTo(null);
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
    
    JLabel lblName = new JLabel("Name:");
    lblName.setBounds(137, 182, 61, 16);
    frame.getContentPane().add(lblName);
    
    JLabel lblThe = new JLabel("the");
    lblThe.setBounds(363, 182, 26, 16);
    frame.getContentPane().add(lblThe);
    
    JLabel lblStartError = new JLabel("");
    lblStartError.setBounds(462, 490, 201, 16);
    frame.getContentPane().add(lblStartError);
    
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
    shipNameField.setColumns(10);
    frame.getContentPane().add(shipNameField);
    
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(177, 300, 331, 86);
    frame.getContentPane().add(scrollPane);
    
    
    JButton btnDeleteSelected = new JButton("Delete selected");
    btnDeleteSelected.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        crewGuiList.removeElement(crewMembersList.getSelectedValue());
      }
    });
    btnDeleteSelected.setBounds(177, 392, 140, 29);
    frame.getContentPane().add(btnDeleteSelected);
    btnDeleteSelected.setEnabled(false);
    
    
    crewMembersList = new JList<CrewMember>(crewGuiList);
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
    
    JTextPane txtpnClassDescription = new JTextPane();
    txtpnClassDescription.setEditable(false);
    txtpnClassDescription.setText("Class description");
    txtpnClassDescription.setBounds(173, 210, 335, 56);
    frame.getContentPane().add(txtpnClassDescription);
    
    JComboBox<String> comboBox = new JComboBox<String>();
    comboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        updateCrewTypeDescription(txtpnClassDescription, comboBox.getSelectedItem().toString());
      }
    });
    comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Investor", "Medic", "Engineer", "Student", "Scientist", "Robot"}));
    comboBox.setBounds(390, 178, 118, 27);
    frame.getContentPane().add(comboBox);
    updateCrewTypeDescription(txtpnClassDescription, comboBox.getSelectedItem().toString());
    
    lblAddError = new JLabel("");
    lblAddError.setBounds(523, 210, 150, 16);
    frame.getContentPane().add(lblAddError);
    
    JButton btnAddCrew = new JButton("Add");
    btnAddCrew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tryAddCrew(txtName.getText(), comboBox.getSelectedItem().toString(), lblAddError);
      }
    });
    btnAddCrew.setBounds(513, 177, 89, 29);
    frame.getContentPane().add(btnAddCrew);
    
    JButton btnStartGame = new JButton("Start Game");
    btnStartGame.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tryStartGame(shipNameField.getText(), slider.getValue(), lblStartError);
      }
    });
    btnStartGame.setBounds(504, 422, 181, 56);
    frame.getContentPane().add(btnStartGame);

  }
  
  
  private void updateCrewTypeDescription(JTextPane description, String typeString) {
    switch (typeString) {
      case "Investor":
        description.setText(typeString + ": " + Investor.getClassDescription());
        break;
      case "Medic":
        description.setText(typeString + ": " + Medic.getClassDescription());
        break;
      case "Engineer":
        description.setText(typeString + ": " + Engineer.getClassDescription());
        break;
      case "Student":
        description.setText(typeString + ": " + Student.getClassDescription());
        break;
      case "Scientist":
        description.setText(typeString + ": " + Scientist.getClassDescription());
        break;
      case "Robot":
        description.setText(typeString + ": " + Robot.getClassDescription());
        break;
      default:
        description.setText("This class has no description!");
    }
  }
  
  
  private void tryAddCrew(String name, String typeString, JLabel error) {
    error.setText("");
    if (name.equals("")) {
      error.setText("please enter a name");
    } else if (crewGuiList.size() >= MAX_CREW) {
      error.setText("max crew size reached");
    } else {
      CrewMember crewMember = CrewMemberFactory.createCrewMember(typeString, name);
      crewGuiList.addElement(crewMember);
    }
  }
  
  
  private void tryStartGame(String shipName, int numDays, JLabel error) {
    if (shipName.equals("")) {
      error.setText("please enter a ship name");
    } else if (crewGuiList.size() < MIN_CREW || crewGuiList.size() > MAX_CREW) {
      error.setText("please add " + (MIN_CREW - crewGuiList.size()) + " crew");
    } else {
      setNumDays(numDays);
      createShip(shipName);
      finishedSetup();
    }
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
    for (int i = 0; i < (days * 2) / 3 && i < planets.size(); i++) {
      env.getPlanets().get(i).setPart(new ShipPart());
    }
    Collections.shuffle(planets, env.getRandomGenerator());
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
    if (crewGuiList.size() == MAX_CREW) {
      throw new RuntimeException("max crew size has been reached");
    }
    crewGuiList.addElement(CrewMemberFactory.createCrewMember(type, name));
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
    if (crewGuiList.size() < MIN_CREW) {
      throw new RuntimeException("not enough crew members created, min " + MIN_CREW + " required");
    }
    if (crewGuiList.size() > MAX_CREW) {
      throw new RuntimeException("too many crew members. please restart the game.");
    }
    if (ship == null) {
      throw new RuntimeException("ship has not been created, run createShip()");
    }
    List<CrewMember> crewList = new ArrayList<CrewMember>();
    for (int i = 0; i < crewGuiList.size(); i++) {
      crewList.add(crewGuiList.get(i));
    }
    CrewState crewState = new CrewState(crewList, ship);
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
                                    "Investor -- " + Investor.getClassDescription(),
                                    "Medic -- " + Medic.getClassDescription(),
                                    "Engineer -- " + Engineer.getClassDescription(),
                                    "Student -- " + Student.getClassDescription(),
                                    "Scientist -- " + Scientist.getClassDescription(),
                                    "Robot -- " + Robot.getClassDescription());
      
      crewName = cl.inputString("Enter crew member's name: ", 30);
      
      createCrewMember(crewTypeNum + 1, crewName);
      
      // Display current crew
      cl.print("\nCurrent Crew:\n");
      cl.print("==============================\n");
      for (int i = 0; i < crewGuiList.size(); i++) {
        cl.print("  + " + crewGuiList.get(i).getFullTitle() + "\n");
      }
      cl.print("==============================\n\n");
      
      // Ask to continue if min or max hasn't been reached
      if (crewGuiList.size() >= MIN_CREW) {
        if (crewGuiList.size() < MAX_CREW) {
          continueMakingCrew = cl.inputBoolean("Create another crew member? (Y/N): ");
        } else {
          cl.print("Max number of crew member reached.\n");
          continueMakingCrew = false;
        }
      }
      
    } while (continueMakingCrew);
    
    String shipName = cl.inputString("Enter crew's ship name: ", 30);
    createShip(shipName);

    CommandLineRunner runner = new CommandLineRunner(env, cl);
    
    finishedSetup();
    
    runner.startGame();
    
  }
  
  public boolean isCL() {
    return useCl;
  }
}
