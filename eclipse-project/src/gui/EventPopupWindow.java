package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EventPopupWindow {

  private JFrame frame;

  /**
   * Create the application.
   */
  public EventPopupWindow(String message) {
    this(message, "Acknowledge");
  }
  
  public EventPopupWindow(String message, String buttonText) {
    initialize(message, buttonText);
    frame.setVisible(true);
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize(String message, String buttonText) {
    frame = new JFrame();
    frame.setBounds(100, 100, 294, 216);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][23.00]"));
    
    JTextPane eventMessage = new JTextPane();
    eventMessage.setText(message);
    eventMessage.setEditable(false);
    frame.getContentPane().add(eventMessage, "cell 0 0,grow");
    
    JButton closePopup = new JButton(buttonText);
    closePopup.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });
    frame.getContentPane().add(closePopup, "cell 0 1,alignx center");
  }

}
