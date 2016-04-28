package jpyramid.entity;

import jpyramid.controller.GameSystem;

public final class EntityFactory {

  private EntityFactory(){}

  // Helper enum clients will use to create game objects
  public enum Type {
      PLAYER, ENEMY, LEVEL;
  }
  
  public static GameEntity create(GameSystem gameSystem, Type type,
      Object... arguments) {
    GameEntity entity = new GameEntity();
    
    try {
      if (type == Type.PLAYER) {
        new TransformComponent(entity, gameSystem);
        new PlayerComponent(entity, gameSystem, arguments);
        gameSystem.registerEntity(entity);
        return entity;
      }
      if (type == Type.LEVEL) {
        new TransformComponent(entity, gameSystem);
        new LevelComponent(entity, gameSystem, arguments);
        gameSystem.registerEntity(entity);
        return entity;
      }
    } catch (EntityException e) {
      e.printStackTrace();
    }
    return null;
  };

}