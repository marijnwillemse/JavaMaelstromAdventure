package maelstrom.entity;

import maelstrom.controller.GameSystem;

public class CharacterComponent extends BaseComponent {

  private int health;
  private boolean hostile;
  private String name;

  public CharacterComponent(GameEntity owner, GameSystem gameSystem,
      Object[] arguments) {
    super(owner, gameSystem);
    owner.addComponent(this);
    gameSystem.registerComponent(this);

    init(arguments);
  }

  @Override
  void init(Object[] arguments) {
    if (arguments.length != 3) {
      throw new IllegalArgumentException("Invalid amount of arguments given.");
    }
    health = (int) arguments[0];
    hostile = (boolean) arguments[1];
    name = (String) arguments[2];
  }
  
  public int getHealth() {
    return health;
  }
  
  public boolean isHostile() {
    return hostile;
  }
  
  public String getName() {
    return name;
  }
}
