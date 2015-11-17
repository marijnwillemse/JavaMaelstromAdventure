package jpyramid.graph;

public class Edge {
	
	Node from, to;
	
	public Edge(Node from, Node to) {
		this.from = from;
		this.to = to;
	}
	

	Node getFrom() { return from; }
	Node getTo() { return to; }
}
