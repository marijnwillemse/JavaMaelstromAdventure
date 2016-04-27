package jpyramid.entity;

import jpyramid.controller.GameWorld;

public class PlayerComponent extends BaseComponent {

  protected PlayerComponent(GameEntity owner, GameWorld gameWorld, Object[] arguments) {
    super(owner);
    owner.addComponent(this);
    gameWorld.registerComponent(this);
  }

  @Override
  void init(Object[] arguments) {
    // TODO Auto-generated method stub
    
  }
}
