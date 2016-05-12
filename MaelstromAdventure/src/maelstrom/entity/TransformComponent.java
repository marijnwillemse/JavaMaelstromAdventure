package maelstrom.entity;


import maelstrom.controller.GameSystem;
import maelstrom.graph.NavigationGraphNode;

public class TransformComponent extends BaseComponent {
  
  NavigationGraphNode location; // Defaults to null
  
  public TransformComponent(GameEntity owner, GameSystem gameSystem,
      Object[] arguments) {
    super(owner, gameSystem);
    owner.addComponent(this);
    gameSystem.registerComponent(this);
    
    init(arguments);
  }
    
  @Override
  void init(Object[] arguments) {
    if (arguments.length > 0) {
      location = (NavigationGraphNode) arguments[0];
    }
  }
  
  public void update() {
    ;
  }
  
  public void setLocation(NavigationGraphNode location) {
    this.location = location;
  }
  public NavigationGraphNode getLocation() {
    return location;
  }
}
