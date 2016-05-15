package maelstrom.commands;

import java.util.UUID;

import maelstrom.controller.GameSystem;
import maelstrom.entity.AreaComponent;
import maelstrom.entity.LevelComponent;
import maelstrom.entity.TimeComponent;
import maelstrom.entity.TransformComponent;
import maelstrom.graph.NavigationGraphNode;

public class LookCommand extends BaseCommand {
  
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
    
    timeComponent.describe();
    areaComponent.describeWeather();
    areaComponent.describeEntities();
    level.describeAvailableDirections(nodeIndex);
  }
}
