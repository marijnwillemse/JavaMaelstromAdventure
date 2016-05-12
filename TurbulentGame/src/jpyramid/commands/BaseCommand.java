package jpyramid.commands;

import jpyramid.controller.GameSystem;

public abstract class BaseCommand {

  public abstract void execute(GameSystem gameSystem, String[] parameters);
}
