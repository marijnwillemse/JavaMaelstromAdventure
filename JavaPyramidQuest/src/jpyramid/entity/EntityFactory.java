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
        checkArgumentsLength(argumentsArray, 2, type);
        new TransformComponent(entity, gameSystem, argumentsArray[0]);
        new PlayerComponent(entity, gameSystem, argumentsArray[1]);
        gameSystem.registerEntity(entity);
        return entity;
      }
      if (type == Type.LEVEL) {
        checkArgumentsLength(argumentsArray, 2, type);
        new TransformComponent(entity, gameSystem, argumentsArray[0]);
        new LevelComponent(entity, gameSystem, argumentsArray[1]);
        gameSystem.registerEntity(entity);
        return entity;
      }
      if (type == Type.AREA) {
        checkArgumentsLength(argumentsArray, 2, type);
        new TransformComponent(entity, gameSystem, argumentsArray[0]);
        new AreaComponent(entity, gameSystem, argumentsArray[1]);
        gameSystem.registerEntity(entity);
        return entity;
      }
    } catch (EntityException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static void checkArgumentsLength(Object[][] argumentsArray, int i,
      Type type) {
    if (argumentsArray.length != i) {
      throw new IllegalArgumentException(i +
          " sets of arguments needed for making entity of type "
          + type + ". " + argumentsArray.length + " was given.");
    }
  };

}