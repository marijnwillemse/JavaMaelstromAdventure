package maelstrom.controller;

import java.util.List;

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
    int difficulty = 1;
    // Create level with dimensions 4 by 4, time 12:00 and the difficulty
    level = createLevel(4, 4, difficulty, 43200000L);

    // Pick the first area from the level
    AreaComponent area = gameSystem.getLevelComponent(level.getID())
        .getGraph().getNode(0).getArea();

    // Construct the player and place it in that area
    player = CharacterFactory.createPlayer(gameSystem, area);
  }

  private GameEntity createLevel(int xSize, int ySize, int difficulty, long time) {
    Object[][] levelArguments = new Object[][] {
      { xSize, ySize, difficulty },
      { time }
    };
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

  public void update() {
    if (gameSystem.getCharacterComponent(player.getID()).getHealth() <= 0) {
      System.out.println("The player is dead. Game over!");
      gameSystem.stopPlaying();
      return;
    }
    
    List<GameEntity> characters = gameSystem.getAreaComponent(
        getPlayerArea().getID()).getEntities();
    
    // Handle turn of each character in player area
    for (GameEntity entity : characters) {
      gameSystem.getCharacterComponent(entity.getID()).update();
    }
  }
}
