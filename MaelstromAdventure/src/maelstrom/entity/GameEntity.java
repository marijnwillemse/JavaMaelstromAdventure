package maelstrom.entity;

import java.util.ArrayList;
import java.util.UUID;

public class GameEntity {

  private UUID id;
  private ArrayList<BaseComponent> components;
  
  // Most objects have names and can be referenced by them.
  private String name;
  
  public GameEntity() {
    id = UUID.randomUUID();
    components = new ArrayList<BaseComponent>();
  }
  
  public UUID getID() {
    return id;
  }
  
  public void addComponent(BaseComponent component) {
    components.add(component);
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
}
