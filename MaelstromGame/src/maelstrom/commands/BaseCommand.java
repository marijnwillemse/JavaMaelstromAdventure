package maelstrom.commands;

import maelstrom.controller.GameSystem;

public abstract class BaseCommand {

  public abstract void execute(GameSystem gameSystem, String[] parameters);
}
