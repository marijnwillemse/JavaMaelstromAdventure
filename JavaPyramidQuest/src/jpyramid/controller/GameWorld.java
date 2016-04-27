package jpyramid.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jpyramid.entity.BaseComponent;
import jpyramid.entity.DialogueComponent;
import jpyramid.entity.EntityException;
import jpyramid.entity.EntityFactory;
import jpyramid.entity.GameEntity;
import jpyramid.entity.LevelComponent;
import jpyramid.entity.TransformComponent;
import jpyramid.entity.PlayerComponent;
import jpyramid.graph.GraphException;


public class GameWorld {
  
  private static boolean playing = false;

  /* All entities are registered in a single HashMap */
  private Map<UUID, GameEntity> entities =
      new HashMap<UUID, GameEntity>();
  
  /* So are all of the components owned by the entities */
  private Map<UUID, TransformComponent> transformComponents =
      new HashMap<UUID, TransformComponent>();
  private Map<UUID, PlayerComponent> playerComponents =
      new HashMap<UUID, PlayerComponent>();
  private Map<UUID, LevelComponent> levelComponents =
      new HashMap<UUID, LevelComponent>();
  private Map<UUID, DialogueComponent> dialogueComponents =
      new HashMap<UUID, DialogueComponent>();

  public GameWorld() {
    entities = new HashMap<UUID, GameEntity>();
  }

  /* Setup game entities and execute game logic */
  public void run() {
    // Construct a 4x4 level and a player
    GameEntity level = EntityFactory.create(
        this, EntityFactory.Type.LEVEL, new Object[] { 4, 4 });
    GameEntity player = EntityFactory.create(
        this, EntityFactory.Type.PLAYER, new Object[0]);
    
    // Place the player in the first room
    try {
      transformComponents.get(player.getID()).setLocation(
         levelComponents.get(level.getID()).getGraph().getNode(0));
    } catch (GraphException e) {
      e.printStackTrace();
    } catch (EntityException e) {
      e.printStackTrace();
    }
    
    // TODO: Execute dialogues and process console input as commands
    playing = true;
    System.out.println("Welcome to PyramidQuest.");
    do {
      /* Print dialogue */
      // Check up on the player and print relevant information
      
      
      /* Handle input */
    } while (playing);
  }

  public GameEntity getEntity(int id) {
    return entities.get(id);
  }
  

  /* Every entity will register itself upon construction */
  public void registerEntity(GameEntity entity) {
    entities.put(entity.getID(), entity);
  }

  /* Every entity will deregister itself upon removal */
  public void deregisterEntity(GameEntity entity) {
      entities.remove(entity.getID(), entity);
  }

  /* Every component will register itself upon construction */
  public void registerComponent(BaseComponent component) {
    if (component instanceof TransformComponent) {
      transformComponents.put(component.getOwner().getID(),
          (TransformComponent) component);
    }
    if (component instanceof PlayerComponent) {
      playerComponents.put(component.getOwner().getID(),
          (PlayerComponent) component);
    }
    if (component instanceof LevelComponent) {
      levelComponents.put(component.getOwner().getID(),
          (LevelComponent) component);
    }
    if (component instanceof DialogueComponent) {
      dialogueComponents.put(component.getOwner().getID(),
          (DialogueComponent) component);
    }
  }

  /* Every component will deregister itself upon removal */
  public void deregisterComponent(BaseComponent component) {
    if (component instanceof TransformComponent) {
      transformComponents.remove(component.getOwner().getID(),
          (TransformComponent) component);
    }
    if (component instanceof PlayerComponent) {
      playerComponents.remove(component.getOwner().getID(),
          (PlayerComponent) component);
    }
    if (component instanceof LevelComponent) {
      levelComponents.remove(component.getOwner().getID(),
          (LevelComponent) component);
    }
    if (component instanceof DialogueComponent) {
      dialogueComponents.remove(component.getOwner().getID(),
          (DialogueComponent) component);
    }
  }
}