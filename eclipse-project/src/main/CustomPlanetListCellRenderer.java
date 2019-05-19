package main;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

//public class CustomPlanetListCellRenderer extends DefaultListCellRenderer {
//
//  /**
//   * Return a component that has been configured to display the specified value.
//   * @param list the JList of Planet objects we are painting
//   * @param planet the Planet returned by list.getModel.getElementAt(index)
//   * @param index the cells index
//   * @param isSelected True if the specified cell was selected
//   * @param cellHasFocus True if the specified cell has the focus
//   * @return a component whose paint() method will render the specified value
//   */
//  public Component getListCellRendererComponent(JList<Planet> list, Planet planet,
//      int index, boolean isSelected, boolean cellHasFocus) {
//    Component c = super.getListCellRendererComponent(list, planet, index, isSelected,
//        cellHasFocus);
//    //setText(planet.getNameShowPart());
//    super.setText("eggs");
//    return c;
//  }
//  
//}


public class CustomPlanetListCellRenderer extends JLabel implements ListCellRenderer<Planet> {
  
  @Override
  public Component getListCellRendererComponent(JList<? extends Planet> list, Planet planet,
      int index,
      boolean isSelected, boolean cellHasFocus) {
        
    setText(planet.getNameShowPart());

    return this;
  }
   
}