package jpyramid.entity;


import jpyramid.controller.GameWorld;
import jpyramid.graph.NavigationGraphNode;

public class TransformComponent extends BaseComponent {
  
  NavigationGraphNode location; // Defaults to null
  
  public TransformComponent(GameEntity owner, GameWorld gameWorld) {
    super(owner);
    owner.addComponent(this);
    gameWorld.registerComponent(this);
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
