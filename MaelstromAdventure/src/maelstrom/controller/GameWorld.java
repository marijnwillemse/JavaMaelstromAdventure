package maelstrom.controller;

import maelstrom.entity.AreaComponent;
import maelstrom.entity.EntityFactory;
import maelstrom.entity.GameEntity;

public class GameWorld {
  
  private GameSystem gameSystem;
  private GameEntity level;
  private GameEntity player;

  public GameWorld(GameSystem gameSystem) {
    this.gameSystem = gameSystem;
  }
  
  public void init() {
    // Create the level
    Object[][] levelArguments = new Object[][] {
      { 4, 4 }, // Level size of 4x4
      { 43200000L } // Set time to 12:00
    };
    level = EntityFactory.createReflective(gameSystem, "LEVEL",
        levelArguments);
    
    // Pick the first area from the level
    AreaComponent area = gameSystem.getLevelComponents().get(level.getID())
        .getGraph().getNode(0).getArea();
    
    // Construct the player and place it in that area
    Object[][] playerArguments = new Object[][] {
      { area }, // Picked area
      { 100 }, // Give character 100 health
      {} // Player component arguments
    };
    player = EntityFactory.createReflective(gameSystem, "PLAYER",
        playerArguments);
  }
  
  public GameEntity getPlayer() {
    return player;
  }

  public GameEntity getLevel() {
    return level;
  }
}
