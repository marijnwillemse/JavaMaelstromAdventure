package jpyramid.entity;

public class BaseComponent {

  private GameEntity owner;
  
  protected BaseComponent(GameEntity gameEntity) {
    this.owner = gameEntity;
  }
  
  public GameEntity getOwner() {
    return owner;
  }
}
