package jpyramid.graph;

import jpyramid.math.Vector2D;

public class NavigationGraphNode extends GraphNode {
  
  private Vector2D position;
  
  public NavigationGraphNode(int i, Vector2D position) {
    super(i);
    this.position = position;
  }
  
  public Vector2D getPosition() { return position; }
}