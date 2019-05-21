package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;

/**
 * popup window for displaying any game event or general message
 * the user may be interested in.
 */
public class EventPopupWindow {

  private JFrame frame;

  /**
   * create the popup window with a custom message.
   * @param message the message to display
   */
  public EventPopupWindow(String message) {
    this(message, "Acknowledge");
  }
  
  /**
   * create the popup window with a custom message and custom button text.
   * @param message the message to display
   * @param buttonText the text to display on the close buton
   */
  public EventPopupWindow(String message, String buttonText) {
    initialize(message, buttonText);
    frame.setVisible(true);
  }

  /**
   * initialize the contents of the frame.
   * @param message the custom message to display
   * @param buttonText the text to display on the close buton
   */
  private void initialize(String message, String buttonText) {
    frame = new JFrame();
    frame.setBounds(100, 100, 294, 216);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][23.00]"));
    
    // message text pane
    JTextPane eventMessage = new JTextPane();
    eventMessage.setText(message);
    eventMessage.setEditable(false);
    frame.getContentPane().add(eventMessage, "cell 0 0,grow");
    
    // close window button
    JButton closePopup = new JButton(buttonText);
    closePopup.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });
    frame.getContentPane().add(closePopup, "cell 0 1,alignx center");
  }

}
