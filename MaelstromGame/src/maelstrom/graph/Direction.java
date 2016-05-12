package maelstrom.graph;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Direction {
  NORTH(0),
  EAST(90),
  SOUTH(180),
  WEST(270);
  
  // Constructor
  private Direction(final int angle) {
    this.angle = angle;
  }
  
  // Internal state
  private int angle;
  
  public int getAngle() {
    return angle;
  }
  
  // Lookup table
  private static final Map<Integer, Direction> lookup
      = new HashMap<Integer, Direction>();
  
  // Populate lookup table on loading time
  static {
    for (Direction d : EnumSet.allOf(Direction.class)) {
      lookup.put(d.getAngle(), d);
    }
  }
  
  // Method which can be used for reverse lookup
  public static Direction get(int angle) {
    return lookup.get(angle);
  }
  
  public Direction opposite() {
    return lookup.get((angle + 180) % 360);
  }
}
