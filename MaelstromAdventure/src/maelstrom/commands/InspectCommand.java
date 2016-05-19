package maelstrom.commands;

import java.util.List;
import java.util.HashMap;
import java.util.UUID;

import maelstrom.controller.GameSystem;
import maelstrom.entity.AreaComponent;
import maelstrom.entity.CharacterComponent;
import maelstrom.entity.GameEntity;

public class InspectCommand extends BaseCommand {

  private static HashMap<String, Runnable> commands;  
  private static HashMap<String, String> synonyms;

  static {
    commands = new HashMap<>();
    synonyms = new HashMap<>();
  }

  private GameSystem gameSystem;

  public InspectCommand() {

    // Populate commands map with lambda methods
    commands.put("SEA", () -> inspectSea());
    commands.put("AREA", () -> new LookCommand().execute(gameSystem, null));

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
    String subject = words.get(1).toUpperCase();

    if (matchWithEntities(subject) == true) { return; }

    // Compare subject with environment elements
    if (commands.containsKey(subject)) {
      // The subject is known in the dictionary
      commands.get(subject).run();
    } else if (synonyms.containsKey(subject)) {
      // The declaration is a synonym for a known subject
      commands.get(synonyms.get(subject)).run();
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
        boolean described = entity.describeComponents();
        if (described == false) {
          System.out.println("No description is available for "
              + c.getCharacterName());
        }
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
