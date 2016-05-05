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

    public static GameEntity createReflective(GameSystem gameSystem, String type,
        Object[][] argumentsArray) {
    // This empty game entity will be referenced in the required components,
    // which will be created and constructed according to its requirements.
  
    GameEntity entity = new GameEntity();
    List<String> entityComponents = entityBlueprints.get(type.toUpperCase());

    if (argumentsArray.length != entityComponents.size()) {
      throw new IllegalArgumentException("Expected " + entityComponents.size() +
          " sets of arguments for making entity of type " + type + ".");
    }    

    // Determine the required class names for entity components
    for (int i = 0; i < entityComponents.size(); i++) {
      String className = entityComponents.get(i);
      
      try {
        Class<?> cl = Class.forName(className);
        Constructor<?> cons = cl.getConstructor(GameEntity.class,
            GameSystem.class, Object[].class);
        cons.newInstance(entity, gameSystem, argumentsArray[i]);
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
    }
    gameSystem.registerEntity(entity);
    return entity;
  }
}