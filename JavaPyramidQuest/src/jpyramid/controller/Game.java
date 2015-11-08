package jpyramid.controller;

import jpyramid.graph.TwoDimensionalGraph;

public class Game {

	public void run() {
//		TwoDimensionalGraph map = new TwoDimensionalGraph();
		TwoDimensionalGraph map = new TwoDimensionalGraph(0, 1);

//		map.addColumn();
//		map.addColumn();
//		map.addColumn();
//		map.addRow();
//		map.addRow();
//		map.addRow();

		map.printGrid();
	}
}
