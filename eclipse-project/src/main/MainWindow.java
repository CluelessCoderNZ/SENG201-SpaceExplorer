package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

public class MainWindow {

  private GameEnvironment env;
  private JFrame frame;
  private DefaultListModel<CrewMember> crew = new DefaultListModel<CrewMember>();
  private DefaultListModel<Item> inventory = new DefaultListModel<Item>();
  private JList<CrewMember> crewList;
  private JList<Item> inventoryList;
  

  /**
   * Create the application.
   */
  public MainWindow(GameEnvironment env) {
    this.env = env;
    initialize();
    updateGui(env.getCrewState());
    frame.setVisible(true);
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 590, 404);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    
    JLabel lblShipName = new JLabel("SHIP: " + env.getCrewState().getShip().getName());
    lblShipName.setBounds(6, 6, 181, 16);
    frame.getContentPane().add(lblShipName);
    
    JLabel lblShield = new JLabel("SHIELD: " + env.getCrewState().getShip().getShieldLevel());
    lblShield.setBounds(6, 27, 181, 16);
    frame.getContentPane().add(lblShield);
    
    JButton btnViewShop = new JButton("Visit Outpost");
    btnViewShop.setBounds(424, 22, 160, 29);
    frame.getContentPane().add(btnViewShop);
    
    crewList = new JList<CrewMember>(crew);
    crewList.setBounds(29, 91, 181, 59);
    crewList.setSelectedIndex(0);
    frame.getContentPane().add(crewList);
    
    JLabel lblCrew = new JLabel("Crew:");
    lblCrew.setBounds(29, 73, 61, 16);
    frame.getContentPane().add(lblCrew);
    
    JLabel lblFunds = new JLabel("Funds: 0");
    lblFunds.setBounds(29, 176, 181, 16);
    frame.getContentPane().add(lblFunds);
    
    JLabel lblPartsCollected = new JLabel("Parts collected: 0");
    lblPartsCollected.setBounds(29, 195, 181, 16);
    frame.getContentPane().add(lblPartsCollected);
    
    JButton btnExplore = new JButton("Explore");
    btnExplore.setBounds(29, 223, 117, 29);
    frame.getContentPane().add(btnExplore);
    
    JButton btnSleep = new JButton("Sleep");
    btnSleep.setBounds(161, 223, 117, 29);
    frame.getContentPane().add(btnSleep);
    
    JButton btnRepair = new JButton("Repair");
    btnRepair.setBounds(29, 264, 117, 29);
    frame.getContentPane().add(btnRepair);
    
    JButton btnViewPlanets = new JButton("View Planets");
    btnViewPlanets.setBounds(29, 336, 141, 29);
    frame.getContentPane().add(btnViewPlanets);
    
    JButton btnNextDay = new JButton("Next Day");
    btnNextDay.setBounds(290, 336, 117, 29);
    frame.getContentPane().add(btnNextDay);
    
    inventoryList = new JList<Item>(inventory);
    inventoryList.setBounds(290, 91, 181, 59);
    frame.getContentPane().add(inventoryList);
    
    JLabel lblInventory = new JLabel("Inventory:");
    lblInventory.setBounds(291, 73, 78, 16);
    frame.getContentPane().add(lblInventory);
  }
  
  
  public void updateGui(CrewState crewState) {
    crew.clear();
    for (CrewMember member : crewState.getCrew()) {
      crew.addElement(member);
    }
    inventory.clear();
    for (Item item : crewState.getInventory()) {
      inventory.addElement(item);
    }
  }
  
}
