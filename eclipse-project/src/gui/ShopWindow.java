package gui;

import items.ConsumableItem;
import items.Item;
import items.ShipUpgrade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.GameEnvironment;

import net.miginfocom.swing.MigLayout;

public class ShopWindow {

  private JFrame frame;
  private GameEnvironment env;
  
  private DefaultListModel<Item> shopInventory = new DefaultListModel<Item>();
  private DefaultListModel<Item> playerInventory = new DefaultListModel<Item>();
  private JList<Item> shopInventoryList;
  private JList<Item> playerInventoryList;
  private JLabel itemStats;
  private JLabel playerFunds;
  private JTextPane shopItemDescription;
  private JButton btnBuyItem;
  private JButton btnSellItem;

  /**
   * Create the application.
   * @param env the GameEnvironment to model the window off of.
   */
  public ShopWindow(GameEnvironment env) {
    this.env = env;
    initialize();
    frame.setVisible(true);
    updateGuiData();
  }
  
  /**
   * finishes the shop window interaction, passing control back to the GameEnvironment.
   */
  private void finishedShop() {
    env.finishShop(this);
  }
  
  /**
   * closes the ShopWindow.
   */
  public void closeWindow() {
    frame.dispose();
  }

  /**
   * Initialize the JFrame and its contents.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 358, 441);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.getContentPane().setLayout(new MigLayout("", "[49.72%,grow]",
        "[16px][grow][][][18.00,grow][-2.00][][30.00][][grow][]"));
    
    JLabel lblShopName = new JLabel(env.getCurrentPlanet().getShop().getName());
    frame.getContentPane().add(lblShopName, "cell 0 0,growx,aligny top");
    
    playerInventoryList = new JList<Item>(playerInventory);
    playerInventoryList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        updatePlayerItemDisplay(playerInventoryList.getSelectedValue());
      }
    });
    playerInventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    initializeShopInventory();
    
    btnBuyItem = new JButton("Buy");
    btnBuyItem.setEnabled(false);
    btnBuyItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buySelectedItem();
      }
    });
    frame.getContentPane().add(btnBuyItem, "cell 0 2,alignx left,aligny center");
    
    btnSellItem = new JButton("Sell");
    btnSellItem.setEnabled(false);
    btnSellItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        sellSelectedItem();
      }
    });
    frame.getContentPane().add(btnSellItem, "cell 0 10,alignx left,aligny center");
    
    shopItemDescription = new JTextPane();
    shopItemDescription.setEditable(false);
    shopItemDescription.setText("");
    frame.getContentPane().add(shopItemDescription, "cell 0 4,grow");
    
    itemStats = new JLabel("Item stats:");
    frame.getContentPane().add(itemStats, "cell 0 5");
    
    JButton btnLeaveShop = new JButton("Leave shop");
    btnLeaveShop.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        finishedShop();
      }
    });
    frame.getContentPane().add(btnLeaveShop, "cell 0 10 1 2,alignx right,aligny bottom");
    
    playerFunds = new JLabel("Player funds: 0");
    frame.getContentPane().add(playerFunds, "cell 0 8");
    
    JScrollPane playerInventoryScroll = new JScrollPane();
    frame.getContentPane().add(playerInventoryScroll, "cell 0 9,grow");
    
    JLabel lblPlayerInventory = new JLabel("Player Inventory");
    playerInventoryScroll.setColumnHeaderView(lblPlayerInventory);
    lblPlayerInventory.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
    lblPlayerInventory.setForeground(Color.GRAY);
    
    playerInventoryScroll.setViewportView(playerInventoryList);
  }
  
  /**
   * initializes components relating to the Shop's inventory display.
   */
  private void initializeShopInventory() {
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
  }
  
  /**
   * refreshes the labels and lists that appear in the gui.
   */
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
  
  /**
   * displays information about a certain item in the shop's inventory.
   * updates the sell button text.
   * @param item the Item to display information about
   */
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
      if (item instanceof ConsumableItem) {
        itemStats.setText("Item stats: " + ((ConsumableItem)item).getEffectsString());
      } else if (item instanceof ShipUpgrade) {
        itemStats.setText("Item stats: " + ((ShipUpgrade)item).getEffectsString());
      }
    }
  }
  
  /**
   * updates the player item sell button text and clickability.
   * @param item the Item to base the view on
   */
  private void updatePlayerItemDisplay(Item item) {
    btnSellItem.setEnabled(false);
    btnSellItem.setText("Sell");
    
    if (item != null) {
      btnSellItem.setEnabled(true);
      int buyPrice = this.env.getCurrentPlanet().getShop().getBuyPrice(item);
      btnSellItem.setText("Sell ($" + buyPrice + ")");
    }
  }
  
  /**
   * buys the selected Item from the shop and adds it to the crew's inventory.
   */
  private void buySelectedItem() {
    Item item = shopInventoryList.getSelectedValue();
    int price = env.getCurrentPlanet().getShop().sellItem(item);
    env.getCrewState().addItemToInventory(item);
    env.getCrewState().removeFunds(price);
    updateGuiData();
  }
  
  /**
   * sells the selected Item to the shop and removes it from the crew's inventory.
   */
  private void sellSelectedItem() {
    Item item = playerInventoryList.getSelectedValue();
    env.getCrewState().addFunds(this.env.getCurrentPlanet().getShop().buyItem(item));
    env.getCrewState().removeItemFromInventory(item);
    updateGuiData();
  }
}
