package maelstrom.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;

public class GameInfoParser {

  public <T> List<T> readInfoStream(Class<T> infoClass, InputStream in)
      throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
    try {
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
      if (infoClass.equals(EnemyInfo.class)) {
        infoList.add((T) readEnemyInfo(reader));
      } else if (infoClass.equals(EntityInfo.class)) {
        infoList.add((T) readEntityInfo(reader));
      }
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
  
  private EntityInfo readEntityInfo(JsonReader reader) throws IOException {
    String id = "";
    List<String> componentNames = null;

    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      if (name.equals("id")) {
        id = reader.nextString().toUpperCase();
      } else if (name.equals("components")) {
        componentNames = readEntityInfoComponents(reader);
      } else {
        reader.skipValue();
      }
    }
    reader.endObject();
    return new EntityInfo(id, componentNames);
  }

  private List<String> readEntityInfoComponents(JsonReader reader)
      throws IOException {
    List<String> components = new ArrayList<String>();

    reader.beginArray();
    while (reader.hasNext()) {
      reader.beginObject();
      while (reader.hasNext()) {
        String name = reader.nextName();
        if (name.equals("componentName")) {
          components.add(reader.nextString());
        } else {
          reader.skipValue();
        }
      }
      reader.endObject();
    }
    reader.endArray();
    return components;
  }
}
