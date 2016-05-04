package jpyramid.entity;

import jpyramid.controller.GameSystem;
import jpyramid.graph.GraphException;
import jpyramid.graph.GraphNodeType;
import jpyramid.graph.GridGraphHelper;
import jpyramid.graph.NavigationGraphEdge;
import jpyramid.graph.NavigationGraphNode;
import jpyramid.graph.SparseGraph;

public class LevelComponent extends BaseComponent {

  SparseGraph<NavigationGraphNode,NavigationGraphEdge> graph;

  public LevelComponent(GameEntity owner, GameSystem gameSystem,
      Object[] arguments) throws EntityException {
    super(owner);
    owner.addComponent(this);
    gameSystem.registerComponent(this);
    
    init(arguments);
  }
  
  @Override
  public void init(Object[] arguments) {
    int width = (int) arguments[0];
    int height = (int) arguments[1];
    
    graph = new SparseGraph<NavigationGraphNode, NavigationGraphEdge>();
    // Seed the graph as a square grid with nodes equal to width times height
    // Then add edges to all four directions of each node
    try {
      GridGraphHelper.createConnectedGrid(graph, width, height, width, height,
          false, GraphNodeType.ROOM);
    } catch (GraphException e) {
      e.printStackTrace();
      System.out.println("Unable to create connected grid in level component.");
    }
  }
  
  public SparseGraph<NavigationGraphNode,NavigationGraphEdge> getGraph()
      throws EntityException {
    if (graph == null) {
      throw new EntityException("Unable to get graph: graph is uninitialized");
    }
    return graph;
  }
}