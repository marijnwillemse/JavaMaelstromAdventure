package jpyramid.entity;

import jpyramid.controller.GameWorld;
import jpyramid.graph.GraphException;
import jpyramid.graph.GraphNodeType;
import jpyramid.graph.GridGraphHelper;
import jpyramid.graph.NavigationGraphEdge;
import jpyramid.graph.NavigationGraphNode;
import jpyramid.graph.SparseGraph;

public class LevelComponent extends BaseComponent {

  SparseGraph<NavigationGraphNode,NavigationGraphEdge> graph;

  public LevelComponent(GameEntity owner, GameWorld gameWorld,
      Object[] arguments) throws EntityException {
    super(owner);
    owner.addComponent(this);
    gameWorld.registerComponent(this);
    
    setup((int) arguments[0], (int) arguments[1]);
  }
  
  @Override
  void init(Object[] arguments) {
    // TODO Auto-generated method stub
    
  }
  
  public void setup(int width, int height) throws EntityException {
    graph = new SparseGraph<NavigationGraphNode, NavigationGraphEdge>();
    
    // Seed the graph as a square grid with nodes equal to width times height
    // Then add edges to all four directions of each node
    try {
      GridGraphHelper.createConnectedGrid(graph, width, height, width, height,
          false, GraphNodeType.ROOM);
    } catch (GraphException e) {
      throw new EntityException(e);
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