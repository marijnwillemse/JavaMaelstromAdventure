package maelstrom.entity;

import maelstrom.controller.GameSystem;

public class CharacterFactory {

  public static GameEntity createMonster(GameSystem gameSystem,
      AreaComponent areaComponent, int difficulty) {

    Object[][] argumentsArray = new Object[][] {
      { areaComponent },
      { 100, true, "Monster" } // Give character 100 health and set to hostile
    };
    return EntityFactory.createReflective(gameSystem, "NPC", argumentsArray);
  }

  public static GameEntity createPlayer(GameSystem gameSystem,
      AreaComponent areaComponent) {
    Object[][] playerArguments = new Object[][] {
      { areaComponent },
      { 100, false, "Player" }, // Give character 100 health and set to friendly
      {} // Player component arguments
    };
    return EntityFactory.createReflective(gameSystem, "PLAYER",
        playerArguments);
  }

}
