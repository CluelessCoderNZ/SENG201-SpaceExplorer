package main;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CrewMemberList {

  DefaultListModel<CrewMember> listModel;
  JList crewList;
  
  public CrewMemberList() {
    listModel  = new DefaultListModel<CrewMember>();
    crewList = new JList<CrewMember>(listModel);
    crewList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
//        if (crewMembersList.getSelectedIndex() == -1) {
//          btnDeleteSelected.setEnabled(false);
//        } else {
//          btnDeleteSelected.setEnabled(true);
//        }
      }
    });
  }
  
  public void deleteSelected() {
    if (crewList.getSelectedIndex() != -1) {
      crewList.remove(crewList.getSelectedIndex());
    }
  }
  
}
