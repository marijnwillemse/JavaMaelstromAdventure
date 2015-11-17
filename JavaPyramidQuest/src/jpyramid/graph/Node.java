package jpyramid.graph;

public class Node {
	private int x;
	private int y;
	
	Edge north;
	Edge east;
	Edge south;
	Edge west;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void print() {
		System.out.println("X[" + x + "] Y[" + y + "]");
		if(north != null)
			System.out.println("North room: X[" + north.getTo().getX() + "] Y[" + north.getTo().getY() + "]");
		if(east != null)
			System.out.println("East room: X[" + east.getTo().getX() + "] Y[" + east.getTo().getY() + "]");
		if(south != null)
			System.out.println("South room: X[" + south.getTo().getX() + "] Y[" + south.getTo().getY() + "]");
		if(west != null)
			System.out.println("West room: X[" + west.getTo().getX() + "] Y[" + west.getTo().getY() + "]");
	}
	
	private int getX() { return x; }
	private int getY() { return y; }

	public void setEdge(String direction, Node node) {
		switch(direction) {
			case "North":	north =	new Edge(this, node);	break;
			case "East":	east =	new Edge(this, node);	break;
			case "South":	south =	new Edge(this, node);	break;
			case "West":	west =	new Edge(this, node);	break;
		}
	}
}