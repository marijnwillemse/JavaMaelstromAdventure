package jpyramid.entity;

public abstract class BaseComponent {

  private GameEntity owner;
  
  protected BaseComponent(GameEntity gameEntity) {
    this.owner = gameEntity;
  }
  
  public GameEntity getOwner() {
    return owner;
  }
  
  abstract void init(Object[] arguments);
}
