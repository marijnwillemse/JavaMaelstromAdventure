package jpyramid.graph;

public class GraphNode {
  protected int index;
  
  public GraphNode() {
    index = -1;
  }
  public GraphNode(int i) {
    index = i;
  }
  
  int getIndex() { return index; }
  void setIndex(int i) { index = i; }
}