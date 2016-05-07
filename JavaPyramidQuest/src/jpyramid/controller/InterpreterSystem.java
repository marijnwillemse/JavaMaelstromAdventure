package jpyramid.controller;

import java.util.HashMap;

import jpyramid.commands.BaseCommand;
import jpyramid.commands.GoCommand;
import jpyramid.commands.HelpCommand;
import jpyramid.commands.LookCommand;
import jpyramid.commands.StopCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InterpreterSystem {

  private static HashMap<String, BaseCommand> commands;
  
  public InterpreterSystem() {
    commands = new HashMap<String, BaseCommand>();
    commands.put("GO", new GoCommand());
    commands.put("HELP", new HelpCommand());
    commands.put("LOOK", new LookCommand());
    commands.put("STOP", new StopCommand());
  }
  
  public void update(GameSystem gameSystem) {
    String input = read();
    String[] words = input.split("\\s"); // Split at every space
    if (commands.containsKey(words[0])) {
      commands.get(words[0]).execute(gameSystem, words);
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
