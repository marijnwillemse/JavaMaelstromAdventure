package jpyramid.graph;

import java.util.ArrayList;

public class VertexGrid {
	private ArrayList<ArrayList<Vertex>> grid; // two-dimensional array of vertices

	public VertexGrid() {
		grid = new ArrayList<ArrayList<Vertex>>();
	}
	
	public void addRow() {
		ArrayList<Vertex> row = new ArrayList<Vertex>();
		grid.add(row);
		if(!grid.isEmpty()) {
			int gridWidth = grid.get(0).size(); // get width from first column size
			for(int i=0; i<gridWidth; i++){
				String label = Integer.toString(grid.size()) + i;
				row.add(new Vertex(label));
			}
		}
	}

	public void addColumn() {
		int count = 0;
		for(ArrayList<Vertex> row : grid) {
			String label = Integer.toString(grid.size()) + Integer.toString(count);
			row.add(new Vertex(label));
		}
//		grid.add(new ArrayList<Vertex>());
	}

	public void addEdge(int start, int end) {
		
	}

	public void printVertex(int v) {
//		vertexList.get(v).print();
	}
	
	public void printLayout() {
		// TODO Print vertices in console with monospaced characters
		System.out.println();
//		for(i=0; i<verte)
	}
	
	public int getHeight() {
		return grid.size();
	}
	
	public int getWidth() {
		if(!grid.isEmpty()) {
			return grid.get(0).size();
		} else { return 0; }
	}
}