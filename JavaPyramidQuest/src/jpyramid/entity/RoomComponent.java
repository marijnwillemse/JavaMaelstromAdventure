package jpyramid.entity;

import jpyramid.controller.GameSystem;

public class RoomComponent extends BaseComponent {

  protected RoomComponent(GameEntity owner, GameSystem gameSystem,
      Object[] arguments) {
    super(owner);
    owner.addComponent(this);
    gameSystem.registerComponent(this);
    
    init(arguments);
  }

  @Override
  void init(Object[] arguments) {
  }

}
