package maelstrom.commands;

import java.util.List;

import maelstrom.controller.GameSystem;

public abstract class BaseCommand {

  public abstract void execute(GameSystem gameSystem, List<String> words);
}
