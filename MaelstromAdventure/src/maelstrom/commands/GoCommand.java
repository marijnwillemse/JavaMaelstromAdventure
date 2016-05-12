package maelstrom.commands;

import java.util.UUID;

import maelstrom.controller.GameSystem;
import maelstrom.entity.LevelComponent;
import maelstrom.entity.TransformComponent;
import maelstrom.graph.Direction;
import maelstrom.graph.GridGraphHelper;
import maelstrom.graph.NavigationGraphNode;

public class GoCommand extends BaseCommand {

  @Override
  public void execute(GameSystem gameSystem, String[] words) {
    
    // Save direction parameter string
    if (words.length <= 1) {
      System.out.println("Where to?");
      return;
    }
    String dString = words[1].toUpperCase();
    Direction d;
    if (dString.equals("NORTH") || dString.equals("EAST") ||
        dString.equals("SOUTH") || dString.equals("WEST")) {
      d = Direction.valueOf(dString);
    } else {
      System.out.println("That is not a valid direction.");
      return;
    }
    
    // Retrieve the player ID
    UUID playerID = gameSystem.getGameWorld().getPlayer().getID();
    // Retrieve the player's location component
    TransformComponent t = gameSystem.getTransformComponents().get(playerID);
    // Retrieve the accompanying node
    NavigationGraphNode fromNode = t.getLocation().getNode();
    // Save the node index
    int nodeIndex = fromNode.getIndex();
    
    // Get the level
    UUID levelID = gameSystem.getGameWorld().getLevel().getID();
    LevelComponent l = gameSystem.getLevelComponents().get(levelID);

    // Find the node in the specified direction
    NavigationGraphNode toNode;
    toNode = GridGraphHelper.getNeighborByDirection(l.getGraph(), nodeIndex, d);
    
    if (toNode == null) {
      System.out.println("You can not go into that direction.");
      return;
    }
    t.setLocation(toNode.getArea());
  }

}
