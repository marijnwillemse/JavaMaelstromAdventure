package maelstrom.commands;

import java.util.List;

import maelstrom.controller.GameSystem;

public class StopCommand extends BaseCommand {

  @Override
  public void execute(GameSystem gameSystem, List<String> words) {
    System.out.println("Goodbye");
    gameSystem.stopPlaying();
  }

}
