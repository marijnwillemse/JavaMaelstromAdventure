package maelstrom.entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import maelstrom.controller.GameSystem;
import maelstrom.gameinfo.EnemyInfo;
import maelstrom.gameinfo.GameInfoParser;
import maelstrom.gameinfo.StatModifier;

public class CharacterFactory {

  // Mapping of enemy types to a list of classnames of its required components
  private static HashMap<String, EnemyInfo> enemyInfoMap = new HashMap<>();

  private static HashMap<String, StatModifier> statModifierMap =
      new HashMap<>();

  private CharacterFactory() {}

  static {
    /* Draft enemy blueprints with the data from descriptive JSON file */
    GameInfoParser parser = new GameInfoParser();

    InputStream in;
    try {
      in = new FileInputStream("src/maelstrom/assets/enemies.json");
      // Use the parser object to read entity info into designated info objects
      List<EnemyInfo> enemyInfoList = parser.readInfoStream(EnemyInfo.class, in);

      // Map the information into the blueprint list
      for (EnemyInfo i : enemyInfoList) {
        enemyInfoMap.put(i.getName(), i);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Unable to read enemy information from JSON file.");
    }
    try {
      in = new FileInputStream("src/maelstrom/assets/stat_modifiers.json");
      // Use the parser object to read entity info into designated info objects
      List<StatModifier> statModifierList = parser.readInfoStream(
          StatModifier.class, in);

      // Map the information into the blueprint list
      for (StatModifier i : statModifierList) {
        statModifierMap.put(i.getAffix(), i);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Unable to read stat modifiers from JSON file.");
    }
  }

  public static GameEntity createEnemy(GameSystem gameSystem, String name,
      AreaComponent areaComponent, int difficulty) {
    if (enemyInfoMap.containsKey(name) == false) {
      throw new IllegalArgumentException("Enemy name not found");
    }
    EnemyInfo enemyInfo = enemyInfoMap.get(name);
    
    Object[][] argumentsArray = new Object[][] {
      { areaComponent },
      {
        enemyInfo.getStamina(),
        true,
        enemyInfo.getName()
      }
    };
    return EntityFactory.createReflective(gameSystem, "Character",
        argumentsArray);
  }

  /**
   * Create a monster randomly picked from all enemy types.
   */
  public static GameEntity createRandomEnemy(GameSystem gameSystem,
      AreaComponent areaComponent, int difficulty) {
    // Select a random key and value set from the enemy info Map
    Random random = new Random();
    List<String> keys = new ArrayList<String>(enemyInfoMap.keySet());
    String randomKey = keys.get(random.nextInt(keys.size()));
    EnemyInfo pick = enemyInfoMap.get(randomKey);
    // Use it to create the monster by passing the name
    GameEntity entity = createEnemy(gameSystem, pick.getName(), areaComponent, difficulty);
    
    // Assign a random stat modifier
    StatModifier randomStatModifier = getRandomStatModifier();
    gameSystem.getCharacterComponent(entity.getID()).setStatModifier(
        randomStatModifier);
    return entity;
  }

  public static GameEntity createPlayer(GameSystem gameSystem,
      AreaComponent areaComponent) {
    Object[][] playerArguments = new Object[][] {
      { areaComponent },
      { 100, false, "PLAYER" }, // Give character 100 health and set to friendly
      {} // Player component arguments
    };
    return EntityFactory.createReflective(gameSystem, "PLAYER",
        playerArguments);
  }

  public static StatModifier getStatModifier(String name) {
    if (statModifierMap.containsKey(name)) {
      return statModifierMap.get(name);
    }
    throw new IllegalArgumentException("Invalid stat modifier name.");
  }
  
  public static StatModifier getRandomStatModifier() {
    // Select a random key and value set from the stat modifier map
    Random random = new Random();
    List<String> keys = new ArrayList<String>(statModifierMap.keySet());
    String randomKey = keys.get(random.nextInt(keys.size()));
    return statModifierMap.get(randomKey);
  }
}
