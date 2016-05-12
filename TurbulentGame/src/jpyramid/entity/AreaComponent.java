package jpyramid.entity;

import jpyramid.controller.GameSystem;
import jpyramid.graph.NavigationGraphNode;

public class AreaComponent extends BaseComponent {
  
  private NavigationGraphNode node;

  public AreaComponent(GameEntity owner, GameSystem gameSystem,
      Object[] arguments) {
    super(owner, gameSystem);
    owner.addComponent(this);
    gameSystem.registerComponent(this);
    
    init(arguments);
  }

  @Override
  void init(Object[] arguments) {
  }

  public void assignNode(NavigationGraphNode node) {
    this.node = node;
  }
  
  public NavigationGraphNode getNode() {
    return node;
  }

}
