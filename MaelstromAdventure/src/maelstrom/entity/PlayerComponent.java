package maelstrom.entity;

import maelstrom.controller.GameSystem;

public class PlayerComponent extends BaseComponent {

  public PlayerComponent(GameEntity owner, GameSystem gameSystem, Object[] arguments) {
    super(owner, gameSystem);
    owner.addComponent(this);
    gameSystem.registerComponent(this);

    init(arguments);
  }

  @Override
  void init(Object[] arguments) {    
  }
  
  public boolean getDescription() {
    System.out.println("A captain never speaks for himself.");
    return true;
  }
}
