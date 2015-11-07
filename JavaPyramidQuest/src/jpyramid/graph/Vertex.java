package jpyramid.graph;

import java.util.ArrayList;

public class Vertex {
	public int x;
	public int y;
    ArrayList<Vertex> adjacencyList;

	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void print() {
		System.out.println("X[" + x + "] Y[" + y + "]");
	}
}