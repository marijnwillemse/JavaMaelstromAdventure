package maelstrom.gameinfo;

import java.util.List;

public class EntityInfo {
  private String id;
  private List<String> componentNames;

  public EntityInfo(String id, List<String> componentNames) {
    this.id = id;
    this.componentNames = componentNames;
  }

  public String getId() {
    return id;
  }

  public List<String> getComponentNames() {
    return componentNames;
  }
}