package maelstrom.utilities;

public class TextEffects {

  public static String properCase(final String text) {
    return Character.toUpperCase(text.charAt(0))
        + text.substring(1).toLowerCase();
  }
}
