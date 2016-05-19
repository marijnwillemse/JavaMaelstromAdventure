package maelstrom.entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import maelstrom.controller.GameSystem;
import maelstrom.utilities.EnemyInfo;
import maelstrom.utilities.GameInfoParser;

public class CharacterFactory {

  // Mapping of enemy types to a list of classnames of its required components
  private static HashMap<String, EnemyInfo> enemyInfoMap = new HashMap<>();

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
    return EntityFactory.createReflective(gameSystem, "Character", argumentsArray);
  }

  /**
   * Create a monster randomly picked from all enemy types.
   */
  public static GameEntity createRandomEnemy(GameSystem gameSystem,
      AreaComponent areaComponent, int difficulty) {
    // Select a random key and value set from the enemy info Map
    Random random = new Random();
    List<String> keys = new ArrayList<String>(enemyInfoMap.keySet());
    String randomKey = keys.get( random.nextInt(keys.size()) );
    EnemyInfo pick = enemyInfoMap.get(randomKey);
    // Use it to create the monster by passing the name
    return createEnemy(gameSystem, pick.getName(), areaComponent, difficulty);
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

}
