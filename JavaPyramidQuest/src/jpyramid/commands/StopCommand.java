package jpyramid.commands;

import java.util.ArrayList;

import jpyramid.controller.GameSystem;

public class StopCommand extends BaseCommand {

  @Override
  public void execute(GameSystem gameSystem, ArrayList<String> parameters) {
    System.out.println("Goodbye");
    gameSystem.stopPlaying();
  }

}
