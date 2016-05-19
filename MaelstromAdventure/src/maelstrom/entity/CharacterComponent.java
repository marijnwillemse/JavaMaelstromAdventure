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
    return stats.getStamina();
  }
  
  public boolean isHostile() {
    return hostile;
  }
  
  public void setStatsModifier(StatsModifier statsModifier) {
    this.statsModifier = statsModifier;
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
    
    System.out.println(getCharacterName() + " attacked "
        + opponentComponent.getCharacterName()); 
  }

  public int getStamina() {
    return stats.getStamina();
  }

  public int getStrength() {
    return stats.getStrength();
  }

  public int getDefense() {
    return stats.getDefense();
  }

  public int getAgility() {
    return stats.getAgility();
  }
}
