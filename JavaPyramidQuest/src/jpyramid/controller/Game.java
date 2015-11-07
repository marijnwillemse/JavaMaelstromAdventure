package jpyramid.controller;

import jpyramid.graph.Graph;

public class Game {

	public void run() {
		Graph graph = new Graph();
		
		graph.addVertex("Tilburg");
		graph.addVertex("Breda");
		graph.addVertex("Eindhoven");
		graph.addVertex("Den Bosch");
		
		graph.printVertex(0);
		
		graph.addEdge(1, 2);
		
		graph.printLayout();
	}

}
