package jpyramid.entity;

import jpyramid.controller.GameSystem;
import jpyramid.graph.GridGraphHelper;
import jpyramid.graph.NavigationGraphEdge;
import jpyramid.graph.NavigationGraphNode;
import jpyramid.graph.SparseGraph;

public class LevelComponent extends BaseComponent {

  SparseGraph<NavigationGraphNode,NavigationGraphEdge> graph;

  public LevelComponent(GameEntity owner, GameSystem gameSystem,
      Object[] arguments) {
    super(owner, gameSystem);
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
    GridGraphHelper.createConnectedGrid(graph, width, height, width, height,
        false);
    // Construct area entities for each node in the grid
    for (int i = 0; i < width * height; i++) {
      GameEntity area = EntityFactory.create(
          gameSystem, EntityFactory.Type.AREA,
          new Object[2][0]);
      gameSystem.getAreaComponents().get(area.getID()).assignNode(
          graph.getNode(i));
    }
  }
  
  public SparseGraph<NavigationGraphNode,NavigationGraphEdge> getGraph() {
    if (graph == null) {
      throw new EntityException("Unable to get graph: graph is uninitialized");
    }
    return graph;
  }
}