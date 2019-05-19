package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;

public class MainWindow {

  private GameEnvironment env;
  private JFrame frame;
  private DefaultListModel<CrewMember> crew = new DefaultListModel<CrewMember>();
  private DefaultListModel<Item> inventory = new DefaultListModel<Item>();
  private DefaultListModel<Planet> planets = new DefaultListModel<Planet>();
  
  
  private JList<CrewMember> crewList;
  private JList<Item> inventoryList;
  private JList<Planet> planetList;
  private JButton explore;
  private JButton sleep;
  private JButton repair;
  private JButton useItem;
  private JButton changePlanet;
  private JScrollPane planetScroll;
  
  private boolean showPlanetList = false;
  

  /**
   * Create the application.
   */
  public MainWindow(GameEnvironment env) {
    this.env = env;
    initialize();
    updateGuiLists(env.getCrewState());
    updateButtons();
    frame.setVisible(true);
  }
  
  public void closeWindow() {
    frame.dispose();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 704, 601);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new MigLayout("", "[140px][15px][117px][12px][117px][17px][160px]", "[16px][29px][16px][80px][16px][3px][24px][29px][29px][29px][100px]"));
    
    JLabel lblShipName = new JLabel("SHIP: " + env.getCrewState().getShip().getName());
    frame.getContentPane().add(lblShipName, "cell 0 0 3 1,alignx left,aligny top");
    
    JLabel lblFunds = new JLabel("Funds: 0");
    frame.getContentPane().add(lblFunds, "cell 0 4 3 1,growx,aligny top");
    
    JLabel lblCrew = new JLabel("Crew:");
    frame.getContentPane().add(lblCrew, "cell 0 2,alignx center,aligny top");
    
    JLabel lblPartsCollected = new JLabel("Parts collected: 0");
    frame.getContentPane().add(lblPartsCollected, "cell 0 6 3 1,alignx center,aligny top");
    
    JLabel lblShield = new JLabel("SHIELD: " + env.getCrewState().getShip().getShieldLevel());
    frame.getContentPane().add(lblShield, "cell 0 1 3 1,growx,aligny center");
    
    JLabel lblInventory = new JLabel("Inventory:");
    frame.getContentPane().add(lblInventory, "cell 4 2,alignx left,aligny top");
    
    
    planetScroll = new JScrollPane();
    frame.getContentPane().add(planetScroll, "cell 0 10 3 1,alignx right,growy");
    
    planetList = new JList<Planet>(planets);
    planetList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updateButtons();
      }
    });
    planetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    planetScroll.setViewportView(planetList);
    
    JScrollPane crewScroll = new JScrollPane();
    frame.getContentPane().add(crewScroll, "cell 0 3 3 1,alignx right,growy");
    
    crewList = new JList<CrewMember>(crew);
    crewScroll.setViewportView(crewList);
    crewList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updateButtons();
      }
    });
    
    JScrollPane inventoryScroll = new JScrollPane();
    frame.getContentPane().add(inventoryScroll, "cell 4 3 3 1,grow");
    
    inventoryList = new JList<Item>(inventory);
    inventoryScroll.setViewportView(inventoryList);
    inventoryList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updateButtons();
      }
    });
    inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    
    JButton btnViewShop = new JButton("Visit Outpost");
    btnViewShop.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        visitOutpostButton();
      }
    });
    frame.getContentPane().add(btnViewShop, "cell 6 1,growx,aligny top");
    
    explore = new JButton("Explore");
    explore.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        exploreButtonPressed();
      }
    });
    frame.getContentPane().add(explore, "cell 0 7,growx,aligny top");
    
    sleep = new JButton("Sleep");
    sleep.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        sleepButtonPressed();
      }
    });
    frame.getContentPane().add(sleep, "cell 2 7,growx,aligny top");
    
    repair = new JButton("Repair");
    repair.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        repairButtonPressed();
      }
    });
    frame.getContentPane().add(repair, "cell 0 8,growx,aligny top");
    
    JButton btnViewPlanets = new JButton("View Planets");
    frame.getContentPane().add(btnViewPlanets, "cell 0 9 3 1,alignx left,aligny top");
    
    JButton btnNextDay = new JButton("Next Day");
    btnNextDay.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        nextDayButtonPressed();
      }
    });
    frame.getContentPane().add(btnNextDay, "cell 4 9,growx,aligny top");
    
    changePlanet = new JButton("Change Planet");
    changePlanet.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        changePlanetButtonPressed();
      }
    });
    frame.getContentPane().add(changePlanet, "cell 2 8 3 1,alignx left,aligny top");
    
    useItem = new JButton("Use item");
    frame.getContentPane().add(useItem, "cell 4 4 1 3,growx,aligny bottom");
  }
  
  /**
   * updates the GUI contents to match the game state.
   * @param crewState the crew state containing the crew and item lists
   */
  public void updateGuiLists(CrewState crewState) {
    crew.clear();
    for (CrewMember member : crewState.getCrew()) {
      crew.addElement(member);
    }
    inventory.clear();
    for (Item item : crewState.getInventory()) {
      inventory.addElement(item);
    }
    planets.clear();
    for (Planet planet : env.getPlanets()) {
      planets.addElement(planet);
    }
  }
  
  /**
   * changes clickability of the crew member action buttons. 
   */
  private void updateButtons() {
    explore.setEnabled(false);
    sleep.setEnabled(false);
    repair.setEnabled(false);
    useItem.setEnabled(false);
    changePlanet.setEnabled(false);
    planetScroll.setVisible(showPlanetList);
    
    int[] selectedCrew = crewList.getSelectedIndices();
    int selectedItem = inventoryList.getSelectedIndex();
    
    if (selectedCrew.length == 1) {
      explore.setEnabled(true);
      sleep.setEnabled(true);
      repair.setEnabled(true);
      if (selectedItem != -1) {
        useItem.setEnabled(true);
      }
    } else if (selectedCrew.length == 2) {
      planetScroll.setVisible(true);
      if (planetList.getSelectedIndex() != -1) {
        changePlanet.setEnabled(true);
      }
    }
  }
  
  private void visitOutpostButton() {
    env.openShop(this);
  }
  
  private void exploreButtonPressed() {
    CrewMember selectedCrewMember = crewList.getSelectedValue();
    
    GameEvent event;
    event = env.explorePlanet(selectedCrewMember);
    
    if (event != null) {
      new EventPopupWindow("While Exploring:\n" + event.getEventMessage());
    }
    
    updateGuiLists(env.getCrewState());
  }
  
  private void sleepButtonPressed() {
    CrewMember selectedCrewMember = crewList.getSelectedValue();
    
    selectedCrewMember.sleep();
    
    updateGuiLists(env.getCrewState());
  }
  
  private void repairButtonPressed() {
    CrewMember selectedCrewMember = crewList.getSelectedValue();
    
    selectedCrewMember.repairShip(env.getCrewState().getShip());
    
    updateGuiLists(env.getCrewState());
  }
  
  private void changePlanetButtonPressed() {
    List<CrewMember> selectedCrewMembers = crewList.getSelectedValuesList();
    Planet selectedPlanet = planetList.getSelectedValue();
    
    GameEvent event;
    event = env.travelToPlanet(selectedCrewMembers.get(0), 
                               selectedCrewMembers.get(1),
                               selectedPlanet);
    
    if (event != null) {
      new EventPopupWindow("While Traveling:\n" + event.getEventMessage());
    }
    
    updateGuiLists(env.getCrewState());
  }
  
  private void nextDayButtonPressed() {
    GameEvent event;
    event = env.moveForwardADay();
    
    if (event != null) {
      new EventPopupWindow("During The Night:\n" + event.getEventMessage());
    }
    
    updateGuiLists(env.getCrewState());
  }
}
