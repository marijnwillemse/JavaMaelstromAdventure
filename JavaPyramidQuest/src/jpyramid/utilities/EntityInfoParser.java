package jpyramid.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;

public class EntityInfoParser {

  public List<EntityInfo> readEntityInfoStream(InputStream in)
      throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
    try {
      return readEntityArray(reader);
    } finally {
      reader.close();
    }
  }

  public List<EntityInfo> readEntityArray(JsonReader reader)
      throws IOException {
    List<EntityInfo> messages = new ArrayList<EntityInfo>();

    reader.beginArray();
    while (reader.hasNext()) {
      messages.add(readEntity(reader));
    }
    reader.endArray();
    return messages;
  }

  public EntityInfo readEntity(JsonReader reader) throws IOException {
    String id = null;
    List<String> componentNames = null;

    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      if (name.equals("id")) {
        id = reader.nextString();
      } else if (name.equals("components")) {
        componentNames = readComponents(reader);
      } else {
        reader.skipValue();
      }
    }
    reader.endObject();
    return new EntityInfo(id, componentNames);
  }

  public List<String> readComponents(JsonReader reader) throws IOException {
    List<String> components = new ArrayList<String>();

    reader.beginArray();
    while (reader.hasNext()) {
      components.add(reader.nextString());
    }
    
    reader.endArray();
    return components;
  }
}
