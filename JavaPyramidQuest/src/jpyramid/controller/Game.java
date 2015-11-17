package jpyramid.controller;

import jpyramid.graph.GraphException;
import jpyramid.graph.TwoDimensionalGraph;

public class Game {

	public void run() {
		TwoDimensionalGraph map = new TwoDimensionalGraph(3, 3);
		
		/* Connect all adjacent rooms in map with edges */
		for(int y=0; y<map.numOfRows(); y++) {
			for(int x=0; x<map.numOfColumns(); x++) {
				// TODO Add edges between all adjacent rooms in graph
				try {
					if(x != map.numOfColumns()-1) { // If not in last column
						map.addEdge(x, y, x+1, y); // Connect east room
					}
					if(y != map.numOfRows()-1) { // If not in last row
						map.addEdge(x, y, x,   y+1); // Connect south room
					}
				} catch (GraphException e) {
					e.printStackTrace();
				}
			}
		}
		
		map.printGrid();
		
		map.printNode(0, 0);
		map.printNode(1, 0);
		map.printNode(2, 0);
	}
}
