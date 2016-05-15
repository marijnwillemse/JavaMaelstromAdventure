package maelstrom.entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import maelstrom.controller.GameSystem;
import maelstrom.utilities.EntityInfo;
import maelstrom.utilities.EntityInfoParser;

/* 
 * This factory object is a simulated static class. It has been declared final,
 * preventing class extension, the constructor has been made private,
 * preventing instantiation and all members and functions are declared as
 * static.
 * 
 * The purpose of this factory is to instantiate specific game entity objects,
 * defined by the configuration of coupled components. Entity objects can be
 * ordered by blueprint name.
 */
public final class EntityFactory {

  // Mapping of entity types to a list of classnames of its required components
  private static HashMap<String, List<String>> entityBlueprints =
      new HashMap<String, List<String>>();

  private EntityFactory() {}

  // Because this object's constructor will never be executed, a simple static
  // function adopts over the initialization
  static {
    /* Draft entity blueprints with the data from descriptive JSON file */
    EntityInfoParser parser = new EntityInfoParser();

    InputStream in;
    try {
      in = new FileInputStream("src/maelstrom/assets/entities.json");
      // Use the parser object to read entity info into designated info objects
      List<EntityInfo> entityInfoList = parser.readEntityInfoStream(in);

      // Map the information into the blueprint list
      for (EntityInfo i : entityInfoList) {
        entityBlueprints.put(i.getId(), i.getComponentNames());
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Unable to read entity blueprint information");
    }
  }

  /* 
   * Creates game entities by blueprint name. A new empty game entity gets
   * referenced in the components required as stated in the accompanying
   * blueprint.
   */
  public static GameEntity createReflective(GameSystem gameSystem,
      String name, Object[][] argumentsArray) {
    // Empty game entity object
    GameEntity entity = new GameEntity();
    // Get list of required components
    List<String> entityComponents = entityBlueprints.get(name.toUpperCase());

    // Check if the amount of argument sets is equal to the required components.
    if (argumentsArray.length != entityComponents.size()) {
      throw new IllegalArgumentException("Expected " + entityComponents.size() +
          " sets of arguments for making entity of type " + name + ".");
    }

    // Create each of the required components
    for (int i = 0; i < entityComponents.size(); i++) {
      String className = entityComponents.get(i);

      try {
        // Determine the required class
        Class<?> cl = Class.forName(className);
        // Create the constructor for that class
        Constructor<?> cons = cl.getConstructor(GameEntity.class,
            GameSystem.class, Object[].class);
        // Make a new instance based on the constructor with provided arguments.
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