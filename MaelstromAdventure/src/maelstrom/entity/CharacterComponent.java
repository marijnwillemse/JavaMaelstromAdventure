package maelstrom.entity;

import maelstrom.controller.GameSystem;
import maelstrom.gameinfo.StatModifier;

public class CharacterComponent extends BaseComponent {

  private String characterName;
  private int health;
  private boolean hostile;
  private StatModifier statModifier;
  
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
    characterName = (String) arguments[2];
  }
  
  public int getHealth() {
    return health;
  }
  
  public boolean isHostile() {
    return hostile;
  }
  
  public void setStatModifier(StatModifier statModifier) {
    this.statModifier = statModifier;
  }
  
  public StatModifier getStatModifier() {
    return statModifier;
  }

  public String getCharacterName() {
    if (statModifier != null) {
      return statModifier.getAffix() + " " + characterName;
    }
    return characterName;
  }
}
