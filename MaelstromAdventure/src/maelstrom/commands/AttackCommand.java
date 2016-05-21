package maelstrom.commands;

import java.util.List;

import maelstrom.controller.GameSystem;
import maelstrom.entity.AreaComponent;
import maelstrom.entity.BattleComponent;
import maelstrom.entity.CharacterComponent;
import maelstrom.entity.EntityFactory;
import maelstrom.entity.GameEntity;

public class AttackCommand extends BaseCommand {

  @Override
  public void execute(GameSystem gameSystem, List<String> words) {

    // Verify declaration of subject
    if (words.size() <= 1) {
      System.out.println("Attack what?");
      return;
    }

    // All words after the command form the subject
    String subject = "";
    for (int i = 1; i < words.size(); i++) {
      subject += words.get(i).toUpperCase();
      if (i != words.size()-1) {
        subject += " ";
      }
    }

    // Look for the subject in the player's area
    GameEntity area = gameSystem.getGameWorld().getPlayerArea();
    AreaComponent areaComponent = gameSystem.getAreaComponent(area.getID());
    if (areaComponent.hasCharacter(subject)) {
      // Subject found!
      
      // Put allied entity and opponent entity in battle arguments
      Object[][] argumentsArray = new Object[][] {
        { gameSystem.getGameWorld().getPlayer(),
          areaComponent.getCharacter(subject)
        }
      };
      
      // Create a battle entity
      GameEntity battle = EntityFactory.createReflective(gameSystem, "battle",
          argumentsArray);
      
      BattleComponent battleComponent = gameSystem.getBattleComponent(
          battle.getID());

      while (!battleComponent.isResolved()) {
        battleComponent.advance();
      }
      battle.deactivate();
      return;
    }

    System.out.println("No such thing is around here.");
  }
}
