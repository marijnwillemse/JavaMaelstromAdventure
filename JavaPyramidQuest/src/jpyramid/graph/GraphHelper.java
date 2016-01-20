package jpyramid.graph;

import jpyramid.math.Vector2D;

public class GraphHelper {

  public static void createConnectedGrid(SparseGraph graph,
      int width, int height,
      int numberOfCellsX, int numberOfCellsY,
      boolean diagonalEdges) {

    // Variables to help calculate node center positions
    double cellSpacingX = (double)width / (double)numberOfCellsX;
    double cellSpacingY = (double)height / (double)numberOfCellsY;

    // Create all nodes and add to graph
    for (int row = 0; row < numberOfCellsY; row++) {
      for (int column = 0; column < numberOfCellsX; column++) {
        try {
          graph.addNode(new NavigationGraphNode(graph.getNextNodeIndex(),
              new Vector2D((double)column * cellSpacingX,
                  (double)row * cellSpacingY)));
        } catch (GraphException e) {
          e.printStackTrace();
        }
      }
    }

    // Calculate edges which have position [x][y] in a 2D array but position
    // [y * numberOfCellsX + x] in a 1D array.
    // Each cell has up to four neighbors.
    for (int row = 0; row < numberOfCellsY; ++row) {
      for (int column = 0; column < numberOfCellsX; ++column) {
        addNeighboursToGridNode(graph, row, column,
            numberOfCellsX, numberOfCellsY, diagonalEdges);
      }
    }
    System.out.println("Finished creating grid.");
  }

  /**
   * Returns true if [x][y] is a valid position in the grid
   * For example: [4][0] is not a valid position in a grid of 3 by 2 cells
   */
  private static boolean isValidNeighbor(int row, int column,
      int numberOfCellsX, int numberOfCellsY) {
    return (row >= 0 && row < numberOfCellsY
        && column >= 0 && column < numberOfCellsX);
  }


  private static void addNeighboursToGridNode(SparseGraph graph,
      int row, int column, int numberOfCellsX, int numberOfCellsY,
      boolean diagonalEdges) {

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

        if (isValidNeighbor(neighborY, neighborX, numberOfCellsX, numberOfCellsY)) {

          int nodeIndex = row * numberOfCellsX + column;
          int neighborIndex = neighborY * numberOfCellsX + neighborX;

          try {
            // Calculate the distance between the nodes
            Vector2D nodePosition;
            Vector2D neighborPosition;
            nodePosition = graph.getNode(nodeIndex).getPosition();
            neighborPosition = graph.getNode(neighborIndex).getPosition();
            double distance = nodePosition.distanceTo(neighborPosition);

            // Add edge to graph
            graph.addEdge(new NavigationGraphEdge(nodeIndex, neighborIndex, distance));

          } catch (GraphException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}