package main;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ImageIcon;


public class CustomCrewListCellRenderer extends JLabel implements ListCellRenderer<CrewMember> {
  
  public CustomCrewListCellRenderer() {
    setOpaque(true);
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
    
    if (crew.isDead()) {
    	setIcon(new ImageIcon(CustomCrewListCellRenderer.class.getResource("/img/grave.png")));
    }
    setText(crew.getFullTitle() + " (AP: " + crew.getActionPoints() + ")");
    return this;
  }
   
}