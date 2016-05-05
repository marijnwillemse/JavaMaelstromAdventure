package jpyramid.entity;

import jpyramid.controller.GameSystem;

public class PlayerComponent extends BaseComponent {

  protected PlayerComponent(GameEntity owner, GameSystem gameSystem, Object[] arguments) {
    super(owner, gameSystem);
    owner.addComponent(this);
    gameSystem.registerComponent(this);

    init(arguments);
  }

  @Override
  void init(Object[] arguments) {    
  }
}
