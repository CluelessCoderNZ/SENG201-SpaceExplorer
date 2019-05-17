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

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 704, 601);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    
    JLabel lblShipName = new JLabel("SHIP: " + env.getCrewState().getShip().getName());
    lblShipName.setBounds(6, 6, 181, 16);
    frame.getContentPane().add(lblShipName);
    
    JLabel lblFunds = new JLabel("Funds: 0");
    lblFunds.setBounds(29, 176, 181, 16);
    frame.getContentPane().add(lblFunds);
    
    JLabel lblCrew = new JLabel("Crew:");
    lblCrew.setBounds(29, 73, 61, 16);
    frame.getContentPane().add(lblCrew);
    
    JLabel lblPartsCollected = new JLabel("Parts collected: 0");
    lblPartsCollected.setBounds(29, 195, 181, 16);
    frame.getContentPane().add(lblPartsCollected);
    
    JLabel lblShield = new JLabel("SHIELD: " + env.getCrewState().getShip().getShieldLevel());
    lblShield.setBounds(6, 27, 181, 16);
    frame.getContentPane().add(lblShield);
    
    JLabel lblInventory = new JLabel("Inventory:");
    lblInventory.setBounds(291, 73, 78, 16);
    frame.getContentPane().add(lblInventory);
    
    planetScroll = new JScrollPane();
    planetScroll.setBounds(39, 377, 239, 100);
    frame.getContentPane().add(planetScroll);
    
    planetList = new JList<Planet>(planets);
    planetList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updateButtons();
      }
    });
    planetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    planetScroll.setViewportView(planetList);
    
    JScrollPane crewScroll = new JScrollPane();
    crewScroll.setBounds(29, 91, 249, 80);
    frame.getContentPane().add(crewScroll);
    
    crewList = new JList<CrewMember>(crew);
    crewScroll.setViewportView(crewList);
    crewList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updateButtons();
      }
    });
    
    JScrollPane inventoryScroll = new JScrollPane();
    inventoryScroll.setBounds(290, 91, 278, 80);
    frame.getContentPane().add(inventoryScroll);
    
    inventoryList = new JList<Item>(inventory);
    inventoryScroll.setViewportView(inventoryList);
    inventoryList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updateButtons();
      }
    });
    inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    JButton btnViewShop = new JButton("Visit Outpost");
    btnViewShop.setBounds(424, 22, 160, 29);
    frame.getContentPane().add(btnViewShop);
    
    explore = new JButton("Explore");
    explore.setBounds(29, 223, 117, 29);
    frame.getContentPane().add(explore);
    
    sleep = new JButton("Sleep");
    sleep.setBounds(161, 223, 117, 29);
    frame.getContentPane().add(sleep);
    
    repair = new JButton("Repair");
    repair.setBounds(29, 264, 117, 29);
    frame.getContentPane().add(repair);
    
    JButton btnViewPlanets = new JButton("View Planets");
    btnViewPlanets.setBounds(29, 336, 141, 29);
    frame.getContentPane().add(btnViewPlanets);
    
    JButton btnNextDay = new JButton("Next Day");
    btnNextDay.setBounds(290, 336, 117, 29);
    frame.getContentPane().add(btnNextDay);
    
    changePlanet = new JButton("Change Planet");
    changePlanet.setBounds(161, 264, 141, 29);
    frame.getContentPane().add(changePlanet);
    
    useItem = new JButton("Use item");
    useItem.setBounds(290, 190, 117, 29);
    frame.getContentPane().add(useItem);
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
}
