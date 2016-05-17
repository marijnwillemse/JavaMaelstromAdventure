package maelstrom.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;

public class CharacterInfoParser {

  public <T> List<T> readInfoStream(Class<T> infoClass, InputStream in)
      throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
    try {
//      if (infoClass.get)
      return (List<T>) readInfoArray(infoClass, reader);
      
    } finally {
      reader.close();
    }
  }

  @SuppressWarnings("unchecked")
  private <T> List<T> readInfoArray(Class<T> infoClass, JsonReader reader)
      throws IOException {
    List<T> infoList = new ArrayList<T>();

    reader.beginArray();
    while (reader.hasNext()) {
      infoList.add((T) readEnemyInfo(reader));
    }
    
    reader.endArray();
    return infoList;
  }

  private EnemyInfo readEnemyInfo(JsonReader reader) throws IOException {
    String name = "";
    int stamina, strength, defense, agility;
    stamina = strength = defense = agility = 0;

    reader.beginObject();
    while (reader.hasNext()) {
      String readerName = reader.nextName();
      if (readerName.equals("name")) {
        name = reader.nextString();
      } else if (readerName.equals("stamina")) {
        stamina = reader.nextInt();
      } else if (readerName.equals("strength")) {
        strength = reader.nextInt();
      } else if (readerName.equals("defense")) {
        defense = reader.nextInt();
      } else if (readerName.equals("agility")) {
        agility = reader.nextInt();
      } else {
        reader.skipValue();
      }
    }
    reader.endObject();
    return new EnemyInfo(name, stamina, strength, defense, agility);
  }
}
