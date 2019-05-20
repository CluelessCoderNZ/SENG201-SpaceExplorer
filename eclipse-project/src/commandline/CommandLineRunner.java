package commandline;

import java.util.ArrayList;
import java.util.List;

import crew.CrewMember;
import eventmanager.GameEvent;
import items.ConsumableItem;
import items.Item;
import items.ShipShieldUpgradeItem;
import items.ShipUpgrade;
import items.ShipWeaponItem;
import main.GameEnvironment;
import main.Planet;
import main.Shop;

public class CommandLineRunner {
  
  
  private GameEnvironment env;
  private CommandLineParser cl;
  
  public CommandLineRunner(GameEnvironment env, CommandLineParser cl) {
    this.env = env;
    this.cl = cl;
  }
  
  /**
   * Starts up the main game loop for the command line version.
   */  
  public void startGame() {
    
    cl.print("\n\n\nGAME START \n\n");
    
    cl.print("You awake finding yourself amongst the burning wreckage that was your Amazon spaceship.\n"
           + "Your crew are suprisingly unharmed and for now are in good spirits. You however are not \n"
           + "you have quotas to meet and know that if you don't find a way to fix the FTL Drive on your\n"
           + "spaceship with spareparts you'll be launched into the sun by Jeff Bezos. Gathering your things\n"
           + "you prepare for what lies ahead...\n\n\n");
    cl.pressToContinue();
    
    
    // Core Loop
    int selectedOption = 0;
    do {
      cl.print("\n");
      selectedOption = cl.inputOptions("What would you like to do captain? ", 
                                       "Get Status",
                                       "Check Inventory",
                                       "Enter Cockpit",
                                       "Browse Outpost Wares",
                                       "Explore planet: 1 AP",
                                       "Repair Ship: 1 AP",
                                       "Sleep: 1 AP",
                                       "Move To Next Day");
      cl.print("\n");
      
      // Run option selected.
      switch (selectedOption) {
      
        // Print Status
        case 0:
          printStatus();
          break;
        // Check Inventory
        case 1:
          inventoryMenu();
          break;
        // Planet Menu
        case 2:
          planetMenu();
          break;
        // Browse Shop
        case 3:
          shopMenu();
          break;
        // Explore
        case 4:
          exploreCommand();
          break;
        // Repair
        case 5:
          repairCommand();
          break;
        // Sleep
        case 6:
          sleepCommand();
          break;
        // Move To Next Day
        case 7:
          nextDayCommand();
          break;
        default:
          throw new IllegalArgumentException("Invalid option selected.");
      }
      
    } while (env.gameStillActive());
    
    
    cl.print("\nGAME OVER\n");
  }
  
  
  private void printStatus() {
    cl.print(String.format("\n%s CREW REPORT - DAY %d:\n", 
                           env.getCrewState().getShip().getName().toUpperCase(), 
                           env.getCurrentDay()));
    cl.print("=====================================\n");
    cl.print(String.format("Planet: %s\n", env.getCurrentPlanet().getName()));
    cl.print(String.format("Days Left: %d\n", env.getMaxDays() - env.getCurrentDay()));
    cl.print(String.format("Parts Found: %d/%d\n", 
        env.getCrewState().getShipPartsFoundCount(),
        env.getShipPartsNeededCount()));
    cl.print(String.format("Funds: %d\n",env.getCrewState().getFunds()));
    cl.print(String.format("Ship Shields: %d/%d\n",
                            env.getCrewState().getShip().getShieldLevel(),
                            env.getCrewState().getShip().getMaxShieldLevel()));
    
    
    cl.print("-------------------------------------\n");
    
    for (CrewMember crewmember : env.getCrewState().getCrew()) {
      cl.print(crewmember.toString() + '\n');
    }
    
    cl.print("=====================================\n\n");
  }
  
  private void printItem(Item item, int price, int counter) {
    String marked = " ";
    if (item instanceof ConsumableItem
        || item instanceof ShipShieldUpgradeItem
        || item instanceof ShipWeaponItem) {
      marked = "*";
    }
    
    cl.print(String.format("%2d: %s %20s - $%-4d %s\n", 
        counter, 
        marked,
        item.getName(), 
        price, 
        item.getEffectsString()));
  }
  
  private void printInventory() {
    cl.print("\nINVENTORY\n");
    cl.print("=============================================================\n");
    
    int counter = 0; 
    for (Item item : env.getCrewState().getInventory()) {
      printItem(item, item.getValue(), ++counter);
    }
    
    // If Empty
    if (counter == 0) {
      cl.print("Empty!\n");
    }
    
    cl.print("=============================================================\n");
    cl.print("* = usable item\n\n");
  }
  
  private CrewMember inputCrewMember(int actionsNeeded) {
    List<CrewMember> options = env.getCrewState().getCrewWithActionPoints(actionsNeeded);
    
    // No valid crew members
    if (options.size() == 0) {
      return null;
    }
    
    // Display
    int counter = 0;
    for (CrewMember crewmember : options) {
      cl.print(String.format("%d: %s AP: %d/%d\n", ++counter, crewmember.getFullTitle(),
                             crewmember.getActionPoints(), crewmember.getMaxActionPoints()));
    }
    
    // Select
    int selected = cl.inputInt("Select a crew member:", 1, options.size()) - 1;
    return options.get(selected);
  }
  
  private List<CrewMember> inputCrewMemberList(int actionsNeeded, int crewNeeded) {
    ArrayList<CrewMember> result = new ArrayList<CrewMember>(crewNeeded);
    
    List<CrewMember> options = env.getCrewState().getCrewWithActionPoints(actionsNeeded);
    
    // No valid crew members
    if (options.size() < 2) {
      return null;
    }
    
    while (result.size() < crewNeeded) {
      // Display
      int counter = 0;
      for (CrewMember crewmember : options) {
        cl.print(String.format("%d: %s AP: %d/%d\n", ++counter, crewmember.getFullTitle(),
                               crewmember.getActionPoints(), crewmember.getMaxActionPoints()));
      }
      
      String prompt = "Select another crew member:";
      if (result.size() == 0) {
        prompt = "Select a crew member:";
      }
      
      // Select
      int selected = cl.inputInt(prompt, 1, options.size()) - 1;
      result.add(options.get(selected));
      options.remove(selected);
    }
    
    return result;
  }
  
  private void inventoryMenu() {
    
    int selectedOption = 0;
    do {
      printInventory();
      
      selectedOption = cl.inputOptions("What would you like to do captain? ",
                                       "Use Item: 1 AP",
                                       "Go Back To Bridge");
      
      switch (selectedOption) {
        case 0:
          // Check not empty 
          if (env.getCrewState().getInventory().size() == 0) {
            cl.printError("Inventory is empty!");
            break;
          }
          
          // Get Item
          int itemIndex = cl.inputInt("Which item? ",
                                      1, env.getCrewState().getInventory().size()) - 1;
          Item item = env.getCrewState().getInventory().get(itemIndex);
          
          // Consumable
          if (item instanceof ConsumableItem) {
            CrewMember cm = inputCrewMember(1);
            
            if (cm != null) {
              env.getCrewState().useItem((ConsumableItem)item, cm);
            } else {
              cl.print("No alive crew members with enough AP");
            }
            
            
          // Ship Shield Upgrade Item
          } else if (item instanceof ShipShieldUpgradeItem || item instanceof ShipWeaponItem) {
            env.getCrewState().useItem((ShipUpgrade)item);
            
          // non-usable
          } else {
            cl.printError("Item is not usable.");
          }
          break;
        default:
          throw new IllegalArgumentException("Invalid option selected.");
      }
      
    } while (selectedOption != 1 && env.gameStillActive());
    
  }
  
  private void printPlanets() {
    cl.print("\nPLANETS\n");
    cl.print("=====================================\n");
    
    boolean visibleShipParts = env.getCrewState().hasScientist();
    
    int counter = 0;
    for (Planet planet : env.getPlanets()) {
      String marked = " ";
      if (planet == env.getCurrentPlanet()) {
        marked = ">";
      }
      
      String planetName = planet.getName();
      if (visibleShipParts) {
        planetName = planet.getNameShowPart();
      }
      
      cl.print(String.format("%-2d: %s%s\n", ++counter, marked, planetName));
    }
    
    cl.print("=====================================\n\n");
  }
  
  private void planetMenu() {
    
    int selectedOption = 0;
    do {
      printPlanets();
      
      selectedOption = cl.inputOptions("What would you like to do captain? ",
                                       "Travel To A New Planet: 2 AP",
                                       "Go Back To Bridge");
      
      switch (selectedOption) {
        case 0:
          // Get Item
          int itemIndex = cl.inputInt("Which planet? ",
                                      1, env.getPlanets().size()) - 1;
          Planet planet = env.getPlanets().get(itemIndex);
          
          // Check not already on planet
          if (planet == env.getCurrentPlanet()) {
            cl.printError("Already on this planet!");
            break;
          }
          
          // Get crew choices
          List<CrewMember> crewList = inputCrewMemberList(1,2);
          
          // Travel To Planet
          if (crewList != null) {
            GameEvent event;
            event = env.travelToPlanet(crewList.get(0), crewList.get(1), planet);
            
            if (event != null) {
              cl.print("\nWhile Traveling:\n");
              cl.print(event.getEventMessage());
              cl.print("\n\n");
            }
          } else {
            cl.print("You don't have two crew members with at least 1 AP\n");
          }

          break;
        default:
          throw new IllegalArgumentException("Invalid option selected.");
      }
      
    } while (selectedOption != 1 && env.gameStillActive());
  }
  
  private void printShop() {
    Shop shop = env.getCurrentPlanet().getShop();
    cl.print(String.format("\n%s\n", shop.getName().toUpperCase()));
    cl.print("=============================================================\n");
    
    int counter = 0; 
    for (Item item : shop.getInventory()) {
      printItem(item, shop.getSellPrice(item), ++counter);
    }
    
    // If Empty
    if (counter == 0) {
      cl.print("Sold Out!\n");
    }
    
    cl.print("=============================================================\n");
    cl.print(String.format("funds: $%d     ", env.getCrewState().getFunds()));
    cl.print("* = usable item\n\n");
  }
  
  private void shopMenu() {
    int selectedOption = 0;
    do {
      printShop();
      
      selectedOption = cl.inputOptions("What would you like to do captain? ",
                                       "Buy",
                                       "Go Back To Bridge");
      
      switch (selectedOption) {
        case 0:
          // Check not empty
          if (env.getCrewState().getInventory().size() == 0) {
            cl.printError("Inventory is empty!");
            break;
          }
          
          // Get Item
          int itemIndex = cl.inputInt("Which item? ",
                                     1, env.getCurrentPlanet().getShop().getInventory().size()) - 1;
          Item item = env.getCurrentPlanet().getShop().getInventory().get(itemIndex);
          
          // Check if crew can afford
          int price = env.getCurrentPlanet().getShop().getSellPrice(item);
          if (env.getCrewState().getFunds() > price) {
            
            // Buy Item
            env.getCurrentPlanet().getShop().sellItem(item);
            env.getCrewState().addItemToInventory(item);
            env.getCrewState().removeFunds(price);
            cl.print("ShopKeeper: 'Thanks for the sale. No refunds!'\n");
          } else {
            cl.print("You can't afford to buy this.\n");
          }
          break;
        default:
          throw new IllegalArgumentException("Invalid option selected.");
      }
      
    } while (selectedOption != 1 && env.gameStillActive());
  }
  
  private void exploreCommand() {
    CrewMember cm = inputCrewMember(1);
    
    if (cm != null) {
      GameEvent event;
      event = env.explorePlanet(cm);
      cl.print("\nWhile Exploring::\n");
      if (event != null) {
        cl.print(event.getEventMessage());
      } else {
        cl.print(String.format("%s found nothing on the barren planet.", cm.getName()));  
      }
      cl.print("\n\n");
    } else {
      cl.print("You need at least one crew member with 1 AP.\n");
    }
    
  }
  
  private void repairCommand() {
    CrewMember cm = inputCrewMember(1);
    
    if (cm != null) {
      env.getCrewState().repairShip(cm);
      
      cl.print(String.format("%s managed to restore some of the shields of the ship.\n", 
          cm.getName()));
    } else {
      cl.print("You need at least one crew member with 1 AP.\n");
    }
  }
  
  private void sleepCommand() {
    CrewMember cm = inputCrewMember(1);
    
    if (cm != null) {
      cm.sleep();
      cl.print(String.format("%s awoke from their nap feeling refreshed and ready for orders.\n", 
               cm.getName()));
    } else {
      cl.print("You need at least one crew member with 1 AP.\n");
    }
  }
  
  private void nextDayCommand() {
    GameEvent event;
    
    event = env.moveForwardADay();
    
    cl.print("============\n");
    cl.print("   DAY " + env.getCurrentDay());
    cl.print("\n============\n");
    
    if (event != null) {
      cl.print("During the night:\n");
      cl.print(event.getEventMessage());
      cl.print("\n\n");
    }
    
    cl.print("The dawn of a new day shines upon you.\n");
    
  }
}


