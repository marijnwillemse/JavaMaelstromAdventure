package maelstrom.entity;

import maelstrom.controller.GameSystem;

public class CharacterComponent extends BaseComponent {

  private int health;

  public CharacterComponent(GameEntity owner, GameSystem gameSystem,
      Object[] arguments) {
    super(owner, gameSystem);
    owner.addComponent(this);
    gameSystem.registerComponent(this);

    init(arguments);
  }

  @Override
  void init(Object[] arguments) {
    if (arguments.length != 1) {
      throw new IllegalArgumentException("Invalid amount of arguments given.");
    }
    health = (int) arguments[0];
  }
  
  public int getHealth() {
    return health;
  }
}
