package main;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;



public class CommandLineParser {
  private PrintStream output;
  private Scanner inputScanner;

  public CommandLineParser(PrintStream output, InputStream input) {
    this.output = output;
    inputScanner = new Scanner(input).useDelimiter("\n");
  }
  
  public void closeInput() {
    inputScanner.close();
  }

  public void print(String message) {
    output.print(message);
  }

  public void printError(String message) {
    print("Error: " + message + "\n");
  }

  /**
   * Get a integer from the InputStream repeating input on type error.
   * @param message message to put into output stream
   * @return validated integer
   */
  public int inputInt(String message) {
    boolean valueFound = false;
    int value = 0;
    
    do {
      print(message);
      
      // Type checking
      try {
        value = inputScanner.nextInt();
      } catch (InputMismatchException e) {
        String token = inputScanner.next();
        if (token.length() > 0) {
          printError(String.format("Expected a number got '%s' instead.", token));
        }
        continue;
      }
      
      valueFound = true;
    } while (!valueFound);
  
    return value;
  }
  
  /**
   * Get a integer from the InputStream repeating input on type error or bounds error.
   * @param message message to put into output stream
   * @param lowerBound lower bound for integer value
   * @param upperBound upper bound for integer value
   * @return validated integer
   */
  public int inputInt(String message, int lowerBound, int upperBound) {
    boolean valueFound = false;
    int value = 0;
    
    do {
      print(message);
      
      // Type checking
      try {
        value = inputScanner.nextInt();
      } catch (InputMismatchException e) {
        String token = inputScanner.next();
        
        if (token.length() > 0) {
          printError(String.format("Expected a number got '%s' instead.", token));
        }
        continue;
      }
      
      // Bounds checking
      if (value < lowerBound || value > upperBound) {
        printError(String.format("Number must be in the range %d-%d.", lowerBound, upperBound));
        continue;
      }
        
      valueFound = true;
    } while (!valueFound);
  
    return value;
  }
  
  /**
   * Get a string from the InputStream repeating input on max length error.
   * @param message message to put into output stream
   * @param maxLength max length the input string can be
   * @return validated string
   */
  public String inputString(String message, int maxLength) {
    boolean valueFound = false;
    String value = "";
    
    do {
      print(message);
      
      value = inputScanner.next();
      
      // Reset if no input
      if (value.length() == 0) {
        continue;
      }
      
      // Check below max length
      if (value.length() > maxLength) {
        printError(String.format("Text must be less than or equal to %d characters, currently %d.", 
                   maxLength, value.length()));
        continue;
      }
      
      valueFound = true;
    } while (!valueFound);
  
    return value;
  }
  
  /**
   * Get a boolean from the InputStream. 
   * Accepting all-case y/n, 1/0, yes/no, true/false. 
   * Repeating on invalid strings.
   * @param message message to put into output stream
   * @return validated boolean
   */
  public boolean inputBoolean(String message) {
    boolean valueFound = false;
    Boolean value = false;
    
    do {
      print(message);
      
      String inputString = inputScanner.next();
      inputString = inputString.toLowerCase().trim();
      
      switch (inputString) {
        case "y":
        case "yes":
        case "true":
        case "1":
          value = true;
          break;
        case "n":
        case "no":
        case "false":
        case "0":
          value = false;
          break;
        default:
          printError(String.format("Expected 'Y' or 'N', got '%s'", inputString));
          continue;
      }
      
      valueFound = true;
    } while (!valueFound);
  
    return value;
  }
  
  /**
   * Get a selected index from input from list of options. 
   * Accepts index of option as well as option string. 
   * Repeats on out of bounds and type errors.
   * @param message message to put into output stream
   * @param options varargs of string options
   * @return 
   */
  public int inputOptions(String message, String... options) {
    boolean valueFound = false;
    int value = 0;
    
    do {
      // Print available options
      for (int i = 0; i < options.length; i++) {
        print(String.format("%d: %s\n", i + 1, options[i]));
      }
      
      print(message);
      
      // Type checking
      try {
        // Expect integer as default
        value = inputScanner.nextInt();
      } catch (InputMismatchException e) {
        
        String token = inputScanner.next();
        
        if (token.length() > 0) {
          String cleanedToken = token.toLowerCase().trim();
          
          // Check if input is the option name
          boolean inputValid = false;
          for (int i = 0; i < options.length; i++) {
            String option = options[i];
            option = option.toLowerCase();
            
            if (cleanedToken.equals(option)) {
              value = i + 1;
              inputValid = true;
            }
          }
          
          // Report error since input is not option name
          if (!inputValid) {
            printError(String.format("Expected a number got '%s' instead.", token));
            continue;
          }
        } else {
          continue;
        }
      }
      
      // Bounds checking
      if (value < 1 || value > options.length) {
        printError(String.format("Number must be in the range 1-%d.", options.length));
        continue;
      }
      
      valueFound = true;
    } while (!valueFound);
  
    return value - 1;
  }
  
  /**
   * Get a selected index from input from list of options. 
   * Accepts index of option as well as option string. 
   * Repeats on out of bounds and type errors.
   * @param message message to put into output stream
   * @param options list of string options
   * @return 
   */
  public int inputOptions(String message, List<String> options) {
    boolean valueFound = false;
    int value = 0;
    
    do {
      // Print available options
      for (int i = 0; i < options.size(); i++) {
        print(String.format("%d: %s\n", i + 1, options.get(i)));
      }
      
      print(message);
      
      // Type checking
      try {
        // Expect integer as default
        value = inputScanner.nextInt();
      } catch (InputMismatchException e) {
        
        String token = inputScanner.next();
        
        if (token.length() > 0) {
          String cleanedToken = token.toLowerCase().trim();
          
          // Check if input is the option name
          boolean inputValid = false;
          for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);
            option = option.toLowerCase();
            
            if (cleanedToken == option) {
              value = i + 1;
              inputValid = true;
            }
          }
          
          // Report error since input is not option name
          if (!inputValid) {
            printError(String.format("Expected a number got '%s' instead.", token));
            continue;
          }
        } else {
          continue;
        }
      }
      
      // Bounds checking
      if (value < 1 || value > options.size()) {
        printError(String.format("Number must be in the range 1-%d.", options.size()));
        continue;
      }
      
      valueFound = true;
    } while (!valueFound);
  
    return value - 1;
  }
  
}
