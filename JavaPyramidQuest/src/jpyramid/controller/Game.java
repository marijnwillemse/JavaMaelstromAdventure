package jpyramid.controller;

import jpyramid.graph.GraphException;
import jpyramid.graph.NavigationGraphEdge;
import jpyramid.graph.NavigationGraphNode;
import jpyramid.graph.SparseGraph;
import jpyramid.math.Vector2D;

public class Game {

  static final int GRAPH_WIDTH = 4;
  static final int GRAPH_HEIGHT = 3;

  public void run() {
    SparseGraph graph = new SparseGraph();

    // Seed the graph
    for(int y=0; y<GRAPH_HEIGHT; y++) {
      for(int x=0; x<GRAPH_WIDTH; x++) {
        try {
          graph.addNode(new NavigationGraphNode(graph.getNextNodeIndex(),
              new Vector2D((double)x, (double)y)));
        } catch (GraphException e) {
          e.printStackTrace();
        }
      }
    }
    System.out.println("Hello");
  }
}

//
//    // Connect all adjacent nodes in the graph with edges
//    for(int y=0; y<GRAPH_HEIGHT; y++) {
//      for(int x=0; x<GRAPH_WIDTH; x++) {
//        graph.addEdge(new NavigationGraphEdge());
//        // Add edges between all adjacent rooms in graph
//        try {
//          if(x != map.numOfColumns()-1) { // If not in last column
//            map.addEdge(x, y, x+1, y); // Connect east room
//          }
//          if(y != map.numOfRows()-1) { // If not in last row
//            map.addEdge(x, y, x,   y+1); // Connect south room
//          }
//        } catch (GraphException e) {
//          e.printStackTrace();
//        }
//      }
//    }
//
//    map.printGrid();
//    try {
//      map.removeEdge(0, 0, Direction.EAST);
//      map.removeEdge(1, 0, Direction.SOUTH);
//    } catch (GraphException e) {
//      e.printStackTrace();
//    }
//    map.printGrid();
//
//    map.printNode(0, 0);
//    map.printNode(1, 0);