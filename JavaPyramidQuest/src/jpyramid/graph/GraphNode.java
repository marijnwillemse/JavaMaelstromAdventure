package jpyramid.graph;

public class GraphNode {
  
  protected int index;
  
  protected static final int INVALID_NODE_INDEX = -1;
  
  public GraphNode() {
    index = INVALID_NODE_INDEX;
  }
  public GraphNode(int i) {
    index = i;
  }
  
  int getIndex() { return index; }
  void setIndex(int i) { index = i; }
}