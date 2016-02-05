package jpyramid.graph;

import jpyramid.math.Vector2D;

public class GraphNodeFactory {

  public static NavigationGraphNode buildNavigationGraphNode(GraphNodeType type) {
    NavigationGraphNode node = null;
    switch (type) {
      case ROOM:
        node = new Room(-1, new Vector2D());
        break;
      default:
        break;
    }
    return node;
  }

}