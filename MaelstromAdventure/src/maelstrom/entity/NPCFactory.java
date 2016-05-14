package maelstrom.entity;

import maelstrom.controller.GameSystem;

public class NPCFactory {

  public static GameEntity create(GameSystem gameSystem,
      Object[][] argumentsArray) {
    
    return EntityFactory.createReflective(gameSystem, "NPC", argumentsArray);
  }

}
