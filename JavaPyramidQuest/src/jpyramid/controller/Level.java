package jpyramid.controller;

import jpyramid.graph.GraphException;
import jpyramid.graph.NavigationGraphNode;
import jpyramid.graph.SparseGraph;
import jpyramid.math.Vector2D;

public class Level {

  int width;
  int height;
  SparseGraph graph;

  public Level(int width, int height) {
    this.width = width;
    this.height = height;
    graph = new SparseGraph();
    seed();
  }

  /**
   * Seed the graph with nodes and edges according to width and height
   */
  private void seed() {
    for (int y=0; y<height; y++) {
      for (int x=0; x<width; x++) {
        try {
          graph.addNode(new NavigationGraphNode(graph.getNextNodeIndex(),
              new Vector2D((double)x, (double)y)));
        } catch (GraphException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
  //
  //  // Connect all adjacent nodes in the graph with edges
  //  for (int i=0; i<GRAPH_HEIGHT*GRAPH_WIDTH; i++) {
  //  
  //  for (int i=0; i<GRAPH_HEIGHT*GRAPH_WIDTH; i++) {
  //    // Add edges between all adjacent rooms in the graph
  //    try {
  //      // If not in last column
  //      if (i % GRAPH_WIDTH != GRAPH_WIDTH-1) {
  //        // Connect with east room
  //        graph.addEdge(new NavigationGraphEdge(i, i+1));
  //      }
  //      // If not in last row
  //      if (i % GRAPH_WIDTH * GRAPH != GRAPH_HEIGHT-1
  //          && i % GRAPH_WIDTH <) {
  //        // Connect with south room
  //        graph.addEdge(new NavigationGraphEdge(i, i+GRAPH_WIDTH));
  //      }
  //    } catch (GraphException e) {
  //      e.printStackTrace();
  //    }
  //  }

//
//  }
//
//  map.printGrid();
//  try {
//    map.removeEdge(0, 0, Direction.EAST);
//    map.removeEdge(1, 0, Direction.SOUTH);
//  } catch (GraphException e) {
//    e.printStackTrace();
//  }
//  map.printGrid();
//
//  map.printNode(0, 0);
//  map.printNode(1, 0);
