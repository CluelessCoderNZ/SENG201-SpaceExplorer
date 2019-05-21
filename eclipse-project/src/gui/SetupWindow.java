package gui;

import crew.CrewMember;
import crew.CrewMemberFactory;
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

public class SetupWindow {

  private static final int MAX_TEXT_SIZE = 20;
  
  private DefaultListModel<CrewMember> crewGuiList = new DefaultListModel<CrewMember>();
  private JList<CrewMember> crewMembersList;
  private JFrame frame;
  private GameEnvironment env;
  private JTextField shipNameField;
  private JTextField crewNameField;
  private JComboBox<String> crewComboBox;
  private JTextPane crewClassDescription;
  private JSlider gameLengthSlider;
  private JButton deleteSelectedButton;
  private JButton addCrewButton;
  private JButton startGameButton;


  /**
   * Create the application.
   */
  public SetupWindow(GameEnvironment env) {
    this.env = env;
    initialize();
    frame.setVisible(true);
    updateGuiInfo();
  }
  
  private void finishedSetup() {
    env.finishSetup(this);
  }
  
  public void closeWindow() {
    frame.dispose();
  }
  
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 609, 592);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new MigLayout("",
        "[61px][169.00px,grow][36.00px][89.00][82.00px]",
        "[43.00px][25.00px][26px][32.00px][29px][56px][86px,grow][29px][56px][16px]"));
    
    initializeDisplayedText();
    initializeButtons();
    initializeInputs();
    
    JScrollPane crewScroll = new JScrollPane();
    frame.getContentPane().add(crewScroll, "cell 0 6 4 1,grow");
    
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
    return Character.isAlphabetic(c) 
        || Character.isDigit(c)
        || (Character.isSpaceChar(c) && inputField.getText().length() > 0)
        || inputField.getText().length() > MAX_TEXT_SIZE;
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
    frame.getContentPane().add(addCrewButton, "cell 4 4,alignx left,aligny center");
    
    deleteSelectedButton = new JButton("Delete selected");
    deleteSelectedButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteSelectedButtonPressed();
      }
    });
    frame.getContentPane().add(deleteSelectedButton, "cell 0 7,alignx center,aligny top");
    deleteSelectedButton.setEnabled(false);
    
    startGameButton = new JButton("Start Game");
    startGameButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        startGameButtonPressed();
      }
    });
    frame.getContentPane().add(startGameButton, "cell 3 8 2 1,grow");
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
    frame.getContentPane().add(gameLengthSlider, "cell 1 1,grow");
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
    shipNameField.setColumns(10);
    frame.getContentPane().add(shipNameField, "cell 1 2,growx,aligny top");
    
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
    frame.getContentPane().add(crewNameField, "cell 1 4,growx,aligny center");
    crewNameField.setColumns(10);
    
    // crew member type combo box
    crewComboBox = new JComboBox<String>();
    crewComboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        updateGuiInfo();
      }
    });
    crewComboBox.setModel(new DefaultComboBoxModel<String>(
        new String[] {"Investor", "Medic", "Engineer", "Student", "Scientist", "Robot"}));
    frame.getContentPane().add(crewComboBox, "cell 3 4,growx,aligny center");
  }
  
  private void initializeDisplayedText() {
    JLabel welcomeLabel = new JLabel("Welcome to Space Explorer!");
    welcomeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
    frame.getContentPane().add(welcomeLabel, "cell 0 0 5 1,alignx center,aligny top");
    
    JLabel gameLengthLabel = new JLabel("Game Length (Days)");
    frame.getContentPane().add(gameLengthLabel, "cell 0 1,grow");
    
    JLabel shipNameLabel = new JLabel("Ship Name");
    frame.getContentPane().add(shipNameLabel, "cell 0 2,growx,aligny center");
    
    JLabel crewMembersLabel = new JLabel("Crew Members");
    frame.getContentPane().add(crewMembersLabel, "cell 0 3 3 1,growx,aligny bottom");
    
    JLabel crewNameLabel = new JLabel("Name:");
    frame.getContentPane().add(crewNameLabel, "cell 0 4,growx,aligny center");
    
    JLabel lblThe = new JLabel("the");
    frame.getContentPane().add(lblThe, "cell 2 4,alignx center,aligny center");
    
    crewClassDescription = new JTextPane();
    crewClassDescription.setEditable(false);
    crewClassDescription.setText("Class description");
    frame.getContentPane().add(crewClassDescription, "cell 0 5 4 1,grow");
  }
  
  
  private void updateGuiInfo() {
    String typeString = crewComboBox.getSelectedItem().toString();
    switch (typeString) {
      case "Investor":
        crewClassDescription.setText(typeString + ": " + Investor.getClassDescription());
        break;
      case "Medic":
        crewClassDescription.setText(typeString + ": " + Medic.getClassDescription());
        break;
      case "Engineer":
        crewClassDescription.setText(typeString + ": " + Engineer.getClassDescription());
        break;
      case "Student":
        crewClassDescription.setText(typeString + ": " + Student.getClassDescription());
        break;
      case "Scientist":
        crewClassDescription.setText(typeString + ": " + Scientist.getClassDescription());
        break;
      case "Robot":
        crewClassDescription.setText(typeString + ": " + Robot.getClassDescription());
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
  
  
  private void addCrewButtonPressed() {
    String name = crewNameField.getText();
    String typeString = crewComboBox.getSelectedItem().toString();
    
    if (name.equals("")) {
      new EventPopupWindow("cannot add crew member: please enter a name");
    } else if (crewGuiList.getSize() >= GameSetup.MAX_CREW) {
      new EventPopupWindow("cannot add crew member: max crew size reached");
    } else {
      CrewMember crewMember = CrewMemberFactory.createCrewMember(typeString, name);
      crewGuiList.addElement(crewMember);
    }
  }
  
  
  private void deleteSelectedButtonPressed() {
    crewGuiList.removeElement(crewMembersList.getSelectedValue());
  }
  
  
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