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
	public TwoDimensionalGraph(int rows, int columns) {
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

	public void addEdge(int x1, int y1, int x2, int y2) throws GraphException {
		Node a, b;
		String aDirection = null, bDirection = null;
		
		if(inGridBounds(x1, y1) && inGridBounds(x2, y2)) {
			System.out.println("Connecting " + x1 + "," + y1 + " to " + x2 + "," + y2);
			a = grid.get(y1).get(x1);
			b = grid.get(y2).get(x2);
			
			if		(x1==x2+1 && y1==y2  ) { aDirection = "West";  bDirection = "East"; }
			else if (x1==x2-1 && y1==y2  ) { aDirection = "East";  bDirection = "West"; }
			else if (x1==x2   && y1==y2+1) { aDirection = "North"; bDirection = "South"; }
			else if (x1==x2   && y1==y2-1) { aDirection = "South"; bDirection = "North"; }
			else { throw new GraphException("Unable to add edge between Node[" + x1 + "][" + y1
				+ "] and Node[" + x2 + "][" + y2 + "]: Not adjacent"); }
			
			a.setEdge(aDirection, b);
			b.setEdge(bDirection, a);
		}
		else { throw new GraphException("Unable to add edge between Node[" + x1 + "][" + y1
				+ "] and Node[" + x2 + "][" + y2 + "]: Out of grid bounds"); }
	}

	public void printNode(int x, int y) {
		if(inGridBounds(x, y))
			grid.get(y).get(x).print();
		else { System.out.println("Unable to print Node[" + x + "][" + y + "]: Out of grid bounds"); }
	}
	
	private boolean inGridBounds(int x, int y) {
		return (y < numOfRows() && x < numOfColumns());
	}
	
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
				}
				System.out.println("");
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