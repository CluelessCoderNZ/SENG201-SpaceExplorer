package gui;

import crew.CrewMember;
import crew.CrewState;
import eventmanager.GameEvent;
import items.ConsumableItem;
import items.Item;
import items.ShipUpgrade;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.GameEnvironment;
import main.Planet;
import net.miginfocom.swing.MigLayout;

/**
 * this class handles the displaying of the state of the game, and user input.
 */
public class MainWindow {

  private GameEnvironment env;
  private JFrame frame;
  private DefaultListModel<CrewMember> crew = new DefaultListModel<CrewMember>();
  private DefaultListModel<Item> inventory = new DefaultListModel<Item>();
  private DefaultListModel<Planet> planets = new DefaultListModel<Planet>();
  
  private JList<CrewMember> crewList;
  private JList<Item> inventoryList;
  private JList<Planet> planetList;
  
  private JTextPane selectedCrewStats;
  
  private JButton explore;
  private JButton sleep;
  private JButton repair;
  private JButton useItem;
  private JButton changePlanet;
  
  private JLabel funds;
  private JLabel shield;
  private JLabel currentWeapon;
  private JLabel partsCollected;
  private JLabel currentPlanet;
  private JLabel daysRemaining;
  private JTextPane selectedItemDescription;
  
  /**
   * Create the application window and set elements to the correct initial values.
   * @param env the GameEnvironment to model the window off of
   */
  public MainWindow(GameEnvironment env) {
    this.env = env;
    initialize();
    updateGuiInfo();
    updateButtons();
    updateCrewStatsMessage();
    frame.setVisible(true);
  }
  
  /**
   * finishes the main game loop.
   */
  private void finishedMainGame() {
    env.finishMainGame(this);
  }
  
  /**
   * disposes the MainWindow frame.
   */
  public void closeWindow() {
    frame.dispose();
  }

  /**
   * Initialize the contents of the window.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 734, 601);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new MigLayout("", "[304.00px][160px,grow]",
        "[29px][16px][152.00px][109.00,grow][36.00px][38.00][32.00px]"));
    
    JTabbedPane controlPane = new JTabbedPane(JTabbedPane.TOP);
    frame.getContentPane().add(controlPane, "cell 1 2 1 4,grow");
    
    JPanel combatRoom = new JPanel();
    controlPane.addTab("Combat Room", null, combatRoom, null);
    combatRoom.setLayout(new MigLayout("", "[grow]", "[][][]"));
    
    JPanel cargoHold = new JPanel();
    controlPane.addTab("Cargo Hold", null, cargoHold, null);
    cargoHold.setLayout(new MigLayout("", "[grow][4px]", "[][243.00,grow][93.00][]"));
    
    JPanel observationDeck = new JPanel();
    controlPane.addTab("Observation Deck", null, observationDeck, null);
    observationDeck.setLayout(new MigLayout("", "[grow][4px]", "[][grow][][4px]"));
    
    initializeLabels();
    initializeCombatRoomPanel(combatRoom);
    initializeCargoHoldPanel(cargoHold);
    initializeObservationDeckPanel(observationDeck);
    
    JScrollPane crewScroll = new JScrollPane();
    frame.getContentPane().add(crewScroll, "cell 0 2,grow");
    
    crewList = new JList<CrewMember>(crew);
    crewScroll.setViewportView(crewList);
    crewList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updateButtons();
        updateCrewStatsMessage();
      }
    });
    crewList.setCellRenderer(new CustomCrewListCellRenderer());
    
    JButton btnViewShop = new JButton("Visit Outpost");
    btnViewShop.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        visitOutpostButton();
      }
    });
    frame.getContentPane().add(btnViewShop, "cell 1 0,alignx right,aligny center");
    
    explore = new JButton("Explore");
    explore.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        exploreButtonPressed();
      }
    });
    
    JScrollPane crewStatsScroll = new JScrollPane();
    frame.getContentPane().add(crewStatsScroll, "cell 0 3,grow");
    
    selectedCrewStats = new JTextPane();
    crewStatsScroll.setViewportView(selectedCrewStats);
    selectedCrewStats.setEditable(false);
    selectedCrewStats.setText("Selected Crew Descriptions");
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
   * creates the JLabels that appear in the MainWindow frame.
   */
  private void initializeLabels() {
    JLabel lblCrew = new JLabel("Crew:");
    frame.getContentPane().add(lblCrew, "cell 0 1,alignx center,aligny top");
    
    JLabel lblShipName = new JLabel("Ship: " + env.getCrewState().getShip().getName());
    frame.getContentPane().add(lblShipName, "cell 1 1,alignx center,aligny top");
    
    funds = new JLabel("Funds: 0");
    frame.getContentPane().add(funds, "flowx,cell 1 0,alignx right,aligny center");
    
    daysRemaining = new JLabel("Days remaining: 0");
    frame.getContentPane().add(daysRemaining, "flowx,cell 1 6,alignx right");
  }
  
  /**
   * initializes the contents of the combat room JPanel.
   * @param combatRoom the JPanel to add the components to
   */
  private void initializeCombatRoomPanel(JPanel combatRoom) {
    shield = new JLabel("SHIELD: " + env.getCrewState().getShip().getShieldLevel());
    combatRoom.add(shield, "cell 0 0,growx,aligny top");
    
    currentWeapon = new JLabel("Current Weapon:");
    combatRoom.add(currentWeapon, "cell 0 1");
    
    repair = new JButton("Repair Shields");
    combatRoom.add(repair, "cell 0 2");
    repair.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        repairButtonPressed();
      }
    });
  }
  
  /**
   * initializes the contents of the cargo hold JPanel.
   * @param combatRoom the JPanel to add the components to
   */
  private void initializeCargoHoldPanel(JPanel cargoHold) {
    partsCollected = new JLabel("Parts collected: 0/" + env.getShipPartsNeededCount());
    cargoHold.add(partsCollected, "cell 0 0");
    
    JScrollPane inventoryScroll = new JScrollPane();
    cargoHold.add(inventoryScroll, "cell 0 1,grow");
    
    inventoryList = new JList<Item>(inventory);
    inventoryScroll.setViewportView(inventoryList);
    inventoryList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updateButtons();
        updateItemInfo();
      }
    });
    inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    useItem = new JButton("Use item");
    useItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        useItemButtonPressed();
      }
    });
    
    selectedItemDescription = new JTextPane();
    cargoHold.add(selectedItemDescription, "cell 0 2,grow");
    cargoHold.add(useItem, "flowy,cell 0 3");
  }
  
  /**
   * initializes the contents of the observation deck JPanel.
   * @param combatRoom the JPanel to add the components to
   */
  private void initializeObservationDeckPanel(JPanel observationDeck) {
    currentPlanet = new JLabel("Current planet: ");
    observationDeck.add(currentPlanet, "cell 0 0");
    
    JScrollPane planetScroll = new JScrollPane();
    observationDeck.add(planetScroll, "cell 0 1,grow");
    
    planetList = new JList<Planet>(planets);
    planetScroll.setViewportView(planetList);
    planetList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updateButtons();
      }
    });
    planetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    // shows a custom label for planets with a ship part if there is a scientist in the crew
    if (env.getCrewState().hasScientist()) {
      planetList.setCellRenderer(new CustomPlanetListCellRenderer());
    }
    
    changePlanet = new JButton("Change Planet");
    observationDeck.add(changePlanet, "cell 0 2");
    changePlanet.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        changePlanetButtonPressed();
      }
    });
  }
  
  /**
   * updates the GUI contents to match the game state.
   */
  private void updateGuiInfo() {
    updateGuiLists();
    updateGuiText();
  }
  
  /**
   * updates the GUI representations of the crew list, crew inventory and planet list.
   */
  private void updateGuiLists() {
    CrewState crewState = env.getCrewState();
    
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
   * updates the GUI labels to display the current game state.
   */
  private void updateGuiText() {
    CrewState crewState = env.getCrewState();
    
    funds.setText("Funds: $" + crewState.getFunds());
    
    shield.setText(String.format("Shield: %d/%d", crewState.getShip().getShieldLevel(),
        crewState.getShip().getMaxShieldLevel()));
    
    partsCollected.setText(String.format("Parts collected: %d/%d",
        crewState.getShipPartsFoundCount(), env.getShipPartsNeededCount()));
    
    currentWeapon.setText(String.format("Current weapon: %s (%d damage)",
        crewState.getShip().getWeapon().getName(),
        crewState.getShip().getWeapon().getDamage()));
    
    if (crewState.hasScientist()) {
      currentPlanet.setText("Current Planet: " + env.getCurrentPlanet().getNameShowPart());
    } else {
      currentPlanet.setText("Current Planet: " + env.getCurrentPlanet().getName());
    }
    
    daysRemaining.setText("Days remaining: " + (env.getMaxDays() - env.getCurrentDay()));
  }
  
  /**
   * updates the info section about the currently selected inventory item.
   */
  private void updateItemInfo() {
    Item selectedItem = inventoryList.getSelectedValue();
    String description = "";
    
    if (selectedItem != null) {
      if (selectedItem.getDescription().equals("")) {
        description += selectedItem.getName();
      } else {
        description += selectedItem.getDescription();
      }
      
      if (selectedItem instanceof ConsumableItem) {
        description += String.format("\nItem stats: %s",
            ((ConsumableItem)selectedItem).getEffectsString());
      } else if (selectedItem instanceof ShipUpgrade) {
        description += String.format("\nItem stats: %s",
            ((ShipUpgrade)selectedItem).getEffectsString());
      }
    }
    selectedItemDescription.setText(description);
  }
  
  /**
   * changes clickability of the action buttons depending on the current list selections.
   * stops buttons being clickable if the required preconditions are not met.
   */
  private void updateButtons() {
    // deactivates each button
    explore.setEnabled(false);
    sleep.setEnabled(false);
    repair.setEnabled(false);
    useItem.setEnabled(false);
    changePlanet.setEnabled(false);
    
    List<CrewMember> selectedCrew = crewList.getSelectedValuesList();
    Item selectedItem = inventoryList.getSelectedValue();
    
    // if the item can be used without an action point
    if (selectedItem != null && selectedItem instanceof ShipUpgrade) {
      useItem.setEnabled(true);
    }
    
    // if a selected crew member can act, enable the action buttons
    if (selectedCrew.size() == 1 && selectedCrew.get(0).canAct()) {
      explore.setEnabled(true);
      sleep.setEnabled(true);
      if (env.getCrewState().getShip().getShieldLevel() > 0) {
        repair.setEnabled(true);
      }
      // use item button requires an item to be selected as well
      if (selectedItem != null && selectedItem instanceof ConsumableItem) {
        useItem.setEnabled(true);
      }
    // if the two selected crew can act, a planet is selected and the ship is not dead, allow flight
    } else if (selectedCrew.size() == 2
        && selectedCrew.get(0).canAct()
        && selectedCrew.get(1).canAct()) {
      if (planetList.getSelectedIndex() != -1
          && planetList.getSelectedValue() != env.getCurrentPlanet()
          && env.getCrewState().getShip().getShieldLevel() > 0) {
        changePlanet.setEnabled(true);
      }
    }
  }
  
  /**
   * updates the contents of the GUI crew stats message box.
   */
  private void updateCrewStatsMessage() {
    String crewStatusString = "";
    for (CrewMember crewMember : crewList.getSelectedValuesList()) {
      crewStatusString += crewMember.getFormattedStatusString() + "\n\n";
    }
    selectedCrewStats.setText(crewStatusString);
    selectedCrewStats.setCaretPosition(0);
  }
  
  /**
   * action to perform when the visit outpost button is clicked.
   */
  private void visitOutpostButton() {
    env.openShop(this);
  }
  
  /**
   * action to perform when the explore button is clicked.
   */
  private void exploreButtonPressed() {
    CrewMember selectedCrewMember = crewList.getSelectedValue();
    
    GameEvent event;
    event = env.explorePlanet(selectedCrewMember);
    
    if (event != null) {
      new EventPopupWindow("While Exploring:\n" + event.getEventMessage());
    } else {
      new EventPopupWindow(String.format("%s found nothing on the barren planet.", 
                                         selectedCrewMember.getName()));
    }
    
    updateGuiInfo();
  }
  
  /**
   * action to perform when the sleep button is clicked.
   */
  private void sleepButtonPressed() {
    CrewMember selectedCrewMember = crewList.getSelectedValue();
    
    selectedCrewMember.sleep();
    
    updateGuiInfo();
  }
  
  /**
   * action to perform when the repair button is clicked.
   */
  private void repairButtonPressed() {
    CrewMember selectedCrewMember = crewList.getSelectedValue();
    
    selectedCrewMember.repairShip(env.getCrewState().getShip());
    
    updateGuiInfo();
  }
  
  /**
   * action to perform when the use item button is clicked.
   */
  private void useItemButtonPressed() {
    CrewMember selectedCrewMember = crewList.getSelectedValue();
    Item selectedItem = inventoryList.getSelectedValue();
    
    if (selectedItem instanceof ConsumableItem) {
      env.getCrewState().useItem((ConsumableItem) selectedItem, selectedCrewMember);
    } else if (selectedItem instanceof ShipUpgrade) {
      env.getCrewState().useItem((ShipUpgrade) selectedItem);
    }
    
    updateGuiInfo();
  }
  
  /**
   * action to perform when the change planet button is clicked.
   */
  private void changePlanetButtonPressed() {
    List<CrewMember> selectedCrewMembers = crewList.getSelectedValuesList();
    Planet selectedPlanet = planetList.getSelectedValue();
    
    GameEvent event;
    event = env.travelToPlanet(selectedCrewMembers.get(0), 
                               selectedCrewMembers.get(1),
                               selectedPlanet);
    
    if (event != null) {
      new EventPopupWindow("While Traveling:\n" + event.getEventMessage());
    } else {
      new EventPopupWindow("You managed to safely travel to " + selectedPlanet.getName());
    }
    
    updateGuiInfo();
  }
  
  /**
   * action to perform when the next day button is clicked.
   */
  private void nextDayButtonPressed() {
    
    if (!env.gameStillActive()) {
      finishedMainGame();
      return;
    }
    
    GameEvent event;
    event = env.moveForwardADay();
    
    if (event != null) {
      new EventPopupWindow("During The Night:\n" + event.getEventMessage());
    } else {
      new EventPopupWindow("The dawn of a new day shines upon you.", "Onwards!");
    }
    
    updateGuiInfo();
  }
  
}
