package jpyramid.graph;

import java.util.ArrayList;

public class Graph {
	private ArrayList<Vertex> vertexList; // array of vertices

	public Graph() {
		vertexList = new ArrayList<Vertex>();
	}

	public void addVertex(String label) {
		vertexList.add(new Vertex(label));
	}

	public void addEdge(int start, int end) {
		
	}

	public void printVertex(int v) {
		vertexList.get(v).print();
	}
	
	public void printLayout() {
		// TODO Print vertices in console with monospaced characters
		System.out.println();
//		for(i=0; i<verte)
	}

}