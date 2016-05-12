package maelstrom.entity;

import maelstrom.controller.GameSystem;
import maelstrom.graph.NavigationGraphNode;

public class AreaComponent extends BaseComponent {
  
  private NavigationGraphNode node;
  private int windSpeed; // Based on the Beaufort scale

  public AreaComponent(GameEntity owner, GameSystem gameSystem,
      Object[] arguments) {
    super(owner, gameSystem);
    owner.addComponent(this);
    gameSystem.registerComponent(this);
    
    init(arguments);
  }

  @Override
  void init(Object[] arguments) {
    windSpeed = (int) Math.round(Math.random() * 12);
  }

  public void assignNode(NavigationGraphNode node) {
    this.node = node;
    node.assignArea(this);
  }
  
  public NavigationGraphNode getNode() {
    return node;
  }
  
  public int getWindSpeed() {
    return windSpeed;
  }

}
