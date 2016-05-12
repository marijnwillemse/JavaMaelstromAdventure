package turbulent.graph;

import java.util.ArrayList;

public class SparseGraph<T extends GraphNode, K extends GraphEdge> {

  // Array of nodes comprising graph
  private ArrayList<T> nodes;

  // Vector of adjacency edge lists, sorted by node index
  private ArrayList<ArrayList<K>> edgeLists;

  private int nextNodeIndex;

  public SparseGraph() {
    nextNodeIndex = 0;
    nodes = new ArrayList<T>();
    edgeLists = new ArrayList<ArrayList<K>>();
  }

  public T getNode(int i) {
    if (i >= nodes.size() || i < 0) {
      throw new GraphException("Unable to get Node " + i
          + ": Invalid index");
    }
    return nodes.get(i);
  }

  public K getEdge(int from, int to) {
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

    for (K currentEdge : edgeLists.get(from)) {
      if (currentEdge.getTo() == to) {
        return currentEdge;
      }
    }
    throw new GraphException(
        "Unable to get Edge " + from + to + ": edge does not exist");
  }

  public int getNextNodeIndex() { return nextNodeIndex; }

  /**
   * Returns the number of active and inactive nodes
   */
  public int numberOfNodes() { return nodes.size(); }

  /**
   * Returns the number of active nodes
   */
  public int numberOfActiveNodes() {
    int count = 0;
    for (int n=0; n<nodes.size(); n++) {
      if (nodes.get(n).index != GraphNode.INVALID_NODE_INDEX) {
        count++;
      }
    }
    return count;
  }

  /**
   * Return the total number of edges
   */
  public int numberOfEdges() {
    int count = 0;
    for (ArrayList<K> edgeList : edgeLists) {
      count += edgeList.size();
    }
    return count;
  }
  
  /**
   * Returns true if a node with given index exists
   */
  public boolean isNodePresent(int n) {
    if (n >= nodes.size()
        || nodes.get(n).index == GraphNode.INVALID_NODE_INDEX) {
      return false;
    } else return true;
  }

  /**
   * Returns true if an edge connecting the from and to nodes exists
   */
  public boolean isEdgePresent(int from, int to) {
    if (isNodePresent(from) && isNodePresent(to)) {
      for (K edge : edgeLists.get(from)) {
        if (edge.getTo() == to) { return true; }
      }
      return false;
    } else return false;
  }

  /**
   * Adds a node to the graph and returns its index
   * 
   * First checks to see if the node has been added previously but
   * is now inactive. If it is, reactivate.
   * 
   * If the node is new it is checked to make sure its index matches
   * the next node index before insertion.
   */
  public int addNode(T node) throws GraphException {

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
      edgeLists.add(new ArrayList<K>());

      return nextNodeIndex++;
    }
  }

  /**
   * Adds an edge to the graph.
   * Ensures that the edge passed as parameter is valid before insertion.
   */
  public void addEdge(K edge) throws GraphException {
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
        edgeLists.get(edge.getFrom()).add(edge);
      }
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
    for (K edgeFrom : edgeLists.get(node)) {
      for (K edgeTo : edgeLists.get(edgeFrom.getTo())) {
        if (edgeTo.getTo() == node) {
          edgeLists.get(edgeFrom.getTo()).remove(edgeTo);
          break; // Break out of edgeTo iteration back to edgeFrom iteration
        }
      }
    }
    // Clear this node's edges
    edgeLists.remove(node);
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
    for (K edge : edgeLists.get(to)) {
      if (edge.getTo() == from) {
        edgeLists.get(to).remove(edge);
        break;
      }
    }

    // Do the same with edge list of node it comes from
    for (K edge : edgeLists.get(from)) {
      if (edge.getTo() == to) {
        edgeLists.get(from).remove(edge);
        break;
      }
    }
  }

  /**
   * Returns true if the edge is not present in the graph.
   * Applied when adding edges in order to prevent duplication.
   */
  boolean UniqueEdge(int from, int to) {
    for (K edge : edgeLists.get(from)) {
      if (edge.getTo() == to) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the list of edges that belong to the node with index i
   */
  public ArrayList<K> getEdgeList(int i) {
    return edgeLists.get(i);
  }
}
