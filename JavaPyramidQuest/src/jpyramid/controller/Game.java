package jpyramid.controller;

import jpyramid.graph.VertexGrid;

public class Game {

	public void run() {
		VertexGrid map = new VertexGrid();
		
//		map.addRow();
//		map.addRow();
		map.addColumn();
		map.addColumn();
		map.addColumn();
		
		
		System.out.println("Width: " + map.getWidth());
		System.out.println("Height: " + map.getHeight());
		
//		graph.addEdge(1, 2);
//		graph.printLayout();
	}

}
