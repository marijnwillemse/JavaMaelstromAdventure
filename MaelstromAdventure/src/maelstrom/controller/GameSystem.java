package maelstrom.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import maelstrom.entity.AreaComponent;
import maelstrom.entity.BaseComponent;
import maelstrom.entity.DialogueComponent;
import maelstrom.entity.GameEntity;
import maelstrom.entity.LevelComponent;
import maelstrom.entity.PlayerComponent;
import maelstrom.entity.TransformComponent;
import maelstrom.utilities.GameLocale;

public class GameSystem {

  private static boolean playing = false;

  // All entities are registered in a single HashMap
  private Map<UUID, GameEntity> entities =
      new HashMap<UUID, GameEntity>();

  // All components owned by entities are stored in seperate HashMaps
  private Map<UUID, TransformComponent> transformComponents =
      new HashMap<UUID, TransformComponent>();
  private Map<UUID, PlayerComponent> playerComponents =
      new HashMap<UUID, PlayerComponent>();
  private Map<UUID, LevelComponent> levelComponents =
      new HashMap<UUID, LevelComponent>();
  private Map<UUID, DialogueComponent> dialogueComponents =
      new HashMap<UUID, DialogueComponent>();
  private Map<UUID, AreaComponent> areaComponents =
      new HashMap<UUID, AreaComponent>();

  // Processing systems perform operations on the data in the components
  private GameWorld gameWorld = new GameWorld(this);
  private InterpreterSystem interpreterSystem = new InterpreterSystem();

  public GameSystem(String language) {
    try {
      GameLocale.load(language);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Language " + language + " not found in assets.");
      System.exit(0);
    }
    entities = new HashMap<UUID, GameEntity>();
  }

  /* Setup game entities and execute game logic */
  public void run() {
    gameWorld.init();
    playing = true;
    System.out.print("Welcome to Maelstrom Adventure.\n\n");
    do {
      // Process commands
      interpreterSystem.update(this);
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
    if (component instanceof AreaComponent) {
      areaComponents.put(component.getOwner().getID(),
          (AreaComponent) component);
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
    if (component instanceof AreaComponent) {
      areaComponents.remove(component.getOwner().getID(),
          (AreaComponent) component);
    }
  }

  /* Component containers getters */

  public Map<UUID, TransformComponent> getTransformComponents() {
    return transformComponents;
  }

  public Map<UUID, PlayerComponent> getPlayerComponents() {
    return playerComponents;
  }

  public Map<UUID, LevelComponent> getLevelComponents() {
    return levelComponents;
  }

  public Map<UUID, DialogueComponent> getDialogueComponents() {
    return dialogueComponents;
  }
  
  public Map<UUID, AreaComponent> getAreaComponents() {
    return areaComponents;
  }
  
  /* Game related functions */
  
  public GameWorld getGameWorld() {
    return gameWorld;
  }
  public void stopPlaying() {
    GameSystem.playing = false;
  }

}