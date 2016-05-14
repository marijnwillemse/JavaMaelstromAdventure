package maelstrom.entity;

import java.sql.Time;

import maelstrom.controller.GameSystem;
import maelstrom.utilities.GameLocale;

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
  
  public void describe() {
    // The hour of day is required in order to describe the time of day.
    long milliseconds = time.getTime();
    long dayTime = milliseconds % (86400000);
    int hours = Math.round(dayTime / 3600000);

    if (hours > 5 && hours < 12) {
      // Morning
      System.out.println(GameLocale.getString("TXT_KEY_TIME_DESCRIPTION_MORNING"));
    } else if (hours < 13) {
      // Noon
      System.out.println(GameLocale.getString("TXT_KEY_TIME_DESCRIPTION_NOON"));
    } else if (hours < 18) {
      // Afternoon
      System.out.println(GameLocale.getString("TXT_KEY_TIME_DESCRIPTION_AFTERNOON"));
    } else if (hours < 20) {
      // Evening
      System.out.println(GameLocale.getString("TXT_KEY_TIME_DESCRIPTION_EVENING"));
    } else {
      // Night
      System.out.println(GameLocale.getString("TXT_KEY_TIME_DESCRIPTION_NIGHT"));
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
