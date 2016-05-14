package maelstrom.entity;


import maelstrom.controller.GameSystem;

public class TransformComponent extends BaseComponent {
  
  AreaComponent location; // Defaults to null
  
  public TransformComponent(GameEntity owner, GameSystem gameSystem,
      Object[] arguments) {
    super(owner, gameSystem);
    owner.addComponent(this);
    gameSystem.registerComponent(this);
    
    init(arguments);
  }
    
  @Override
  void init(Object[] arguments) {
    if (arguments.length != 1) {
      throw new IllegalArgumentException("Invalid amount of arguments given.");
    }
    setLocation((AreaComponent) arguments[0]);
  }
  
  public void update() {
    ;
  }
  
  public void setLocation(AreaComponent location) {
    if (this.location != null) {
      this.location.deregisterEntity(owner);
    }
    location.registerEntity(owner);
    this.location = location;
  }
  public AreaComponent getLocation() {
    return location;
  }
}
