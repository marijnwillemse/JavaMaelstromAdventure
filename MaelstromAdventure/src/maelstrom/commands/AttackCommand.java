package maelstrom.commands;

import java.util.List;

import maelstrom.controller.GameSystem;
import maelstrom.entity.AreaComponent;
import maelstrom.entity.CharacterComponent;
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

    GameEntity area = gameSystem.getGameWorld().getPlayerArea();
    AreaComponent areaComponent = gameSystem.getAreaComponent(area.getID());
    if (areaComponent.hasCharacter(subject)) {
      
      // Get allied character component
      CharacterComponent alliedComponent = gameSystem.getCharacterComponent(
          gameSystem.getGameWorld().getPlayer().getID());

      // Make the ally perform the attack on the opponent
      alliedComponent.Attack(areaComponent.getCharacter(subject));
      return;
    }

    System.out.println("No such thing is around here.");
  }
}
