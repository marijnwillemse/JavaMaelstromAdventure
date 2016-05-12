package maelstrom.graph;

import maelstrom.entity.AreaComponent;
import maelstrom.math.Vector2D;

public class NavigationGraphNode extends GraphNode {
  
  private Vector2D position;
  private AreaComponent area;
  
  public NavigationGraphNode(int i, Vector2D position) {
    super(i);
    this.position = position;
  }
  
  public Vector2D getPosition() { return position; }
  public void setPosition(Vector2D position) { this.position = position; }

  public void assignArea(AreaComponent areaComponent) {
    area = areaComponent;
  }
  
  public AreaComponent getArea() {
    return area;
  }
}