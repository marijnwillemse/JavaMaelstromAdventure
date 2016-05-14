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
import maelstrom.utilities.GameLocale;

public class LookCommand extends BaseCommand {
  
  private static final HashMap<Integer, String> areaDescriptions;
  static {
    areaDescriptions = new HashMap<Integer, String>();
    areaDescriptions.put(0, "TXT_KEY_BEAUFORT_DESCRIPTION_0");
    areaDescriptions.put(1, "TXT_KEY_BEAUFORT_DESCRIPTION_1");
    areaDescriptions.put(2, "TXT_KEY_BEAUFORT_DESCRIPTION_2");
    areaDescriptions.put(3, "TXT_KEY_BEAUFORT_DESCRIPTION_3");
    areaDescriptions.put(4, "TXT_KEY_BEAUFORT_DESCRIPTION_4");
    areaDescriptions.put(5, "TXT_KEY_BEAUFORT_DESCRIPTION_5");
    areaDescriptions.put(6, "TXT_KEY_BEAUFORT_DESCRIPTION_6");
    areaDescriptions.put(7, "TXT_KEY_BEAUFORT_DESCRIPTION_7");
    areaDescriptions.put(8, "TXT_KEY_BEAUFORT_DESCRIPTION_8");
    areaDescriptions.put(9, "TXT_KEY_BEAUFORT_DESCRIPTION_9");
    areaDescriptions.put(10, "TXT_KEY_BEAUFORT_DESCRIPTION_10");
    areaDescriptions.put(11, "TXT_KEY_BEAUFORT_DESCRIPTION_11");
    areaDescriptions.put(12, "TXT_KEY_BEAUFORT_DESCRIPTION_12");
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
    System.out.println(GameLocale.getString(
        areaDescriptions.get(area.getWindSpeed())));
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
