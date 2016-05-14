package maelstrom.entity;

import java.sql.Time;

import maelstrom.controller.GameSystem;

public class TimeComponent extends BaseComponent {
  
  private Time time;

  public TimeComponent(GameEntity owner, GameSystem gameSystem,
      Object[] arguments) {
    super(owner, gameSystem);
    owner.addComponent(this);
    gameSystem.registerComponent(this);
    
    init(arguments);
  }

  @Override
  void init(Object[] arguments) {
    if (arguments.length == 0) {
      // Initialize to zero
      time = new Time(0);
    } else if (arguments.length == 1) {
      // Initialize using milliseconds
      time = new Time((long) arguments[0]);
    } else {
      throw new IllegalArgumentException("Invalid argument count.");
    }
  }

  public Time getTime() {
    return time;
  }
  
  public long getTimeInMilliseconds() {
    return time.getTime();
  }

  public void increment(long milliseconds) {
    time = new Time(time.getTime() + milliseconds);
  }
}
