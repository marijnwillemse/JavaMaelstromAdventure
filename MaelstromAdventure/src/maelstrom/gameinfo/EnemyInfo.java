package maelstrom.gameinfo;

public class EnemyInfo {
  private String name;
  private int stamina;
  private int strength;
  private int defense;
  private int agility;

  public EnemyInfo(String name, int stamina, int strength, int defense,
      int agility) {
    this.name = name;
    this.stamina = stamina;
    this.strength = strength;
    this.defense = defense;
    this.agility = agility;
  }

  public String getName() {
    return name;
  }

  public int getStamina() {
    return stamina;
  }

  public int getStrength() {
    return strength;
  }

  public int getDefense() {
    return defense;
  }

  public int getAgility() {
    return agility;
  }
}