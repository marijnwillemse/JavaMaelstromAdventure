package maelstrom.entity;

import java.util.ArrayList;
import java.util.UUID;

import maelstrom.controller.GameSystem;
import maelstrom.graph.Direction;
import maelstrom.graph.GridGraphHelper;
import maelstrom.graph.NavigationGraphEdge;
import maelstrom.graph.NavigationGraphNode;
import maelstrom.graph.SparseGraph;
import maelstrom.math.RandomGenerator;

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
  void init(Object[] arguments) {
    if (arguments.length != 2) {
      throw new IllegalArgumentException("Invalid amount of arguments given.");
    }
    int width = (int) arguments[0];
    int height = (int) arguments[1];

    graph = new SparseGraph<NavigationGraphNode, NavigationGraphEdge>();
    // Seed the graph as a square grid with nodes equal to width times height
    // Then add edges to all four directions of each node
    GridGraphHelper.createConnectedGrid(graph, width, height, width, height,
        false);
    // Construct area entities for each node in the grid
    for (int i = 0; i < width * height; i++) {

      Object[][] areaArguments = new Object[][] {
        { } // Area component arguments
      };
      
      UUID areaID = EntityFactory.createReflective( gameSystem, "AREA",
          areaArguments).getID();
      gameSystem.getAreaComponents().get(areaID).assignNode(
          graph.getNode(i));
      
      // Insert n enemies based on a Poisson-distribution
      int n = RandomGenerator.getPoisson(1/2);
      while (n > 0) {
        gameSystem.getAreaComponents().get(areaID).spawnEntity();
      }
    }
  }

  public void describeAvailableDirections(int nodeIndex) {
    ArrayList<Direction> directions =
        GridGraphHelper.getNeighborDirections(graph, nodeIndex);
    if (directions.size() > 0) {
      String message = "You can go ";
      for (int i = 0; i < directions.size(); i++) {
        Direction d = directions.get(i);
        message += d.toString();
        if (directions.size()-2 > i) {
          message += ", ";
        } else if (directions.size()-2 == i) {
          message += " and ";
        } else {
          message += ".";
        }
      }
      System.out.println(message);
    }
  }

  public SparseGraph<NavigationGraphNode,NavigationGraphEdge> getGraph() {
    return graph;
  }
}