package jpyramid.model;

import jpyramid.graph.GraphHelper;
import jpyramid.graph.NavigationGraphEdge;
import jpyramid.graph.NavigationGraphNode;
import jpyramid.graph.SparseGraph;

public class Level {

  SparseGraph graph;

  public Level(int width, int height) {
    graph = new SparseGraph<NavigationGraphNode, NavigationGraphEdge>();
    
    // Seed the graph as a square grid with nodes equal to width times height
    // Then add edges to all four directions of each node
    GraphHelper.createConnectedGrid(graph, width, height, width, height, false);

    System.out.println("Hello world");
  }
}