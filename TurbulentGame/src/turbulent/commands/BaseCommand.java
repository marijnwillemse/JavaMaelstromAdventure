package turbulent.commands;

import turbulent.controller.GameSystem;

public abstract class BaseCommand {

  public abstract void execute(GameSystem gameSystem, String[] parameters);
}
