package jpyramid.entity;

import jpyramid.controller.GameSystem;

public final class EntityFactory {

  private EntityFactory(){}

  // Helper enum clients will use to create game objects
  public enum Type {
      PLAYER, LEVEL, AREA;
  }
  
  public static GameEntity create(GameSystem gameSystem, Type type,
      Object[][] argumentsArray) {
    GameEntity entity = new GameEntity();
    
    try {
      if (type == Type.PLAYER) {
        new TransformComponent(entity, gameSystem, argumentsArray[0]);
        new PlayerComponent(entity, gameSystem, argumentsArray[1]);
        gameSystem.registerEntity(entity);
        return entity;
      }
      if (type == Type.LEVEL) {
        new TransformComponent(entity, gameSystem, argumentsArray[0]);
        new LevelComponent(entity, gameSystem, argumentsArray[1]);
        gameSystem.registerEntity(entity);
        return entity;
      }
      if (type == Type.AREA) {
        new TransformComponent(entity, gameSystem, argumentsArray[0]);
        new AreaComponent(entity, gameSystem, argumentsArray[1]);
        gameSystem.registerEntity(entity);
        return entity;
      }
    } catch (EntityException e) {
      e.printStackTrace();
    }
    return null;
  };

}