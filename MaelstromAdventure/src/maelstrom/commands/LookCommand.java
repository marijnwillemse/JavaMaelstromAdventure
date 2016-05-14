package maelstrom.commands;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import maelstrom.controller.GameSystem;
import maelstrom.entity.AreaComponent;
import maelstrom.entity.LevelComponent;
import maelstrom.entity.TimeComponent;
import maelstrom.entity.TransformComponent;
import maelstrom.graph.Direction;
import maelstrom.graph.GridGraphHelper;
import maelstrom.graph.NavigationGraphNode;
import maelstrom.math.Vector2D;
import maelstrom.utilities.GameLocale;

public class LookCommand extends BaseCommand {
  
  private static HashMap<Integer, String> beaufortTags;

  static {
    beaufortTags = new HashMap<Integer, String>();
    beaufortTags.put(0, "TXT_KEY_BEAUFORT_DESCRIPTION_0");
    beaufortTags.put(1, "TXT_KEY_BEAUFORT_DESCRIPTION_1");
    beaufortTags.put(2, "TXT_KEY_BEAUFORT_DESCRIPTION_2");
    beaufortTags.put(3, "TXT_KEY_BEAUFORT_DESCRIPTION_3");
    beaufortTags.put(4, "TXT_KEY_BEAUFORT_DESCRIPTION_4");
    beaufortTags.put(5, "TXT_KEY_BEAUFORT_DESCRIPTION_5");
    beaufortTags.put(6, "TXT_KEY_BEAUFORT_DESCRIPTION_6");
    beaufortTags.put(7, "TXT_KEY_BEAUFORT_DESCRIPTION_7");
    beaufortTags.put(8, "TXT_KEY_BEAUFORT_DESCRIPTION_8");
    beaufortTags.put(9, "TXT_KEY_BEAUFORT_DESCRIPTION_9");
    beaufortTags.put(10, "TXT_KEY_BEAUFORT_DESCRIPTION_10");
    beaufortTags.put(11, "TXT_KEY_BEAUFORT_DESCRIPTION_11");
    beaufortTags.put(12, "TXT_KEY_BEAUFORT_DESCRIPTION_12");
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
    
    TimeComponent timeComponent = gameSystem.getTimeComponents()
        .get(levelID);
    
    // Retrieve the accompanying node
    NavigationGraphNode node = transform.getLocation().getNode();

    // Retrieve the area ID
    UUID areaID = node.getArea().getOwner().getID();
    
    AreaComponent areaComponent = gameSystem.getAreaComponents()
        .get(areaID);

    // Save the node index
    int nodeIndex = node.getIndex();
    
    // Retrieve the position of the node
    Vector2D location = node.getPosition();
    
    describeArea(location, areaComponent);
    describeTime(timeComponent);
    describeDirections(level, nodeIndex);
  }
  
  private void describeArea(Vector2D location, AreaComponent area) {
    System.out.println("You are at sea at position {" + location.getX() +
        "; " + location.getY() + "}.");
    System.out.println(GameLocale.getString(
        beaufortTags.get(area.getWindSpeed())));
  }
  
  private void describeTime(TimeComponent timeComponent) {
    long time = timeComponent.getTimeInMilliseconds();
    long dayTime = time % (86400000);
    int hour = Math.round(dayTime / 3600000);
    if (hour > 5 && hour < 12) {
      // Morning
      System.out.println(GameLocale.getString("TXT_KEY_TIME_DESCRIPTION_MORNING"));
    } else if (hour < 13) {
      // Noon
      System.out.println(GameLocale.getString("TXT_KEY_TIME_DESCRIPTION_NOON"));
    } else if (hour < 18) {
      // Afternoon
      System.out.println(GameLocale.getString("TXT_KEY_TIME_DESCRIPTION_AFTERNOON"));
    } else if (hour < 20) {
      // Evening
      System.out.println(GameLocale.getString("TXT_KEY_TIME_DESCRIPTION_EVENING"));
    } else {
      // Night
      System.out.println(GameLocale.getString("TXT_KEY_TIME_DESCRIPTION_NIGHT"));
    }
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
