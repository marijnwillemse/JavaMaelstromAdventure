package turbulent.graph;

import java.util.ArrayList;

import turbulent.math.Vector2D;

public class GridGraphHelper {

  public static void createConnectedGrid(
      SparseGraph<NavigationGraphNode,NavigationGraphEdge> graph,
      int width, int height,
      int numberOfCellsX, int numberOfCellsY,
      boolean diagonalEdges) {
    
    // Validate arguments
    if (width < 1 || height < 1 || numberOfCellsX < 1 || numberOfCellsY < 1) {
      throw new GraphException("Unable to create grid: invalid arguments");
    }

    // Variables to help calculate node center positions
    double cellSpacingX = (double)width / (double)numberOfCellsX;
    double cellSpacingY = (double)height / (double)numberOfCellsY;

    // Create all nodes and add to graph
    for (int row = 0; row < numberOfCellsY; row++) {
      for (int column = 0; column < numberOfCellsX; column++) {
        NavigationGraphNode node = new NavigationGraphNode(-1, new Vector2D());
        node.setIndex(graph.getNextNodeIndex());
        node.setPosition(new Vector2D((double)column * cellSpacingX, (double)row * cellSpacingY));
        graph.addNode(node);
      }
    }

    // Calculate edges which have position [x][y] in a 2D array but position
    // [y * numberOfCellsX + x] in a 1D array.
    // Each cell has up to four neighbors.
    for (int row = 0; row < numberOfCellsY; ++row) {
      for (int column = 0; column < numberOfCellsX; ++column) {
        addNeighborsToGridNode(graph, row, column,
            numberOfCellsX, numberOfCellsY, diagonalEdges);
      }
    }
  }

  /**
   * Returns true if [x][y] is a valid position in the grid
   * For example: [4][0] is not a valid position in a grid of 3 by 2 cells
   */
  private static boolean isValidPosition(int row, int column,
      int numberOfCellsX, int numberOfCellsY) {
    return (row >= 0 && row < numberOfCellsY
        && column >= 0 && column < numberOfCellsX);
  }


  private static void addNeighborsToGridNode(
      SparseGraph<NavigationGraphNode,NavigationGraphEdge> graph,
      int row, int column, int numberOfCellsX, int numberOfCellsY,
      boolean diagonalEdges) {

	// Loop through all neighboring nodes O around node X
	// OOO 
	// OXO
	// OOO
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        int neighborX = column + j;
        int neighborY = row + i;

        // Skip if equal to current node
        if (i == 0 && j == 0) {
          continue;
        }

        // Skip diagonal edges if necessary
        if (!diagonalEdges && ( 
               ((i == -1) && (j == 1))
            || ((i == 1)  && (j == 1))
            || ((i == -1) && (j == -1))
            || ((i == 1)  && (j == -1)))) {
          continue;
        }

        if (isValidPosition(neighborY, neighborX, numberOfCellsX, numberOfCellsY)) {
          // Current neighbor node qualifies for edge connection
          
          int nodeIndex = row * numberOfCellsX + column;
          int neighborIndex = neighborY * numberOfCellsX + neighborX;

          try {
            // Calculate the distance between the nodes
            Vector2D nodePosition;
            Vector2D neighborPosition;
            nodePosition = graph.getNode(nodeIndex).getPosition();
            neighborPosition = graph.getNode(neighborIndex).getPosition();
            double distance = nodePosition.distanceTo(neighborPosition);

            // Add edge to graph coming from both sides
            graph.addEdge(new NavigationGraphEdge(nodeIndex, neighborIndex, distance));
            graph.addEdge(new NavigationGraphEdge(neighborIndex, nodeIndex, distance));

          } catch (GraphException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
  
  public static Direction getDirection(Vector2D from, Vector2D to) {
    int angle = (int) Math.toDegrees(Math.atan2(
        to.getY() - from.getY(),
        to.getX() - from.getX()));
    
    return Direction.get((angle % 360 + 360) % 360);
    // Note the expression "(a % b + b) % b"
    // This works as the result of (a % b) is necessarily lower than b,
    // no matter if a is positive or negative. Adding b takes care of the
    // negative values of a, since (a % b) is a negative value between -b and 0,
    // (a % b + b) is necessarily lower than b and positive.
    // The last modulo is there in case a was positive to begin with,
    // since if a is positive (a % b + b) would become larger than b.
    // Therefore, (a % b + b) % b turns it into smaller than b again.
    // Taken from answer of Peter Lawrey at Stack Overflow
  }

  /**
   * Return a list of direction objects in which the neighboring nodes of node
   * with index i lie.
   */
  public static ArrayList<Direction> getNeighborDirections(
      SparseGraph<NavigationGraphNode, NavigationGraphEdge> graph, int i) {
    ArrayList<Direction> directionList = new ArrayList<Direction>();
    NavigationGraphNode from = graph.getNode(i);
    ArrayList<NavigationGraphEdge> edges = graph.getEdgeList(i);
    for (NavigationGraphEdge edge : edges) {
      NavigationGraphNode to = graph.getNode(edge.getTo());
      directionList.add(getDirection(from.getPosition(), to.getPosition()));
    }
    return directionList;
  }

  public static NavigationGraphNode getNeighborByDirection(
      SparseGraph<NavigationGraphNode, NavigationGraphEdge> graph, int i,
      Direction d) {
    NavigationGraphNode from = graph.getNode(i);
    ArrayList<NavigationGraphEdge> edges = graph.getEdgeList(i);
    for (NavigationGraphEdge edge : edges) {
      NavigationGraphNode to = graph.getNode(edge.getTo());
      Direction reference = getDirection(from.getPosition(), to.getPosition());
      if (d.equals(reference)) {
        // Match found
        return to;
      }
    }
    // Match not found
    return null;
  }
}