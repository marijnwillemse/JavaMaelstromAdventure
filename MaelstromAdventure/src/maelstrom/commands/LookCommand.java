package maelstrom.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import maelstrom.controller.GameSystem;
import maelstrom.entity.AreaComponent;
import maelstrom.entity.LevelComponent;
import maelstrom.entity.TransformComponent;
import maelstrom.graph.Direction;
import maelstrom.graph.GridGraphHelper;
import maelstrom.graph.NavigationGraphNode;
import maelstrom.math.Vector2D;

public class LookCommand extends BaseCommand {
  
  private static final HashMap<Integer, String> areaDescriptions;
  static {
    areaDescriptions = new HashMap<Integer, String>();
    areaDescriptions.put(0, "The sea is clear as a mirror.");
    areaDescriptions.put(1, "Slight ripples are visible in the sea.");
    areaDescriptions.put(2, "A light breeze causes the sails to rustle.");
    areaDescriptions.put(3, "There is a steady breeze.");
    areaDescriptions.put(4, "A moderate breeze is revealing whitecaps.");
    areaDescriptions.put(5, "A fresh wind is blowing through the sails.");
    areaDescriptions.put(6, "You can hear a howling whistle in the strong wind.");
    areaDescriptions.put(7, "Streaks of white foam appear in the stormy wind.");
    areaDescriptions.put(8, "The waves in the storm make your ship rock back and forth.");
    areaDescriptions.put(9, "A blinding spray drives in the raging storm.");
    areaDescriptions.put(10, "Your ship is barely holding the fury of the storm.");
    areaDescriptions.put(11, "The sea is heaving in mountainous waves from the blasting hurricane.");
    areaDescriptions.put(12, "You are in the most terrible hurricane ever to come out of the heavens.");
  }
  

  @Override
  public void execute(GameSystem gameSystem, String[] words) {
    
    // Retrieve the player ID
    UUID playerID = gameSystem.getGameWorld().getPlayer().getID();
    TransformComponent transform = gameSystem.getTransformComponents()
        .get(playerID);
    
    // Retrieve the player's location component
    UUID levelID = gameSystem.getGameWorld().getLevel().getID();
    LevelComponent level = gameSystem.getLevelComponents().get(levelID);
    
    // Retrieve the accompanying node
    NavigationGraphNode node = transform.getLocation().getNode();

    AreaComponent area = gameSystem.getAreaComponents()
        .get(node.getArea().getOwner().getID());

    // Save the node index
    int nodeIndex = node.getIndex();
    
    // Retrieve the position of the node
    Vector2D location = node.getPosition();
    
    describeArea(location, area);
    describeDirections(level, nodeIndex);
  }
  
  private void describeArea(Vector2D location, AreaComponent area) {
    System.out.println("You are at sea at position {" + location.getX() +
        "; " + location.getY() + "}.");
    System.out.println(areaDescriptions.get(area.getWindSpeed()));
  }
  
  private void describeDirections(LevelComponent level, int nodeIndex) {
    ArrayList<Direction> directions =
        GridGraphHelper.getNeighborDirections(level.getGraph(), nodeIndex);
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
