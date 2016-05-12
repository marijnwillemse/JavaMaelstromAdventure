package turbulent.graph;

public class GraphEdge {

  protected int from, to;

  protected double cost;

  public GraphEdge(int from, int to, double cost) {
    this.cost = cost;
    this.from = from;
    this.to = to;
  }
  public GraphEdge(int from, int to) {
    cost = 1.0;
    this.from = from;
    this.to = to;
  }
  public GraphEdge() {
    cost = 1.0;
    from = -1;
    to = -1;
  }

  public int getFrom()    { return from; }
  public int getTo()      { return to; }
  public double getCost() { return cost; }

  public void setFrom(int from)    { this.from = from; }
  public void setTo(int to)        { this.to = to; }
  public void setCost(double cost) { this.cost = cost; }
}
