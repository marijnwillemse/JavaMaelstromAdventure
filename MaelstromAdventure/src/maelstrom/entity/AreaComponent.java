package maelstrom.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import maelstrom.controller.GameSystem;
import maelstrom.graph.NavigationGraphNode;
import maelstrom.utilities.GameLocale;

public class AreaComponent extends BaseComponent {

  private NavigationGraphNode node;
  private int windSpeed; // Based on the Beaufort scale
  private int difficulty; // Determines the amount of trouble in the area
  private ArrayList<UUID> entities = new ArrayList<UUID>();

  private static final HashMap<Integer, String> BEAUFORT_TAGS;

  static {
    BEAUFORT_TAGS = new HashMap<Integer, String>();
    BEAUFORT_TAGS.put(0, "TXT_KEY_BEAUFORT_DESCRIPTION_0");
    BEAUFORT_TAGS.put(1, "TXT_KEY_BEAUFORT_DESCRIPTION_1");
    BEAUFORT_TAGS.put(2, "TXT_KEY_BEAUFORT_DESCRIPTION_2");
    BEAUFORT_TAGS.put(3, "TXT_KEY_BEAUFORT_DESCRIPTION_3");
    BEAUFORT_TAGS.put(4, "TXT_KEY_BEAUFORT_DESCRIPTION_4");
    BEAUFORT_TAGS.put(5, "TXT_KEY_BEAUFORT_DESCRIPTION_5");
    BEAUFORT_TAGS.put(6, "TXT_KEY_BEAUFORT_DESCRIPTION_6");
    BEAUFORT_TAGS.put(7, "TXT_KEY_BEAUFORT_DESCRIPTION_7");
    BEAUFORT_TAGS.put(8, "TXT_KEY_BEAUFORT_DESCRIPTION_8");
    BEAUFORT_TAGS.put(9, "TXT_KEY_BEAUFORT_DESCRIPTION_9");
    BEAUFORT_TAGS.put(10, "TXT_KEY_BEAUFORT_DESCRIPTION_10");
    BEAUFORT_TAGS.put(11, "TXT_KEY_BEAUFORT_DESCRIPTION_11");
    BEAUFORT_TAGS.put(12, "TXT_KEY_BEAUFORT_DESCRIPTION_12");
  }

  public AreaComponent(GameEntity owner, GameSystem gameSystem,
      Object[] arguments) {
    super(owner, gameSystem);
    owner.addComponent(this);
    gameSystem.registerComponent(this);

    init(arguments);
  }

  @Override
  void init(Object[] arguments) {
    windSpeed = (int) Math.round(Math.random() * 12);
  }
  
  public void spawnEntity() {
    CharacterFactory.createMonster(gameSystem, this, difficulty);
  }
  
  /* Every entity will register itself upon entry */
  public void registerEntity(GameEntity entity) {
    entities.add(entity.getID());
  }

  /* Every entity will deregister itself upon exit */
  public void deregisterEntity(GameEntity entity) {
    // Check at which index the entity is present
    int entityIndex = entities.indexOf(entity.getID());
    // Exit if entity does not exist in list
    if (entityIndex == -1) { return; }
    // Remove the entity from the list
    entities.remove(entityIndex);
  }


  public void describeWeather() {
    System.out.println(GameLocale.getString(BEAUFORT_TAGS.get(windSpeed)));
  }
  
  public void describeEntities() {
    for (UUID entity : entities) {
      System.out.println("There is a " + 
          gameSystem.getCharacterComponents().get(entity).getName());
    }
  }
  
  public void assignNode(NavigationGraphNode node) {
    this.node = node;
    node.assignArea(this);
  }

  public NavigationGraphNode getNode() {
    return node;
  }

  public int getWindSpeed() {
    return windSpeed;
  }

}