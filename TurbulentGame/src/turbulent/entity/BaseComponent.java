package turbulent.entity;

import turbulent.controller.GameSystem;

public abstract class BaseComponent {

  protected GameEntity owner;
  protected GameSystem gameSystem;
  
  public BaseComponent(GameEntity gameEntity, GameSystem gameSystem) {
    this.owner = gameEntity;
    this.gameSystem = gameSystem;
  }
  
  public GameEntity getOwner() {
    return owner;
  }
  
  abstract void init(Object[] arguments);
}
