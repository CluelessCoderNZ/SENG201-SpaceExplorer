package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;

public class MainWindow {

  private GameEnvironment env;
  private JFrame frame;
  

  /**
   * Create the application.
   */
  public MainWindow(GameEnvironment env) {
    this.env = env;
    initialize();
    frame.setVisible(true);
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 450, 404);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    
    
    JLabel lblShipName = new JLabel("SHIP: " + env.getCrewState().getShip().getName());
    lblShipName.setBounds(6, 6, 181, 16);
    frame.getContentPane().add(lblShipName);
    
    JLabel lblShield = new JLabel("SHIELD: " + env.getCrewState().getShip().getShieldLevel());
    lblShield.setBounds(6, 27, 181, 16);
    frame.getContentPane().add(lblShield);
    
    JButton btnInventory = new JButton("Inventory");
    btnInventory.setBounds(327, 14, 117, 29);
    frame.getContentPane().add(btnInventory);
    
    JButton btnViewShop = new JButton("Visit Outpost");
    btnViewShop.setBounds(327, 44, 117, 29);
    frame.getContentPane().add(btnViewShop);
    
    JList list = new JList();
    list.setBounds(29, 91, 181, 59);
    frame.getContentPane().add(list);
    
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
    btnViewPlanets.setBounds(29, 336, 117, 29);
    frame.getContentPane().add(btnViewPlanets);
    
    JButton btnNextDay = new JButton("Next Day");
    btnNextDay.setBounds(290, 336, 117, 29);
    frame.getContentPane().add(btnNextDay);
  }
}
