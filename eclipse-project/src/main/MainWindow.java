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
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

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
  private JLabel funds;
  private JLabel shield;
  private JLabel currentWeapon;
  private JLabel partsCollected;

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
    frame.getContentPane().setLayout(new MigLayout("", "[257.00px][160px,grow]",
        "[16px][29px][16px][149.00px,grow][36.00px][38.00][32.00px]"));
    
    JLabel lblCrew = new JLabel("Crew:");
    frame.getContentPane().add(lblCrew, "cell 0 2,alignx center,aligny top");
    
    JLabel lblShipName = new JLabel("Ship: " + env.getCrewState().getShip().getName());
    frame.getContentPane().add(lblShipName, "cell 1 2,alignx center,aligny top");
    
    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    frame.getContentPane().add(tabbedPane, "cell 1 3 1 3,grow");
    
    JPanel panel = new JPanel();
    tabbedPane.addTab("Combat Room", null, panel, null);
    panel.setLayout(new MigLayout("", "[grow]", "[][][]"));
    
    shield = new JLabel("SHIELD: " + env.getCrewState().getShip().getShieldLevel());
    panel.add(shield, "cell 0 0,growx,aligny top");
    
    currentWeapon = new JLabel("Current Weapon:");
    panel.add(currentWeapon, "cell 0 1");
    
    repair = new JButton("Repair Shields");
    panel.add(repair, "cell 0 2");
    repair.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        repairButtonPressed();
      }
    });
    
    JPanel cargoHold = new JPanel();
    tabbedPane.addTab("Cargo Hold", null, cargoHold, null);
    cargoHold.setLayout(new MigLayout("", "[grow][4px]", "[][grow][][][4px]"));
    
    partsCollected = new JLabel("Parts collected: 0");
    cargoHold.add(partsCollected, "cell 0 0");
    
    JScrollPane inventoryScroll = new JScrollPane();
    cargoHold.add(inventoryScroll, "cell 0 1,grow");
    
    inventoryList = new JList<Item>(inventory);
    inventoryScroll.setViewportView(inventoryList);
    inventoryList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updateButtons();
      }
    });
    inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    useItem = new JButton("Use item");
    useItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        useItemButtonPressed();
      }
    });
    cargoHold.add(useItem, "flowy,cell 0 2");
    
    JPanel observationDeck = new JPanel();
    tabbedPane.addTab("Observation Deck", null, observationDeck, null);
    observationDeck.setLayout(new MigLayout("", "[grow][4px]", "[grow][][4px]"));
    
    JScrollPane planetScroll = new JScrollPane();
    observationDeck.add(planetScroll, "cell 0 0,grow");
    
    planetList = new JList<Planet>(planets);
    planetScroll.setViewportView(planetList);
    planetList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updateButtons();
      }
    });
    planetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    changePlanet = new JButton("Change Planet");
    observationDeck.add(changePlanet, "cell 0 1");
    changePlanet.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        changePlanetButtonPressed();
      }
    });
    
    JScrollPane crewScroll = new JScrollPane();
    frame.getContentPane().add(crewScroll, "cell 0 3,alignx right,growy");
    
    crewList = new JList<CrewMember>(crew);
    crewScroll.setViewportView(crewList);
    crewList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updateButtons();
      }
    });
    
    
    JButton btnViewShop = new JButton("Visit Outpost");
    btnViewShop.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        visitOutpostButton();
      }
    });
    
    funds = new JLabel("Funds: 0");
    frame.getContentPane().add(funds, "flowx,cell 1 1,alignx right,aligny center");
    frame.getContentPane().add(btnViewShop, "cell 1 1,alignx right,aligny center");
    
    explore = new JButton("Explore");
    explore.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        exploreButtonPressed();
      }
    });
    frame.getContentPane().add(explore, "cell 0 4,growx,aligny top");
    
    sleep = new JButton("Sleep");
    sleep.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        sleepButtonPressed();
      }
    });
    frame.getContentPane().add(sleep, "cell 0 5,growx,aligny top");
    
    JButton btnNextDay = new JButton("Next Day");
    btnNextDay.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        nextDayButtonPressed();
      }
    });
    frame.getContentPane().add(btnNextDay, "cell 1 6,alignx right,growy");
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
    
    funds.setText("Funds: $" + crewState.getFunds());
    shield.setText(String.format("Shield: %d/%d", crewState.getShip().getShieldLevel(),
        crewState.getShip().getMaxShieldLevel()));
    partsCollected.setText("Parts collected: " + crewState.getShipPartsFoundCount());
    currentWeapon.setText(String.format("Current weapon: %s (%d damage)",
        crewState.getShip().getWeapon().getName(),
        crewState.getShip().getWeapon().getDamage()));
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
    
    List<CrewMember> selectedCrew = crewList.getSelectedValuesList();
    Item selectedItem = inventoryList.getSelectedValue();
    
    if (selectedCrew.size() == 1 && selectedCrew.get(0).canAct()) {
      explore.setEnabled(true);
      sleep.setEnabled(true);
      repair.setEnabled(true);
      if (selectedItem != null) {
        useItem.setEnabled(true);
      }
    } else if (selectedCrew.size() == 2
        && selectedCrew.get(0).canAct()
        && selectedCrew.get(1).canAct()) {
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
  
  private void useItemButtonPressed() {
    CrewMember selectedCrewMember = crewList.getSelectedValue();
    Item selectedItem = inventoryList.getSelectedValue();
    
    // TODO code here
    
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
