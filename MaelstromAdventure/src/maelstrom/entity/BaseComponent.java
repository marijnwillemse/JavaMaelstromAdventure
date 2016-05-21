package maelstrom.entity;

import maelstrom.controller.GameSystem;

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
  public abstract void destroy();

  // Empty method. Can be overridden if a component should be made describable.
  public boolean getDescription() { return false; }

}
