package maelstrom.entity;

import java.util.HashMap;

import maelstrom.controller.GameSystem;
import maelstrom.graph.NavigationGraphNode;
import maelstrom.utilities.GameLocale;

public class AreaComponent extends BaseComponent {

  private NavigationGraphNode node;
  private int windSpeed; // Based on the Beaufort scale

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

  public void describe() {
    System.out.println(GameLocale.getString(BEAUFORT_TAGS.get(windSpeed)));
  }

  @Override
  void init(Object[] arguments) {
    windSpeed = (int) Math.round(Math.random() * 12);
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
