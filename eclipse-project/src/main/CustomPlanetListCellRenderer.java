package main;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


public class CustomPlanetListCellRenderer extends JLabel implements ListCellRenderer<Planet> {
  
  public CustomPlanetListCellRenderer() {
    setOpaque(true);
  }
  
  @Override
  public Component getListCellRendererComponent(JList<? extends Planet> list, Planet planet,
      int index,
      boolean isSelected, boolean cellHasFocus) {

    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    } else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }
    
    setText(planet.getNameShowPart());

    return this;
  }
   
}