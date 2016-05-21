package maelstrom.entity;

import maelstrom.controller.GameSystem;

public class BattleComponent extends BaseComponent {

  private GameEntity aEntity;
  private GameEntity bEntity;
  private CharacterComponent aCharacter;
  private CharacterComponent bCharacter;

  private boolean resolved = false;
  
  private final double MISSPERCENTAGE = 0.3;

  public BattleComponent(GameEntity owner, GameSystem gameSystem,
      Object[] arguments) {
    super(owner, gameSystem);
    owner.addComponent(this);
    gameSystem.registerComponent(this);

    init(arguments);
  }

  @Override
  void init(Object[] arguments) {
    if (arguments.length != 2) {
      throw new IllegalArgumentException("Invalid amount of arguments given.");
    }
    aEntity = (GameEntity) arguments[0];
    bEntity = (GameEntity) arguments[1];
    aCharacter = gameSystem.getCharacterComponent(aEntity.getID());
    bCharacter = gameSystem.getCharacterComponent(bEntity.getID());
  }

  public void advance() {    
    // The engager attacks the opponent
    attack(aCharacter, bCharacter);
    // Resolve if the attacking party has no more health.
    if (aCharacter.getHealth() <= 0 || bCharacter.getHealth() <= 0) {
      resolve(); return;
    }

    // The opponent attacks the engager
    attack(bCharacter, aCharacter);
    if (aCharacter.getHealth() <= 0 || bCharacter.getHealth() <= 0) {
      resolve(); return;
    }    
  }

  private void attack(CharacterComponent alpha, CharacterComponent beta) {
    System.out.print(alpha.getCharacterName() + " attacks");
    
    try {
      Thread.sleep(800);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    double agilityRatio = (double) alpha.getAgility() / (double) beta.getAgility();
    double roll = Math.random();
    // Calculate wheter the attack is a hit or a miss
    if (roll * agilityRatio > MISSPERCENTAGE) {
      // It's a hit
      double levelMul = (0.1d * (alpha.getLevel() + 1d)) + 1d;
      double strDefMul = (double) alpha.getStrength() / (double) beta.getDefense();
      int damage = (int) Math.round(levelMul * strDefMul * 20);
      beta.applyDamage(damage);
      System.out.print(" and hits with " + damage + " damage.\n");
    } else {
      // It's a miss
      System.out.print(" and misses.\n");
    }
    
    try {
      Thread.sleep(800);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void resolve() {
    if (aCharacter.getHealth() <= 0 && bCharacter.getHealth() <= 0) {
      System.out.println(aCharacter.getCharacterName() + " and "
          + bCharacter.getCharacterName() + " both died in battle.");
    } else if (aCharacter.getHealth() <= 0 && bCharacter.getHealth() > 0) {
      System.out.println(aCharacter.getCharacterName()
          + " died whilst fighting " + bCharacter.getCharacterName());
    } else if (aCharacter.getHealth() > 0 && bCharacter.getHealth() <= 0) {
      System.out.println(bCharacter.getCharacterName()
          + " died whilst fighting " + aCharacter.getCharacterName());
    }
    resolved = true;
  }

  public boolean isResolved() {
    return resolved;
  }

  @Override
  public void delete() {
    gameSystem.deregisterComponent(this);
  }
}
