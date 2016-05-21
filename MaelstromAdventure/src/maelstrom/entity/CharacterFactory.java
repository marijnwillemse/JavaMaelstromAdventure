package maelstrom.entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import maelstrom.controller.GameSystem;
import maelstrom.gameinfo.Stats;
import maelstrom.gameinfo.GameInfoParser;
import maelstrom.gameinfo.StatsModifier;

public class CharacterFactory {

  // Mapping of enemy types to a list of classnames of its required components
  private static HashMap<String, Stats> enemyInfoMap = new HashMap<>();

  private static HashMap<String, StatsModifier> statsModifierMap =
      new HashMap<>();

  private CharacterFactory() {}

  static {
    /* Draft enemy blueprints with the data from descriptive JSON file */
    GameInfoParser parser = new GameInfoParser();

    InputStream in;
    try {
      in = new FileInputStream("src/maelstrom/assets/enemies.json");
      // Use the parser object to read entity info into designated info objects
      List<Stats> enemyInfoList = parser.readInfoStream(Stats.class, in);

      // Map the information into the blueprint list
      for (Stats i : enemyInfoList) {
        enemyInfoMap.put(i.getName(), i);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Unable to read enemy information from JSON file.");
    }
    try {
      in = new FileInputStream("src/maelstrom/assets/stats_modifiers.json");
      // Use the parser object to read entity info into designated info objects
      List<StatsModifier> statsModifierList = parser.readInfoStream(
          StatsModifier.class, in);

      // Map the information into the blueprint list
      for (StatsModifier i : statsModifierList) {
        statsModifierMap.put(i.getAffix(), i);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Unable to read stats modifiers from JSON file.");
    }
  }

  public static GameEntity createEnemy(GameSystem gameSystem, String name,
      AreaComponent areaComponent, int level) {
    if (enemyInfoMap.containsKey(name) == false) {
      throw new IllegalArgumentException("Enemy name not found");
    }
    Stats stats = enemyInfoMap.get(name);

    Object[][] argumentsArray = new Object[][] {
      { areaComponent },
      {
        stats.getName(), // Character name
        level,
        true, // Hostile?
        stats,
      }
    };
    return EntityFactory.createReflective(gameSystem, "Character",
        argumentsArray);
  }

  /**
   * Create a monster randomly picked from all enemy types.
   */
  public static GameEntity createRandomEnemy(GameSystem gameSystem,
      AreaComponent areaComponent, int level) {

    // Select a random key and value set from the enemy info Map
    Random random = new Random();
    List<String> keys = new ArrayList<String>(enemyInfoMap.keySet());
    String randomKey = keys.get(random.nextInt(keys.size()));
    Stats pick = enemyInfoMap.get(randomKey);

    // Use it to create the monster by passing the name
    GameEntity entity = createEnemy(gameSystem, pick.getName(), areaComponent,
        level);

    // Assign a random stats modifier
    StatsModifier randomStatsModifier = getRandomStatsModifier();
    gameSystem.getCharacterComponent(entity.getID()).setStatsModifier(
        randomStatsModifier);
    return entity;
  }

  public static GameEntity createPlayer(GameSystem gameSystem,
      AreaComponent areaComponent) {

    Stats stats = new Stats("PLAYER", 100, 80, 80, 80);
    
    Object[][] playerArguments = new Object[][] {
      { areaComponent },
      { 
        "PLAYER",
        1,
        false,
        stats
      },
      {} // Player component arguments
    };
    return EntityFactory.createReflective(gameSystem, "PLAYER",
        playerArguments);
  }

  public static StatsModifier getStatModifier(String name) {
    if (statsModifierMap.containsKey(name)) {
      return statsModifierMap.get(name);
    }
    throw new IllegalArgumentException("Invalid stats modifier name.");
  }

  public static StatsModifier getRandomStatsModifier() {
    // Select a random key and value set from the stat modifier map
    Random random = new Random();
    List<String> keys = new ArrayList<String>(statsModifierMap.keySet());
    String randomKey = keys.get(random.nextInt(keys.size()));
    return statsModifierMap.get(randomKey);
  }
}
