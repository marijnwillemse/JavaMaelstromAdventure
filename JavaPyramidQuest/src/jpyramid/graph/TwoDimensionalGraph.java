package jpyramid.graph;

import java.util.ArrayList;

public class TwoDimensionalGraph {
  private ArrayList<ArrayList<Node>> grid; // two-dimensional array of node vertices

  /**
   * Initialize two-dimensional graph with one empty row
   */
  public TwoDimensionalGraph() {
    grid = new ArrayList<ArrayList<Node>>();
    addRow();
  }

  /**
   * Initialize two-dimensional graph with specified number of rows and columns
   */
  public TwoDimensionalGraph(int columns, int rows) {
    grid = new ArrayList<ArrayList<Node>>();

    if(rows==0 && columns>0) {
      throw new IllegalArgumentException("Rows must be larger than 0 to add columns");
    }

    /* Add rows */
    for(int i=0; i<rows; i++) {
      addRow();
    }
    /* Add columns*/
    for(int i=0; i<columns; i++) {
      addColumnToRows();
    }
  }

  public void addRow() {
    int numOfRows = numOfRows();
    int numOfColumns = numOfColumns();

    /* Prepare new row */
    ArrayList<Node> row = new ArrayList<Node>();

    /* Add number of columns as vertices to new row */ 
    for(int column=0; column<numOfColumns; column++) {
      row.add(new Node(numOfRows, column));
    }
    grid.add(row);
  }

  public void addColumnToRows() {
    int numOfRows = numOfRows();
    int numOfColumns = numOfColumns();

    /* Add new vertices to each row */
    for(int row=0; row<numOfRows; row++){
      grid.get(row).add(new Node(numOfColumns, row));
    }
  }

  /**
   * Function to connect edges between two nodes on both sides
   * @throws GraphException when nodes are not adjacent
   */
  public void addEdge(int x1, int y1, int x2, int y2) throws GraphException {
    Node a, b;
    Direction aDir, bDir;

    if(!(inGridBounds(x1, y1) && inGridBounds(x2, y2))) {
      throw new GraphException("Unable to add edge between Node[" + x1 + "][" + y1
          + "] and Node[" + x2 + "][" + y2 + "]: Out of grid bounds");
    }

    a = grid.get(y1).get(x1);
    b = grid.get(y2).get(x2);

    if      (x1==x2+1 && y1==y2  ) { aDir = Direction.WEST;}
    else if (x1==x2-1 && y1==y2  ) { aDir = Direction.EAST;}
    else if (x1==x2   && y1==y2+1) { aDir = Direction.NORTH;}
    else if (x1==x2   && y1==y2-1) { aDir = Direction.SOUTH;}
    else { throw new GraphException("Unable to add edge between Node["
        + x1 + "," + y1 + "] and Node[" + x2 + "," + y2 + "]: Not adjacent");
    }
    bDir = aDir.opposite();

//    System.out.println("Connecting [" + x1 + "," + y1
//        + "] to ["+ x2 + ","+ y2 + "]");
    a.connect(aDir, b);
    b.connect(bDir, a);
  }


  /**
   * Function to remove edges between two nodes on both sides
   * @throws GraphException when nodes are not adjacent
   */
  public void removeEdge(int x1, int y1, Direction aDir) throws GraphException {
    Node a, b;
    Direction bDir = aDir.opposite();

    if(!inGridBounds(x1, y1)) {
      throw new GraphException("Unable to remove " + aDir.name()
      + " edge of Node [" + x1 + "," + y1 + "]: Out of grid bounds");
    }

    // Locate the edge in Node a
    a = grid.get(y1).get(x1);
    if (a.getEdge(aDir) == null) {
      throw new GraphException("Unable to remove " + aDir.name()
      + " edge of Node[" + x1 + "," + y1
      + "]: Edge not found");
    }

    // Locate the edge in Node b
    b = a.getEdge(aDir).getTo();
    if (b.getEdge(bDir) == null) {
      throw new GraphException("Unable to remove " + bDir.name()
          + " edge of Node[" + b.getX() + "," + b.getY()
          + "]: Edge not found");
    }

    System.out.println("Removing " + aDir.name() + " from [" + x1 + "," + y1
        + "] and " + bDir.name() + " from [" + b.getX() + "," + b.getY() + "]");

    // Set edges in both Nodes to uninitialized
    a.detach(aDir);
    b.detach(bDir);
  }


  public void printNode(int x, int y) {
    if(inGridBounds(x, y))
      grid.get(y).get(x).print();
    else { System.out.println("Unable to print Node[" + x + "," + y + "]: Out of grid bounds"); }
  }

  private boolean inGridBounds(int x, int y) {
    return (y < numOfRows() && x < numOfColumns());
  }

  /**
   * Prints the nodes and edges present in grid into console 
   */
  public void printGrid() {
    if(grid.isEmpty()) {
      System.out.println("Unable to print grid: no rows");
    } else if (grid.get(0).isEmpty()) {
      System.out.println("Unable to print grid: no columns");
    } else {
      System.out.println("Graph:");
      for(int i=0; i<numOfRows(); i++) {
        for(int j=0; j<numOfColumns(); j++) {
          System.out.print("O");
          // Check for drawing of horizontal edge
          if (grid.get(i).get(j).hasEdge(Direction.EAST)) {
            System.out.print("-");
          } else {
            System.out.print(" ");
          }
        }
        System.out.println();
        // Line for drawing of vertical edges
        for(int j=0; j<numOfColumns(); j++) {
          // Check for vertical edge
          if (grid.get(i).get(j).hasEdge(Direction.SOUTH)) {
            System.out.print("|");
          } else {
            System.out.print(" ");
          }
          System.out.print(" ");
        }
        System.out.println();
      }
    }
  }

  public int numOfColumns() {
    if(!grid.isEmpty())
      return grid.get(0).size();
    else { return 0; }
  }

  public int numOfRows() {
    return grid.size(); // determined from number of rows
  }
}