package maelstrom.entity;

import maelstrom.controller.GameSystem;
import maelstrom.gameinfo.StatsModifier;
import maelstrom.gameinfo.Stats;

public class CharacterComponent extends BaseComponent {

  private String characterName;
  private int level;
  private boolean hostile;
  private int health;

  private Stats stats;
  private StatsModifier statsModifier;

  public CharacterComponent(GameEntity owner, GameSystem gameSystem,
      Object[] arguments) {
    super(owner, gameSystem);
    owner.addComponent(this);
    gameSystem.registerComponent(this);

    init(arguments);
  }

  @Override
  void init(Object[] arguments) {
    if (arguments.length != 4) {
      throw new IllegalArgumentException("Invalid amount of arguments given.");
    }
    characterName = (String) arguments[0];
    level = (int) arguments[1];
    hostile = (boolean) arguments[2];
    stats = (Stats) arguments[3];
    health = stats.getStamina();
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public boolean isHostile() {
    return hostile;
  }

  public void setStatsModifier(StatsModifier statsModifier) {
    this.statsModifier = statsModifier;
    if (health > getStamina()) {
      setHealth(getStamina());
    }
  }

  public StatsModifier getStatModifier() {
    return statsModifier;
  }

  public String getCharacterName() {
    if (statsModifier != null) {
      return statsModifier.getAffix() + " " + characterName;
    }
    return characterName;
  }

  public void Attack(GameEntity opponent) {
    CharacterComponent opponentComponent = gameSystem.getCharacterComponent(
        opponent.getID());

    System.out.println(getCharacterName() + " attacks "
        + opponentComponent.getCharacterName()); 

  }

  public int getStamina() {
    int i = stats.getStamina();
    if (statsModifier != null) {
      i += statsModifier.getStamina();
    }
    i = (i > 0) ? i : 0;
    return i;
  }

  public int getStrength() {
    int i = stats.getStrength();
    if (statsModifier != null) {
      i += statsModifier.getStrength();
    }
    i = (i > 0) ? i : 0;
    return i;
  }

  public int getDefense() {
    int i = stats.getDefense();
    if (statsModifier != null) {
      i += statsModifier.getDefense();
    }
    i = (i > 0) ? i : 0;
    return i;
  }

  public int getAgility() {
    int i = stats.getAgility();
    if (statsModifier != null) {
      i += statsModifier.getAgility();
    }
    i = (i > 0) ? i : 0;
    return i;
  }

  @Override
  public void destroy() {
    gameSystem.deregisterComponent(this);
  }

  public void applyDamage(int damage) {
    health -= damage;
  }

  public int getLevel() {
    return level;
  }
  
  public void update() {
    // Clean up if dead
    if (health <= 0) {
      owner.deactivate();
      return;
    }
    
    if (hostile) {
      System.out.println(getCharacterName() + " is idle.");
    }
  }
}
