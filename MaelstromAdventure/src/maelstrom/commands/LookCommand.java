package maelstrom.commands;

import java.util.List;
import java.util.UUID;

import maelstrom.controller.GameSystem;
import maelstrom.entity.AreaComponent;
import maelstrom.entity.LevelComponent;
import maelstrom.entity.TimeComponent;
import maelstrom.entity.TransformComponent;
import maelstrom.graph.NavigationGraphNode;

public class LookCommand extends BaseCommand {
  
  @Override
  public void execute(GameSystem gameSystem, List<String> words) {
    
    // Retrieve the player ID
    UUID playerID = gameSystem.getGameWorld().getPlayer().getID();
    TransformComponent transform = gameSystem.getTransformComponent(playerID);
    
    // Retrieve the player's location component
    UUID levelID = gameSystem.getGameWorld().getLevel().getID();
    LevelComponent level = gameSystem.getLevelComponent(levelID);
    
    TimeComponent timeComponent = gameSystem.getTimeComponent(levelID);
    
    // Retrieve the accompanying node
    NavigationGraphNode node = transform.getLocation().getNode();

    // Retrieve the area ID
    UUID areaID = node.getArea().getOwner().getID();
    
    AreaComponent areaComponent = gameSystem.getAreaComponent(areaID);

    // Save the node index
    int nodeIndex = node.getIndex();
    
    timeComponent.printTime();
    areaComponent.printWeatherDescription();
    areaComponent.printEnemies(gameSystem);
    level.describeAvailableDirections(nodeIndex);
  }
}
