package maelstrom.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;

public class EnemyInfoParser {

  public List<EnemyInfo> readEnemyInfoStream(InputStream in)
      throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
    try {
      return readEnemyArray(reader);
    } finally {
      reader.close();
    }
  }

  private List<EnemyInfo> readEnemyArray(JsonReader reader)
      throws IOException {
    List<EnemyInfo> enemyInfoList = new ArrayList<EnemyInfo>();

    reader.beginArray();
    while (reader.hasNext()) {
      enemyInfoList.add(readEnemy(reader));
    }
    reader.endArray();
    return enemyInfoList;
  }

  private EnemyInfo readEnemy(JsonReader reader) throws IOException {
    String name = "";
    int stamina, strength, defense, agility;
    stamina = strength = defense = agility = 0;

    reader.beginObject();
    while (reader.hasNext()) {
      String readerName = reader.nextName();
      if (readerName.equals("name")) {
        name = reader.nextString().toUpperCase();
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
