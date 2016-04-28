package jpyramid.commands;

import java.util.ArrayList;

import jpyramid.controller.GameSystem;

public abstract class BaseCommand {

  public abstract void execute(GameSystem gameSystem, ArrayList<String> parameters);
}
