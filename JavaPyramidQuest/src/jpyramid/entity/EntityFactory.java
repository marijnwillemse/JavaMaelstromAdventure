package jpyramid.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.stream.JsonReader;

import jpyramid.commands.BaseCommand;
import jpyramid.commands.LookCommand;
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

  private EntityFactory() {
    /* Draft entity blueprints with the data from descriptive JSON file */
    EntityInfoParser parser = new EntityInfoParser();
    List<EntityInfo> entityInfoList = null;
    try {
      entityInfoList = parser.readEntityInfoStream(
          new FileInputStream(new File("/jpyramid/resources/entities.json")));
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Unable to read entity info from file");
    }
    
    for (EntityInfo i : entityInfoList) {
      entityBlueprints.put(i.getId(), i.getComponentNames());
    }
  }

  public static GameEntity create(String entityType, GameSystem gameSystem,
      ArrayList<Object[]> parameters) {
    // This empty game entity will be referenced in the required components,
    // which will be created and constructed according to its requirements.
    GameEntity entity = new GameEntity();
    List<String> entityComponents = entityBlueprints.get(entityType);

    // Determine the required class names for entity components
    for ()

    String className = "";
    try {
      Class<?> cl = Class.forName(className);
      Constructor<?> cons = cl.getConstructor(String.class);
      return (GameEntity) cons.newInstance(parameters);
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


  public static GameEntity create2(GameSystem gameSystem, Type type,
      Object[]... parameterCollection) {
    GameEntity entity = new GameEntity();

    try {
      if (type == Type.PLAYER) {
        new TransformComponent(entity, gameSystem, parameterCollection[0]);
        new PlayerComponent(entity, gameSystem, parameterCollection[1]);
        gameSystem.registerEntity(entity);
        return entity;
      }
      if (type == Type.LEVEL) {
        new TransformComponent(entity, gameSystem, parameterCollection[0]);
        new LevelComponent(entity, gameSystem, parameterCollection[1]);
        gameSystem.registerEntity(entity);
        return entity;
      }
      if (type == Type.ROOM) {
        new TransformComponent(entity, gameSystem, parameterCollection[0]);
        new RoomComponent(entity, gameSystem, parameterCollection[1]);
        gameSystem.registerEntity(entity);
        return entity;
      }
    } catch (EntityException e) {
      e.printStackTrace();
    }
    return null;
  };

}