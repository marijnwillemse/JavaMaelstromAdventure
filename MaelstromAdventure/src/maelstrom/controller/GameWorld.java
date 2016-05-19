package maelstrom.controller;

import maelstrom.entity.AreaComponent;
import maelstrom.entity.CharacterFactory;
import maelstrom.entity.EntityFactory;
import maelstrom.entity.GameEntity;
import maelstrom.entity.TransformComponent;
import maelstrom.graph.NavigationGraphNode;

public class GameWorld {

  private GameSystem gameSystem;
  private GameEntity level;
  private GameEntity player;

  public GameWorld(GameSystem gameSystem) {
    this.gameSystem = gameSystem;
  }

  public void init() {
    // Create level with dimensions 4 by 4 and time 12:00
    level = createLevel(4, 4, 43200000L);

    // Pick the first area from the level
    AreaComponent area = gameSystem.getLevelComponent(level.getID())
        .getGraph().getNode(0).getArea();

    // Construct the player and place it in that area
    player = CharacterFactory.createPlayer(gameSystem, area);
  }

  private GameEntity createLevel(int xSize, int ySize, long time) {
    Object[][] levelArguments = new Object[][] { { xSize, ySize }, { time } };
    return EntityFactory.createReflective(gameSystem, "LEVEL", levelArguments);
  }

  public GameEntity getPlayer() {
    return player;
  }
  
  public GameEntity getPlayerArea() {
    // Retrieve the player's transform component
    TransformComponent transform = gameSystem.getTransformComponent(
        player.getID());
    
    // Retrieve the accompanying node and the connected area
    NavigationGraphNode node = transform.getLocation().getNode();
    return node.getArea().getOwner();
  }

  public GameEntity getLevel() {
    return level;
  }
}
