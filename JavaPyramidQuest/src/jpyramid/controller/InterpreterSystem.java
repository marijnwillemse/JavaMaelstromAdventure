package jpyramid.controller;

import java.util.ArrayList;
import java.util.HashMap;

import jpyramid.commands.BaseCommand;
import jpyramid.commands.LookCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InterpreterSystem {

  private HashMap<String, BaseCommand> commands;
  
  public InterpreterSystem() {
    commands = new HashMap<String, BaseCommand>();
    commands.put("LOOK", new LookCommand());
  }
  
  public void update(GameSystem gameSystem) {
    String input = read();
    ArrayList<String> parameters = new ArrayList<String>();
    if (commands.containsKey(input)) {
      commands.get(input).execute(gameSystem, parameters);
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

}
