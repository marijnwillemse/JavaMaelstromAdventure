package maelstrom.commands;

import maelstrom.controller.GameSystem;

public class StopCommand extends BaseCommand {

  @Override
  public void execute(GameSystem gameSystem, String[] words) {
    System.out.println("Goodbye");
    gameSystem.stopPlaying();
  }

}
