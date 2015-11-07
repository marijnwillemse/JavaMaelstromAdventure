package jpyramid.controller;

import jpyramid.graph.VertexGrid;

public class Game {

	public void run() {
		VertexGrid map = new VertexGrid();

		map.addColumn();
		map.addColumn();
		map.addRow();
		map.addRow();



		map.printGrid();
		
//		map.printVertex(0, 0);
//		map.printVertex(1, 0);
//		map.printVertex(2, 0);
//
//		map.printVertex(0, 1);
//		map.printVertex(1, 1);
//		map.printVertex(2, 1);
//
//		map.printVertex(0, 2);
//		map.printVertex(1, 2);
//		map.printVertex(2, 2);

	}
}
