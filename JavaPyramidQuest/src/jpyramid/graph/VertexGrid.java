package jpyramid.graph;

import java.util.ArrayList;

public class VertexGrid {
	private ArrayList<ArrayList<Vertex>> grid; // two-dimensional array of vertices

	public VertexGrid() {
		grid = new ArrayList<ArrayList<Vertex>>();
		
		/* Add initial vertex in new row and column */
		ArrayList<Vertex> column = new ArrayList<Vertex>();
		column.add(new Vertex(0, 0));
		grid.add(column);
	}
	
	public void addRow() {
		int width = gridWidth();
		int height = gridHeight();
		for(int x=0; x<width; x++){
			grid.get(x).add(new Vertex(x, height+1));
		}
	}

	public void addColumn() {
		int width = gridWidth();
		int height = gridHeight();
		
		ArrayList<Vertex> column = new ArrayList<Vertex>();
		for(int i=0; i<height; i++) {
			column.add(new Vertex(width+1, i));
		}
	}

	public void addEdge(int start, int end) {
		
	}

	public void printVertex(int x, int y) {
		grid.get(x).get(y).print();
	}
	
	public void printGrid() {
		// TODO Print vertices in console with monospaced characters
		System.out.println();
		for(int i=0; i<gridHeight(); i++) {
			for(int j=0; j<gridWidth(); j++) {
				System.out.print("O");
			}
			System.out.println("");
		}
	}
	
	public int gridHeight() {
		return grid.size(); // determined from number of rows
	}
	
	public int gridWidth() {
		return grid.get(0).size();
	}
}