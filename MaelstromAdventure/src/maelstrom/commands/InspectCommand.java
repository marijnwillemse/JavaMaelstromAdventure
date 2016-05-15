package maelstrom.commands;

import java.util.List;
import java.util.HashMap;
import java.util.UUID;

import maelstrom.controller.GameSystem;
import maelstrom.entity.AreaComponent;
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

    // Populate synonyms map
    synonyms.put("OCEAN", "SEA");
  }


  @Override
  public void execute(GameSystem gameSystem, String[] words) {

    // Update game system field
    // This is a cheap solution for now
    this.gameSystem = gameSystem;

    // Verify declaration of subject
    if (words.length <= 1) {
      System.out.println("Inspect what?");
      return;
    }
    String subject = words[1].toUpperCase();

    // Compare subject with NPC's in player area
    List<GameEntity> entityList = getEntityList(gameSystem);
    
    for (GameEntity entity : entityList) {
      if (entity.getName().toUpperCase().equals(subject)) {
        boolean described = entity.describeComponents();
        if (described == false) {
          System.out.println("No description is available for "
              + entity.getName());
        }
        return;
      }
    }

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

  private List<GameEntity> getEntityList(GameSystem gameSystem) {
    UUID areaID = gameSystem.getGameWorld().getPlayerArea().getID();
    AreaComponent areaComponent = gameSystem.getAreaComponents()
        .get(areaID);
    return areaComponent.getEntities();
  }


  public void inspectSea() {
    UUID areaID = gameSystem.getGameWorld().getPlayerArea().getID();
    AreaComponent areaComponent = gameSystem.getAreaComponents()
        .get(areaID);
    areaComponent.printWeatherDescription();
  }
}
