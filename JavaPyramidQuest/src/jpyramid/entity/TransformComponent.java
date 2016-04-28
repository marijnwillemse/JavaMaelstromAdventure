package jpyramid.entity;


import jpyramid.controller.GameSystem;
import jpyramid.graph.NavigationGraphNode;

public class TransformComponent extends BaseComponent {
  
  NavigationGraphNode location; // Defaults to null
  
  public TransformComponent(GameEntity owner, GameSystem gameSystem) {
    super(owner);
    owner.addComponent(this);
    gameSystem.registerComponent(this);
  }
    
  @Override
  void init(Object[] arguments) {
    // TODO Auto-generated method stub
    
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
