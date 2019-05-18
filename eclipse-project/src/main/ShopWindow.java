package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.SpringLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShopWindow {

  private JFrame frame;
  private GameEnvironment env;
  
  private DefaultListModel<Item> shopInventory = new DefaultListModel<Item>();
  private DefaultListModel<Item> playerInventory = new DefaultListModel<Item>();
  private JList<Item> shopInventoryList;
  private JLabel itemStats;
  private JLabel playerFunds;
  private JTextPane shopItemDescription;
  private JButton btnBuyItem;

  /**
   * Create the application.
   */
  public ShopWindow(GameEnvironment env) {
    this.env = env;
    initialize();
    frame.setVisible(true);
    updateGuiData();
  }
  
  private void finishedShop() {
    env.finishShop(this);
  }
  
  public void closeWindow() {
    frame.dispose();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 358, 441);
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.getContentPane().setLayout(new MigLayout("", "[49.72%,grow]",
        "[16px][grow][][][18.00,grow][-2.00][][30.00][][grow][]"));
    
    JLabel lblShopName = new JLabel(env.getCurrentPlanet().getShop().getName());
    frame.getContentPane().add(lblShopName, "cell 0 0,growx,aligny top");
    
    JScrollPane shopInventoryScroll = new JScrollPane();
    frame.getContentPane().add(shopInventoryScroll, "cell 0 1,grow");
    
    JLabel lblShopInventory = new JLabel("Shop Inventory");
    lblShopInventory.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
    lblShopInventory.setForeground(Color.GRAY);
    shopInventoryScroll.setColumnHeaderView(lblShopInventory);
    
    shopInventoryList = new JList<Item>(shopInventory);
    shopInventoryList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updateShopItemDisplay(shopInventoryList.getSelectedValue());
      }
    });
    shopInventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    shopInventoryList.setSelectedIndex(0);
    shopInventoryScroll.setViewportView(shopInventoryList);
    
    btnBuyItem = new JButton("Buy");
    btnBuyItem.setEnabled(false);
    btnBuyItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buySelectedItem();
      }
    });
    frame.getContentPane().add(btnBuyItem, "cell 0 2,alignx left,aligny center");
    
    shopItemDescription = new JTextPane();
    shopItemDescription.setEditable(false);
    shopItemDescription.setText("");
    frame.getContentPane().add(shopItemDescription, "cell 0 4,grow");
    
    itemStats = new JLabel("Item Stats:");
    frame.getContentPane().add(itemStats, "cell 0 5");
    
    JButton btnLeaveShop = new JButton("Leave shop");
    btnLeaveShop.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        finishedShop();
      }
    });
    frame.getContentPane().add(btnLeaveShop, "cell 0 6 1 2,alignx left,aligny bottom");
    
    playerFunds = new JLabel("Player funds: 0");
    frame.getContentPane().add(playerFunds, "cell 0 8");
    
    JScrollPane playerInventoryScroll = new JScrollPane();
    frame.getContentPane().add(playerInventoryScroll, "cell 0 9,grow");
    
    JLabel lblPlayerInventory = new JLabel("Player Inventory");
    playerInventoryScroll.setColumnHeaderView(lblPlayerInventory);
    lblPlayerInventory.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
    lblPlayerInventory.setForeground(Color.GRAY);
    
    JList<Item> playerInventoryList = new JList<Item>(playerInventory);
    playerInventoryScroll.setViewportView(playerInventoryList);
  }
  
  private void updateGuiData() {
    playerFunds.setText("Crew funds: " + env.getCrewState().getFunds());
    
    shopInventory.clear();
    for (Item item : env.getCurrentPlanet().getShop().getInventory()) {
      shopInventory.addElement(item);
    }
    
    playerInventory.clear();
    for (Item item : env.getCrewState().getInventory()) {
      playerInventory.addElement(item);
    }
  }
  
  private void updateShopItemDisplay(Item item) {
    btnBuyItem.setEnabled(false);
    itemStats.setText("");
    
    if (item != null) {
      if (item.getDescription().equals("")) {
        shopItemDescription.setText(item.getName());
      } else {
        shopItemDescription.setText(item.getDescription());
      }
      
      if (env.getCurrentPlanet().getShop().getSellPrice(item) <= env.getCrewState().getFunds()) {
        btnBuyItem.setEnabled(true);
      }

      btnBuyItem.setText("Buy ($" + this.env.getCurrentPlanet().getShop().getSellPrice(item) + ")");
      if (item instanceof GenericRestorationItem) {
        itemStats.setText("Item stats: " + ((GenericRestorationItem)item).getEffectsString());
      } else if (item instanceof ShipUpgrade) {
        itemStats.setText("Item stats: " + ((ShipUpgrade)item).getEffectsString());
      }
    }
  }
  
  
  private void buySelectedItem() {
    Item item = shopInventoryList.getSelectedValue();
    int price = env.getCurrentPlanet().getShop().sellItem(item);
    env.getCrewState().addItemToInventory(item);
    env.getCrewState().removeFunds(price);
    updateGuiData();
  }

}
