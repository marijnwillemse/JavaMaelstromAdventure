package turbulent.commands;

import java.util.HashMap;

import turbulent.controller.GameSystem;
import turbulent.controller.InterpreterSystem;

public class HelpCommand extends BaseCommand {

  @Override
  public void execute(GameSystem gameSystem, String[] words) {
    
    System.out.println("You can use the following commands:");
    HashMap<String, BaseCommand> commands = InterpreterSystem.getCommands();
    String string = "";
    
    for (String key : commands.keySet()) {
        string += key.toLowerCase() + ", ";
    }
    // Now we remove the last comma & space and capitalize the first letter
    String firstLetter = string.substring(0, 1);
    string = firstLetter.toUpperCase() + string.substring(1, string.length()-2);
    // And put the cherry on the cake
    string += ".";
    System.out.println(string);

  }

}
