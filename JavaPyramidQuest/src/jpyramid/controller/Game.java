package jpyramid.controller;

import jpyramid.graph.Direction;
import jpyramid.graph.GraphException;
import jpyramid.graph.TwoDimensionalGraph;

public class Game {

  static final int GRAPH_WIDTH = 4;
  static final int GRAPH_HEIGHT = 3;

  public void run() {
    TwoDimensionalGraph map = new TwoDimensionalGraph(GRAPH_WIDTH, GRAPH_HEIGHT);

    /* Connect all adjacent rooms in map with edges */
    for(int y=0; y<map.numOfRows(); y++) {
      for(int x=0; x<map.numOfColumns(); x++) {
        // Add edges between all adjacent rooms in graph
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
    try {
      map.removeEdge(0, 0, Direction.EAST);
      map.removeEdge(1, 0, Direction.SOUTH);
    } catch (GraphException e) {
      e.printStackTrace();
    }
    map.printGrid();

    map.printNode(0, 0);
    map.printNode(1, 0);
  }
}
