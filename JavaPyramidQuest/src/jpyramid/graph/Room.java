package jpyramid.graph;

public class Room {
	private int x;
	private int y;
	
	Room north;
	Room east;
	Room south;
	Room west;

	public Room(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void print() {
		System.out.println("X[" + x + "] Y[" + y + "]");
		if(north != null)
			System.out.println("North room: X[" + north.getX() + "] Y[" + north.getY() + "]");
		if(east != null)
			System.out.println("East room: X[" + east.getX() + "] Y[" + east.getY() + "]");
		if(south != null)
			System.out.println("South room: X[" + south.getX() + "] Y[" + south.getY() + "]");
		if(west != null)
			System.out.println("West room: X[" + west.getX() + "] Y[" + west.getY() + "]");
	}
	
	private int getX() { return x; }
	private int getY() { return y; }

	public void setEdge(String direction, Room room) {
		switch(direction) {
			case "North":	north = room;	break;
			case "East":	east = room;	break;
			case "South":	south = room;	break;
			case "West":	west = room;	break;
		}
	}
}