package jpyramid.model;

import java.util.UUID;

public class GameEntity {

  public UUID id;
  
  public GameEntity() {
    id = UUID.randomUUID();
  }
}
