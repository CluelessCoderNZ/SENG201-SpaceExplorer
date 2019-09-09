package gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import main.Planet;

/**
 * custom renderer for Planets that shows a star if the planet has a part to be used
 * in the MainWindow planet list if the crew has a scientist.
 */
public class CustomPlanetListCellRenderer extends JLabel implements ListCellRenderer<Planet> {
  
  /**
   * constructs a CustomPlanetListCellRenderer object.
   */
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