package jpyramid.commands;

import java.util.ArrayList;
import java.util.UUID;

import jpyramid.controller.GameSystem;
import jpyramid.entity.TransformComponent;
import jpyramid.math.Vector2D;

public class LookCommand extends BaseCommand {

  @Override
  public void execute(GameSystem gameSystem, ArrayList<String> parameters) {
    
    // Get the player ID
    UUID playerID = gameSystem.getGameWorld().getPlayer().getID();
    // Get the player's location component
    TransformComponent t = gameSystem.getTransformComponents().get(playerID);
    // Get the position of the room he is in
    Vector2D location = t.getLocation().getPosition();
    System.out.println("You are at position {" + location.getX() + "; " +
        location.getY() + "}.");
  }

}
