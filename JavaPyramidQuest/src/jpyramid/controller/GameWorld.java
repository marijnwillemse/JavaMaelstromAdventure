package jpyramid.controller;

import jpyramid.entity.EntityException;
import jpyramid.entity.EntityFactory;
import jpyramid.entity.GameEntity;
import jpyramid.graph.GraphException;

public class GameWorld {
  
  private GameSystem gameSystem;

  public GameWorld(GameSystem gameSystem) {
    this.gameSystem = gameSystem;
  }
  
  public void init() {
    // Construct a 4x4 level and a player
    GameEntity level = EntityFactory.create(
        gameSystem, EntityFactory.Type.LEVEL,
        new Object[][] { {}, { 4, 4 }});
    GameEntity player = EntityFactory.create(
        gameSystem, EntityFactory.Type.PLAYER,
        new Object[2][0]);
    
    // Place the player in the first room
    try {
      gameSystem.getTransformComponents().get(player.getID())
          .setLocation(gameSystem.getLevelComponents().get(level.getID())
              .getGraph().getNode(0));
    } catch (GraphException e) {
      e.printStackTrace();
    } catch (EntityException e) {
      e.printStackTrace();
    }
  }
}
