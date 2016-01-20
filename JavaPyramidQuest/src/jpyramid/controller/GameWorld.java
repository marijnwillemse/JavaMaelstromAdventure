package jpyramid.controller;

import java.util.HashMap;
import java.util.Map;

import jpyramid.model.GameEntity;
import jpyramid.model.Level;

public class GameWorld {

  private Map<Integer, GameEntity> entities;
  
  public GameWorld() {
    entities = new HashMap<Integer, GameEntity>();
  }
  
  public void run() {
    Level level = new Level(4, 4);
  }
  
  public GameEntity getEntity(int id) {
    return entities.get(id);
  }
}