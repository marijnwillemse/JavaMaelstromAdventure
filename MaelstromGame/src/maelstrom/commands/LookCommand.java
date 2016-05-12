package maelstrom.commands;

import java.util.ArrayList;
import java.util.UUID;

import maelstrom.controller.GameSystem;
import maelstrom.entity.LevelComponent;
import maelstrom.entity.TransformComponent;
import maelstrom.graph.Direction;
import maelstrom.graph.GridGraphHelper;
import maelstrom.graph.NavigationGraphNode;
import maelstrom.math.Vector2D;

public class LookCommand extends BaseCommand {

  @Override
  public void execute(GameSystem gameSystem, String[] words) {
    
    // Retrieve the player ID
    UUID playerID = gameSystem.getGameWorld().getPlayer().getID();
    // Retrieve the player's location component
    TransformComponent t = gameSystem.getTransformComponents().get(playerID);
    // Retrieve the accompanying node
    NavigationGraphNode node = t.getLocation();
    // Save the node index
    int nodeIndex = node.getIndex();
    // Retrieve the position of the node
    Vector2D location = node.getPosition();
        
    // Describe the room
    System.out.println("You are at sea at position {" + location.getX() +
        "; " + location.getY() + "}.");
    
    // Describe the neighboring directions
    UUID levelID = gameSystem.getGameWorld().getLevel().getID();
    LevelComponent l = gameSystem.getLevelComponents().get(levelID);
    ArrayList<Direction> directions =
        GridGraphHelper.getNeighborDirections(l.getGraph(), nodeIndex);
    if (directions.size() > 0) {
      String message = "You can go ";
      for (int i = 0; i < directions.size(); i++) {
        Direction d = directions.get(i);
        message += d.toString();
        if (directions.size()-2 > i) {
          message += ", ";
        } else if (directions.size()-2 == i) {
          message += " and ";
        } else {
          message += ".";
        }
      }
      System.out.println(message);
    }
  }

}
