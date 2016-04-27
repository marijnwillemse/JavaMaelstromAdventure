package jpyramid.entity;

import java.util.ArrayList;
import java.util.UUID;

public class GameEntity {

  private UUID id;
  private ArrayList<BaseComponent> components;
  
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
}
