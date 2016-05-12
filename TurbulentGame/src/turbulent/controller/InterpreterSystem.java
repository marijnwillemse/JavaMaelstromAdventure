package turbulent.controller;

import java.util.HashMap;

import turbulent.commands.BaseCommand;
import turbulent.commands.GoCommand;
import turbulent.commands.HelpCommand;
import turbulent.commands.LookCommand;
import turbulent.commands.StopCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InterpreterSystem {

  private static HashMap<String, BaseCommand> commands;
  
  private static HashMap<String, String> synonyms;
  
  public InterpreterSystem() {
    commands = new HashMap<String, BaseCommand>();
    commands.put("GO", new GoCommand());
    commands.put("HELP", new HelpCommand());
    commands.put("LOOK", new LookCommand());
    commands.put("STOP", new StopCommand());
    
    synonyms = new HashMap<String, String>();
    synonyms.put("MOVE",    "GO");
    synonyms.put("WALK",    "GO");
    synonyms.put("?",       "HELP");
    synonyms.put("VIEW",    "LOOK");
    synonyms.put("EXAMINE", "LOOK");
    synonyms.put("INSPECT", "LOOK");
    synonyms.put("EXIT",    "STOP");
    synonyms.put("ESCAPE",  "STOP");
  }
  
  
  public void update(GameSystem gameSystem) {
    String input = read();
    String[] words = input.split("\\s"); // Split at every space
    // Interpret the first word as a command
    if (commands.containsKey(words[0])) {
      // The first word is in the dictionary
      commands.get(words[0]).execute(gameSystem, words);
    } else if (synonyms.containsKey(words[0])) {
      // The first word is a synonym
      commands.get(synonyms.get(words[0])).execute(gameSystem, words);
    } else {
      System.out.println("That is no verb I recognize.");
    }
    System.out.println();
  }
  
  private String read() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    System.out.print(">");
    try {
      return br.readLine().toUpperCase();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Failed to receive input");
    }
    return null;
  }
  
  public static HashMap<String, BaseCommand> getCommands() {
    return commands;
  }

}
