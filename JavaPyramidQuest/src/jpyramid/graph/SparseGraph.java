package jpyramid.graph;

import java.util.ArrayList;

public class SparseGraph {

  // Array of nodes comprising graph
  private ArrayList<GraphNode> nodes;

  // Vector of adjacency edge lists
  private ArrayList<ArrayList<GraphEdge>> edges;

  private int nextNodeIndex;

  public SparseGraph() {
    nextNodeIndex = 0;
    nodes = new ArrayList<GraphNode>();
    edges = new ArrayList<ArrayList<GraphEdge>>();
  }

  public GraphNode getNode(int i) throws GraphException {
    if (i >= nodes.size() || i < 0) {
      throw new GraphException("Unable to get Node " + i
          + ": Invalid index");
    }
    return nodes.get(i);
  }

  public GraphEdge getEdge(int from, int to) throws GraphException {
    if (from >= nodes.size() || from < 0
        || nodes.get(from).index == GraphNode.INVALID_NODE_INDEX) {
      throw new GraphException(
          "Unable to get Edge " + from + to + ": Invalid 'from' index");
    }
    if (to >= nodes.size() || to < 0
        || nodes.get(to).index == GraphNode.INVALID_NODE_INDEX) {
      throw new GraphException(
          "Unable to get Edge " + from + to + ": Invalid 'to' index");
    }

    for (GraphEdge currentEdge : edges.get(from)) {
      if (currentEdge.getTo() == to) {
        return currentEdge;
      }
    }
    throw new GraphException(
        "Unable to get Edge " + from + to + ": edge does not exist");
  }

  public int getNextNodeIndex() { return nextNodeIndex; }

  /**
   * Adds a node to the graph and returns its index
   * 
   * First checks to see if the node has been added previously but
   * is now inactive. If it is, reactivate.
   * 
   * If the node is new it is checked to make sure its index matches
   * the next node index before insertion.
   */
  public int addNode(GraphNode node) throws GraphException {

    if (node.index < nodes.size()) {

      // Make sure the node does not have the same ID
      // as a currently active node
      if (nodes.get(node.index).index != GraphNode.INVALID_NODE_INDEX) {
        throw new GraphException(
            "Unable to add Node: node contains duplicate ID");
      }
      nodes.set(node.index, node);
      return nextNodeIndex;
    }
    else {
      // Make sure the new node is indexed correctly
      if (node.index != nextNodeIndex) {
        throw new GraphException(
            "Unable to add Node: invalid index");
      }

      nodes.add(node);
      edges.add(new ArrayList<GraphEdge>());

      return nextNodeIndex++;
    }
  }

  /**
   * Adds an edge to the graph.
   * Ensures that the edge passed as parameter is valid before insertion.
   * Also adds a similar edge connecting the nodes in the opposite direction.
   */
  public void addEdge(GraphEdge edge) throws GraphException {
    // Make sure the from and to nodes exist
    if (edge.getFrom() >= nextNodeIndex || edge.getTo() >= nextNodeIndex) {
      throw new GraphException(
          "Unable to add Edge: invalid node index");
    }

    // Make sure both nodes are active
    if (   nodes.get(edge.getTo()  ).index != GraphNode.INVALID_NODE_INDEX
        && nodes.get(edge.getFrom()).index != GraphNode.INVALID_NODE_INDEX) {
      // Add the edge
      if (UniqueEdge(edge.getFrom(), edge.getTo())) {
        edges.get(edge.getFrom()).add(edge);
      }
    }

    // Add another connection leading from the opposite direction
    if (UniqueEdge(edge.getTo(), edge.getFrom())) {
      GraphEdge oppositeEdge = edge;
      oppositeEdge.setTo(edge.getFrom());
      oppositeEdge.setFrom(edge.getTo());

      edges.get(edge.getTo()).add(oppositeEdge);
    }
  }

  /**
   * Removes a node from the graph including any links to neighboring nodes
   */
  public void removeNode(int node) throws GraphException {
    if (node >= nodes.size()) {
      throw new GraphException(
          "Unable to remove Node: invalid node index");
    }

    // Set the node index to invalid
    nodes.get(node).setIndex(GraphNode.INVALID_NODE_INDEX);

    // Remove all edges leading to this node then clear edges leading from it
    for (GraphEdge edgeFrom : edges.get(node)) {
      for (GraphEdge edgeTo : edges.get(edgeFrom.getTo())) {
        if (edgeTo.getTo() == node) {
          edges.get(edgeFrom.getTo()).remove(edgeTo);
          break; // Break out of edgeTo iteration back to edgeFrom iteration
        }
      }
    }
    // Clear this node's edges
    edges.remove(node);
  }

  /**
   * Removes edge from connecting nodes
   */
  void RemoveEdge(int from, int to) throws GraphException {
    if (from <= nodes.size() || to >= nodes.size()) {
      throw new GraphException(
          "Unable to remove Edge: invalid node index");
    }
    // Remove edge from edge list of node it leads to 
    for (GraphEdge edge : edges.get(to)) {
      if (edge.getTo() == from) {
        edges.get(to).remove(edge);
        break;
      }
    }
    
    // Do the same with edge list of node it comes from
    for (GraphEdge edge : edges.get(from)) {
      if (edge.getTo() == to) {
        edges.get(from).remove(edge);
        break;
      }
    }
  }

  /**
   * Returns true if the edge is not present in the graph.
   * Applied when adding edges in order to prevent duplication.
   */
  boolean UniqueEdge(int from, int to) {
    for (GraphEdge edge : edges.get(from)) {
      if (edge.getTo() == to) {
        return false;
      }
    }
    return true;
  }
}
