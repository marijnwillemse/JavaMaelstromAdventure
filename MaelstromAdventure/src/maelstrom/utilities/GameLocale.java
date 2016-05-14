package maelstrom.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.stream.JsonReader;

public class GameLocale {

  private static HashMap<String, String> transcript =
      new HashMap<String, String>();
  
  // Disable instantiation
  private GameLocale() {}
  
  private static class LocaleParser {
    
    public HashMap<String, String> readLocaleStream(InputStream in)
        throws IOException {
      JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
      try {
        return readLocaleArray(reader);
      } finally {
        reader.close();
      }
    }
    
    private HashMap<String, String> readLocaleArray(JsonReader reader)
        throws IOException {
      HashMap<String, String> localeMap = new HashMap<String, String>();

      reader.beginObject();
      reader.nextName();
      reader.beginObject();
      reader.nextName();
      reader.beginArray();
      while (reader.hasNext()) {
        reader.beginObject();
        String tag, text;
        tag = text = "";
        while (reader.hasNext()) {
          String name = reader.nextName();
          if (name.equals("tag")) {
            tag = reader.nextString().toUpperCase();
          } else if (name.equals("text")) {
            text = reader.nextString();
          } else {
            reader.skipValue();
          }
        }
        reader.endObject();
      
        if (tag.isEmpty() || text.isEmpty()) {
          throw new IOException("Unable to parse object from locale at "
              + reader.getPath());
        } else {
          localeMap.put(tag, text);
        }
      }
      reader.endArray();
      reader.endObject();
      reader.endObject();
      return localeMap;
    }

  }
  
  public static void load(String language) throws IOException {
    /* Load transcript data from locale JSON files */
    LocaleParser parser = new LocaleParser();

    InputStream in;
    
    String localeURL = "src/maelstrom/assets/text/" + language;
    
    ArrayList<Path> localePaths = getLocaleFilePaths(localeURL);
    
    for (Path myPath : localePaths) {
      try {
        in = new FileInputStream(myPath.toString());
        // Use the parser object to read language texts
        HashMap<String, String> result = parser.readLocaleStream(in);
  
        // Put all new information into the transcript
        transcript.putAll(result);
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Unable to read locale from file "
            + myPath.toString());
      }
    }
  }

  private static ArrayList<Path> getLocaleFilePaths(String localeURL)
      throws IOException {
    ArrayList<Path> paths = new ArrayList<Path>();
    Files.walk(Paths.get(localeURL)).forEach(filePath -> {
      if (Files.isRegularFile(filePath)) {
          paths.add(filePath);
      }
    });
    return paths;
  }

  public static String getString(String string) {
    return transcript.get(string);
  }
  
}
