package jpyramid.controller;

import jpyramid.graph.GraphException;
import jpyramid.graph.TwoDimensionalGraph;

public class Game {

	public void run() {
		TwoDimensionalGraph map = new TwoDimensionalGraph(3, 3);

		for(int i=0; i<map.numOfRows()-1; i++) {
			for(int j=0; j<map.numOfColumns()-1; j++) {
				// TODO Add edges between all adjacent rooms in graph
				try {
					map.addEdge(i, j, i+1, j);
					map.addEdge(i, j, i,   j+1);
				} catch (GraphException e) {
					e.printStackTrace();
				}
			}
		}
		
		map.printGrid();
		
		map.printRoom(0, 0);
		map.printRoom(0, 1);
	}
}
