package jpyramid.controller;

import jpyramid.graph.TwoDimensionalGraph;

public class Game {

	public void run() {
		TwoDimensionalGraph map = new TwoDimensionalGraph(3, 3);

		for(int i=0; i<map.numOfRows(); i++) {
			for(int j=0; j<map.numOfColumns(); j++) {
				// TODO Add edges between all adjacent vertices in graph
//				map.addEdge(a, b);
			}
		}
		
		map.printGrid();
	}
}
