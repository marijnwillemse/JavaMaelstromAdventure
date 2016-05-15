package maelstrom.controller;

import java.util.HashMap;

import maelstrom.commands.BaseCommand;
import maelstrom.commands.GoCommand;
import maelstrom.commands.HelpCommand;
import maelstrom.commands.InspectCommand;
import maelstrom.commands.LookCommand;
import maelstrom.commands.StopCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InterpreterSystem {

  private static HashMap<String, BaseCommand> commands;
  private static HashMap<String, String> synonyms;
  
  static {
    commands = new HashMap<String, BaseCommand>();
    commands.put("GO", new GoCommand());
    commands.put("HELP", new HelpCommand());
    commands.put("INSPECT", new InspectCommand());
    commands.put("LOOK", new LookCommand());
    commands.put("STOP", new StopCommand());
    
    synonyms = new HashMap<String, String>();
    synonyms.put("?",       "HELP");
    synonyms.put("EXAMINE", "INSPECT");
    synonyms.put("INSPECT", "LOOK");
    synonyms.put("VIEW",    "LOOK");
    synonyms.put("ESCAPE",  "STOP");
    synonyms.put("EXIT",    "STOP");
    synonyms.put("MOVE",    "GO");
    synonyms.put("WALK",    "GO");
  }
  
  public InterpreterSystem() {}
  
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
