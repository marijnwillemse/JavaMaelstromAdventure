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

    if (matchWithEntities(subject) == true) { return; }

    // Compare subject with environment elements
    if (genericSubjects.containsKey(subject)) {
      // The subject is known in the dictionary
      genericSubjects.get(subject).run();
    } else if (synonyms.containsKey(subject)) {
      // The declaration is a synonym for a known subject
      genericSubjects.get(synonyms.get(subject)).run();
    } else {
      System.out.println("No such thing is around here.");
    }
  }

  private boolean matchWithEntities(String subject) {
    // Compare subject with NPC's in player area
    List<GameEntity> entityList = getEntityList(gameSystem);
    
    for (GameEntity entity : entityList) {
      CharacterComponent c = gameSystem.getCharacterComponent(entity.getID());
      if (c.getCharacterName().toUpperCase().equals(subject)) {
        System.out.println(c.getCharacterName());
        return true;
      }
    }
    return false;
  }

  private List<GameEntity> getEntityList(GameSystem gameSystem) {
    UUID areaID = gameSystem.getGameWorld().getPlayerArea().getID();
    AreaComponent areaComponent = gameSystem.getAreaComponent(areaID);
    return areaComponent.getEntities();
  }


  public void inspectSea() {
    UUID areaID = gameSystem.getGameWorld().getPlayerArea().getID();
    AreaComponent areaComponent = gameSystem.getAreaComponent(areaID);
    areaComponent.printWeatherDescription();
  }
}
