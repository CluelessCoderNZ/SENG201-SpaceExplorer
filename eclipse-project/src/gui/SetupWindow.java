package gui;

import crew.CrewMember;
import crew.CrewMemberFactory;
import crew.CrewMemberType;
import crew.Engineer;
import crew.Investor;
import crew.Medic;
import crew.Robot;
import crew.Scientist;
import crew.Student;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.GameEnvironment;
import main.GameSetup;
import net.miginfocom.swing.MigLayout;

/**
 * handles the gui user input for the game setup.
 */
public class SetupWindow {

  private static final int MAX_TEXT_SIZE = 20;
  
  private DefaultListModel<CrewMember> crewGuiList = new DefaultListModel<CrewMember>();
  private JList<CrewMember> crewMembersList;
  private JFrame frame;
  private GameEnvironment env;
  private JTextField shipNameField;
  private JTextField crewNameField;
  private JComboBox<CrewMemberType> crewComboBox;
  private JTextPane crewClassDescription;
  private JSlider gameLengthSlider;
  private JButton deleteSelectedButton;
  private JButton addCrewButton;
  private JButton startGameButton;


  /**
   * Create the application window.
   */
  public SetupWindow(GameEnvironment env) {
    this.env = env;
    initialize();
    frame.setVisible(true);
    updateGuiInfo();
  }
  
  /**
   * finishes the setup window interaction, passing control back to the GameEnvironment.
   */
  private void finishedSetup() {
    env.finishSetup(this);
  }
  
  /**
   * closes the SetupWindow.
   */
  public void closeWindow() {
    frame.dispose();
  }
  
  /**
   * initializes the SetupWindow JFrame and its contents.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 703, 583);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new MigLayout("",
        "[61px][169.00px,grow][36.00px][89.00][82.00px]",
        "[43.00px][25.00px][][][28.00px][28.00px][29px][41.00px][86px,grow][28.00px][45.00px]"));
    
    initializeDisplayedText();
    initializeButtons();
    initializeInputs();
    
    JScrollPane crewScroll = new JScrollPane();
    frame.getContentPane().add(crewScroll, "cell 0 8 4 1,grow");
    
    crewMembersList = new JList<CrewMember>(crewGuiList);
    crewMembersList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updateGuiInfo();
      }
    });
    crewMembersList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    crewMembersList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    crewScroll.setViewportView(crewMembersList);

  }
  
  /**
   * returns whether a character in an input field is allowed to be entered in the given JTextField.
   * @param c the character entered
   * @param inputField the JTextField the character will be in
   * @return whether the character is valid
   */
  private boolean isValidInputCharacter(char c, JTextField inputField) {
    return (Character.isAlphabetic(c) 
        || Character.isDigit(c)
        || (Character.isSpaceChar(c) && inputField.getText().length() > 0))
        && inputField.getText().length() <= MAX_TEXT_SIZE;
  }
  
  /**
   * initializes the JButtons shown in the setup gui.
   */
  private void initializeButtons() {
    addCrewButton = new JButton("Add");
    addCrewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addCrewButtonPressed();
      }
    });
    frame.getContentPane().add(addCrewButton, "cell 4 6,alignx left,aligny center");
    
    deleteSelectedButton = new JButton("Delete selected");
    deleteSelectedButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteSelectedButtonPressed();
      }
    });
    frame.getContentPane().add(deleteSelectedButton, "cell 0 9,alignx center,aligny top");
    deleteSelectedButton.setEnabled(false);
    
    startGameButton = new JButton("Start Game");
    startGameButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        startGameButtonPressed();
      }
    });
    frame.getContentPane().add(startGameButton, "cell 3 10 2 1,grow");
  }
  
  /**
   * initialized the gui components that take user input.
   */
  private void initializeInputs() {
    // number of days slider
    Hashtable<Integer, JLabel> daysSliderMarkings = new Hashtable<Integer, JLabel>();
    for (int i = GameSetup.MIN_DAYS; i <= GameSetup.MAX_DAYS; i++) {
      daysSliderMarkings.put(i, new JLabel(Integer.toString(i)));
    }
    
    gameLengthSlider = new JSlider();
    gameLengthSlider.setSnapToTicks(true);
    gameLengthSlider.setPaintLabels(true);
    gameLengthSlider.setPaintTicks(true);
    gameLengthSlider.setMinimum(GameSetup.MIN_DAYS);
    gameLengthSlider.setMaximum(GameSetup.MAX_DAYS);
    frame.getContentPane().add(gameLengthSlider, "cell 0 2 5 1,alignx center,aligny top");
    gameLengthSlider.setLabelTable(daysSliderMarkings);
    
    // ship name text field
    shipNameField = new JTextField();
    shipNameField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        // ignore entry of invalid characters
        if (!isValidInputCharacter(e.getKeyChar(), shipNameField)) {
          e.consume();
        }
      }
      
      @Override
      public void keyReleased(KeyEvent e) {
        updateGuiInfo();
      }
    });
    shipNameField.setColumns(25);
    frame.getContentPane().add(shipNameField, "cell 0 4 5 1,alignx center,aligny top");
    
    // crew member name text field
    crewNameField = new JTextField();
    crewNameField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        // ignore entry of invalid characters
        if (!isValidInputCharacter(e.getKeyChar(), crewNameField)) {
          e.consume();
        }
        updateGuiInfo();
      }
      
      @Override
      public void keyReleased(KeyEvent e) {
        updateGuiInfo();
      }
    });
    frame.getContentPane().add(crewNameField, "cell 1 6,growx,aligny center");
    crewNameField.setColumns(10);
    
    // crew member type combo box
    crewComboBox = new JComboBox<CrewMemberType>();
    DefaultComboBoxModel<CrewMemberType> comboModel = new DefaultComboBoxModel<CrewMemberType>(
        CrewMemberType.values());
    crewComboBox.setModel(comboModel);
    crewComboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        updateGuiInfo();
      }
    });
    frame.getContentPane().add(crewComboBox, "cell 3 6,growx,aligny center");
  }
  
  /**
   * initializes the JLabels shown in the gui and the JTextPane for the crew class descriptions.
   */
  private void initializeDisplayedText() {
    JLabel welcomeLabel = new JLabel("Welcome to Space Explorer!");
    welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
    frame.getContentPane().add(welcomeLabel, "cell 0 0 5 1,alignx center,aligny top");
    
    JLabel gameLengthLabel = new JLabel("Game Length (Days)");
    gameLengthLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    frame.getContentPane().add(gameLengthLabel, "cell 0 1 5 1,alignx center,aligny bottom");
    
    JLabel shipNameLabel = new JLabel("Ship Name");
    shipNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    frame.getContentPane().add(shipNameLabel, "cell 0 3 5 1,alignx center,aligny bottom");
    
    JLabel crewMembersLabel = new JLabel("Crew Members");
    crewMembersLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    frame.getContentPane().add(crewMembersLabel, "cell 0 5 5 1,alignx center,aligny bottom");
    
    JLabel crewNameLabel = new JLabel("Name:");
    frame.getContentPane().add(crewNameLabel, "cell 0 6,alignx right,aligny center");
    
    JLabel lblThe = new JLabel("the");
    frame.getContentPane().add(lblThe, "cell 2 6,alignx center,aligny center");
    
    crewClassDescription = new JTextPane();
    crewClassDescription.setEditable(false);
    crewClassDescription.setText("Class description");
    frame.getContentPane().add(crewClassDescription, "cell 0 7 4 1,grow");
  }
  
  /**
   * updates the crew type description text pane and the clickability of the add and delete crew
   * member and start game buttons.
   */
  private void updateGuiInfo() {
    CrewMemberType type = (CrewMemberType)crewComboBox.getSelectedItem();

    switch (type) {
      case INVESTOR:
        crewClassDescription.setText("Investor: " + Investor.getClassDescription());
        break;
      case MEDIC:
        crewClassDescription.setText("Medic: " + Medic.getClassDescription());
        break;
      case ENGINEER:
        crewClassDescription.setText("Engineer: " + Engineer.getClassDescription());
        break;
      case STUDENT:
        crewClassDescription.setText("Student: " + Student.getClassDescription());
        break;
      case SCIENTIST:
        crewClassDescription.setText("Scientist: " + Scientist.getClassDescription());
        break;
      case ROBOT:
        crewClassDescription.setText("Robot: " + Robot.getClassDescription());
        break;
      default:
        crewClassDescription.setText("This class has no description!");
    }
    
    if (crewMembersList.getSelectedIndex() == -1) {
      deleteSelectedButton.setEnabled(false);
    } else {
      deleteSelectedButton.setEnabled(true);
    }
    
    if (!crewNameField.getText().equals("")) {
      addCrewButton.setEnabled(true);
    } else {
      addCrewButton.setEnabled(false);
    }
    
    if (!shipNameField.getText().equals("")) {
      startGameButton.setEnabled(true);
    } else {
      startGameButton.setEnabled(false);
    }
  }
  
  /**
   * adds a crew member to the gui list if there is space remaining,
   * or shows a popup if the max crew size has been reached.
   */
  private void addCrewButtonPressed() {
    String name = crewNameField.getText();
    CrewMemberType type = (CrewMemberType)crewComboBox.getSelectedItem();
    
    if (crewGuiList.getSize() >= GameSetup.MAX_CREW) {
      new EventPopupWindow("cannot add crew member: max crew size reached");
    } else {
      CrewMember crewMember = CrewMemberFactory.createCrewMember(type, name);
      crewGuiList.addElement(crewMember);
    }
  }
  
  /**
   * deletes the selected CrewMember from the list model.
   */
  private void deleteSelectedButtonPressed() {
    crewGuiList.removeElement(crewMembersList.getSelectedValue());
  }
  
  /**
   * tries to start the game if a ship name is entered and number of days is selected.
   * shows a popup if an invalid number of CrewMembers have been created.
   */
  private void startGameButtonPressed() {
    String shipName = shipNameField.getText();
    int numDays = gameLengthSlider.getValue();
        
    if (shipName.equals("")) {
      new EventPopupWindow("cannot start game: please enter a ship name");
    } else if (crewGuiList.size() < GameSetup.MIN_CREW || crewGuiList.size() > GameSetup.MAX_CREW) {
      new EventPopupWindow(String.format("cannot start game: add %d crew",
          GameSetup.MIN_CREW - crewGuiList.size()));
    } else {
      env.getGameSetup().setNumDays(numDays);
      for (int i = 0; i < crewGuiList.getSize(); i++) {
        env.getGameSetup().addCrewMember(crewGuiList.getElementAt(i));
      }
      env.getGameSetup().createShip(shipName);
      finishedSetup();
    }
  }

}
