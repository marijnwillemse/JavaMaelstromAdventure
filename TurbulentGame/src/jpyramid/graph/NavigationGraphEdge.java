package jpyramid.graph;

public class NavigationGraphEdge extends GraphEdge {

  public NavigationGraphEdge(int from, int to, double cost) {
    super(from, to, cost);
  }
  public NavigationGraphEdge(int from, int to) {
    super(from, to, 1.0);
  }
  public NavigationGraphEdge() {
    super(GraphNode.INVALID_NODE_INDEX, GraphNode.INVALID_NODE_INDEX, 1.0);
  }
  
  /**
   * Copy constructor.
   */
  public NavigationGraphEdge(NavigationGraphEdge edge) {
    super(edge.getFrom(), edge.getTo(), edge.getCost());
  }

}
