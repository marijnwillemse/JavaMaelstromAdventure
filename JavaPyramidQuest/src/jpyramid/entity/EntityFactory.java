package jpyramid.entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import jpyramid.controller.GameSystem;
import jpyramid.utilities.EntityInfo;
import jpyramid.utilities.EntityInfoParser;

/* 
 * This factory object is a simulated static class. It has been declared final,
 * preventing class extension, the constructor has been made private,
 * preventing instantiation and all members and functions are declared as
 * static.
 */
public final class EntityFactory {

  // Mapping of entity types to a list of classnames of its required components
  private static HashMap<String, List<String>> entityBlueprints =
      new HashMap<String, List<String>>();

  private EntityFactory() {}
  
  static {
    /* Draft entity blueprints with the data from descriptive JSON file */
    EntityInfoParser parser = new EntityInfoParser();
    
    InputStream in;
    try {
      in = new FileInputStream("src/jpyramid/resources/entities.json");

      List<EntityInfo> entityInfoList = parser.readEntityInfoStream(in);

      for (EntityInfo i : entityInfoList) {
        entityBlueprints.put(i.getId(), i.getComponentNames());
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Unable to read entity info");
    }
  }

    public static GameEntity createReflective(GameSystem gameSystem, Type type,
        Object[][] argumentsArray) {
    // This empty game entity will be referenced in the required components,
    // which will be created and constructed according to its requirements.
  
    GameEntity entity = new GameEntity();
    List<String> entityComponents = entityBlueprints.get(type);

    // Determine the required class names for entity components
    for (String s : entityComponents) {
      
    }

    String className = "";
    try {
      Class<?> cl = Class.forName(className);
      Constructor<?> cons = cl.getConstructor(String.class);
      return (GameEntity) cons.newInstance(argumentsArray[0]);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.out.println("Unable to find class " + className);
    } catch (NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
      System.out.println("Unable to create constructor for class " + className);
    } catch (InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException e) {
      System.out.println("Unable to create and initialize class " + className);
      e.printStackTrace();
    }

    return null;

  }

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