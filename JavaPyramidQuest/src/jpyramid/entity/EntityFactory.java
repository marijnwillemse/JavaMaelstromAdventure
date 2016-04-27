package jpyramid.entity;

import jpyramid.controller.GameWorld;

public final class EntityFactory {

  private EntityFactory(){}

  // Helper enum clients will use to create game objects
  public enum Type {
      PLAYER, ENEMY, LEVEL;
  }
  
  public static GameEntity create(GameWorld gameWorld, Type type,
      Object... arguments) {
    GameEntity entity = new GameEntity();
    
    try {
      if (type == Type.PLAYER) {
        new TransformComponent(entity, gameWorld);
        new PlayerComponent(entity, gameWorld, arguments);
        gameWorld.registerEntity(entity);
        return entity;
      }
      if (type == Type.LEVEL) {
        new TransformComponent(entity, gameWorld);
        new LevelComponent(entity, gameWorld, arguments);
        gameWorld.registerEntity(entity);
        return entity;
      }
    } catch (EntityException e) {
      e.printStackTrace();
    }
    return null;
  };

}