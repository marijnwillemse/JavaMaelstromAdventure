package jpyramid.graph;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class Node {
  private int x;
  private int y;

  private Map<Integer, Edge> edges
      = new HashMap<Integer, Edge>();
  
  public Node(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public Edge getEdge(Direction direction) {
    return edges.get(direction.getAngle());
  }

  public void print() {
    System.out.println("X[" + x + "] Y[" + y + "]");
    for (Direction d : EnumSet.allOf(Direction.class)) {
      if (edges.get(d.getAngle()) != null) {
        System.out.println(d.name() + " room: X["
            + edges.get(d.getAngle()).getTo().getX()
            + "] Y[" + edges.get(d.getAngle()).getTo().getY() + "]");
      }
    }
  }

  public int getX() { return x; }
  public int getY() { return y; }

  public void connect(Direction direction, Node node) {
    edges.put(direction.getAngle(), new Edge(this, node));
  }
  
  public void detach(Direction direction) {
    edges.remove(direction.getAngle());
  }
  
  public boolean hasEdge(Direction direction) {
    Edge edge = edges.get(direction.getAngle());
    return (edge != null);
  }
}