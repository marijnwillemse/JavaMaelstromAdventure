package jpyramid.graph;

import java.util.ArrayList;

public class TwoDimensionalGraph {
	private ArrayList<ArrayList<Vertex>> grid; // two-dimensional array of vertices

	public TwoDimensionalGraph() {
		grid = new ArrayList<ArrayList<Vertex>>();
		
		/* Add first row */
		addRow();
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

	public void addColumn() {
		int numOfRows = numOfRows();
		int numOfColumns = numOfColumns();
		for(int row=0; row<numOfRows; row++){
			grid.get(row).add(new Vertex(row, numOfColumns));
		}
	}

	public void addEdge(int start, int end) {
		
	}

	public void printVertex(int x, int y) {
		grid.get(y).get(x).print();
	}
	
	public void printGrid() {
		// TODO Print vertices in console with monospaced characters
		System.out.println();
		for(int i=0; i<numOfRows(); i++) {
			for(int j=0; j<numOfColumns(); j++) {
				System.out.print("O");
			}
			System.out.println("");
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