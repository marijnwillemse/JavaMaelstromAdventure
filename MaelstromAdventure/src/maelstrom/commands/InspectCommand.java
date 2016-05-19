package maelstrom.commands;

import java.util.List;
import java.util.HashMap;
import java.util.UUID;

import maelstrom.controller.GameSystem;
import maelstrom.entity.AreaComponent;
import maelstrom.entity.CharacterComponent;
import maelstrom.entity.GameEntity;

public class InspectCommand extends BaseCommand {

  private static HashMap<String, Runnable> genericSubjects;  
  private static HashMap<String, String> synonyms;

  static {
    genericSubjects = new HashMap<>();
    synonyms = new HashMap<>();
  }

  private GameSystem gameSystem;

  public InspectCommand() {

    // Populate generic subject map with lambda methods
    genericSubjects.put("SEA", () -> inspectSea());
    genericSubjects.put("AREA", () -> new LookCommand().execute(gameSystem, null));

    // Populate synonyms map
    synonyms.put("OCEAN", "SEA");
  }

  @Override
  public void execute(GameSystem gameSystem, List<String> words) {

    // Update game system field
    // This is a rather cheap solution for memorizing the parameter
    this.gameSystem = gameSystem;

    // Verify declaration of subject
    if (words.size() <= 1) {
      System.out.println("Inspect what?");
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
      inspectCharacter(areaComponent.getCharacter(subject));
      return;
    }

    // Compare subject with environment elements
    if (genericSubjects.containsKey(subject)) {
      // The subject is known in the dictionary
      genericSubjects.get(subject).run();
      return;
    } else if (synonyms.containsKey(subject)) {
      // The declaration is a synonym for a known subject
      genericSubjects.get(synonyms.get(subject)).run();
      return;
    }
    
    System.out.println("No such thing is around here.");
  }

  private void inspectCharacter(GameEntity entity) {
      CharacterComponent c = gameSystem.getCharacterComponent(entity.getID());
      System.out.println("Inspecting " + c.getCharacterName());
  }

  public void inspectSea() {
    UUID areaID = gameSystem.getGameWorld().getPlayerArea().getID();
    AreaComponent areaComponent = gameSystem.getAreaComponent(areaID);
    areaComponent.printWeatherDescription();
  }
}
