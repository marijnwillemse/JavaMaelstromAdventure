package jpyramid.graph;

import java.util.ArrayList;

public class Vertex {
	public String label;
    ArrayList<Vertex> adjacencyList;

	public Vertex(String label) {
		this.label = label;
	}

	public void print() {
		System.out.println(label);
	}
}