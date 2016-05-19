package maelstrom.gameinfo;

public class StatModifier {
  private String affix;
  private int stamina;
  private int strength;
  private int defense;
  private int agility;

  public StatModifier(String affix, int stamina, int strength, int defense,
      int agility) {
    this.affix = affix;
    this.stamina = stamina;
    this.strength = strength;
    this.defense = defense;
    this.agility = agility;
  }

  public String getAffix() {
    return affix;
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