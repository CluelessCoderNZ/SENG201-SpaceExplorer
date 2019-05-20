package gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import crew.CrewMember;
import crew.CrewMemberEffect;

/**
 * custom renderer for CrewMembers to be used in the MainWindow crew list.
 */
public class CustomCrewListCellRenderer extends JPanel implements ListCellRenderer<CrewMember> {
  
  private static final ImageIcon plagueIcon = new ImageIcon(
      CustomCrewListCellRenderer.class.getResource("/img/plague.png"));
  private static final ImageIcon hungryIcon = new ImageIcon(
      CustomCrewListCellRenderer.class.getResource("/img/hungry.png"));
  private static final ImageIcon tiredIcon = new ImageIcon(
      CustomCrewListCellRenderer.class.getResource("/img/tired.png"));
  private static final ImageIcon stressedIcon = new ImageIcon(
      CustomCrewListCellRenderer.class.getResource("/img/stressed.png"));
  private static final ImageIcon deadIcon = new ImageIcon(
      CustomCrewListCellRenderer.class.getResource("/img/dead.png"));
  
  private JLabel plaguedLabel = new JLabel();
  private JLabel hungryLabel = new JLabel();
  private JLabel tiredLabel = new JLabel();
  private JLabel stressedLabel = new JLabel();
  private JLabel deadLabel = new JLabel();
  private JLabel crewText = new JLabel();
  
  /**
   * sets up the icons for each status effect label and adds them to the renderer.
   */
  public CustomCrewListCellRenderer() {
    setOpaque(true);
    setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
    
    plaguedLabel.setIcon(plagueIcon);
    plaguedLabel.setText(" ");
    add(plaguedLabel);
    
    hungryLabel.setIcon(hungryIcon);
    hungryLabel.setText(" ");
    add(hungryLabel);
    
    tiredLabel.setIcon(tiredIcon);
    tiredLabel.setText(" ");
    add(tiredLabel);
    
    stressedLabel.setIcon(stressedIcon);
    stressedLabel.setText(" ");
    add(stressedLabel);
    
    deadLabel.setIcon(deadIcon);
    deadLabel.setText(" ");
    add(deadLabel);

    add(crewText);
  }
  
  @Override
  public Component getListCellRendererComponent(JList<? extends CrewMember> list, CrewMember crew,
      int index,
      boolean isSelected, boolean cellHasFocus) {

    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    } else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }
    
    displayIcons(crew);

    if (crew.isDead()) {
      crewText.setText(crew.getName() + " the Deceased");
    } else {
      crewText.setText(crew.getFullTitle() + " (AP: " + crew.getActionPoints() + ")");
    }
    return this;
  }
  
  /**
   * handles the displaying of status effect icons next to the given crew member.
   * @param the CrewMember to display the status effects of
   */
  private void displayIcons(CrewMember crew) {
    plaguedLabel.setVisible(false);
    hungryLabel.setVisible(false);
    tiredLabel.setVisible(false);
    stressedLabel.setVisible(false);
    deadLabel.setVisible(false);
    
    if (crew.isDead()) {
      deadLabel.setVisible(true);
    } else {
      if (crew.hasEffect(CrewMemberEffect.PLAGUED)) {
        plaguedLabel.setVisible(true);
      }
      if (crew.hasEffect(CrewMemberEffect.HUNGRY)) {
        hungryLabel.setVisible(true);
      }
      if (crew.hasEffect(CrewMemberEffect.TIRED)) {
        tiredLabel.setVisible(true);
      }
      if (crew.hasEffect(CrewMemberEffect.STRESSED)) {
        stressedLabel.setVisible(true);
      }
    }
  }
   
}