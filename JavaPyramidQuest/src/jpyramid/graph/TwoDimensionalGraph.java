package jpyramid.graph;

import java.util.ArrayList;

public class TwoDimensionalGraph {
	private ArrayList<ArrayList<Vertex>> grid; // two-dimensional array of vertices

	/**
	 * Initialize two-dimensional graph with one empty row
	 */
	public TwoDimensionalGraph() {
		grid = new ArrayList<ArrayList<Vertex>>();
		addRow();
	}
	
	/**
	 * Initialize two-dimensional graph with specified number of rows and columns
	 */
	public TwoDimensionalGraph(int rows, int columns) {
		grid = new ArrayList<ArrayList<Vertex>>();
		
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
		ArrayList<Vertex> row = new ArrayList<Vertex>();
		
		/* Add number of columns as vertices to new row */ 
		for(int column=0; column<numOfColumns; column++) {
			row.add(new Vertex(numOfRows, column));
		}
		grid.add(row);
	}

	public void addColumnToRows() {
		int numOfRows = numOfRows();
		int numOfColumns = numOfColumns();
		
		/* Add new vertices to each row */
		for(int row=0; row<numOfRows; row++){
			grid.get(row).add(new Vertex(row, numOfColumns));
		}
	}

	public void addEdge(int x, int y) {
		
	}

	public void printVertex(int x, int y) {
		if(y<numOfRows() && x< numOfColumns())
			grid.get(y).get(x).print();
		else { System.out.println("Unable to print Vertex[" + x + "][" + y + "]: Out of grid bounds"); }
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