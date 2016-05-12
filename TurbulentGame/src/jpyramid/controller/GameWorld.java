package jpyramid.controller;

import jpyramid.entity.EntityFactory;
import jpyramid.entity.GameEntity;
import jpyramid.graph.NavigationGraphNode;

public class GameWorld {
  
  private GameSystem gameSystem;
  private GameEntity level;
  private GameEntity player;

  public GameWorld(GameSystem gameSystem) {
    this.gameSystem = gameSystem;
  }
  
  public void init() {
    // Construct the level
    Object[][] levelArguments = new Object[][] {
      {},
      { 4, 4 }
    };
//    level = EntityFactory.create(
//        gameSystem, EntityFactory.Type.LEVEL,
//        levelArguments);
    level = EntityFactory.createReflective(
        gameSystem, "LEVEL",
        levelArguments);
    
    // Pick the first area from the level
    NavigationGraphNode area = gameSystem.getLevelComponents().get(level.getID())
        .getGraph().getNode(0);
    
    // Construct the player and place it in that area
    Object[][] playerArguments = new Object[][] {
      { area },
      {}
    };
    player = EntityFactory.createReflective(
        gameSystem, "PLAYER",
        playerArguments);
  }
  
  public GameEntity getPlayer() {
    return player;
  }

  public GameEntity getLevel() {
    return level;
  }
}
