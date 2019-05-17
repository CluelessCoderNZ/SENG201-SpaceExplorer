package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.CommandLineParser;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

class CommandLineParserTest {
  

  @Test
  void testInputInt() {
    
    // Test input and output
    String inputSource = "my_string\n"
                       + "500\n"
                       + "0\n"
                       + "a number\n"
                       + "-1\n"
                       + "100\n"
                       + "10\n";
    String[] expectedOutput = {"Custom value: ", 
                               "Error: Expected a number got 'my_string' instead.",
                               "Custom value: ", 
                               "Another value: ",
                               "Bounded value: ",
                               "Error: Expected a number got 'a number' instead.",
                               "Bounded value: ",
                               "Error: Number must be in the range 0-10.",
                               "Bounded value: ",
                               "Error: Number must be in the range 0-10.",
                               "Bounded value: "};
    
    // Setup CommandLineParser
    InputStream inputStream = new ByteArrayInputStream(inputSource.getBytes(StandardCharsets.UTF_8));
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream outputStream = new PrintStream(baos);
    
    CommandLineParser CL = new CommandLineParser(outputStream, inputStream);
    
    // Do Tests
    assertEquals(500, CL.inputInt("Custom value: \n")); // Under normal input user supplies newline character
    assertEquals(0, CL.inputInt("Another value: \n"));
    assertEquals(10, CL.inputInt("Bounded value: \n", 0, 10));
    
    
    // Test output strings
    outputStream.flush();
    Scanner lineScanner = new Scanner(baos.toString());
    for(String expected : expectedOutput) {
      assertEquals(expected, lineScanner.nextLine());
    }
    
    // Check no remaining output
    assertFalse(lineScanner.hasNextLine());
    
    lineScanner.close();
  }
  
  @Test
  void testInputBoolean() {
    
    // Test input and output
    String inputSource = "incorrect\n"
                       + "false\n"
                       + "True\n"
                       + "yES\n"
                       + "N\n"
                       + "1\n";
    String[] expectedOutput = {"Boolean value: ", 
                               "Error: Expected 'Y' or 'N', got 'incorrect'",
                               "Boolean value: ",
                               "Boolean value: ",
                               "Boolean value: ",
                               "Boolean value: ",
                               "Boolean value: "
                               };
    
    // Setup CommandLineParser
    InputStream inputStream = new ByteArrayInputStream(inputSource.getBytes(StandardCharsets.UTF_8));
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream outputStream = new PrintStream(baos);
    
    CommandLineParser CL = new CommandLineParser(outputStream, inputStream);
    
    // Do Tests
    assertEquals(false, CL.inputBoolean("Boolean value: \n")); // Under normal input user supplies newline character
    assertEquals(true, CL.inputBoolean("Boolean value: \n"));
    assertEquals(true, CL.inputBoolean("Boolean value: \n"));
    assertEquals(false, CL.inputBoolean("Boolean value: \n"));
    assertEquals(true, CL.inputBoolean("Boolean value: \n"));
    
    
    // Test output strings
    outputStream.flush();
    Scanner lineScanner = new Scanner(baos.toString());
    for(String expected : expectedOutput) {
      assertEquals(expected, lineScanner.nextLine());
    }
    
    // Check no remaining output
    assertFalse(lineScanner.hasNextLine());
    
    lineScanner.close();
  }

  @Test
  void testInputString() {
    
    // Test input and output
    String inputSource = "Really invalid\n"
                       + "correct\n";
    String[] expectedOutput = {"String value: ", 
                               "Error: Text must be less than or equal to 10 characters, currently 14.",
                               "String value: ",
                               };
    
    // Setup CommandLineParser
    InputStream inputStream = new ByteArrayInputStream(inputSource.getBytes(StandardCharsets.UTF_8));
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream outputStream = new PrintStream(baos);
    
    CommandLineParser CL = new CommandLineParser(outputStream, inputStream);
    
    // Do Tests
    assertEquals("correct", CL.inputString("String value: \n", 10)); // Under normal input user supplies newline character
    
    
    // Test output strings
    outputStream.flush();
    Scanner lineScanner = new Scanner(baos.toString());
    for(String expected : expectedOutput) {
      assertEquals(expected, lineScanner.nextLine());
    }
    
    // Check no remaining output
    assertFalse(lineScanner.hasNextLine());
    
    lineScanner.close();
  }
  
  @Test
  void testInputOptions() {
    
    // Test input and output
    String inputSource = "Error\n"
                       + "0\n"
                       + "5\n"
                       + "  FeiJOa  \n"
                       + "2\n";
                      
    String[] expectedOutput = {"1: Apple", 
                               "2: Bannana",
                               "3: Feijoa",
                               "4: Orange",
                               "Select one: ",
                               "Error: Expected a number got 'Error' instead.",
                               "1: Apple", 
                               "2: Bannana",
                               "3: Feijoa",
                               "4: Orange",
                               "Select one: ",
                               "Error: Number must be in the range 1-4.",
                               "1: Apple", 
                               "2: Bannana",
                               "3: Feijoa",
                               "4: Orange",
                               "Select one: ",
                               "Error: Number must be in the range 1-4.",
                               "1: Apple", 
                               "2: Bannana",
                               "3: Feijoa",
                               "4: Orange",
                               "Select one: ",
                               "1: Simon", 
                               "2: Lewis",
                               "3: Jeff",
                               "Select one: "
                               };
    
    // Setup CommandLineParser
    InputStream inputStream = new ByteArrayInputStream(inputSource.getBytes(StandardCharsets.UTF_8));
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream outputStream = new PrintStream(baos);
    
    CommandLineParser CL = new CommandLineParser(outputStream, inputStream);
    
    // Do Tests
    assertEquals(2, CL.inputOptions("Select one: \n", "Apple", "Bannana", "Feijoa", "Orange")); // Under normal input user supplies newline character
    assertEquals(1, CL.inputOptions("Select one: \n", "Simon", "Lewis", "Jeff"));
    
    // Test output strings
    outputStream.flush();
    Scanner lineScanner = new Scanner(baos.toString());
    for(String expected : expectedOutput) {
      assertEquals(expected, lineScanner.nextLine());
    }
    
    // Check no remaining output
    assertFalse(lineScanner.hasNextLine());
    
    lineScanner.close();
  }
  
}
